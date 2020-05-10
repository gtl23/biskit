package com.project.biskit.repository;

import com.project.biskit.entity.Items;
import com.project.biskit.model.StockCountProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Items, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM items i where i.id IN ?1")
    List<Items> getStockCount(List<Long> itemIds);
}
