package com.project.biskit.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.biskit.utils.Status;

public class OrderDetailItems {

    @JsonProperty("item_status")
    private Status itemStatus;

    @JsonProperty("order_item_id")
    private Long orderItemId;

    @JsonProperty("item_id")
    private Long itemId;

    @JsonProperty("count")
    private Long count;

   @JsonProperty("amount")
   private Double amount;

   @JsonProperty("name")
   private String name;

    public Status getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(Status itemStatus) {
        this.itemStatus = itemStatus;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OrderDetailItems() {
    }
}
