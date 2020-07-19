package com.alikorkmaz.shoppingcart.campaign.picker;

import com.alikorkmaz.shoppingcart.Category;
import com.alikorkmaz.shoppingcart.campaign.AmountCampaign;
import com.alikorkmaz.shoppingcart.campaign.Campaign;
import com.alikorkmaz.shoppingcart.campaign.RateCampaign;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class CampaignPickerImplTest {

    private CampaignPicker campaignPicker;

    @BeforeEach
    public void setUp() {
        campaignPicker = new CampaignPickerImpl();
    }

    @Test
    public void should_pick() {
        //given
        Category food = new Category("Food");
        Campaign campaign1 = new RateCampaign(20.0, 3, food);
        Campaign campaign2 = new AmountCampaign(40.0, 3, food);
        List<Campaign> campaigns = Arrays.asList(campaign1, campaign2);

        //when
        Optional<Campaign> campaign = campaignPicker.pick(campaigns, 100);

        //then
        assertThat(campaign.get()).isEqualTo(campaign2);
    }
}