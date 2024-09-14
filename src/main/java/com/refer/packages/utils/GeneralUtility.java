package com.refer.packages.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import com.refer.packages.models.User;

public class GeneralUtility {
    
    public static int getUserId(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return ((User) userDetails).getId();
    }

}
