package com.refer.packages.services;

import com.refer.packages.DTO.interfaces.IUserCVService;
import com.refer.packages.DTO.response.UserCVResponse;
import com.refer.packages.exceptions.UnauthorizedUserException;
import com.refer.packages.exceptions.UserNotFoundException;
import com.refer.packages.models.User;
import com.refer.packages.models.UserCV;
import com.refer.packages.repository.UserCVRepository;
import com.refer.packages.repository.UserRepository;
import com.refer.packages.utils.GeneralUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserCVService implements IUserCVService {

    @Autowired
    private UserCVRepository userCVRepository;

    @Autowired
    private UserRepository userRepository;


    private Optional<User> checkUserExist(int userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        return user;
    }

    private void checkAuthorizeUser(Optional<User> user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int currentUserId = GeneralUtility.getUserId(authentication);
        if (user.get().getId() != currentUserId) {
            throw new UnauthorizedUserException("Invalid user to add CV");
        }
    }

    @Override
    public void addUserCV(int userId) throws UserNotFoundException, UnauthorizedUserException {

        // check if user exist
        Optional<User> user = this.checkUserExist(userId);

        // check if user is authorized user
        this.checkAuthorizeUser(user);

        UserCV userCV = UserCV.builder()
                .path("THIS TEMP PATH")
                .user(user.get())
                .build();

        userCVRepository.save(userCV);
    }

    @Override
    public List<UserCVResponse> getAllUserCVbyUserId(int userId) {

        // check if user exist
        Optional<User> user = this.checkUserExist(userId);

        // check if user is authorized user
        this.checkAuthorizeUser(user);
        return userCVRepository.getAllUserCVbyUserId(userId);
    }
}
