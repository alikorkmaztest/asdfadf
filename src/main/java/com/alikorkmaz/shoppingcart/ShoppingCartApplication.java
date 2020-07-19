package com.alikorkmaz.shoppingcart;

import com.alikorkmaz.shoppingcart.campaign.AmountCampaign;
import com.alikorkmaz.shoppingcart.campaign.Campaign;
import com.alikorkmaz.shoppingcart.campaign.RateCampaign;
import com.alikorkmaz.shoppingcart.campaign.picker.CampaignPickerImpl;
import com.alikorkmaz.shoppingcart.coupon.AmountCoupon;
import com.alikorkmaz.shoppingcart.coupon.Coupon;
import com.alikorkmaz.shoppingcart.delivery.DeliveryCostCalculatorImpl;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShoppingCartApplication {

    public static void main(String[] args) {
        //SpringApplication.run(ShoppingCartApplication.class, args);
        Category food = new Category("Food");

        Category vegetable = new Category("Vegetables", food);
        Product spinach = new Product("Spinach", 12, vegetable);

        Category fruit = new Category("Fruits", food);
        Product apple = new Product("Apple", 50.00, fruit);
        Product banana = new Product("Banana", 20.00, fruit);
        Product strawberry = new Product("Strawberry", 15.00, fruit);

        Cart cart = new Cart(new CampaignPickerImpl(), new DeliveryCostCalculatorImpl(2.99, 1, 1));
        cart.addCartItem(3, apple);
        cart.addCartItem(2, strawberry);
        cart.addCartItem(2, banana);
        cart.addCartItem(2, spinach);

        Campaign campaign1 = new RateCampaign(20.0, 3, fruit);
        Campaign campaign2 = new AmountCampaign(40.0, 3, vegetable);
        cart.applyCampaigns(campaign1, campaign2);

        Coupon coupon = new AmountCoupon(100.0, 20.0);
        cart.applyCoupon(coupon);

        cart.print();
    }

}
