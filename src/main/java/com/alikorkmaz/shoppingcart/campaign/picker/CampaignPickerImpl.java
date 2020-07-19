package com.alikorkmaz.shoppingcart.campaign.picker;

import com.alikorkmaz.shoppingcart.campaign.Campaign;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class CampaignPickerImpl implements CampaignPicker {

    @Override
    public Optional<Campaign> pick(List<Campaign> campaigns, double amount) {
        return campaigns.stream()
                .max(Comparator.comparingDouble(campaign -> campaign.getDiscountAmount(amount)));
    }
}
