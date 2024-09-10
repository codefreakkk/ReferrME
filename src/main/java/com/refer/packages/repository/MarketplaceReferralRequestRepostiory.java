package com.refer.packages.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.refer.packages.models.MarketplaceReferralRequest;

public interface MarketplaceReferralRequestRepostiory extends JpaRepository<MarketplaceReferralRequest, Integer> {
    
    @Query(value = "SELECT COUNT(*) FROM marketplace_referral_request WHERE candidate_id = :candidateId AND MONTH(created_at) = MONTH(CURRENT_DATE()) AND YEAR(created_at) = YEAR(CURRENT_DATE())", nativeQuery = true)
    int getReferralCountForCurrentMonth(@Param("candidateId") int candidateId);

    @Query(value = "SELECT * FROM marketplace_referral_request WHERE candidate_id = :candidateId AND company_id = :companyId", nativeQuery = true)
    Optional<MarketplaceReferralRequest> getReferralRequestByCandidateIdAndCompanyId(int candidateId, int companyId);
}
