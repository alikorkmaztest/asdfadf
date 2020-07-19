package com.alikorkmaz.shoppingcart;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class ProductTest {

    @Test
    public void should_validate() {
        //given
        Category category = new Category("Food");

        //when
        Throwable throwable = catchThrowable(() -> new Product("Apple", 10.0, category));

        //then
        assertThat(throwable).isNull();
    }

    @Test
    public void should_throw_exception_when_price_is_not_valid() {
        //given
        Category category = new Category("Food");

        //when
        Throwable throwable = catchThrowable(() -> new Product("Apple", -1, category));

        //then
        assertThat(throwable).isNotNull();
        assertThat(throwable).hasMessage("invalid parameter for price");
    }

    @Test
    public void should_return_true_when_category_is_equal() {
        //given
        Category category = new Category("Food");
        Product product = new Product("Apple", 10.0, category);

        //when
        boolean val = product.categoryEquals(new Category("Food"));

        //then
        assertThat(val).isEqualTo(true);
    }

    @Test
    public void should_return_false_when_category_is_not_equal() {
        //given
        Category category = new Category("Food");
        Product product = new Product("Apple", 10.0, category);

        //when
        boolean val = product.categoryEquals(new Category("Dessert"));

        //then
        assertThat(val).isEqualTo(false);
    }
}