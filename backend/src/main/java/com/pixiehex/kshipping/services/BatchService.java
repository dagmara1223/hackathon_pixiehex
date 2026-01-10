package com.pixiehex.kshipping.services;

import com.pixiehex.kshipping.model.GroupOrder;
import com.pixiehex.kshipping.model.SingleOrder;
import com.pixiehex.kshipping.model.SingleOrder.OrderStatus;
import com.pixiehex.kshipping.repository.GroupOrderRepository;
import com.pixiehex.kshipping.repository.SingleOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BatchService {

    private final SingleOrderRepository singleOrderRepository;
    private final GroupOrderRepository groupOrderRepository;

    private static final double MAX_BOX_WEIGHT = 10000.0;
    private static final double MIN_VIABLE_WEIGHT = 1000.0;

    private static final int MIN_PEOPLE_COUNT = 5;

    private static final double VAT_RATE = 1.23; // 23% VAT

    public BatchService(SingleOrderRepository singleOrderRepository, GroupOrderRepository groupOrderRepository) {
        this.singleOrderRepository = singleOrderRepository;
        this.groupOrderRepository = groupOrderRepository;
    }

    @Transactional
    public String processBatching() {
        // 1. Pobierz wszystkie otwarte zamówienia
        List<SingleOrder> openOrders = singleOrderRepository.findByStatus(OrderStatus.OPEN);

        if (openOrders.isEmpty()) {
            return "Brak otwartych zamówień do spakowania.";
        }

        int batchesCreated = 0;
        int ordersRolledOver = 0;

        GroupOrder currentBatch = new GroupOrder("Batch #" + System.currentTimeMillis());

        for (SingleOrder order : openOrders) {
            double orderWeight = order.getProductWeight();

            if (currentBatch.getTotalWeight() + orderWeight > MAX_BOX_WEIGHT) {

                if (finalizeBatchIfViable(currentBatch)) {
                    batchesCreated++;
                } else {
                    ordersRolledOver += currentBatch.getOrders().size();
                    for(SingleOrder so : currentBatch.getOrders()) {
                        so.setGroupOrder(null);
                    }
                }

                currentBatch = new GroupOrder("Batch #" + System.currentTimeMillis());
            }

            currentBatch.addOrder(order);
        }

        if (finalizeBatchIfViable(currentBatch)) {
            batchesCreated++;
        } else {
            ordersRolledOver += currentBatch.getOrders().size();
            for(SingleOrder so : currentBatch.getOrders()) {
                so.setGroupOrder(null);
            }
        }

        return String.format("Cykl zakończony. Utworzono paczek: %d. Przeniesiono na kolejny cykl: %d zamówień.",
                batchesCreated, ordersRolledOver);
    }

    private boolean finalizeBatchIfViable(GroupOrder batch) {
        if (batch.getOrders().isEmpty()) return false;

        // Liczymy unikalnych klientów (maile)
        long uniqueUsersCount = batch.getOrders().stream()
                .map(SingleOrder::getUserEmail)
                .distinct()
                .count();

        boolean weightOk = batch.getTotalWeight() >= MIN_VIABLE_WEIGHT;
        boolean peopleOk = uniqueUsersCount >= MIN_PEOPLE_COUNT;

        if (weightOk && peopleOk) {
            // A. Pobieramy koszt wysyłki dla całej paczki
            double totalShippingCost = calculateShippingCostFromTable(batch.getTotalWeight());

            // B. Dzielimy ten koszt sprawiedliwie na ludzi
            distributeCosts(batch, totalShippingCost);

            // C. Zamykamy paczkę
            batch.setStatus(GroupOrder.GroupStatus.READY_TO_SHIP);
            groupOrderRepository.save(batch); // Zapis do bazy
            return true;
        }

        return false;
    }

    /**
     * TWOJA TABELA CENOWA
     * Logika: Im cięższa paczka, tym taniej za kilogram.
     */
    private double calculateShippingCostFromTable(double weightInGrams) {
        double weightInKg = weightInGrams / 1000.0;

        if (weightInKg <= 1.0) return 300.0;
        if (weightInKg <= 2.0) return 360.0;
        if (weightInKg <= 3.0) return 440.0;
        if (weightInKg <= 4.0) return 520.0;
        if (weightInKg <= 5.0) return 600.0;
        if (weightInKg <= 6.0) return 660.0;

        // Skok cenowy: paczki 6-10kg kosztują ryczałtowo 900 zł
        if (weightInKg <= 10.0) return 900.0;

        // Powyżej 10kg (teoretycznie niemożliwe przez MAX_BOX_WEIGHT, ale bezpiecznik)
        return 1500.0;
    }

    /**
     * Algorytm "Robin Hood" - bogatsi (cięższe paczki) płacą proporcjonalnie więcej,
     * ale i tak wszyscy zyskują względem wysyłki indywidualnej.
     */
    private void distributeCosts(GroupOrder batch, double totalShippingCost) {
        double batchTotalWeight = batch.getTotalWeight();

        for (SingleOrder order : batch.getOrders()) {
            // 1. Jaki % wagi całej paczki zajmuje to zamówienie?
            double weightShare = order.getProductWeight() / batchTotalWeight;

            // 2. Wyliczamy opłatę za wysyłkę dla tego konkretnego zamówienia
            double shippingForThisOrder = totalShippingCost * weightShare;

            // 3. Dodajemy cenę produktu i VAT
            double basePrice = order.getOriginalPrice();
            double totalWithTax = (basePrice + shippingForThisOrder) * VAT_RATE;

            // 4. Ile dopłaty zostało (Cena końcowa - Zaliczka)
            double remaining = totalWithTax - order.getDepositAmount();

            // 5. Aktualizujemy zamówienie w bazie
            order.setFinalPrice(Math.round(totalWithTax * 100.0) / 100.0);   // Zaokrąglenie do groszy
            order.setRemainingToPay(Math.round(remaining * 100.0) / 100.0);
            order.setStatus(OrderStatus.LOCKED); // Blokujemy możliwość anulowania
        }
    }
}