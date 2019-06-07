package com.apiit.eeashopingandroid.package_cart;

import com.apiit.eeashopingandroid.package_product.Product;

import java.io.Serializable;

public class CartItem implements Serializable {
    private String cid;
    private int amount;
    private String uid;
    private Product product;


    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void addToCart() {

    }
}
