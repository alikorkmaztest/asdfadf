package com.alikorkmaz.shoppingcart.coupon;

import com.alikorkmaz.shoppingcart.Cart;

public interface Coupon {

    boolean isApplicableFor(Cart cart);

    double getDiscountAmountFor(double cartAmount);
}
