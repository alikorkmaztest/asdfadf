package com.alikorkmaz.shoppingcart;

import com.alikorkmaz.shoppingcart.campaign.Campaign;
import com.alikorkmaz.shoppingcart.campaign.CampaignDiscountAmountCalculator;
import com.alikorkmaz.shoppingcart.campaign.CampaignPicker;
import com.alikorkmaz.shoppingcart.coupon.Coupon;
import com.alikorkmaz.shoppingcart.coupon.CouponDiscountAmountCalculator;
import com.alikorkmaz.shoppingcart.delivery.BasicDeliveryCostCalculator;
import com.alikorkmaz.shoppingcart.enumtype.DiscountType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShoppingCartApplication {

    public static void main(String[] args) {
        //SpringApplication.run(ShoppingCartApplication.class, args);
        Category foodCategory = new Category("Food");
        Category fruitCategory = new Category("Fruits", foodCategory);
        Product appleProduct = new Product("Apple", 10.00, fruitCategory);
        Product bananaProduct = new Product("Banana", 20.00, fruitCategory);
        Product strawberryProduct = new Product("Strawberry", 15.00, fruitCategory);
        Category vegetableCategory = new Category("Vegetables", foodCategory);
        Product spinachProduct = new Product("Spinach", 12, vegetableCategory);


        Cart cart = new Cart(new CampaignPicker(new CampaignDiscountAmountCalculator()),
                new BasicDeliveryCostCalculator(2.99, 1, 1),
                new CampaignDiscountAmountCalculator(),
                new CouponDiscountAmountCalculator());

        cart.addCartItem(3, appleProduct);
        cart.addCartItem(2, strawberryProduct);
        cart.addCartItem(2, bananaProduct);
        cart.addCartItem(2, spinachProduct);

        Campaign campaign1 = new Campaign(20.0, 3, DiscountType.RATE, fruitCategory);
        Campaign campaign2 = new Campaign(400.0, 3, DiscountType.AMOUNT, vegetableCategory);
        cart.applyCampaigns(campaign1, campaign2);
        Coupon coupon = new Coupon(20.0, 100.0, DiscountType.AMOUNT);
        cart.applyCoupon(coupon);

        System.out.println(cart);
    }

}
