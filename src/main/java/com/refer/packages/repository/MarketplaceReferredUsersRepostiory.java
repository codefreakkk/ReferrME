package com.refer.packages.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.refer.packages.models.MarketplaceReferredUsers;

public interface MarketplaceReferredUsersRepostiory extends JpaRepository<MarketplaceReferredUsers, Integer> {
    
}
