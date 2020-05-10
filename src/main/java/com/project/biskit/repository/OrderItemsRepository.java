package com.project.biskit.repository;

import com.project.biskit.entity.OrderItems;
import com.project.biskit.model.OrderDetailProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems, Long> {
    Optional<OrderItems> findOneByItemId(Long id);

    List<OrderItems> findByOrderId(Long orderId);

    @Query(nativeQuery = true, value = "SELECT * FROM order_items where order_id = ?1 and id <> ?2 AND item_status <> 'CANCELLED'")
    List<OrderItems> findRemainingOrderItems(Long orderId, Long orderItemId);

    @Query(nativeQuery = true, value = "SELECT " +
            " oi.item_status as itemStatus, " +
            " oi.id as orderItemId, " +
            " oi.item_id as itemId, " +
            " oi.count as count, " +
            " oi.amount as amount, " +
            " i.name as name " +
            " FROM " +
            " order_items oi " +
            " join items i on " +
            " oi.item_id = i.id " +
            " WHERE " +
            " oi.order_id = :id")
    List<OrderDetailProjection> findByOrderItems(Long id);
}
