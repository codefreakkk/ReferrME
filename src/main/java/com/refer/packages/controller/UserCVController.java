package com.refer.packages.controller;


import com.refer.packages.DTO.response.UserCVResponse;
import com.refer.packages.services.UserCVService;
import com.refer.packages.utils.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/usercv")
@RestController
public class UserCVController {

    /**
     * POST /userId
     * GET  /userId
     * GET  /cvid/user/userId
     */

    @Autowired
    private UserCVService userCVService;

    @GetMapping(value = "/{userId}")
    public ResponseEntity<?> getAllUserCVbyUserId(@PathVariable("userId") int userId) {
        List<UserCVResponse> userCVs = userCVService.getAllUserCVbyUserId(userId);
        return new ResponseEntity<>(userCVs, HttpStatus.OK);
    }

    @GetMapping(value = "/{cvId}/user/{userId}")
    public ResponseEntity<?> getUserCVbyUserIdAndCvId(@PathVariable int cvId, @PathVariable int userId) {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping(value = "/{userId}")
    public ResponseEntity<?> addUserCV(@PathVariable int userId) {
        userCVService.addUserCV(userId);
        GenericResponse genericResponse = new GenericResponse("CV added successfully");
        return new ResponseEntity<>(genericResponse, HttpStatus.CREATED);
    }
}
