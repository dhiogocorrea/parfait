package com.megahack.parfait_api.service;

import java.util.List;

import com.megahack.parfait_api.dao.Customer;
import com.megahack.parfait_api.dao.Product;
import com.megahack.parfait_api.dto.ProductDto;

public interface ProductService extends BaseService<Product, ProductDto> {
	List<Product> getSample(int size, String gender);
	Product getOne(String id);
	List<Product> getCustomerProducts(Customer c, 
									  String terms, 
									  String brand, 
									  String category, 
									  float lowestPrice, 
									  float highestPrice);
	List<String> getAvailableCategories();
}
