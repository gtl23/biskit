package com.project.biskit.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.biskit.utils.Status;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class OrderItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;

    @NotNull
    @JsonProperty("order_id")
    private Long orderId;

    @NotNull
    @JsonProperty("item_id")
    private Long itemId;

    @JsonProperty("count")
    private Long count;

    @JsonProperty("amount")
    private Double amount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @JsonProperty("item_status")
    private Status itemStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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

    public Status getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(Status itemStatus) {
        this.itemStatus = itemStatus;
    }

    public OrderItems(@NotNull Long orderId, @NotNull Long itemId, Long count, Double amount, @NotNull Status itemStatus) {
        this.orderId = orderId;
        this.itemId = itemId;
        this.count = count;
        this.amount = amount;
        this.itemStatus = itemStatus;
    }

    public OrderItems() {
    }
}
