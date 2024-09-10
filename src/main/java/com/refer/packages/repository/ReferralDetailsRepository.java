package com.refer.packages.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.refer.packages.models.CandidateReferralRequestDetails;

public interface ReferralDetailsRepository extends JpaRepository<CandidateReferralRequestDetails, Integer> {
    
}
