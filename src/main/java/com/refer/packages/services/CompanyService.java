package com.refer.packages.services;

import com.refer.packages.DTO.interfaces.ICompanyService;
import com.refer.packages.DTO.response.CompanyResponse;
import com.refer.packages.exceptions.CompanyNotFoundException;
import com.refer.packages.models.Company;
import com.refer.packages.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService implements ICompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public void addCompany(List<Company> company) throws CompanyNotFoundException {
        if (company.isEmpty()) {
            throw new CompanyNotFoundException("Company not found");
        }
        companyRepository.saveAll(company);
    }

    @Override
    public CompanyResponse getCompany(int companyId) throws CompanyNotFoundException {
        Optional<Company> company = companyRepository.findById(companyId);
        if (company.isEmpty()) {
            throw new CompanyNotFoundException("Company not found");
        }

        return CompanyResponse.builder()
                .id(company.get().getId())
                .companyName(company.get().getCompanyName())
                .path(company.get().getPath())
                .build();
    }
}
