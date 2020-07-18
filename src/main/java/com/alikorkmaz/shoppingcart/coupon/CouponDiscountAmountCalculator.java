package com.alikorkmaz.shoppingcart.coupon;

import com.alikorkmaz.shoppingcart.enumtype.DiscountType;

public class CouponDiscountAmountCalculator {

    public double calculate(double totalAmount, Coupon coupon) {
        if (coupon.getDiscountType().equals(DiscountType.RATE)) {
            return totalAmount * (coupon.getDiscountAmount() / 100);
        }
        return coupon.getDiscountAmount();
    }
}
