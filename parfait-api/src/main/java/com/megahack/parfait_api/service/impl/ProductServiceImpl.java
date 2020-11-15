package com.megahack.parfait_api.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
import com.megahack.parfait_api.enums.Sex;
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
		keywords.add("sutia"); keywords.add("top"); 
	}

	@Override
	public List<Product> getSample(int size, String gender) {
		List<Product> allProducts = null;
		
		if (gender != null) {
			System.out.println("FIltering by gender = " + gender);
			allProducts = productRepository.findByGender(gender);
		} else {
			allProducts = this.getAll();
		}

		allProducts = filterOnlyTopClothes(allProducts);

		
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
		return products.stream().filter(x -> {
			for(String k : keywords) {
				if (x.getUrl().contains(k))
					return true;
			}
			return false;
		}).collect(Collectors.toList());
	}

	@Override
	public List<Product> getCustomerProducts(Customer c,
			  String terms, 
			  String brand, 
			  String category, 
			  Float lowestPrice, 
			  Float highestPrice) {
		
		Stream<Product> customerProducts = c.getProducts().stream();
		
		if (brand != null && brand != "") {
			customerProducts = customerProducts.filter(x -> x.getBrand().toLowerCase() == brand.toLowerCase());
		}
		if (category != null && category != "") {
			if (terms == null) terms = category;
			else terms += " " + category;
			
			terms = terms.trim();
		}
		
		if (lowestPrice != null && highestPrice != null) {
			if (lowestPrice > 0 && highestPrice > 0) {
				customerProducts = customerProducts.filter(x -> x.getListPrice() >= lowestPrice && x.getListPrice() <= highestPrice);
			}
		}
		
		if (terms != null && terms != "") {
			List<Product> filterProds = productRepository.findByDescriptionIgnoreCaseContaining(terms);
			List<String> filterProdsIds = filterProds.stream().map(x -> x.getProductId()).collect(Collectors.toList());

			customerProducts = customerProducts.filter(x -> filterProdsIds.contains(x.getProductId()));
		}
		
		return filterOnlyTopClothes(customerProducts.collect(Collectors.toList()));
	}

	@Override
	public List<String> getAvailableCategories() {
		return keywords;
	}

}
