package com.refer.packages.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.refer.packages.models.UserCV;
import java.util.List;


public interface UserCVRepository extends JpaRepository<UserCV, Integer> {
     
}
