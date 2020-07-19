package com.alikorkmaz.shoppingcart.coupon;

import com.alikorkmaz.shoppingcart.Cart;

public class RateCoupon implements Coupon {

    private final double minimumCartAmount;
    private final double discountRate;

    public RateCoupon(double minimumCartAmount, double discountRate) {
        this.minimumCartAmount = minimumCartAmount;
        this.discountRate = discountRate;
    }

    @Override
    public boolean isApplicableFor(Cart cart) {
        return cart.getTotalAmountAfterCampaignDiscount() >= minimumCartAmount;
    }

    @Override
    public double getDiscountAmountFor(double cartAmount) {
        return cartAmount * discountRate * 0.01;
    }
}
