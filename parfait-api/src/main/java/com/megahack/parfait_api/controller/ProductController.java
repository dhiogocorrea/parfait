package com.megahack.parfait_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.megahack.parfait_api.dao.Customer;
import com.megahack.parfait_api.dao.Product;
import com.megahack.parfait_api.dto.ProductDto;
import com.megahack.parfait_api.service.CustomerService;
import com.megahack.parfait_api.service.ProductService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin
public class ProductController {

	@Autowired
	ProductService productsService;
	
	@Autowired
	CustomerService customerService;

	@RequestMapping(value = "/product/sample", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List<Product> getSample(@RequestParam int size, @RequestParam(required=false) String gender) {
		return productsService.getSample(size, gender);
	}
	
	@RequestMapping(value = "/product", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List<Product> getAll() {
		return productsService.getAll();
	}
	
	@ApiOperation(value = "", authorizations = { @io.swagger.annotations.Authorization(value="jwtToken") })
	@RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public Product getOne(@PathVariable String id) {
		return productsService.getOne(id);
	}
	
	@ApiOperation(value = "", authorizations = { @io.swagger.annotations.Authorization(value="jwtToken") })
	@RequestMapping(value = "/product/me", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List<Product> getCustomerProducts(
			@RequestParam(required = false) String terms,
			@RequestParam(required = false) String brand,
			@RequestParam(required = false) String category,
			@RequestParam(required = false) float lowestPrice,
			@RequestParam(required = false) float highestPrice) {
		return productsService.getAll().subList(0, 100);
		//Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//Customer c = customerService.getByEmail(auth.getName());
		//return productsService.getCustomerProducts(c, terms, brand, category, lowestPrice, highestPrice);
	}
	
	@RequestMapping(value = "/product/categories", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List<String> availableCategories() {
		return productsService.getAvailableCategories();
	}

	@RequestMapping(value = "/product", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@RequestBody ProductDto productDto) {
		productsService.create(productDto);
	}
}
