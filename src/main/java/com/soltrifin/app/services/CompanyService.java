package com.soltrifin.app.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.soltrifin.app.dtos.CompanyDto;
import com.soltrifin.app.entities.Company;
import com.soltrifin.app.repositories.ICompany;

@Service
public class CompanyService implements ICompanyService {

	private static final String ERROR = "error";
	private static final String MESSAGE = "message";
	private static final String STATUS_CODE = "statusCode";
	private static final String COMPANY = "company";
	private static final String  BD_ERROR="there are no records in the db";

	@Autowired
	ICompany iCompany;

	@Override
	public ResponseEntity<?> getAll() {
		Map<String, Object> response = new HashMap<>();
		List<Company> companies = null;

		companies = (List<Company>) iCompany.findAll();

		if (companies.isEmpty()) {
			response.put(STATUS_CODE, HttpStatus.NOT_FOUND);
			response.put(MESSAGE, "Alert: there are no companies".concat(BD_ERROR));
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

		response.put(STATUS_CODE, HttpStatus.OK);
		response.put("companies", companies);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@Override
	public ResponseEntity<?> getCompanyById(Long id) {
		Map<String, Object> response = new HashMap<>();
		Company company = null;

		try {

			company = iCompany.findById(id).orElse(null);

			if (company == null) {
				response.put(MESSAGE, "Alert: There is no company with ID: "
						.concat(id.toString().concat(BD_ERROR)));
				response.put(STATUS_CODE, HttpStatus.NOT_FOUND);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}

			response.put(COMPANY, company);

		} catch (DataAccessException e) {
			response.put(MESSAGE, "Alert : Error when making the query in the db");
			response.put(ERROR, e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			response.put(STATUS_CODE, HttpStatus.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> save(@Valid CompanyDto companyDto, BindingResult result) {
		Map<String, Object> response = new HashMap<>();
		Company company = null;

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(err -> err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put(ERROR, errors);
			response.put(STATUS_CODE, HttpStatus.NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

		try {

			Company newCompany = new Company();
			newCompany.setStatus(companyDto.getStatusDto());
			newCompany.setNameCompany(companyDto.getNameCompanyDto());

			company = iCompany.save(newCompany);

		} catch (DataAccessException e) {
			return constraint(e);
		}

		response.put(MESSAGE, "Company created");
		response.put(COMPANY, company);
		response.put(STATUS_CODE, HttpStatus.CREATED);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<?> update(@Valid CompanyDto companyDto, BindingResult result, Long id) {

		Map<String, Object> response = new HashMap<>();
		Company company = null;
		Company currentCompany = null;

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(err -> err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put(ERROR, errors);
			response.put(STATUS_CODE, HttpStatus.NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

		try {
			currentCompany = iCompany.findById(id).orElse(null);

			if (currentCompany == null) {
				response.put(MESSAGE, "Alert: There is no company with ID: "
						.concat(id.toString().concat(BD_ERROR)));
				response.put(STATUS_CODE, HttpStatus.NOT_FOUND);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}

			currentCompany.setNameCompany(companyDto.getNameCompanyDto());
			currentCompany.setStatus(companyDto.getStatusDto());

			company = iCompany.save(currentCompany);

		} catch (DataAccessException e) {
			return constraint(e);
		}

		response.put(MESSAGE, "Company updated");
		response.put(COMPANY, company);
		response.put(STATUS_CODE, HttpStatus.OK);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	private ResponseEntity<?> constraint(DataAccessException e) {

		Map<String, Object> response = new HashMap<>();

		if (e.getMessage().contains("UQ_nameCompany")) {
			response.put(MESSAGE, "The company field is unique");
			response.put(ERROR, e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			response.put(STATUS_CODE, HttpStatus.NOT_FOUND.value());
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

		response.put(MESSAGE, "General system failure!");
		response.put(ERROR, e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
		response.put(STATUS_CODE, HttpStatus.INTERNAL_SERVER_ERROR.value());
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}

}
