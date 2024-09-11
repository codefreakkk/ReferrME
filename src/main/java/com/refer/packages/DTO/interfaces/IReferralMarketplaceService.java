package com.refer.packages.DTO.interfaces;

public interface IReferralMarketplaceService {
    public void raiseMarketplaceReferralRequest(int companyId);
    public IMarketplaceReferralRequest getMarketplaceReferralRequestById(int referralRequestId);
}
