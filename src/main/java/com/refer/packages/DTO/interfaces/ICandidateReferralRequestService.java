package com.refer.packages.DTO.interfaces;

import java.util.List;

import com.refer.packages.DTO.ReferralRequestDTO;
import com.refer.packages.utils.Enums;

public interface ICandidateReferralRequestService {
    public void raiseReferralRequest(ReferralRequestDTO referralRequestDTO, int employeeId);
    public List<IReferralRequestByEmployeeId> getReferralRequestByEmployeeId(int employeeId);
    public List<IReferralRequestByCandidateId> getReferralRequestByCandidateId(int candidateId);
    public void updateReferralStatus(int referralId, String status);
}

