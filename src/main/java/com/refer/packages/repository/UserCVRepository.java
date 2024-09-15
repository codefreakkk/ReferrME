package com.refer.packages.repository;

import com.refer.packages.DTO.response.UserCVResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import com.refer.packages.models.UserCV;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserCVRepository extends JpaRepository<UserCV, Integer> {

    @Query(value = "SELECT * FROM usercv WHERE id = :cvId AND user_id = :userId", nativeQuery = true)
    Optional<UserCV> findCVByUserId(@Param("cvId") int cvId, @Param("userId") int userId);

    @Query(value = "SELECT id as cvId, path FROM usercv WHERE user_id = :userId", nativeQuery = true)
    List<UserCVResponse> getAllUserCVbyUserId(@Param("userId") int userId);
}
