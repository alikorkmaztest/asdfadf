package com.alikorkmaz.shoppingcart;

import com.alikorkmaz.shoppingcart.campaign.Campaign;
import com.alikorkmaz.shoppingcart.campaign.CampaignDiscountAmountCalculator;
import com.alikorkmaz.shoppingcart.campaign.CampaignPicker;
import com.alikorkmaz.shoppingcart.coupon.Coupon;
import com.alikorkmaz.shoppingcart.coupon.CouponDiscountAmountCalculator;
import com.alikorkmaz.shoppingcart.delivery.DeliveryCostCalculator;

import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.math.NumberUtils.DOUBLE_ZERO;

public class Cart {

    private final List<CartItem> cartItems;
    private Campaign appliedCampaign;
    private Coupon appliedCoupon;
    private final CampaignPicker campaignPicker;
    private final DeliveryCostCalculator deliveryCostCalculator;
    private final CampaignDiscountAmountCalculator campaignDiscountAmountCalculator;
    private final CouponDiscountAmountCalculator couponDiscountAmountCalculator;

    public Cart(CampaignPicker campaignPicker,
                DeliveryCostCalculator deliveryCostCalculator,
                CampaignDiscountAmountCalculator campaignDiscountAmountCalculator,
                CouponDiscountAmountCalculator couponDiscountAmountCalculator) {
        this.campaignPicker = campaignPicker;
        this.deliveryCostCalculator = deliveryCostCalculator;
        this.campaignDiscountAmountCalculator = campaignDiscountAmountCalculator;
        this.couponDiscountAmountCalculator = couponDiscountAmountCalculator;
        cartItems = new LinkedList<>();
    }

    public void addCartItem(int quantity, Product product) {
        cartItems.add(new CartItem(quantity, product));
    }

    private int getTotalItemQuantityByCategory(Category category) {
        return cartItems.stream()
                .filter(cartItem -> cartItem.productCategoryEquals(category))
                .mapToInt(CartItem::getQuantity)
                .sum();
    }

    private double getTotalAmount() {
        return cartItems.stream()
                .mapToDouble(CartItem::getTotalAmount)
                .sum();
    }

    public double getCampaignDiscount() {
        return Optional.ofNullable(appliedCampaign)
                .map(campaign -> campaignDiscountAmountCalculator.calculate(getTotalAmount(), campaign))
                .orElse(DOUBLE_ZERO);
    }

    private double getTotalAmountAfterCampaignDiscount() {
        return getTotalAmount() - getCampaignDiscount();
    }

    public double getCouponDiscount() {
        return Optional.ofNullable(appliedCoupon)
                .map(coupon -> couponDiscountAmountCalculator.calculate(getTotalAmountAfterCampaignDiscount(), coupon))
                .orElse(DOUBLE_ZERO);
    }

    public double getTotalAmountAfterDiscounts() {
        return getTotalAmountAfterCampaignDiscount() - getCouponDiscount();
    }

    public void applyCampaigns(Campaign... campaigns) {
        List<Campaign> validCampaigns = Arrays.stream(campaigns)
                .filter(campaign -> campaign.isApplicable(getTotalItemQuantityByCategory(campaign.getCategory())))
                .collect(Collectors.toList());
        appliedCampaign = campaignPicker.pickBest(validCampaigns, getTotalAmount())
                .orElseThrow(() -> new RuntimeException("not valid campaign"));
    }

    public void applyCoupon(Coupon coupon) {
        if (coupon.isApplicable(getTotalAmount())) {
            appliedCoupon = coupon;
        } else {
            //winlentia you have to add x money to active coupon
            throw new RuntimeException("not valid coupon");
        }
    }

    private List<Category> getCategories() {
        return cartItems.stream()
                .map(CartItem::getProductCategory)
                .collect(Collectors.toList());
    }

    public double getNumberOfDistinctCategories() {
        return getCategories().stream()
                .distinct()
                .count();
    }

    private List<Product> getProducts() {
        return cartItems.stream()
                .map(CartItem::getProduct)
                .collect(Collectors.toList());
    }

    public double getNumberOfDifferentProducts() {
        return getProducts().stream()
                .distinct()
                .count();
    }

    public double getDeliveryCost() {
        return deliveryCostCalculator.calculateFor(this);
    }

    private HashMap<Category, List<CartItem>> getGroupedCardItems() {
        HashMap<Category, List<CartItem>> groupedMap = new HashMap<>();
        cartItems.forEach(cartItem -> {
            Category productCategory = cartItem.getProductCategory();
            if (!groupedMap.containsKey(productCategory)) {
                groupedMap.put(productCategory, new ArrayList<>());
            }
            groupedMap.get(productCategory).add(cartItem);
        });
        return groupedMap;
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        HashMap<Category, List<CartItem>> groupedCardItems = getGroupedCardItems();
        groupedCardItems.forEach(
                (category, cartItems) -> {
                    stringBuilder.append(category);
                    cartItems.forEach(stringBuilder::append);
                    stringBuilder.append("\n");
                }
        );
        stringBuilder.append("Total Amount: ").append(getTotalAmount()).append("\n")
                .append("Campaign Discount: $").append(getCampaignDiscount()).append("\n")
                .append("Total Amount After Campaign: $").append(getTotalAmountAfterCampaignDiscount()).append("\n")
                .append("Coupon Discount: $").append(getCouponDiscount()).append("\n")
                .append("Total Amount After Campaign and Coupon: $").append(getTotalAmountAfterDiscounts()).append("\n")
                .append("Delivery Cost: $").append(getDeliveryCost());
    return stringBuilder.toString();
    }
}
