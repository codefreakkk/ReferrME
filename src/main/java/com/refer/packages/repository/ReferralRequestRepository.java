package com.refer.packages.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.refer.packages.DTO.interfaces.IReferralRequestByEmployeeId;
import com.refer.packages.models.CandidateReferralRequest;

@Repository
public interface ReferralRequestRepository extends JpaRepository<CandidateReferralRequest, Integer> {
    
    @Query(value = "SELECT * FROM candidate_referral_request cr WHERE cr.candidate_id = :candidateId AND cr.employee_Id = :employeeId", nativeQuery = true)
    Optional<CandidateReferralRequest> findReferralRequestByCandidateIdAndEmployeeID(@Param("candidateId") int candidateId, @Param("employeeId") int employeeId);

    @Query(value = "SELECT cr.id, cr.request_type, cr.status as referralStatus, referredCompany.company_name as referredCompanyName, u.name, u.email, u.open_to_relocation as relocationStatus, c.company_name as candidateCompanyName, rd.job_url as jobUrl, rd.message, cv.path as path FROM candidate_referral_request cr INNER JOIN user as u ON cr.candidate_id = u.id INNER JOIN company as c ON u.company_id = c.id INNER JOIN company as referredCompany ON cr.reffered_company_id = referredCompany.id INNER JOIN referral_details as rd ON cr.id = rd.referral_id INNER JOIN usercv as cv ON rd.cv_id = cv.id WHERE cr.employee_id = :employeeId", nativeQuery = true)
    List<IReferralRequestByEmployeeId> findReferralRequestByEmployeeId(@Param("employeeId") int employeeId);

}
