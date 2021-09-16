package pt.uc.sob.defektor.server.api.mapper;

import pt.uc.sob.defektor.server.api.data.CampaignData;
import pt.uc.sob.defektor.server.campaign.data.CampaignStatus;
import pt.uc.sob.defektor.server.model.Campaign;

public class CampaignMapper {

    public static CampaignData convertToDAO(Campaign campaign) {
        CampaignData campaignData = new CampaignData();
        campaignData.setId(campaign.getId());
        campaignData.setCurrentRun(campaign.getCurrentRun());
        campaignData.setTotalRuns(campaign.getTotalRuns());
        campaignData.setStatus(CampaignStatus.valueOf(campaign.getStatus()));
        campaignData.setMessage(campaign.getMessage());
        return campaignData;
    }


    public static Campaign convertToDTO(CampaignData campaignData) {
        Campaign campaign = new Campaign();
        campaign.setId(campaignData.getId());
        campaign.setCurrentRun(campaignData.getCurrentRun());
        campaign.setTotalRuns(campaignData.getTotalRuns());
        campaign.setStatus(campaignData.getStatus().toString());
        campaign.setMessage(campaignData.getMessage());
        return campaign;
    }
}
