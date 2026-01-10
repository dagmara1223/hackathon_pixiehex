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

    private final PdfGeneratorService pdfGeneratorService;

    private static final double MAX_BOX_WEIGHT = 10000.0;
    private static final double MIN_VIABLE_WEIGHT = 1000.0;

    private static final int MIN_PEOPLE_COUNT = 2;

    private static final double VAT_RATE = 1.23;

    public BatchService(SingleOrderRepository singleOrderRepository,
                        GroupOrderRepository groupOrderRepository,
                        PdfGeneratorService pdfGeneratorService) {
        this.singleOrderRepository = singleOrderRepository;
        this.groupOrderRepository = groupOrderRepository;
        this.pdfGeneratorService = pdfGeneratorService;
    }

    @Transactional
    public String processBatching() {
        List<SingleOrder> openOrders = singleOrderRepository.findByStatus(OrderStatus.OPEN);

        if (openOrders.isEmpty()) {
            return "Brak otwartych zamówień do spakowania.";
        }

        int batchesCreated = 0;
        int ordersRolledOver = 0;

        GroupOrder currentBatch = new GroupOrder("Batch " + java.time.LocalDate.now() + " #" + System.currentTimeMillis() % 1000);

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
                currentBatch = new GroupOrder("Batch " + java.time.LocalDate.now() + " #" + System.currentTimeMillis() % 1000);
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

        long uniqueUsersCount = batch.getOrders().stream()
                .map(SingleOrder::getUserEmail)
                .distinct()
                .count();

        boolean weightOk = batch.getTotalWeight() >= MIN_VIABLE_WEIGHT;
        boolean peopleOk = uniqueUsersCount >= MIN_PEOPLE_COUNT;

        if (weightOk && peopleOk) {
            double totalShippingCost = calculateShippingCostFromTable(batch.getTotalWeight());
            distributeCosts(batch, totalShippingCost);

            batch.setStatus(GroupOrder.GroupStatus.READY_TO_SHIP);
            groupOrderRepository.save(batch);


            pdfGeneratorService.generateVendorPdf(batch);
            pdfGeneratorService.generateInternalSummaryPdf(batch);

            return true;
        }

        return false;
    }

    private double calculateShippingCostFromTable(double weightInGrams) {
        double weightInKg = weightInGrams / 1000.0;

        if (weightInKg <= 1.0) return 300.0;
        if (weightInKg <= 2.0) return 360.0;
        if (weightInKg <= 3.0) return 440.0;
        if (weightInKg <= 4.0) return 520.0;
        if (weightInKg <= 5.0) return 600.0;
        if (weightInKg <= 6.0) return 660.0;
        if (weightInKg <= 7.0) return 720.0;
        if (weightInKg <= 8.0) return 770.0;
        if (weightInKg <= 9.0) return 820.0;
        if (weightInKg <= 10.0) return 860.0;

        return 1000.0;
    }

    private void distributeCosts(GroupOrder batch, double totalShippingCost) {
        double batchTotalWeight = batch.getTotalWeight();

        for (SingleOrder order : batch.getOrders()) {
            double weightShare = order.getProductWeight() / batchTotalWeight;
            double shippingForThisOrder = totalShippingCost * weightShare;

            double basePrice = order.getOriginalPrice();
            double totalWithTax = (basePrice + shippingForThisOrder) * VAT_RATE;
            double remaining = totalWithTax - order.getDepositAmount();

            order.setFinalPrice(Math.round(totalWithTax * 100.0) / 100.0);
            order.setRemainingToPay(Math.round(remaining * 100.0) / 100.0);
            order.setStatus(OrderStatus.LOCKED);
        }
    }
}