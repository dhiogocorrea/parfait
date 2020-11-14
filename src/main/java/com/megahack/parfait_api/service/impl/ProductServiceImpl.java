package com.megahack.parfait_api.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.megahack.parfait_api.dao.Product;
import com.megahack.parfait_api.dto.ProductDto;
import com.megahack.parfait_api.repository.ProductRepository;
import com.megahack.parfait_api.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	private ProductRepository clothesRepository;
	
	@Autowired
	public ProductServiceImpl(ProductRepository clothesRepository) {
		this.clothesRepository = clothesRepository;
	}

	@Override
	public List<Product> getSample(int size) {
		List<Product> allClothes = Lists.newArrayList(clothesRepository.findAll());
		
		if (size >= allClothes.size())
			return allClothes;

		Collections.shuffle(allClothes);
		return allClothes.subList(0, size);
	}

	@Override
	public List<Product> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product get(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(ProductDto dto) {
		// TODO Auto-generated method stub
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
	}

}
