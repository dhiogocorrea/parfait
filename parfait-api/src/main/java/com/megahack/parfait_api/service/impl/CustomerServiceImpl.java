package com.megahack.parfait_api.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.megahack.parfait_api.dao.Product;
import com.google.common.collect.Lists;
import com.megahack.parfait_api.dao.Customer;
import com.megahack.parfait_api.dao.Picture;
import com.megahack.parfait_api.dto.CustomerChangePasswordDto;
import com.megahack.parfait_api.dto.CustomerDto;
import com.megahack.parfait_api.enums.Status;
import com.megahack.parfait_api.repository.ProductRepository;
import com.megahack.parfait_api.repository.CustomerRepository;
import com.megahack.parfait_api.service.CustomerService;
import com.megahack.parfait_api.utils.RecommendationUtils;
import com.megahack.parfait_api.utils.TryonUtils;

@Service
public class CustomerServiceImpl implements CustomerService {

	private CustomerRepository customerRepository;
	private PasswordEncoder passwordEncoder;
	
	private ProductRepository productRepository;
	
	private RecommendationUtils recommendationUtils;
	private TryonUtils tryonUtils;

	@Autowired
	public CustomerServiceImpl(CustomerRepository customerRepository,
						       PasswordEncoder passwordEncoder,
							   ProductRepository productRepository,
							   RecommendationUtils recommendationUtils,
							   TryonUtils tryonUtils) {
		this.customerRepository = customerRepository;
		this.passwordEncoder = passwordEncoder;
		this.productRepository = productRepository;
		this.recommendationUtils = recommendationUtils;
		this.tryonUtils = tryonUtils;
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

		Customer customer = new Customer();
		customer.setName(customerDto.getName());
		customer.setLastName(customerDto.getLastName());
		customer.setEmail(customerDto.getEmail());
		customer.setPassword(passwordEncoder.encode(customerDto.getPassword()));
		customer.setHeight(customerDto.getHeight());
		customer.setWeight(customerDto.getWeight());
		customer.setSex(customerDto.getSex());
		
		List<Picture> pictures = new ArrayList<Picture>();
		pictures.add(new Picture(customerDto.getFrontPic(), customerDto.getSidePic()));
		customer.setPictures(pictures);
		
		customer.setRecommendationStatus(Status.CREATING);
		customer.setMetricsStatus(Status.CREATING);
		
		List<Product> products = new ArrayList<Product>();
		for (String productId : customerDto.getChosenProductsIds()) {
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
		List<String> newProductsIds = recommendationUtils.generateRecommendations(customer);

		Iterable<Product> newProducts = productRepository.findAllById(newProductsIds);

		List<Product> newProductsList = Lists.newArrayList(newProducts);
		
		Product p1 = productRepository.findById("552007420-552007462").get();
		newProductsList.add(p1);
		
		customer.setProducts(newProductsList);
		customer.setRecommendationStatus(Status.SUCCESS);
		
		customer = customerRepository.save(customer);

		//TRIGGER DE POSE ESTIMATION
		boolean completed = tryonUtils.poseEstimation(customer.getCustomerId(),
													  customer.getPictures().get(0).getPictureId(),
													  customer.getPictures().get(0).getFrontPic());
		
		if(completed) {
			customer.getPictures().get(0).setPoseEstimationStatus(Status.SUCCESS);
		} else {
			customer.getPictures().get(0).setPoseEstimationStatus(Status.ERROR);
		}
		
		customer = customerRepository.save(customer);
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

	@Override
	public String tryOn(Customer customer, String productId) {
		Optional<Product> opt = productRepository.findById(productId);
		
		if (opt.isPresent() && customer.getPictures().get(0) != null) {
			if (customer.getPictures().get(0).getPoseEstimationStatus() != Status.SUCCESS) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User picture still being processed or error");
			}
			
			long pictureId = customer.getPictures().get(0).getPictureId();
			
			return tryonUtils.tryOn(customer.getCustomerId(), pictureId, productId);
		}
		return null;
	}
}
