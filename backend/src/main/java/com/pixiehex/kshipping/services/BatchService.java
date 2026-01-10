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

    private static final double MAX_BOX_WEIGHT = 20000.0;
    private static final double MIN_VIABLE_WEIGHT = 1000.0;
    private static final int MIN_PEOPLE_COUNT = 5;

    public BatchService(SingleOrderRepository singleOrderRepository, GroupOrderRepository groupOrderRepository) {
        this.singleOrderRepository = singleOrderRepository;
        this.groupOrderRepository = groupOrderRepository;
    }

    @Transactional
    public String processBatching() {
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

        return String.format("Cykl zakończony. Utworzono paczek: %d. Przeniesiono na kolejny cykl (niespełnione warunki): %d zamówień.",
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
            batch.setStatus(GroupOrder.GroupStatus.READY_TO_SHIP);

            for (SingleOrder order : batch.getOrders()) {
                order.setStatus(OrderStatus.LOCKED);
            }

            groupOrderRepository.save(batch);
            return true;
        }

        return false;
    }
}