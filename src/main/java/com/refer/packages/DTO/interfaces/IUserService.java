package com.refer.packages.DTO.interfaces;

import com.refer.packages.DTO.LoginDTO;
import com.refer.packages.DTO.SignupDTO;
import com.refer.packages.models.User;

public interface IUserService {
    public User getUserById(String email);
    public void signUp(SignupDTO signupDTO);
    public User login(LoginDTO loginDTO);
}
