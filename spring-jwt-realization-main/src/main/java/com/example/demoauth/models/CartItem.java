//package com.example.demoauth.models;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "cart_items")
//public class CartItem {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "product_id")
//    private Product product;
//
//    private int quantity;
//
//    @OneToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    public CartItem() {}
//
//    public CartItem(Product product, int quantity, User user) {
//        this.product = product;
//        this.quantity = quantity;
//        this.user = user;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Product getProduct() {
//        return product;
//    }
//
//    public void setProduct(Product product) {
//        this.product = product;
//    }
//
//    public int getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(int quantity) {
//        this.quantity = quantity;
//    }
//
//}
