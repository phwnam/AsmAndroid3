package com.example.appbanhangandroid.model;

import com.google.gson.annotations.SerializedName;

public class Cart {
    @SerializedName("_id")
    private String id;
    private String productId;
    private String name;
    private Integer quantity;
    private Integer price;
    private String image;

    public Cart() {
    }

    public Cart(String productId, String name, Integer quantity, Integer price, String image) {
        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.image = image;
    }

    public Cart(String id, String productId, String name, Integer quantity, Integer price, String image) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
