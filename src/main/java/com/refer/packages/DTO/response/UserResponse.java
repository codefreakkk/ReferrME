package com.refer.packages.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private int id;
    private String name;
    private String email;
    private String phoneNumber;
    private int experience;
    private String location;
    private String professionalTitle;
    private String bio;
    private String openToRelocation;
}
