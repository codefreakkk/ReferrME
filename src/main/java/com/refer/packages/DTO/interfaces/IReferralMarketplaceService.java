package com.refer.packages.DTO.interfaces;

import java.util.List;

public interface IReferralMarketplaceService {
    public void raiseMarketplaceReferralRequest(int companyId);
    public IMarketplaceReferralRequest getMarketplaceReferralRequestById(int referralRequestId);
    public List<IMarketplaceReferralRequest> getAllMarketplaceReferralRequests();
    public List<IMarketplaceReferralRequest> getAllMarketplaceReferralRequestByCandidateId(int candidateId);
}
