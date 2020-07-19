package com.alikorkmaz.shoppingcart;

import com.alikorkmaz.shoppingcart.exception.InvalidParamException;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public class Category {

    private final String title;
    private Category parentCategory;

    public Category(String title) {
        this.title = title;
        validate();
    }

    public Category(String title, Category parentCategory) {
        this.title = title;
        this.parentCategory = parentCategory;
        validate();
    }

    private void validate() {
        if (StringUtils.isBlank(title)) {
            throw new InvalidParamException("title");
        }
    }

    public String getTitle() {
        return title;
    }

    public Category getParentCategory() {
        return parentCategory;
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
        return Objects.equals(getTitle(), category.getTitle()) &&
                Objects.equals(getParentCategory(), category.getParentCategory());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getParentCategory());
    }
}
