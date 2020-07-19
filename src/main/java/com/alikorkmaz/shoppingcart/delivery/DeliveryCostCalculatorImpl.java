package com.alikorkmaz.shoppingcart.delivery;

import com.alikorkmaz.shoppingcart.Cart;

public class DeliveryCostCalculatorImpl implements DeliveryCostCalculator {

    private final double fixedCost;
    private final double costPerDelivery;
    private final double costPerProduct;

    public DeliveryCostCalculatorImpl(double fixedCost, double costPerDelivery, double costPerProduct) {
        this.fixedCost = fixedCost;
        this.costPerDelivery = costPerDelivery;
        this.costPerProduct = costPerProduct;
    }

    @Override
    public double calculateFor(Cart cart) {
        return cart.getNumberOfDistinctCategories() * costPerDelivery +
                cart.getNumberOfDifferentProducts() * costPerProduct + fixedCost;
    }
}
