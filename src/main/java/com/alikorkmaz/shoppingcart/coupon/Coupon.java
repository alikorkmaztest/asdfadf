package com.alikorkmaz.shoppingcart.coupon;

import com.alikorkmaz.shoppingcart.enumtype.DiscountType;

public class Coupon {

    private final double discountAmount;
    private final double minimumCartAmount;
    private final DiscountType discountType;

    public Coupon(double discountAmount, double minimumCartAmount, DiscountType discountType) {
        this.discountAmount = discountAmount;
        this.minimumCartAmount = minimumCartAmount;
        this.discountType = discountType;
    }

    public boolean isApplicable(double cartAmount) {
        return cartAmount >= minimumCartAmount;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }
}
