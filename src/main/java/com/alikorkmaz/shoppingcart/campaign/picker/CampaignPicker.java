package com.alikorkmaz.shoppingcart.campaign.picker;

import com.alikorkmaz.shoppingcart.campaign.Campaign;

import java.util.List;
import java.util.Optional;

public interface CampaignPicker {

    Optional<Campaign> pick(List<Campaign> campaigns, double amount);
}
