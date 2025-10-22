package com.project.eCommerceBackend.api.model;

import com.project.eCommerceBackend.model.CartItem;

import java.util.List;

public class CartResponseBody {
    private Long userId;
    private List<CartItemBody> items;
    private double totalAmount;

    public CartResponseBody() {
    }

    // Parameterized constructor
    public CartResponseBody(Long userId, List<CartItemBody> items, double totalAmount) {
        this.userId = userId;
        this.items = items;
        this.totalAmount = totalAmount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<CartItemBody> getItems() {
        return items;
    }

    public void setItems(List<CartItemBody> items) {
        this.items = items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
