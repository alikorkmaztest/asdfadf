package com.alikorkmaz.shoppingcart.campaign;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class CampaignPicker {

    private final CampaignDiscountAmountCalculator campaignDiscountAmountCalculator;

    public CampaignPicker(CampaignDiscountAmountCalculator campaignDiscountAmountCalculator) {
        this.campaignDiscountAmountCalculator = campaignDiscountAmountCalculator;
    }

    public Optional<Campaign> pickBest(List<Campaign> campaigns, double totalAmount) {
        return campaigns.stream()
                .max(Comparator.comparingDouble(campaign ->
                        campaignDiscountAmountCalculator.calculate(totalAmount, campaign)));
    }
}
