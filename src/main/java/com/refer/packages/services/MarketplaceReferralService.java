package com.refer.packages.services;

import java.util.List;
import java.util.Optional;

import com.refer.packages.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.refer.packages.DTO.interfaces.IMarketplaceReferralRequest;
import com.refer.packages.DTO.interfaces.IReferralMarketplaceService;
import com.refer.packages.models.Company;
import com.refer.packages.models.MarketplaceReferralRequest;
import com.refer.packages.models.User;
import com.refer.packages.repository.CompanyRepository;
import com.refer.packages.repository.MarketplaceReferralRequestRepostiory;
import com.refer.packages.repository.UserRepository;
import com.refer.packages.utils.GeneralUtility;


@Service
public class MarketplaceReferralService implements IReferralMarketplaceService {

    @Autowired
    private MarketplaceReferralRequestRepostiory marketplaceReferralRequestRepostiory;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired 
    private UserRepository userRepository;

    private void checkValidUser(int candidateId) throws UserNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userId = GeneralUtility.getUserId(authentication);
        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty() || candidateId != userId) {
            throw new UserNotFoundException("Invalid User");
        }
    }

    @Override
    public void raiseMarketplaceReferralRequest(int companyId) throws SameCompanyException, RaiseReferralRequestException, CompanyNotFoundException, UserNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userId = GeneralUtility.getUserId(authentication);
        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new UserNotFoundException("Invalid User");
        }

        // check if referral request are out of count
        int referralCountForCurrentMonth = marketplaceReferralRequestRepostiory.getReferralCountForCurrentMonth(userId);
        if (referralCountForCurrentMonth >= 5) {
            throw new RaiseReferralRequestException("You can only raise 5 referral requests per month");
        }

        // check if user has already raised a referral for the company
        Optional<MarketplaceReferralRequest> referralRequest = marketplaceReferralRequestRepostiory.getReferralRequestByCandidateIdAndCompanyId(userId, companyId);
        if (referralRequest.isPresent()) {
            throw new RaiseReferralRequestException("Marketplace Referral already raised for this company");
        }
        
        // check if company is valid
        Optional<Company> company = companyRepository.findById(companyId);
        if (company.isEmpty()) {
            throw new CompanyNotFoundException("Invalid company");
        }

        // check if user's company matches with referral company
        if (companyId == user.get().getCompany().getId()) {
            throw new SameCompanyException("Referral request cannot be raised for your own company");
        }

        MarketplaceReferralRequest marketplaceReferralRequest = MarketplaceReferralRequest.builder()
            .candidate(user.get())
            .company(company.get())
            .build();

        marketplaceReferralRequestRepostiory.save(marketplaceReferralRequest);
    }

    @Override
    public IMarketplaceReferralRequest getMarketplaceReferralRequestById(int referralRequestId) throws ReferralNotFoundException {
        Optional<IMarketplaceReferralRequest> marketplaceReferralRequest = marketplaceReferralRequestRepostiory.getMarketplaceReferralRequestById(referralRequestId);
        if (marketplaceReferralRequest.isEmpty()) {
            throw new ReferralNotFoundException("Marketplace Referral request not found");
        }
        return marketplaceReferralRequest.get();
    }

    @Override
    public List<IMarketplaceReferralRequest> getAllMarketplaceReferralRequests() {
        List<IMarketplaceReferralRequest> referralRequests = marketplaceReferralRequestRepostiory.getAllMarketplaceReferralRequest();
        if (referralRequests.isEmpty()) {
            throw new ReferralNotFoundException("No referral requests found");
        }
        return referralRequests;
    }

    @Override
    public List<IMarketplaceReferralRequest> getAllMarketplaceReferralRequestByCandidateId(int candidateId) throws ReferralNotFoundException, UserNotFoundException {

        // check if user is valid
        checkValidUser(candidateId);

        // get referral request
        List<IMarketplaceReferralRequest> referralRequests = marketplaceReferralRequestRepostiory.getAllMarketplaceReferralRequestByCandidateId(candidateId);
        if (referralRequests.isEmpty()) {
            throw new ReferralNotFoundException("No referral requests found");
        }
        return referralRequests;
    }
}


