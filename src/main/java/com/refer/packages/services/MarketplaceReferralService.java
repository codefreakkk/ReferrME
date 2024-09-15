package com.refer.packages.services;

import java.util.List;
import java.util.Optional;

import com.refer.packages.DTO.interfaces.IMarketplaceReferredUser;
import com.refer.packages.exceptions.*;
import com.refer.packages.models.MarketplaceReferredUsers;
import com.refer.packages.repository.MarketplaceReferralRequestRepository;
import com.refer.packages.repository.MarketplaceReferredUsersRepository;
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
import com.refer.packages.repository.UserRepository;
import com.refer.packages.utils.GeneralUtility;


@Service
public class MarketplaceReferralService implements IReferralMarketplaceService {

    @Autowired
    private MarketplaceReferralRequestRepository marketplaceReferralRequestRepository;

    @Autowired
    private MarketplaceReferredUsersRepository marketplaceReferredUsersRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired 
    private UserRepository userRepository;

    private void checkValidUser(int id) throws UnauthorizedUserException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userId = GeneralUtility.getUserId(authentication);
        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty() || (userId != id)) {
            throw new UnauthorizedUserException("Invalid User");
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
        int referralCountForCurrentMonth = marketplaceReferralRequestRepository.getReferralCountForCurrentMonth(userId);
        if (referralCountForCurrentMonth >= 5) {
            throw new RaiseReferralRequestException("You can only raise 5 referral requests per month");
        }

        // check if user has already raised a referral for the company
        Optional<MarketplaceReferralRequest> referralRequest = marketplaceReferralRequestRepository.getReferralRequestByCandidateIdAndCompanyId(userId, companyId);
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

        marketplaceReferralRequestRepository.save(marketplaceReferralRequest);
    }

    @Override
    public IMarketplaceReferralRequest getMarketplaceReferralRequestById(int referralRequestId) throws ReferralNotFoundException {
        Optional<IMarketplaceReferralRequest> marketplaceReferralRequest = marketplaceReferralRequestRepository.getMarketplaceReferralRequestById(referralRequestId);
        if (marketplaceReferralRequest.isEmpty()) {
            throw new ReferralNotFoundException("Marketplace Referral request not found");
        }
        return marketplaceReferralRequest.get();
    }

    @Override
    public List<IMarketplaceReferralRequest> getAllMarketplaceReferralRequests() {
        List<IMarketplaceReferralRequest> referralRequests = marketplaceReferralRequestRepository.getAllMarketplaceReferralRequest();
        if (referralRequests.isEmpty()) {
            throw new ReferralNotFoundException("No referral requests found");
        }
        return referralRequests;
    }

    @Override
    public List<IMarketplaceReferredUser> getMarketplaceReferredRequestByCandidateId(int candidateId) throws ReferralException, UserNotFoundException {

        // check if user is valid
        this.checkValidUser(candidateId);

        // get referral request
        List<IMarketplaceReferredUser> referredUsers = marketplaceReferralRequestRepository.getAllMarketplaceReferralRequestByCandidateId(candidateId);
        if (referredUsers.isEmpty()) {
            throw new ReferralException("No referred user found");
        }
        return referredUsers;
    }

    @Override
    public void referMarketplaceRequest(int referralId) throws ReferralNotFoundException, ReferralException {

        // check for marketplace request
        Optional<MarketplaceReferralRequest> marketplaceReferralRequest = marketplaceReferralRequestRepository.findById(referralId);
        if (marketplaceReferralRequest.isEmpty()) {
            throw new ReferralNotFoundException("Marketplace Referral request not found");
        }

        // handle case for user referring himself
        int employeeId = GeneralUtility.getUserId(SecurityContextHolder.getContext().getAuthentication());
        if (employeeId == marketplaceReferralRequest.get().getCandidate().getId()) {
            throw new ReferralException("User cannot refer himself");
        }

        // check if user is already referred
        Optional<MarketplaceReferredUsers> referredUsers = marketplaceReferredUsersRepository.getMarketplaceReferralRequestById(referralId);
        if (referredUsers.isPresent()) {
            throw new ReferralException("User is already referred");
        }

        // add the id of employee who is referring user
        Optional<User> employee = userRepository.findById(employeeId);
        if (employee.isEmpty()) {
            throw new UserNotFoundException("Invalid user");
        }

        MarketplaceReferredUsers marketplaceReferredUsers = MarketplaceReferredUsers.builder()
                .referringEmployee(employee.get())
                .marketplaceReferralRequest(marketplaceReferralRequest.get())
                .build();

        marketplaceReferredUsersRepository.save(marketplaceReferredUsers);
    }

    @Override
    public List<IMarketplaceReferredUser> getMarketplaceReferredRequestByEmployeeId(int employeeId) throws UnauthorizedUserException {

        // check if employee is valid
        this.checkValidUser(employeeId);

        // get referral request
        List<IMarketplaceReferredUser> referredUsers = marketplaceReferralRequestRepository.getAllMarketplaceReferralRequestByEmployeeId(employeeId);
        if (referredUsers.isEmpty()) {
            throw new ReferralException("No referred user found");
        }
        return referredUsers;
    }
}


