package com.soltrifin.app.controllersTest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.soltrifin.app.controllers.Company;
import com.soltrifin.app.services.ICompanyService;

@RunWith(MockitoJUnitRunner.Silent.class)
@SpringBootTest
@AutoConfigureMockMvc

class CompanyTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	ICompanyService iCompanyService;

	@InjectMocks
	Company companyController;

	@Test
	@DisplayName(value = "get all companies")
	void getListCompany() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/soltrifin/v1/companies")
				.contentType(MediaType.APPLICATION_JSON_VALUE);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertThat(result.getResponse().getStatus()).isEqualTo(200);

	}

	@Test
	@DisplayName(value = "get company by id")
	void getCompanyById() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/soltrifin/v1/companies/1")
				.contentType(MediaType.APPLICATION_JSON_VALUE);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertThat(result.getResponse().getStatus()).isEqualTo(200);
	}

	@Test
	@DisplayName(value = "post company")
	void newCompany() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/soltrifin/v1/companies")
				.content("{\n" + "    \"statusDto\":1,\n" + "    \"nameCompanyDto\":1\n" + "}")
				.contentType(MediaType.APPLICATION_JSON_VALUE);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertThat(result.getResponse().getStatus()).isEqualTo(200);
	}

	@Test
	@DisplayName(value = "put company")
	void updateCompany() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/soltrifin/v1/companies/1")
				.content("{\n" + "    \"statusDto\":1,\n" + "    \"nameCompanyDto\":1\n" + "}")
				.contentType(MediaType.APPLICATION_JSON_VALUE);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertThat(result.getResponse().getStatus()).isEqualTo(200);
	}

}
