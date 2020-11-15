package com.megahack.parfait_api.service;

import com.megahack.parfait_api.dao.Customer;
import com.megahack.parfait_api.dto.CustomerChangePasswordDto;
import com.megahack.parfait_api.dto.CustomerDto;

public interface CustomerService extends BaseService<Customer, CustomerDto> {
	Customer getByEmail(String email);
	void updatePassword(Customer customer, CustomerChangePasswordDto customerChangePasswordDto);
	byte[] tryOn(Customer customer, String productId);
}
