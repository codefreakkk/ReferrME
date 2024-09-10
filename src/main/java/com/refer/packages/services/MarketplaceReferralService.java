package com.refer.packages.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.refer.packages.DTO.interfaces.IReferralMarketplaceService;
import com.refer.packages.exceptions.CompanyNotFoundException;
import com.refer.packages.exceptions.RaiseReferralRequestException;
import com.refer.packages.exceptions.SameCompanyException;
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

    @Override
    public void raiseMarketplaceReferralRequest(int companyId) throws SameCompanyException, RaiseReferralRequestException, CompanyNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userId = GeneralUtility.getUserId(authentication);
        Optional<User> user = userRepository.findById(userId);

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
            throw new SameCompanyException("Referral request cannot be raised for current company");
        }

        MarketplaceReferralRequest marketplaceReferralRequest = MarketplaceReferralRequest.builder()
            .candidate(user.get())
            .company(company.get())
            .build();

        marketplaceReferralRequestRepostiory.save(marketplaceReferralRequest);
    }
    

    // service for getting marketplace referral request by id 

    // Query for etting marketplace referral request by id -
    // SELECT u.id as userId, u.name userName, u.email userEmail, userCompany.company_name as userCompanyName, u.experience userExperience, u.location userLocation,
    // u.open_to_relocation as openToRelocation, u.phone_number as userPhoneNumber, cv.path as userCVPath, mr.id as marketplaceReferralRequestId,
    // mr.company_id as referringCompanyId, employeeCompany.company_name as referringCompanyName FROM marketplace_referral_request mr
    // INNER JOIN user u
    //     ON mr.candidate_id = u.id
    // INNER JOIN company userCompany
    //     ON u.company_id = userCompany.id
    // INNER JOIN company employeeCompany
    //     ON mr.company_id = employeeCompany.id
    // INNER JOIN usercv cv
    //     ON u.cv_id = cv.id
    // WHERE mr.id = 2;

    

}
