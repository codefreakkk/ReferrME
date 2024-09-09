package com.refer.packages.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.refer.packages.models.ReferralDetails;

public interface ReferralDetailsRepository extends JpaRepository<ReferralDetails, Integer> {
    
}
