package com.refer.packages.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.refer.packages.DTO.LoginDTO;
import com.refer.packages.DTO.SignupDTO;
import com.refer.packages.DTO.interfaces.IUserService;
import com.refer.packages.exceptions.CompanyNotFoundException;
import com.refer.packages.exceptions.DuplicateUserException;
import com.refer.packages.exceptions.UserCVNotFoundException;
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
    public void signUp(SignupDTO signupDTO) throws DuplicateUserException, CompanyNotFoundException, UserCVNotFoundException {
        User currentUser = userRepository.findByEmail(signupDTO.getEmail());
        Company company = companyRepository.findById(signupDTO.getCompanyId());
        UserCV userCV = userCVRepository.findById(signupDTO.getCvId());

        if (currentUser != null) {
            throw new DuplicateUserException("Email already in use");
        }

        if (company == null) {
            throw new CompanyNotFoundException("Company not valid");
        }

        if (userCV == null) {
            throw new UserCVNotFoundException("CV not found");
        }

        User user = User.builder()
                .name(signupDTO.getName())
                .email(signupDTO.getEmail())
                .password(passwordEncoder.encode(signupDTO.getPassword()))
                .phoneNumber(signupDTO.getPhoneNumber())
                .experience(signupDTO.getExperience())
                .location(signupDTO.getLocation())
                .professionalTitle(signupDTO.getProfessionalTitle())
                .bio(signupDTO.getBio())
                .openToRelocation(signupDTO.getOpenToRelocation())
                .company(company)
                .usercv(userCV)
                .build();

        // save user to DB
        userRepository.save(user);
    }

    @Override
    public User login(LoginDTO loginDTO) {
        System.out.println("User name = " + loginDTO.getEmail() + " Password = " + loginDTO.getPassword());
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
        );

        return userRepository.findByEmail(loginDTO.getEmail());
    }
    
}
