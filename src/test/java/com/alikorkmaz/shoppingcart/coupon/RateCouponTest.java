package com.alikorkmaz.shoppingcart.coupon;

import com.alikorkmaz.shoppingcart.Cart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RateCouponTest {

    private Coupon rateCoupon;

    @Mock
    private Cart cart;

    @BeforeEach
    public void setUp() {
        rateCoupon = new RateCoupon(100, 10);
    }

    @Test
    public void should_return_true_when_coupon_minimum_cart_amount_less_than_cart_amount() {
        //given
        when(cart.getTotalAmountAfterCampaignDiscount()).thenReturn(150.0);

        //when
        boolean val = rateCoupon.isApplicableFor(cart);

        //then
        assertThat(val).isTrue();
    }


    @Test
    public void should_return_false_when_cart_amount_less_than_coupon_minimum_cart_amount() {
        //given
        when(cart.getTotalAmountAfterCampaignDiscount()).thenReturn(50.0);

        //when
        boolean val = rateCoupon.isApplicableFor(cart);

        //then
        assertThat(val).isFalse();
    }
}