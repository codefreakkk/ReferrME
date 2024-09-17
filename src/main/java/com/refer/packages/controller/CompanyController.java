package com.refer.packages.controller;

import com.refer.packages.DTO.response.CompanyResponse;
import com.refer.packages.models.Company;
import com.refer.packages.services.CompanyService;
import com.refer.packages.utils.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/company")
@RestController
public class CompanyController {

    /**
     * POST  /company
     * GET   /company/companyId
     * GET   /company
     */

    @Autowired
    private CompanyService companyService;

    @PostMapping
    public ResponseEntity<?> addCompany(@RequestBody List<Company> company) {
        companyService.addCompany(company);
        GenericResponse genericResponse = new GenericResponse("Company added successfully");
        return new ResponseEntity<>(genericResponse, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{companyId}")
    public ResponseEntity<?> getCompanyById(@PathVariable int companyId) {
        CompanyResponse companyResponse = companyService.getCompany(companyId);
        return new ResponseEntity<>(companyResponse, HttpStatus.OK);
    }

}
