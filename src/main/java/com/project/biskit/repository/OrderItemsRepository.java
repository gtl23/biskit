package com.project.biskit.repository;

import com.project.biskit.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems, Long> {
    Optional<OrderItems> findOneByItemId(Long id);
}
