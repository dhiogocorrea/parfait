package com.megahack.parfait_api.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.megahack.parfait_api.dao.Product;
import com.megahack.parfait_api.dao.Customer;
import com.megahack.parfait_api.dao.Picture;
import com.megahack.parfait_api.dto.CustomerChangePasswordDto;
import com.megahack.parfait_api.dto.CustomerDto;
import com.megahack.parfait_api.enums.Status;
import com.megahack.parfait_api.repository.ProductRepository;
import com.megahack.parfait_api.repository.CustomerRepository;
import com.megahack.parfait_api.service.CustomerService;
import com.megahack.parfait_api.utils.RecommendationUtils;

@Service
public class CustomerServiceImpl implements CustomerService {

	private CustomerRepository customerRepository;
	private PasswordEncoder passwordEncoder;
	
	private ProductRepository productRepository;
	
	private RecommendationUtils recommendationUtils;

	@Autowired
	public CustomerServiceImpl(CustomerRepository customerRepository,
						       PasswordEncoder passwordEncoder,
							   ProductRepository productRepository,
							   RecommendationUtils recommendationUtils) {
		this.customerRepository = customerRepository;
		this.passwordEncoder = passwordEncoder;
		this.productRepository = productRepository;
		this.recommendationUtils = recommendationUtils;
	}
	
	@Override
	public Customer getByEmail(String email) {
		return customerRepository.findByEmail(email);
	}
	
	@Override
	public List<Customer> getAll() {
		ArrayList<Customer> result = new ArrayList<Customer>();
		customerRepository.findAll().forEach(result::add);
		return result;
	}

	@Override
	public Customer get(long id) {
		return customerRepository.findById(id).get();
	}

	@Override
	public void create(CustomerDto customerDto) {
		if (this.getByEmail(customerDto.getEmail()) != null) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered.");
		}
		if(customerDto.getPassword() != customerDto.getConfirmationPassword()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password and Confirmation "
					+ "password does not matches.");
		}
		Customer customer = new Customer();
		customer.setName(customerDto.getName());
		customer.setLastName(customerDto.getLastName());
		customer.setEmail(customerDto.getEmail());
		customer.setPassword(passwordEncoder.encode(customerDto.getPassword()));
		customer.setHeight(customerDto.getHeight());
		customer.setWeight(customerDto.getWeight());
		customer.setSex(customerDto.getSex());
		
		List<Picture> pictures = new ArrayList<Picture>();
		pictures.add(new Picture(customerDto.getPicture()));

		customer.setPictures(pictures);
		
		customer.setRecommendationStatus(Status.CREATING);
		customer.setMetricsStatus(Status.CREATING);
		
		List<Product> products = new ArrayList<Product>();
		for (Long productId : customerDto.getChosenProductsIds()) {
			productRepository.findById(productId).ifPresent(p -> {
				products.add(p);
			});
		}
		customer.setProducts(products);
		
		Customer newCustomer = customerRepository.save(customer);
		new Thread(() -> triggerProcessesWhenUserCreated(newCustomer)).start();
	}
	
	private void triggerProcessesWhenUserCreated(Customer customer) {
		//TRIGGER DA RECOMENDAÇÃO
		List<Product> newProducts = recommendationUtils.generateRecommendations(customer);
		customer.setProducts(newProducts);
		customer.setRecommendationStatus(Status.SUCCESS);
		
		customerRepository.save(customer);

		//TRIGGER DE POSE ESTIMATION
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
	}

	@Override
	public void updatePassword(Customer customer, CustomerChangePasswordDto customerChangePasswordDto) {
		if (customerChangePasswordDto.getNewPassword() != customerChangePasswordDto.getNewPasswordConfirmation()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password and Confirmation password does not matches.");
		}

		customer.setPassword(passwordEncoder.encode(customerChangePasswordDto.getNewPassword()));
		customerRepository.save(customer);
	}
}
