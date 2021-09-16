package pt.uc.sob.defektor.server.api.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pt.uc.sob.defektor.server.api.data.CampaignData;
import pt.uc.sob.defektor.server.api.data.PlanData;
import pt.uc.sob.defektor.server.api.expection.DuplicateEntryException;
import pt.uc.sob.defektor.server.api.expection.EntityNotFoundException;
import pt.uc.sob.defektor.server.api.repository.DefektorRepository;
import pt.uc.sob.defektor.server.api.service.CampaignService;
import pt.uc.sob.defektor.server.utils.Strings;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CampaignServiceImpl implements CampaignService {

    private final DefektorRepository<CampaignData> defektorRepository;

    @Override
    public CampaignData campaignAdd(CampaignData campaign) {
        defektorRepository.save(campaign, Strings.DB.CAMPAIGN_DB_PATH);
        return campaign;
    }

    @Override
    public void campaignUpdate(CampaignData campaign) {
        defektorRepository.update(campaign, Strings.DB.CAMPAIGN_DB_PATH);
    }

    @Override
    public CampaignData campaignGet(UUID uuid) throws EntityNotFoundException {
        CampaignData campaign = defektorRepository.findById(uuid, Strings.DB.CAMPAIGN_DB_PATH);
        if(campaign == null) throw new EntityNotFoundException(Strings.Errors.PLAN_NOT_FOUND);
        return campaign;
    }

    @Override
    public List<CampaignData> campaignsList() {
        return defektorRepository.findAll(Strings.DB.CAMPAIGN_DB_PATH);
    }
}
