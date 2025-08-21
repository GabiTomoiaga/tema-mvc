package com.example.temamvc.model;

public class Stock {
    private Integer stockId;
    private Integer storeId; // Poți șterge complet dacă nu mai e relevant
    private Integer perfumeId;
    private Integer quantity;

    public Stock(Integer stockId, Integer storeId, Integer perfumeId, Integer quantity) {
        this.stockId = stockId;
        this.storeId = storeId;
        this.perfumeId = perfumeId;
        this.quantity = quantity;
    }

    public Integer getStockId() {
        return stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getPerfumeId() {
        return perfumeId;
    }

    public void setPerfumeId(Integer perfumeId) {
        this.perfumeId = perfumeId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
