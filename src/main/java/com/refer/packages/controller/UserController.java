package com.refer.packages.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.refer.packages.DTO.LoginDTO;
import com.refer.packages.DTO.SignupDTO;
import com.refer.packages.DTO.response.LoginResponse;
import com.refer.packages.models.User;
import com.refer.packages.services.JwtService;
import com.refer.packages.services.UserService;
import com.refer.packages.utils.GenericResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequestMapping("/api/v1/user")
@RestController
public class UserController {
 
    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupDTO signupDTO) {
        userService.signUp(signupDTO);
        GenericResponse genericResponse = new GenericResponse("User signup sucessful!");
        return new ResponseEntity<>(genericResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loin(@RequestBody LoginDTO loginDTO) {
        User authenticatedUser = userService.login(loginDTO);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponse loginResponse = LoginResponse.builder()
            .token(jwtToken)
            .build();

        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

}
