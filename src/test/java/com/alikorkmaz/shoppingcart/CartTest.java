package com.alikorkmaz.shoppingcart;

import com.alikorkmaz.shoppingcart.campaign.AmountCampaign;
import com.alikorkmaz.shoppingcart.campaign.Campaign;
import com.alikorkmaz.shoppingcart.campaign.RateCampaign;
import com.alikorkmaz.shoppingcart.campaign.picker.CampaignPicker;
import com.alikorkmaz.shoppingcart.coupon.AmountCoupon;
import com.alikorkmaz.shoppingcart.coupon.Coupon;
import com.alikorkmaz.shoppingcart.coupon.RateCoupon;
import com.alikorkmaz.shoppingcart.delivery.DeliveryCostCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartTest {

    private Cart cart;

    @Mock
    CampaignPicker campaignPicker;

    @Mock
    DeliveryCostCalculator deliveryCostCalculator;

    @BeforeEach
    public void setUp() {
        cart = new Cart(campaignPicker, deliveryCostCalculator);
    }

    @Test
    public void should_add_cart_item() {
        //given
        Category fruit = new Category("Fruits");
        Product apple = new Product("Apple", 50.00, fruit);

        //when
        Throwable throwable = catchThrowable(() -> cart.addCartItem(2, apple));

        //then
        assertThat(throwable).isNull();
    }

    @Test
    public void should_apply_campaigns() {
        //given
        Category food = new Category("Food");
        Product apple = new Product("Apple", 50.00, food);
        cart.addCartItem(20, apple);
        Campaign campaign1 = new RateCampaign(20.0, 3, food);
        Campaign campaign2 = new AmountCampaign(40.0, 3, food);

        when(campaignPicker.pick(Arrays.asList(campaign1, campaign2), 50.0 * 20.0)).thenReturn(Optional.of(campaign2));

        //when
        Throwable throwable = catchThrowable(() -> cart.applyCampaigns(campaign1, campaign2));

        //then
        assertThat(throwable).isNull();
    }

    @Test
    public void should_throw_exception_when_there_is_no_valid_campaign() {
        //given
        Category food = new Category("Food");
        Product apple = new Product("Apple", 50.00, food);
        cart.addCartItem(1, apple);
        Campaign campaign1 = new RateCampaign(20.0, 3, food);
        Campaign campaign2 = new AmountCampaign(40.0, 3, food);

        when(campaignPicker.pick(Collections.emptyList(), 50.0)).thenReturn(Optional.empty());

        //when
        Throwable throwable = catchThrowable(() -> cart.applyCampaigns(campaign1, campaign2));

        //then
        assertThat(throwable).isNotNull();
        assertThat(throwable).hasMessage("discount not applicable for campaign");
    }


    @Test
    public void should_apply_coupons() {
        //given
        Category food = new Category("Food");
        Product apple = new Product("Apple", 50.00, food);
        cart.addCartItem(2, apple);
        Coupon coupon = new RateCoupon(100, 10);

        //when
        Throwable throwable = catchThrowable(() -> cart.applyCoupon(coupon));

        //then
        assertThat(throwable).isNull();
    }

    @Test
    public void should_throw_exception_when_coupon_is_not_valid() {
        //given
        Category food = new Category("Food");
        Product apple = new Product("Apple", 50.00, food);
        cart.addCartItem(1, apple);
        Coupon coupon = new RateCoupon(200, 10);

        //when
        Throwable throwable = catchThrowable(() -> cart.applyCoupon(coupon));

        //then
        assertThat(throwable).isNotNull();
        assertThat(throwable).hasMessage("discount not applicable for coupon");
    }

    @Test
    public void should_return_number_of_different_products() {
        //given
        Category fruit = new Category("Fruits");
        Category vegetable = new Category("Vegetables");
        Product apple = new Product("Apple", 50.00, fruit);
        Product banana = new Product("Banana", 20.00, fruit);
        Product strawberry = new Product("Strawberry", 15.00, fruit);
        Product spinach = new Product("Spinach", 12, vegetable);
        cart.addCartItem(3, apple);
        cart.addCartItem(2, strawberry);
        cart.addCartItem(2, banana);
        cart.addCartItem(2, spinach);

        //when
        double numberOfDifferentProducts = cart.getNumberOfDifferentProducts();

        //then
        assertThat(numberOfDifferentProducts).isEqualTo(4);
    }

    @Test
    public void should_return_number_of_distinct_categories() {
        //given
        Category fruit = new Category("Fruits");
        Category vegetable = new Category("Vegetables");
        Product apple = new Product("Apple", 50.00, fruit);
        Product banana = new Product("Banana", 20.00, fruit);
        Product strawberry = new Product("Strawberry", 15.00, fruit);
        Product spinach = new Product("Spinach", 12, vegetable);
        cart.addCartItem(3, apple);
        cart.addCartItem(2, strawberry);
        cart.addCartItem(2, banana);
        cart.addCartItem(2, spinach);

        //when
        double numberOfDistinctCategories = cart.getNumberOfDistinctCategories();

        //then
        assertThat(numberOfDistinctCategories).isEqualTo(2);
    }

    @Test
    public void should_return_total_amount() {
        //given
        Category fruit = new Category("Fruits");
        Category vegetable = new Category("Vegetables");
        Product apple = new Product("Apple", 50.00, fruit);
        Product banana = new Product("Banana", 20.00, fruit);
        Product strawberry = new Product("Strawberry", 15.00, fruit);
        Product spinach = new Product("Spinach", 12, vegetable);
        cart.addCartItem(3, apple);
        cart.addCartItem(2, strawberry);
        cart.addCartItem(2, banana);
        cart.addCartItem(2, spinach);

        //when
        double numberOfDistinctCategories = cart.getTotalAmount();

        //then
        assertThat(numberOfDistinctCategories).isEqualTo(244);
    }

    @Test
    public void should_return_delivery_cost() {
        //given
        when(deliveryCostCalculator.calculateFor(cart)).thenReturn(10.0);

        //when
        double deliveryCost = cart.getDeliveryCost();

        //then
        assertThat(deliveryCost).isEqualTo(10);
    }

    @Test
    public void should_return_coupon_discount_when_coupon_applied() {
        //given
        Category fruit = new Category("Fruits");
        Product apple = new Product("Apple", 100.00, fruit);
        cart.addCartItem(3, apple);
        Coupon coupon = new AmountCoupon(100.0, 20.0);
        cart.applyCoupon(coupon);

        //when
        double couponDiscount = cart.getCouponDiscount();

        //then
        assertThat(couponDiscount).isEqualTo(20);
    }

    @Test
    public void should_return_coupon_discount_as_zero_when_there_is_no_applied_coupon() {
        //given
        Category fruit = new Category("Fruits");
        Product apple = new Product("Apple", 100.00, fruit);
        cart.addCartItem(3, apple);

        //when
        double couponDiscount = cart.getCouponDiscount();

        //then
        assertThat(couponDiscount).isEqualTo(0);
    }

    @Test
    public void should_return_campaign_discount_when_campaign_applied() {
        //given
        Category fruit = new Category("Fruits");
        Product apple = new Product("Apple", 100.00, fruit);
        cart.addCartItem(4, apple);
        Campaign campaign = new AmountCampaign(100.0, 3, fruit);
        when(campaignPicker.pick(Collections.singletonList(campaign), 400)).thenReturn(Optional.of(campaign));

        //when
        cart.applyCampaigns(campaign);
        double campaignDiscount = cart.getCampaignDiscount();

        //then
        assertThat(campaignDiscount).isEqualTo(100);
    }

    @Test
    public void should_return_campaign_discount_as_zero_when_there_is_no_applied_campaign() {
        //given
        Category fruit = new Category("Fruits");
        Product apple = new Product("Apple", 100.00, fruit);
        cart.addCartItem(3, apple);

        //when
        double campaignDiscount = cart.getCampaignDiscount();

        //then
        assertThat(campaignDiscount).isEqualTo(0);
    }

    //
    @Test
    public void should_return_total_amount_after_campaign_discount() {
        //given
        Category fruit = new Category("Fruits");
        Product apple = new Product("Apple", 100.00, fruit);
        cart.addCartItem(4, apple);
        Campaign campaign = new AmountCampaign(100.0, 3, fruit);
        when(campaignPicker.pick(Collections.singletonList(campaign), 400)).thenReturn(Optional.of(campaign));

        //when
        cart.applyCampaigns(campaign);
        double totalAmountAfterCampaignDiscount = cart.getTotalAmountAfterCampaignDiscount();

        //then
        assertThat(totalAmountAfterCampaignDiscount).isEqualTo(300);
    }

    @Test
    public void should_return_total_amount_after_discounts() {
        //given
        Category fruit = new Category("Fruits");
        Product apple = new Product("Apple", 100.00, fruit);
        cart.addCartItem(4, apple);
        Campaign campaign = new AmountCampaign(100.0, 3, fruit);
        when(campaignPicker.pick(Collections.singletonList(campaign), 400)).thenReturn(Optional.of(campaign));
        Coupon coupon = new AmountCoupon(100.0, 20.0);

        //when
        cart.applyCoupon(coupon);
        cart.applyCampaigns(campaign);
        double totalAmountAfterDiscounts = cart.getTotalAmountAfterDiscounts();

        //then
        assertThat(totalAmountAfterDiscounts).isEqualTo(280);
    }

    @Test
    public void should_return_total_item_quantity_by_category() {
        //given
        Category fruit = new Category("Fruits");
        Category desserts = new Category("Desserts");
        Product apple = new Product("Apple", 100.00, fruit);
        Product waffle = new Product("Waffle", 50.00, desserts);
        cart.addCartItem(4, apple);
        cart.addCartItem(2, waffle);

        //when
        double totalItemQuantityByCategory = cart.getTotalItemQuantityByCategory(desserts);

        //then
        assertThat(totalItemQuantityByCategory).isEqualTo(2);
    }
}