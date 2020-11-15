package com.megahack.parfait_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.megahack.parfait_api.dao.Customer;
import com.megahack.parfait_api.dto.CustomerChangePasswordDto;
import com.megahack.parfait_api.dto.CustomerDto;
import com.megahack.parfait_api.service.CustomerService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin
public class CustomerController {
	
	@Autowired
	CustomerService customerService;

	@ApiOperation(value = "", authorizations = { @io.swagger.annotations.Authorization(value="jwtToken") })
	@RequestMapping(value = "/users/me", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public Customer getMe() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return customerService.getByEmail(auth.getName());
	}

	@RequestMapping(value = "/users", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@RequestBody CustomerDto customerDto) {
		customerService.create(customerDto);
	}
	
	@ApiOperation(value = "", authorizations = { @io.swagger.annotations.Authorization(value="jwtToken") })
	@RequestMapping(value = "/users/changepassword", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updatePassword(@RequestBody CustomerChangePasswordDto customerChangePasswordDto) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Customer c = customerService.getByEmail(auth.getName());
		customerService.updatePassword(c, customerChangePasswordDto);
	}
	
	@ApiOperation(value = "", authorizations = { @io.swagger.annotations.Authorization(value="jwtToken") })
	@RequestMapping(value = "/users/tryon/{productId}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public String tryon(@PathVariable String productId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Customer c = customerService.getByEmail(auth.getName());

		return customerService.tryOn(c, productId);
	}
}
