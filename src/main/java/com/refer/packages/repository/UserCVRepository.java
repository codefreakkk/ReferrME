package com.refer.packages.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.refer.packages.models.UserCV;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface UserCVRepository extends JpaRepository<UserCV, Integer> {

    @Query(value = "SELECT * FROM usercv WHERE id = :cvId AND user_id = :userId", nativeQuery = true)
    Optional<UserCV> findCVByUserId(int cvId, int userId);
}
