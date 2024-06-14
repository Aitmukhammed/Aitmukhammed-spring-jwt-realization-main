package com.example.demoauth.details;

public class CartDetails {

    private Long userId;
    private String username;
    private String email;
    private Long productId;
    private String productName;
    private String pictureUrl;
    private double price;
    private int quantity;

    public CartDetails() {}

    public CartDetails(Long userId, String username, String email, Long productId, String productName, String pictureUrl, double price, int quantity) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.productId = productId;
        this.productName = productName;
        this.pictureUrl = pictureUrl;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
