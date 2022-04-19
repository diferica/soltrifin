package com.soltrifin.app.servicesTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.soltrifin.app.dtos.CompanyDto;
import com.soltrifin.app.entities.Company;
import com.soltrifin.app.repositories.ICompany;
import com.soltrifin.app.services.CompanyService;

@RunWith(MockitoJUnitRunner.Silent.class)
@SpringBootTest
class CompanyServiceTest {

	@Mock
	ICompany iCompany;

	@Mock
	DataAccessException dataAccessException;

	@InjectMocks
	CompanyService companyService;

	@Mock
	private BindingResult mockBindingResult;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		when(mockBindingResult.hasErrors()).thenReturn(false);
	}

	@Test
	@DisplayName(value = "get all companies true")
	void getAllCompaniesTrue() {

		List<Company> companies = new ArrayList<Company>();
		companies.add(mock(Company.class));
		when(iCompany.findAll()).thenReturn(companies);
		ResponseEntity<?> response = companyService.getAll();

		assertThat(response.getStatusCodeValue()).isEqualTo(200);

	}

	@Test
	@DisplayName(value = "get all companies false")
	void getAllCompaniesFalse() {
		ResponseEntity<?> response = companyService.getAll();
		assertThat(response.getStatusCodeValue()).isEqualTo(404);

	}

	@Test
	@DisplayName(value = "get company by id true")
	void getCompanybyIdTrue() {
		Long id = (long) 1;
		List<Company> Company = new ArrayList<Company>();
		Company.add(mock(Company.class));
		Optional<Company> selectCompany = Optional.of(Company.get(0));
		when(iCompany.findById(any())).thenReturn(selectCompany);
		ResponseEntity<?> response = companyService.getCompanyById(id);
		assertThat(response.getStatusCodeValue()).isEqualTo(200);

	}

	@Test
	@DisplayName(value = "get company by id false")
	void getCompanybyIdFalse() {
		Long id = (long) 1;
		ResponseEntity<?> response = companyService.getCompanyById(id);
		assertThat(response.getStatusCodeValue()).isEqualTo(404);

	}

	@Test
	@DisplayName(value = "Create company false")
	void newCompanyFalse() {
		when(mockBindingResult.hasErrors()).thenReturn(true);
		CompanyDto company = new CompanyDto();
		ResponseEntity<?> response = companyService.save(company, mockBindingResult);
		assertThat(response.getStatusCodeValue()).isEqualTo(404);
	}

	@Test
	@DisplayName(value = "Create company true")
	void newCompanyTrue() {

		CompanyDto company = new CompanyDto();
		company.setNameCompanyDto("test");
		company.setStatusDto(1);

		ResponseEntity<?> response = companyService.save(company, mockBindingResult);
		assertThat(response.getStatusCodeValue()).isEqualTo(201);
	}

	@Test
	@DisplayName(value = "update company false")
	void updateCompanyFalse() {
		when(mockBindingResult.hasErrors()).thenReturn(true);
		CompanyDto company = new CompanyDto();
		ResponseEntity<?> response = companyService.update(company, mockBindingResult, (long) 10);
		assertThat(response.getStatusCodeValue()).isEqualTo(404);
	}

	@Test
	@DisplayName(value = "update company findbyidfalse")
	void updateCompanyFindByIdFalse() {

		CompanyDto company = new CompanyDto();
		company.setNameCompanyDto("test");
		company.setStatusDto(1);

		List<Company> Company = new ArrayList<Company>();
		Company.add(mock(Company.class));

		ResponseEntity<?> response = companyService.update(company, mockBindingResult, (long) 10);
		assertThat(response.getStatusCodeValue()).isEqualTo(404);
	}

	@Test
	@DisplayName(value = "update company true")
	void updateCompanyTrue() {
		CompanyDto company = new CompanyDto();
		company.setNameCompanyDto("test");
		company.setStatusDto(1);

		List<Company> Company = new ArrayList<Company>();
		Company.add(mock(Company.class));
		Optional<Company> selectCompany = Optional.of(Company.get(0));
		when(iCompany.findById(any())).thenReturn(selectCompany);

		ResponseEntity<?> response = companyService.update(company, mockBindingResult, (long) 10);
		assertThat(response.getStatusCodeValue()).isEqualTo(201);
	}

}
