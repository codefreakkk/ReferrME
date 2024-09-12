package com.refer.packages.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.refer.packages.DTO.interfaces.IMarketplaceReferralRequest;
import com.refer.packages.models.MarketplaceReferralRequest;

public interface MarketplaceReferralRequestRepostiory extends JpaRepository<MarketplaceReferralRequest, Integer> {
    
    @Query(value = "SELECT COUNT(*) FROM marketplace_referral_request WHERE candidate_id = :candidateId AND MONTH(created_at) = MONTH(CURRENT_DATE()) AND YEAR(created_at) = YEAR(CURRENT_DATE())", nativeQuery = true)
    int getReferralCountForCurrentMonth(@Param("candidateId") int candidateId);

    @Query(value = "SELECT * FROM marketplace_referral_request WHERE candidate_id = :candidateId AND referring_company_id = :companyId", nativeQuery = true)
    Optional<MarketplaceReferralRequest> getReferralRequestByCandidateIdAndCompanyId(int candidateId, int companyId);


    @Query(value = "SELECT u.id as userId, u.name userName, u.email userEmail, userCompany.company_name as userCompanyName, u.experience userExperience, u.location userLocation, u.open_to_relocation as openToRelocation, u.phone_number as userPhoneNumber, cv.path as userCVPath, mr.id as marketplaceReferralRequestId, mr.referring_company_id as referringCompanyId, employeeCompany.company_name as referringCompanyName FROM marketplace_referral_request mr INNER JOIN user u ON mr.candidate_id = u.id INNER JOIN company userCompany ON u.company_id = userCompany.id INNER JOIN company employeeCompany ON mr.referring_company_id = employeeCompany.id INNER JOIN usercv cv ON u.cv_id = cv.id WHERE mr.id = :referralRequestId", nativeQuery = true)
    Optional<IMarketplaceReferralRequest> getMarketplaceReferralRequestById(int referralRequestId);

}
