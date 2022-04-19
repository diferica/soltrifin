package com.soltrifin.app.services;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.soltrifin.app.dtos.CompanyDto;

public interface ICompanyService {

	ResponseEntity<?> getAll();

	ResponseEntity<?> getCompanyById(Long id);

	ResponseEntity<?> save(@Valid CompanyDto companyDto, BindingResult result);

	ResponseEntity<?> update(@Valid CompanyDto companyDto, BindingResult result, Long id);
}
