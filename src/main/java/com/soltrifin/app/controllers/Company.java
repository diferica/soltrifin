package com.soltrifin.app.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soltrifin.app.dtos.CompanyDto;
import com.soltrifin.app.services.ICompanyService;

@RestController
@RequestMapping("/soltrifin/v1/")
public class Company {
	
	
	
	@Autowired
	ICompanyService iCompanyService;
	
	@GetMapping("companies")
	public ResponseEntity<?>getListCompany(){
		
		return iCompanyService.getAll();
	}
	
	@GetMapping("companies/{id}")
	public ResponseEntity<?>getCompanyById(@PathVariable Long id){
		
		return iCompanyService.getCompanyById(id);
	}
	
	@PostMapping("companies")
	public ResponseEntity<?>newCompany(@Valid @RequestBody CompanyDto companyDto ,  BindingResult result ){
			
			return iCompanyService.save(companyDto, result);
		}
	
	@PutMapping("companies/{id}")
	public ResponseEntity<?>updateCompany(@Valid @RequestBody CompanyDto companyDto ,  BindingResult result , @PathVariable Long id ){
			
			return iCompanyService.update(companyDto, result,id);
		}
	
	
}
