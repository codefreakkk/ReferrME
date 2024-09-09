package com.refer.packages.DTO.interfaces;

import java.util.List;

import com.refer.packages.DTO.ReferralRequestDTO;

public interface IReferralRequestService {
    public void raiseReferralRequest(ReferralRequestDTO referralRequestDTO, int employeeId);
    public List<IReferralRequestByEmployeeId> getReferralRequestByEmployeeId(int employeeId);
    public List<IReferralRequestByCandidateId> getReferralRequestByCandidateId(int candidateId);
}
