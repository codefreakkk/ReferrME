package com.refer.packages.services;

import java.util.List;
import java.util.Optional;

import com.refer.packages.exceptions.*;
import com.refer.packages.utils.Enums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.refer.packages.DTO.ReferralRequestDTO;
import com.refer.packages.DTO.interfaces.IReferralRequestByCandidateId;
import com.refer.packages.DTO.interfaces.IReferralRequestByEmployeeId;
import com.refer.packages.DTO.interfaces.ICandidateReferralRequestService;
import com.refer.packages.models.CandidateReferralRequest;
import com.refer.packages.models.Company;
import com.refer.packages.models.CandidateReferralRequestDetails;
import com.refer.packages.models.User;
import com.refer.packages.models.UserCV;
import com.refer.packages.repository.CompanyRepository;
import com.refer.packages.repository.ReferralDetailsRepository;
import com.refer.packages.repository.ReferralRequestRepository;
import com.refer.packages.repository.UserCVRepository;
import com.refer.packages.repository.UserRepository;
import com.refer.packages.utils.GeneralUtility;

@Service
public class CandidateReferralRequestService implements ICandidateReferralRequestService {
    
    @Autowired
    private ReferralRequestRepository referralRequestRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserCVRepository userCVRepository;

    @Autowired
    private ReferralDetailsRepository referralDetailsRepository;

    // Raise referral request directly to employee
    @Override
    public void raiseReferralRequest(ReferralRequestDTO referralRequestDTO, int employeeId) throws UserNotFoundException, CompanyNotFoundException, UserCVNotFoundException, ReferralNotFoundException, DuplicateReferralException, SameCompanyException {

        // check employeeId should not be same as user id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userId = GeneralUtility.getUserId(authentication);
        if (userId == employeeId) {
            throw new DuplicateUserException("Referring Employee and User cannot be same");
        }

        // check if referral request already raised by candidate to employee
        Optional<CandidateReferralRequest> existingCandidateReferralRequest = referralRequestRepository.findReferralRequestByCandidateIdAndEmployeeID(userId, employeeId);
        if (existingCandidateReferralRequest.isPresent()) {
            throw new DuplicateReferralException("Referral already raised");
        }

        // handle candidate referral request
        Optional<User> candidate = userRepository.findById(userId);
        if (candidate.isEmpty()) {
            throw new UserNotFoundException("Candidate not found");
        }

        Optional<User> employee = userRepository.findById(employeeId);
        if (employee.isEmpty()) {
            throw new UserNotFoundException("Employee not found");
        }

        Optional<Company> company = companyRepository.findById(referralRequestDTO.getReferredCompanyID());
        if (company.isEmpty()) {
            throw new CompanyNotFoundException("Company not found");
        }

        Optional<UserCV> userCV = userCVRepository.findCVByUserId(referralRequestDTO.getCvId(), userId);
        if (userCV.isEmpty()) {
            throw new UserCVNotFoundException("CV not found");
        }

        // company of user and employee should not be same
        int companyId = candidate.get().getCompany().getId();
        if (companyId == referralRequestDTO.getReferredCompanyID()) {
            throw new SameCompanyException("Cannot refer in same company");
        }

        CandidateReferralRequest candidateReferralRequest = CandidateReferralRequest.builder()
                .candidate(candidate.get())
                .employee(employee.get())
                .company(company.get())
                .status(Enums.Status.PENDING)
                .build();

        // save referral request
        CandidateReferralRequest savedReferralRequest = referralRequestRepository.save(candidateReferralRequest);
        
        // handle referral details
        Optional<CandidateReferralRequest> referralRequest = referralRequestRepository.findById(savedReferralRequest.getId());
        if (referralRequest.isEmpty()) {
            throw new ReferralNotFoundException("Referral not found");
        }

        CandidateReferralRequestDetails referralDetails = CandidateReferralRequestDetails.builder()
            .candidateReferralRequest(referralRequest.get())
            .userCV(userCV.get())
            .jobUrl(referralRequestDTO.getJobURL())
            .message(referralRequestDTO.getMessage())
            .build();

        referralDetailsRepository.save(referralDetails);
    }

    @Override
    public List<IReferralRequestByEmployeeId> getReferralRequestByEmployeeId(int employeeId) throws UnauthorizedUserException {
        
        // check if user's employee ID matches with provided employee ID
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int currentEmployeeID = GeneralUtility.getUserId(authentication);
        if (currentEmployeeID != employeeId) {
            throw new UnauthorizedUserException("Not a authorized employee");
        }

        return referralRequestRepository.findReferralRequestByEmployeeId(employeeId);
    }

    @Override
    public List<IReferralRequestByCandidateId> getReferralRequestByCandidateId(int candidateId) throws UnauthorizedUserException {
        
        // check if user's ID matches with provided ID
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int currentCandidateId = GeneralUtility.getUserId(authentication);
        if (currentCandidateId != candidateId) {
            throw new UnauthorizedUserException("Not a authorized candidate");
        }

        return referralRequestRepository.findReferralRequestByCandidateId(candidateId);
    }

    // TODO testing is remaining
    @Override
    public void updateReferralStatus(int referralId, String status) throws ReferralNotFoundException, InvalidStatusException {

        // check if referral request exist
        Optional<CandidateReferralRequest> candidateReferralRequest = referralRequestRepository.findById(referralId);
        if (candidateReferralRequest.isEmpty()) {
            throw new ReferralNotFoundException("Referral request not found");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int candidateId = candidateReferralRequest.get().getCandidate().getId();
        int currentCandidateId = GeneralUtility.getUserId(authentication);
        if (currentCandidateId == candidateId) {
            throw new UnauthorizedUserException("Not a authorized user to update status");
        }

        // check for valid status
        if (!status.equals("APPROVED") && !status.equals("DECLINED") && !status.equals("PENDING")) {
            throw new InvalidStatusException("Not a valid status");
        }

        // update the status
        CandidateReferralRequest request = candidateReferralRequest.get();
        request.setStatus(Enums.Status.valueOf(status));
        referralRequestRepository.save(request);
    }

}
