package com.refer.packages.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.refer.packages.models.Company;
import java.util.List;


public interface CompanyRepository extends JpaRepository<Company, Integer> {

}
