package com.alikorkmaz.shoppingcart;

import java.util.Objects;

public class Category {

    private final String title;
    private Category parentCategory;

    public Category(String title) {
        this.title = title;
    }

    public Category(String title, Category parentCategory) {
        this.title = title;
        this.parentCategory = parentCategory;
    }

    @Override
    public String toString() {
        return "Category: " + title + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(title, category.title) &&
                Objects.equals(parentCategory, category.parentCategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, parentCategory);
    }
}
