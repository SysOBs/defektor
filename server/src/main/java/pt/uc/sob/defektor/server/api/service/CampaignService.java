package pt.uc.sob.defektor.server.api.service;

import pt.uc.sob.defektor.server.api.data.CampaignData;
import pt.uc.sob.defektor.server.api.expection.DuplicateEntryException;
import pt.uc.sob.defektor.server.api.expection.EntityNotFoundException;

import java.util.List;
import java.util.UUID;

public interface CampaignService {

    CampaignData campaignAdd(CampaignData campaign);

    void campaignUpdate(CampaignData campaign);

    CampaignData campaignGet(UUID uuid) throws EntityNotFoundException;

    List<CampaignData> campaignsList();

    //TODO STOP CAMPAIGN
}
