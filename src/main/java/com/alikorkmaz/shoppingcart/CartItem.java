package com.alikorkmaz.shoppingcart;

import com.alikorkmaz.shoppingcart.exception.InvalidParamException;

public class CartItem {

    private final int quantity;
    private final Product product;

    public CartItem(int quantity, Product product) {
        this.quantity = quantity;
        this.product = product;
        validate();
    }

    private void validate() {
        if (quantity < 0) {
            throw new InvalidParamException("quantity");
        }
    }

    public double getTotalAmount() {
        return quantity * product.getPrice();
    }

    public Category getProductCategory() {
        return product.getCategory();
    }

    public boolean productCategoryEquals(Category category) {
        return product.categoryEquals(category);
    }

    public int getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return product;
    }

    @Override
    public String toString() {
        return String.format("%d pieces of %s for $%s\n", quantity, product, getTotalAmount());
    }
}
