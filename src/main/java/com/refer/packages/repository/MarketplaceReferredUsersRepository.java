package com.refer.packages.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.refer.packages.models.MarketplaceReferredUsers;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MarketplaceReferredUsersRepository extends JpaRepository<MarketplaceReferredUsers, Integer> {

    @Query(value = "SELECT * FROM marketplace_referred_users WHERE marketplace_referral_request_id = :referralId", nativeQuery = true)
    Optional<MarketplaceReferredUsers> getMarketplaceReferralRequestById(@Param("referralId") int referralId);
}
