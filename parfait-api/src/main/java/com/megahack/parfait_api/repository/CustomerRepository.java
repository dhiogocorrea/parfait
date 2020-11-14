package com.megahack.parfait_api.repository;

import org.springframework.data.repository.CrudRepository;

import com.megahack.parfait_api.dao.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {	
	Customer findByEmail(String email);
}
