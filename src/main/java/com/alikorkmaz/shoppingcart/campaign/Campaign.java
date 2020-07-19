package com.alikorkmaz.shoppingcart.campaign;

import com.alikorkmaz.shoppingcart.Cart;
import com.alikorkmaz.shoppingcart.Category;

public interface Campaign {

    boolean isApplicableFor(Cart cart);

    double getDiscountAmount(double cartAmount);

    Category getCategory();
}
