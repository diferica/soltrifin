package com.soltrifin.app.dtos;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CompanyDto {

	@NotNull(message = "Status is required")
	private Integer statusDto;

	@NotEmpty(message = "Company is required")
	private String nameCompanyDto;

	public Integer getStatusDto() {
		return statusDto;
	}

	public void setStatusDto(Integer statusDto) {
		this.statusDto = statusDto;
	}

	public String getNameCompanyDto() {
		return nameCompanyDto;
	}

	public void setNameCompanyDto(String nameCompanyDto) {
		this.nameCompanyDto = nameCompanyDto;
	}

}
