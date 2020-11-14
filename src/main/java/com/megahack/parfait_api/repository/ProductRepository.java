package com.megahack.parfait_api.repository;

import org.springframework.data.repository.CrudRepository;

import com.megahack.parfait_api.dao.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
