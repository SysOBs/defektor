package pt.uc.sob.defektor.server.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pt.uc.sob.defektor.server.api.CampaignApi;
import pt.uc.sob.defektor.server.api.data.CampaignData;
import pt.uc.sob.defektor.server.api.expection.EntityNotFoundException;
import pt.uc.sob.defektor.server.api.mapper.Mapper;
import pt.uc.sob.defektor.server.api.service.CampaignService;
import pt.uc.sob.defektor.server.model.Campaign;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("${openapi.server.base-path:/defektor-api/1.0.0}")
@RequiredArgsConstructor
public class CampaignController implements CampaignApi {

    private final CampaignService campaignService;

    @Override
    public ResponseEntity<List<Campaign>> campaignList() {
        List<Campaign> campaignList = new ArrayList<>();
        for(CampaignData campaignData : campaignService.campaignsList())
            campaignList.add(Mapper.convertToDTO(campaignData));

        return new ResponseEntity<>(campaignList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Campaign> campaignGet(UUID id) {
        try {
            Campaign returnableCampaign = Mapper.convertToDTO(campaignService.campaignGet(id));
            return new ResponseEntity<>(returnableCampaign, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
