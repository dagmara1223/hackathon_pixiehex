package com.pixiehex.kshipping.services;

import com.pixiehex.kshipping.model.GroupOrder;
import com.pixiehex.kshipping.repository.GroupOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupOrderService {
       private GroupOrderRepository groupOrderRepository;

       public List<GroupOrder> findAllGroups(){
           return groupOrderRepository.findAll();
       }

    public GroupOrder findGroupById(long id){
        return groupOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("GroupOrder not found with id " + id));
    }

       public GroupOrder saveGroup(GroupOrder groupOrder) {
           return groupOrderRepository.save(groupOrder);
       }

        public void deleteGroupById(long id) {
            groupOrderRepository.deleteById(id);
        }

        public GroupOrder updateGroup(GroupOrder groupOrder) {
           return groupOrderRepository.save(groupOrder);
        }

}
