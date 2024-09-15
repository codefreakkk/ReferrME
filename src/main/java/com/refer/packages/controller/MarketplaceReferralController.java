package com.refer.packages.controller;

import com.refer.packages.DTO.interfaces.IMarketplaceReferredUser;
import org.apache.coyote.Response;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.refer.packages.DTO.interfaces.IMarketplaceReferralRequest;
import com.refer.packages.services.MarketplaceReferralService;
import com.refer.packages.utils.GenericResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@RequestMapping("api/v1/marketplace/request")
@RestController
public class MarketplaceReferralController {

    /**
     * GET   /referralRequestId/referral
     * GET   /referral
     * GET   /candidateId/candidate
     * GET   /employeeId/employee
     * POST  /companyId/referral
     * POST  /referralId/refer
     */

    @Autowired
    private MarketplaceReferralService referralMarketplaceService;

    @GetMapping(value = "/{referralRequestId}/referral")
    public ResponseEntity<?> getMarketplaceReferralRequestById(@PathVariable int referralRequestId) {
        IMarketplaceReferralRequest marketplaceReferralRequest = referralMarketplaceService.getMarketplaceReferralRequestById(referralRequestId);
        return new ResponseEntity<>(marketplaceReferralRequest, HttpStatus.OK);
    }

    @GetMapping(value = "/referral")
    public ResponseEntity<?> getAllMarketplaceReferralRequests() {
        List<IMarketplaceReferralRequest> referralRequests =  referralMarketplaceService.getAllMarketplaceReferralRequests();
        return new ResponseEntity<>(referralRequests, HttpStatus.OK);
    }

    @GetMapping(value = "/{candidateId}/candidate")
    public ResponseEntity<?> getMarketplaceReferredRequestByCandidateId(@PathVariable int candidateId) {
        List<IMarketplaceReferredUser> referredUsers = referralMarketplaceService.getMarketplaceReferredRequestByCandidateId(candidateId);
        return new ResponseEntity<>(referredUsers, HttpStatus.OK);
    }

    @GetMapping(value = "/{employeeId}/employee")
    public ResponseEntity<?> getMarketplaceReferredRequestByEmployeeId(@PathVariable int employeeId) {
        List<IMarketplaceReferredUser> referredUsers = referralMarketplaceService.getMarketplaceReferredRequestByEmployeeId(employeeId);
        return new ResponseEntity<>(referredUsers, HttpStatus.OK);
    }

    @PostMapping(value = "/{companyId}/referral")
    public ResponseEntity<?> raiseMarketplaceReferralRequest(@PathVariable int companyId) {
        referralMarketplaceService.raiseMarketplaceReferralRequest(companyId);
        GenericResponse genericResponse = new GenericResponse("Market place referral request raised");
        return new ResponseEntity<>(genericResponse, HttpStatus.CREATED);
    }

    @PostMapping(value =  "/{referralId}/refer")
    public ResponseEntity<?> referMarketplaceRequest(@PathVariable int referralId) {
        referralMarketplaceService.referMarketplaceRequest(referralId);
        GenericResponse genericResponse = new GenericResponse("Marketplace user referred successfully!");
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }

}
