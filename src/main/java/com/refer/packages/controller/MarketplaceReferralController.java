package com.refer.packages.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.refer.packages.services.MarketplaceReferralService;
import com.refer.packages.utils.GenericResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;



@RequestMapping("api/v1/marketplace/request")
@RestController
public class MarketplaceReferralController {

    @Autowired
    private MarketplaceReferralService referralMarketplaceService;

    @PostMapping(value = "/{companyId}")
    public ResponseEntity<?> raiseMarketplaceReferralRequest(@PathVariable int companyId) {
        referralMarketplaceService.raiseMarketplaceReferralRequest(companyId);
        GenericResponse genericResponse = new GenericResponse("Market place referral request raised");
        return new ResponseEntity<>(genericResponse, HttpStatus.CREATED);
    }

}
