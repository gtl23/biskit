package com.project.biskit.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddItemRequest {

    @JsonProperty("name")
    private String name;

    @JsonProperty("item_price")
    private Double itemPrice;

    @JsonProperty("stock_count")
    private Long stockCount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Long getStockCount() {
        return stockCount;
    }

    public void setStockCount(Long stockCount) {
        this.stockCount = stockCount;
    }
}
