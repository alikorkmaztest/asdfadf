package com.alikorkmaz.shoppingcart.coupon;

import com.alikorkmaz.shoppingcart.Cart;

public class AmountCoupon implements Coupon {

    private final double minimumCartAmount;
    private final double discountAmount;

    public AmountCoupon(double minimumCartAmount, double discountAmount) {
        this.minimumCartAmount = minimumCartAmount;
        this.discountAmount = discountAmount;
    }

    @Override
    public boolean isApplicableFor(Cart cart) {
        return cart.getTotalAmountAfterCampaignDiscount() >= minimumCartAmount;
    }

    @Override
    public double getDiscountAmountFor(double cartAmount) {
        return discountAmount;
    }
}
