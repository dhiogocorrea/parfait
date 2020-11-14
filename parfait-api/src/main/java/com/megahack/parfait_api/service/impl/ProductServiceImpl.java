package com.megahack.parfait_api.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.megahack.parfait_api.dao.Customer;
import com.megahack.parfait_api.dao.Image;
import com.megahack.parfait_api.dao.Product;
import com.megahack.parfait_api.dto.ProductDto;
import com.megahack.parfait_api.repository.ProductRepository;
import com.megahack.parfait_api.service.ProductService;
import com.megahack.parfait_api.utils.ImageUtils;

@Service
public class ProductServiceImpl implements ProductService {

	private ProductRepository productRepository;
	private ImageUtils imageUtils;
	
	List<String> keywords = new ArrayList<String>();

	@Autowired
	public ProductServiceImpl(ProductRepository productRepository, ImageUtils imageUtils) {
		this.productRepository = productRepository;
		this.imageUtils = imageUtils;
		
		keywords.add("blusa"); keywords.add("camiseta"); keywords.add("gravata");
		keywords.add("camisa"); keywords.add("macacao"); keywords.add("body");
		keywords.add("macaquinho"); keywords.add("jaqueta"); keywords.add("blaizer"); 
		keywords.add("sueter"); keywords.add("vestido"); keywords.add("manga");
		keywords.add("sutia"); 
	}

	@Override
	public List<Product> getSample(int size, String gender) {
		List<Product> allProducts = this.getAll();
		allProducts = filterOnlyTopClothes(allProducts);
		
		if (gender != null) {
			final String genderLower = gender.toLowerCase();
			if (genderLower == "masculino" || genderLower == "feminino") {
				allProducts = allProducts.stream().filter(x -> x.getGender().toLowerCase() == genderLower)
												  .collect(Collectors.toList());
			}
		}
		
		if (size >= allProducts.size())
			return allProducts;

		Collections.shuffle(allProducts);
		return allProducts.subList(0, size);
	}

	@Override
	public List<Product> getAll() {
		List<Product> products = Lists.newArrayList(productRepository.findAll());
		return products;
	}

	@Override
	public Product get(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(ProductDto dto) {
		Product product = new Product();
		product.setProductId(dto.getId() + "-" + dto.getSkuId());
		product.setUrl(dto.getUrl());
		product.setTitle(dto.getTitle());
		product.setDescription(dto.getDescription());
		product.setSkuId(dto.getSkuId());
		product.setPercentDiscount(dto.getPercentDiscount());
		product.setGender(dto.getGender());
		product.setBrand(dto.getBrand());
		product.setListPrice(dto.getListPrice());
		
		Image lastImage = dto.getImages().get(dto.getImages().size() - 1);
		
		if (!imageUtils.isCertificate(lastImage)) {
			dto.getImages().get(dto.getImages().size() - 1).setTarget(true);
		} else {
			dto.getImages().get(dto.getImages().size() - 2).setTarget(true);
		}
		
		product.setImages(dto.getImages());

		productRepository.save(product);
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
	}

	@Override
	public Product getOne(String id) {
		Optional<Product> opt = productRepository.findById(id);
		
		if (opt.isPresent()) {
			Product p = opt.get();
			
			if (keywords.contains(p.getUrl())) {
				return p;
			}
		}
		
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id " + id);
	}
	
	private List<Product> filterOnlyTopClothes(List<Product> products) {
		keywords.add("blusa"); keywords.add("camiseta"); keywords.add("gravata");
		keywords.add("camisa"); keywords.add("macacao"); keywords.add("body");
		keywords.add("macaquinho"); keywords.add("jaqueta"); keywords.add("blaizer"); 
		keywords.add("sueter"); keywords.add("vestido"); keywords.add("manga");
		keywords.add("sutia"); 
		
		return products.stream().filter(x -> {
			return keywords.contains(x.getUrl());
		}).collect(Collectors.toList());
	}

	@Override
	public List<Product> getMyRecomendations(Customer c, String filter) {
		if (filter != null && filter != "") {
			List<Product> filterProds = productRepository.findByDescriptionIgnoreCaseContaining(filter);
			List<String> filterProdsIds = filterProds.stream().map(x -> x.getProductId()).collect(Collectors.toList());
			
			List<Product> customerProducts = c.getProducts();
			
			return customerProducts.stream().filter(x -> filterProdsIds.contains(x.getProductId()))
											.collect(Collectors.toList());
		}
		return c.getProducts();
	}

}
