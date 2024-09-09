package com.refer.packages.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReferralRequestDTO {
    private int candidateId;
    private int referredCompanyID;
    private int cvId;
    private String requestType;
    private String status;
    private String jobURL;
    private String message;
}
