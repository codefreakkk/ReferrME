package com.refer.packages.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.refer.packages.DTO.interfaces.IUserDTO;
import com.refer.packages.models.User;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    @Query(value = "SELECT u.id as id, u.name, u.email, u.bio, u.experience, u.location, u.open_to_relocation as relocation, u.phone_number as phone, u.professional_title as professionalTitle, cv.path, c.company_name as companyName FROM user as u INNER JOIN company c ON u.company_id = c.id INNER JOIN usercv as cv ON u.cv_id = cv.id WHERE u.id = :userId", nativeQuery = true)
    IUserDTO findUserById(@Param("userId") int userId);
}

