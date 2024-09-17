package com.refer.packages.DTO.interfaces;

import com.refer.packages.DTO.response.CompanyResponse;
import com.refer.packages.models.Company;

import java.util.List;

public interface ICompanyService {
    public void addCompany(List<Company> company);
    public CompanyResponse getCompany(int companyId);
}
