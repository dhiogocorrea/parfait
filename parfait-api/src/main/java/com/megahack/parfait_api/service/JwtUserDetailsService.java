package com.megahack.parfait_api.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.megahack.parfait_api.dao.Customer;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
    private CustomerService customerService;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Customer customer = customerService.getByEmail(email);
		if (customer != null && customer.getEmail().equals(email)) {
			return new User(customer.getEmail(), customer.getPassword(), new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User not found with email: " + email);
		}
	}
}
