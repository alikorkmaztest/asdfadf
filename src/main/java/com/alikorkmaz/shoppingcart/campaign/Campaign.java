package com.alikorkmaz.shoppingcart.campaign;

import com.alikorkmaz.shoppingcart.Category;
import com.alikorkmaz.shoppingcart.enumtype.DiscountType;

public class Campaign {

    private final double discountAmount;
    private final int minimumItemQuantity;
    private final DiscountType discountType;
    private final Category category;

    public Campaign(double discountAmount, int minimumItemQuantity, DiscountType discountType, Category category) {
        this.discountAmount = discountAmount;
        this.minimumItemQuantity = minimumItemQuantity;
        this.discountType = discountType;
        this.category = category;
    }

    public boolean isApplicable(int itemQuantity) {
        return itemQuantity >= minimumItemQuantity;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public Category getCategory() {
        return category;
    }
}
