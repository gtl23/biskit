package com.project.biskit.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;

@Entity
public class Items {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("item_price")
    @Column(columnDefinition = "double default 0.0")
    private Double itemPrice;

    @JsonProperty("stock_count")
    @Column(columnDefinition = "integer default 0")
    private Long stockCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Items(Long id, String name, Double itemPrice, Long stockCount) {
        this.id = id;
        this.name = name;
        this.itemPrice = itemPrice;
        this.stockCount = stockCount;
    }

    public Items(String name, Double itemPrice, Long stockCount) {
        this.name = name;
        this.itemPrice = itemPrice;
        this.stockCount = stockCount;
    }

    public Items(Long id, String name, Double itemPrice) {
        this.id = id;
        this.name = name;
        this.itemPrice = itemPrice;
    }

    public Items() {}
}
