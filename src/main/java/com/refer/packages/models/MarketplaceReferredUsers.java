package com.refer.packages.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "marketplace_referred_users")
public class MarketplaceReferredUsers {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "referring_employee_id", referencedColumnName = "id")
    private User referringEmployee;

    @ManyToOne
    @JoinColumn(name = "marketplace_referral_request_id", referencedColumnName = "id")
    private MarketplaceReferralRequest marketplaceReferralRequest;
}
