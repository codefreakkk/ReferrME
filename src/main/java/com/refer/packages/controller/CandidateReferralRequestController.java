package com.refer.packages.controller;

import com.refer.packages.utils.Enums;
import org.springframework.web.bind.annotation.*;

import com.refer.packages.DTO.ReferralRequestDTO;
import com.refer.packages.DTO.interfaces.IReferralRequestByCandidateId;
import com.refer.packages.DTO.interfaces.IReferralRequestByEmployeeId;
import com.refer.packages.services.CandidateReferralRequestService;
import com.refer.packages.utils.GenericResponse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


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

    @PutMapping(value = "/{referralId}/status")
    public ResponseEntity<?> updateReferralStatus(@PathVariable int referralId, @RequestParam String s) {
        referralRequestService.updateReferralStatus(referralId, s);
        GenericResponse genericResponse = new GenericResponse("Referral Status Updated");
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/{employeeId}")
    public ResponseEntity<?> raiseReferralRequest(@PathVariable int employeeId, @RequestBody ReferralRequestDTO referralRequestDTO) {
        referralRequestService.raiseReferralRequest(referralRequestDTO, employeeId);
        GenericResponse genericResponse = new GenericResponse("Referral request saved");
        return new ResponseEntity<>(genericResponse, HttpStatus.CREATED);
    }

}
