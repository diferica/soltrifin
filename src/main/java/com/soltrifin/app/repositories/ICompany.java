package com.soltrifin.app.repositories;

import org.springframework.data.repository.CrudRepository;

import com.soltrifin.app.entities.Company;


public interface ICompany extends CrudRepository<Company, Long>{

}
