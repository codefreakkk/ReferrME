package com.refer.packages.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupDTO {
    
    private int companyId;
    private int cvId;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private int experience;
    private String location;
    private String professionalTitle;
    private String bio;
    private String openToRelocation;

}
