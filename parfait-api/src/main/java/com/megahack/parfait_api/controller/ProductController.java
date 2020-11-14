package com.megahack.parfait_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.megahack.parfait_api.dao.Product;
import com.megahack.parfait_api.dto.ProductDto;
import com.megahack.parfait_api.service.ProductService;

@RestController
@CrossOrigin
public class ProductController {

	@Autowired
	ProductService productsService;

	@RequestMapping(value = "/product/sample", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List<Product> getSample(@RequestParam int size) {
		return productsService.getSample(size);
	}

	@RequestMapping(value = "/product", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@RequestBody ProductDto productDto) {
		productsService.create(productDto);
	}
}
