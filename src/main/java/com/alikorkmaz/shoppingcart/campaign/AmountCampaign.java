package com.alikorkmaz.shoppingcart.campaign;

import com.alikorkmaz.shoppingcart.Cart;
import com.alikorkmaz.shoppingcart.Category;

public class AmountCampaign implements Campaign {

    private final double discountAmount;
    private final int minimumItemQuantity;
    private final Category category;

    public AmountCampaign(double discountAmount, int minimumItemQuantity, Category category) {
        this.discountAmount = discountAmount;
        this.minimumItemQuantity = minimumItemQuantity;
        this.category = category;
    }

    @Override
    public boolean isApplicableFor(Cart cart) {
        return cart.getTotalItemQuantityByCategory(category) >= minimumItemQuantity;
    }

    @Override
    public double getDiscountAmount(double cartAmount) {
        return discountAmount;
    }

    @Override
    public Category getCategory() {
        return category;
    }
}
