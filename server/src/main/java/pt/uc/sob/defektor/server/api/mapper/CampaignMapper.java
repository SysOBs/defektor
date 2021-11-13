package pt.uc.sob.defektor.server.api.mapper;

import pt.uc.sob.defektor.server.api.data.CampaignData;
import pt.uc.sob.defektor.server.orchestrator.campaign.CampaignStatus;
import pt.uc.sob.defektor.server.model.Campaign;

import java.util.stream.Collectors;

public class CampaignMapper {

    public static CampaignData convertToDAO(Campaign campaign) {
        CampaignData campaignData = new CampaignData();
        campaignData.setId(campaign.getId());
        campaignData.setName(campaign.getName());
        campaignData.setCurrentInjection(campaign.getCurrentInjection());
        campaignData.setTotalInjections(campaign.getTotalInjections());
        campaignData.setStatus(CampaignStatus.valueOf(campaign.getStatus()));
        campaignData.setStartTimestamp(campaign.getStartTimestamp());
        campaignData.setEndTimestamp(campaign.getEndTimestamp());
        campaignData.setInjections(
                campaign.getInjections().stream()
                        .map(InjectionMapper::convertToDAO)
                        .collect(Collectors.toList())
        );
        return campaignData;
    }

    public static Campaign convertToDTO(CampaignData campaignData) {
        Campaign campaign = new Campaign();
        campaign.setId(campaignData.getId());
        campaign.setName(campaignData.getName());
        campaign.setCurrentInjection(campaignData.getCurrentInjection());
        campaign.setTotalInjections(campaignData.getTotalInjections());
        campaign.setStatus(campaignData.getStatus().toString());
        campaign.setStartTimestamp(campaignData.getStartTimestamp());
        campaign.setEndTimestamp(campaignData.getEndTimestamp());
        campaign.setInjections(
                campaignData.getInjections().stream()
                        .map(InjectionMapper::convertToDTO)
                        .collect(Collectors.toList())
        );
        return campaign;
    }
}
