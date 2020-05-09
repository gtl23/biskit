package com.project.biskit.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.biskit.entity.Items;
import java.util.List;

public class AllItemsResponse {

    @JsonProperty("all_items")
    List<Items> allItems;

    @JsonProperty("total_elements")
    Long totalElements;

    public List<Items> getAllItems() {
        return allItems;
    }

    public void setAllItems(List<Items> allItems) {
        this.allItems = allItems;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public AllItemsResponse(List<Items> allItems, Long totalElements) {
        this.allItems = allItems;
        this.totalElements = totalElements;
    }
}
