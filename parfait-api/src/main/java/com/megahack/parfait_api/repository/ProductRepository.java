package com.megahack.parfait_api.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.megahack.parfait_api.dao.Product;

public interface ProductRepository extends CrudRepository<Product, String> {
	
	List<Product> findByDescriptionIgnoreCaseContaining(String description);
}
