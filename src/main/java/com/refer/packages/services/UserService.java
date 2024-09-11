package com.refer.packages.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.refer.packages.DTO.LoginDTO;
import com.refer.packages.DTO.SignupDTO;
import com.refer.packages.DTO.interfaces.IUserDTO;
import com.refer.packages.DTO.interfaces.IUserService;
import com.refer.packages.DTO.response.UserResponse;
import com.refer.packages.exceptions.CompanyNotFoundException;
import com.refer.packages.exceptions.DuplicateUserException;
import com.refer.packages.exceptions.UserCVNotFoundException;
import com.refer.packages.exceptions.UserNotFoundException;
import com.refer.packages.models.Company;
import com.refer.packages.models.User;
import com.refer.packages.models.UserCV;
import com.refer.packages.repository.CompanyRepository;
import com.refer.packages.repository.UserCVRepository;
import com.refer.packages.repository.UserRepository;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserCVRepository userCVRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User getUserById(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void signUp(SignupDTO signupDTO) throws DuplicateUserException, CompanyNotFoundException {
        User user = userRepository.findByEmail(signupDTO.getEmail());
        Optional<Company> company = companyRepository.findById(signupDTO.getCompanyId());

        if (user != null) {
            throw new DuplicateUserException("Email already in use");
        }

        if (company.isEmpty()) {
            throw new CompanyNotFoundException("Company not valid");
        }

        User currentUser = User.builder()
                .name(signupDTO.getName())
                .email(signupDTO.getEmail())
                .password(passwordEncoder.encode(signupDTO.getPassword()))
                .phoneNumber(signupDTO.getPhoneNumber())
                .experience(signupDTO.getExperience())
                .location(signupDTO.getLocation())
                .professionalTitle(signupDTO.getProfessionalTitle())
                .bio(signupDTO.getBio())
                .openToRelocation(signupDTO.getOpenToRelocation())
                .company(company.get())
                .build();

        // save user to DB
        userRepository.save(currentUser);
    }

    @Override
    public User login(LoginDTO loginDTO) {
        System.out.println("User name = " + loginDTO.getEmail() + " Password = " + loginDTO.getPassword());
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
        );

        return userRepository.findByEmail(loginDTO.getEmail());
    }
    
    @Override
    public UserResponse getUserByUserId(int userId) throws UserNotFoundException {
        IUserDTO user = userRepository.findUserById(userId);

        if (user == null) {
            throw new UserNotFoundException("User with id "+ userId +" not found");
        }

        UserResponse userResponse = UserResponse.builder()
            .id(user.getId())
            .name(user.getName())
            .email(user.getEmail())
            .phoneNumber(user.getPhone())
            .experience(user.getExperience())
            .location(user.getLocation())
            .professionalTitle(user.getProfessionalTitle())
            .bio(user.getBio())
            .openToRelocation(user.getRelocation())
            .build();

        return userResponse;
    }
}
