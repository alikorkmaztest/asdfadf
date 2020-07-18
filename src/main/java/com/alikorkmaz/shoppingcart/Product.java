package com.alikorkmaz.shoppingcart;

import java.util.Objects;

public class Product {

    private final String title;
    private final double price;
    private final Category category;

    public Product(String title, double price, Category category) {
        this.title = title;
        this.price = price;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }

    public boolean categoryEquals(Category category) {
        return this.category.equals(category);
    }

    @Override
    public String toString() {
        return title + "($" + price + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(getTitle(), product.getTitle()) &&
                Objects.equals(getPrice(), product.getPrice()) &&
                Objects.equals(getCategory(), product.getCategory());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getPrice(), getCategory());
    }
}
