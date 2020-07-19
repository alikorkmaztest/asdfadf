package com.alikorkmaz.shoppingcart;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class CartItemTest {

    @Test
    public void should_validate() {
        //given
        Category category = new Category("Food");
        Product product = new Product("Apple", 10.0, category);

        //when
        Throwable throwable = catchThrowable(() -> new CartItem(2, product));

        //then
        assertThat(throwable).isNull();
    }

    @Test
    public void should_throw_exception_when_quantity_is_not_valid() {
        //given
        Category category = new Category("Food");
        Product product = new Product("Apple", 10.0, category);

        //when
        Throwable throwable = catchThrowable(() -> new CartItem(-1, product));

        //then
        assertThat(throwable).isNotNull();
        assertThat(throwable).hasMessage("invalid parameter for quantity");
    }

    @Test
    void should_return_total_amount() {
        //given
        Category category = new Category("Food");
        Product product = new Product("Apple", 10.0, category);
        CartItem cartItem = new CartItem(2, product);

        //when
        double totalAmount = cartItem.getTotalAmount();

        //then
        assertThat(totalAmount).isEqualTo(20);
    }

    @Test
    public void should_return_true_when_product_category_is_equal() {
        //given
        Category category = new Category("Food");
        Product product = new Product("Apple", 10.0, category);
        CartItem cartItem = new CartItem(2, product);

        //when
        boolean val = cartItem.productCategoryEquals(new Category("Food"));

        //then
        assertThat(val).isTrue();
    }

    @Test
    public void should_return_false_when_product_category_is_not_equal() {
        //given
        Category category = new Category("Food");
        Product product = new Product("Apple", 10.0, category);
        CartItem cartItem = new CartItem(2, product);

        //when
        boolean val = cartItem.productCategoryEquals(new Category("Dessert"));

        //then
        assertThat(val).isFalse();
    }
}