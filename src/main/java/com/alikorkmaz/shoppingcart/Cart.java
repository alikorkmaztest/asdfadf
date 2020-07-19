package com.alikorkmaz.shoppingcart;

import com.alikorkmaz.shoppingcart.campaign.Campaign;
import com.alikorkmaz.shoppingcart.campaign.picker.CampaignPicker;
import com.alikorkmaz.shoppingcart.coupon.Coupon;
import com.alikorkmaz.shoppingcart.delivery.DeliveryCostCalculator;
import com.alikorkmaz.shoppingcart.exception.DiscountNotApplicableException;

import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.math.NumberUtils.DOUBLE_ZERO;

public class Cart {

    private final List<CartItem> cartItems;
    private Campaign appliedCampaign;
    private Coupon appliedCoupon;
    private final CampaignPicker campaignPicker;
    private final DeliveryCostCalculator deliveryCostCalculator;

    public Cart(CampaignPicker campaignPicker,
                DeliveryCostCalculator deliveryCostCalculator) {
        this.campaignPicker = campaignPicker;
        this.deliveryCostCalculator = deliveryCostCalculator;
        cartItems = new LinkedList<>();
    }

    public void addCartItem(int quantity, Product product) {
        cartItems.add(new CartItem(quantity, product));
    }

    public void applyCampaigns(Campaign... campaigns) {
        List<Campaign> validCampaigns = Arrays.stream(campaigns)
                .filter(campaign -> campaign.isApplicableFor(this))
                .collect(Collectors.toList());
        appliedCampaign = campaignPicker.pick(validCampaigns, getTotalAmount())
                .orElseThrow(() -> new DiscountNotApplicableException("campaign"));
    }

    public void applyCoupon(Coupon coupon) {
        if (coupon.isApplicableFor(this)) {
            appliedCoupon = coupon;
        } else {
            throw new DiscountNotApplicableException("coupon");
        }
    }

    public double getNumberOfDifferentProducts() {
        return getProducts().stream()
                .distinct()
                .count();
    }

    public double getNumberOfDistinctCategories() {
        return getCategories().stream()
                .distinct()
                .count();
    }

    public double getTotalAmount() {
        return cartItems.stream()
                .mapToDouble(CartItem::getTotalAmount)
                .sum();
    }

    public double getDeliveryCost() {
        return deliveryCostCalculator.calculateFor(this);
    }

    public double getCouponDiscount() {
        return Optional.ofNullable(appliedCoupon)
                .map(coupon -> coupon.getDiscountAmountFor(getTotalAmount()))
                .orElse(DOUBLE_ZERO);
    }

    public double getCampaignDiscount() {
        return Optional.ofNullable(appliedCampaign)
                .map(campaign -> campaign.getDiscountAmount(getTotalAmount()))
                .orElse(DOUBLE_ZERO);
    }

    public double getTotalAmountAfterCampaignDiscount() {
        return getTotalAmount() - getCampaignDiscount();
    }

    public double getTotalAmountAfterDiscounts() {
        return getTotalAmountAfterCampaignDiscount() - getCouponDiscount();
    }

    public int getTotalItemQuantityByCategory(Category category) {
        return cartItems.stream()
                .filter(cartItem -> cartItem.productCategoryEquals(category))
                .mapToInt(CartItem::getQuantity)
                .sum();
    }

    private List<Category> getCategories() {
        return cartItems.stream()
                .map(CartItem::getProductCategory)
                .collect(Collectors.toList());
    }

    private List<Product> getProducts() {
        return cartItems.stream()
                .map(CartItem::getProduct)
                .collect(Collectors.toList());
    }

    public void print() {
        System.out.println(this);
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
