package com.project.biskit.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.biskit.entity.Items;
import com.project.biskit.entity.Orders;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AllItemsResponse {

    @JsonProperty("all_items")
    List<Items> allItems;

    @JsonProperty("total_elements")
    Long totalElements;

    @JsonProperty("all_orders")
    List<Orders> allOrders;

    public List<Orders> getAllOrders() {
        return allOrders;
    }

    public void setAllOrders(List<Orders> allOrders) {
        this.allOrders = allOrders;
    }

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

    public AllItemsResponse(Long totalElements, List<Orders> allOrders) {
        this.allOrders = allOrders;
        this.totalElements = totalElements;
    }
}
