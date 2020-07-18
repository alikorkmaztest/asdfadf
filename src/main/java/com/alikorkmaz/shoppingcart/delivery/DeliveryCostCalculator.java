package com.alikorkmaz.shoppingcart.delivery;

import com.alikorkmaz.shoppingcart.Cart;

public interface DeliveryCostCalculator {

    double calculateFor(Cart cart);
}
