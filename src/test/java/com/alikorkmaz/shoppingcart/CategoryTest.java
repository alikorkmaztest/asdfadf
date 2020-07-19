package com.alikorkmaz.shoppingcart;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class CategoryTest {

    @Test
    public void should_validate_without_parent_category() {
        //when
        Throwable throwable = catchThrowable(() -> new Category("Food"));

        //then
        assertThat(throwable).isNull();
    }

    @Test
    public void should_validate_with_parent_category() {
        //given
        Category food = new Category("Food");

        //when
        Throwable throwable = catchThrowable(() -> new Category("Vegetable", food));

        //then
        assertThat(throwable).isNull();
    }

    @Test
    public void should_throw_exception_when_title_is_not_valid() {
        //when
        Throwable throwable = catchThrowable(() -> new Category(""));

        //then
        assertThat(throwable).isNotNull();
        assertThat(throwable).hasMessage("invalid parameter for title");
    }
}