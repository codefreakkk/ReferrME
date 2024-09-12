package com.refer.packages.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.refer.packages.DTO.ReferralRequestDTO;
import com.refer.packages.DTO.interfaces.IReferralRequestByCandidateId;
import com.refer.packages.DTO.interfaces.IReferralRequestByEmployeeId;
import com.refer.packages.services.CandidateReferralRequestService;
import com.refer.packages.utils.GenericResponse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;




@RequestMapping("api/v1/referral/request")
@RestController
public class CandidateReferralRequestController {

    @Autowired
    private CandidateReferralRequestService referralRequestService; 

    @GetMapping(value = "/{employeeId}/employee")
    public ResponseEntity<?> getReferralRequestByEmployeeId(@PathVariable int employeeId) {
        List<IReferralRequestByEmployeeId> referralRequest = referralRequestService.getReferralRequestByEmployeeId(employeeId);
        return new ResponseEntity<>(referralRequest, HttpStatus.OK);
    }

    @GetMapping(value = "/{candidateId}/candidate")
    public ResponseEntity<?> getReferralRequestByCandidateId(@PathVariable int candidateId) {
        List<IReferralRequestByCandidateId> referralRequest = referralRequestService.getReferralRequestByCandidateId(candidateId);
        return new ResponseEntity<>(referralRequest, HttpStatus.OK);
    }

    @PostMapping(value = "/{employeeId}")
    public ResponseEntity<?> raiseReferralRequest(@PathVariable int employeeId, @RequestBody ReferralRequestDTO referralRequestDTO) {
        referralRequestService.raiseReferralRequest(referralRequestDTO, employeeId);
        GenericResponse genericResponse = new GenericResponse("Referral request saved");
        return new ResponseEntity<>(genericResponse, HttpStatus.CREATED);
    }

}
