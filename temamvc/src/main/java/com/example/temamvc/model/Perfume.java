package com.example.temamvc.model;

public class Perfume {
    private Integer id;
    private String name;
    private String brand;
    private Double price;
    private String description;
    private Perfume perfume;

    public Perfume(Integer id, String name, String brand, Double price, String description) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.description = description;
    }

    public Perfume (Perfume perfume) {
        this.perfume = perfume;
    }

    public Perfume() {}

    public void setId(Integer id) {this.id = id;}

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
