package com.alikorkmaz.shoppingcart.campaign;

import com.alikorkmaz.shoppingcart.Cart;
import com.alikorkmaz.shoppingcart.Category;

public class RateCampaign implements Campaign {

    private final double discountRate;
    private final int minimumItemQuantity;
    private final Category category;

    public RateCampaign(double discountRate, int minimumItemQuantity, Category category) {
        this.discountRate = discountRate;
        this.minimumItemQuantity = minimumItemQuantity;
        this.category = category;
    }

    @Override
    public boolean isApplicableFor(Cart cart) {
        return cart.getTotalItemQuantityByCategory(category) >= minimumItemQuantity;
    }

    @Override
    public double getDiscountAmount(double cartAmount) {
        return cartAmount * discountRate * 0.01;
    }

    @Override
    public Category getCategory() {
        return category;
    }
}
