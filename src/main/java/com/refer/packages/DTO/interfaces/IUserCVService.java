package com.refer.packages.DTO.interfaces;

import com.refer.packages.DTO.response.UserCVResponse;

import java.util.List;

public interface IUserCVService {
    public void addUserCV(int userId);
    public List<UserCVResponse> getAllUserCVbyUserId(int userId);
    public List<UserCVResponse> getUserCVbyUserIdAndCvId(int cvId, int userId);
}
