package com.refer.packages.repository;

import com.refer.packages.DTO.response.CompanyResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import com.refer.packages.models.Company;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
}
