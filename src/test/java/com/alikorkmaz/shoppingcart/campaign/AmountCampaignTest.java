package com.alikorkmaz.shoppingcart.campaign;

import com.alikorkmaz.shoppingcart.Cart;
import com.alikorkmaz.shoppingcart.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AmountCampaignTest {

    private Campaign amountCampaign;

    @Mock
    private Cart cart;

    @BeforeEach
    public void setUp() {
        Category food = new Category("Food");
        amountCampaign = new AmountCampaign(100, 5, food);
    }

    @Test
    public void should_return_true_when_campaign_minimum_item_quantity_less_than_total_item_quantity_by_category() {
        //given
        Category category = new Category("Food");
        when(cart.getTotalItemQuantityByCategory(category)).thenReturn(10);

        //when
        boolean val = amountCampaign.isApplicableFor(cart);

        //then
        assertThat(val).isTrue();
    }


    @Test
    public void should_return_false_when_total_item_quantity_by_category_less_than_campaign_minimum_item_quantity() {
        //given
        Category category = new Category("Food");
        when(cart.getTotalItemQuantityByCategory(category)).thenReturn(3);

        //when
        boolean val = amountCampaign.isApplicableFor(cart);

        //then
        assertThat(val).isFalse();
    }

    @Test
    public void should_return_discount_amount() {
        //given
        double cartAmount = 300.0;

        //when
        double discountAmount = amountCampaign.getDiscountAmount(cartAmount);

        //then
        assertThat(discountAmount).isEqualTo(100);
    }
}