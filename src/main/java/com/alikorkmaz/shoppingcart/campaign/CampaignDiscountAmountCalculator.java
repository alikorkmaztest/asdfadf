package com.alikorkmaz.shoppingcart.campaign;

import com.alikorkmaz.shoppingcart.enumtype.DiscountType;

public class CampaignDiscountAmountCalculator {

    public double calculate(double totalAmount, Campaign campaign) {
        if (campaign.getDiscountType().equals(DiscountType.RATE)) {
            return totalAmount * (campaign.getDiscountAmount() / 100);
        }
        return campaign.getDiscountAmount();
    }
}
