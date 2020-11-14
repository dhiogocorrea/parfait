package com.megahack.parfait_api.service;

import java.util.List;

import com.megahack.parfait_api.dao.Product;
import com.megahack.parfait_api.dto.ProductDto;

public interface ProductService extends BaseService<Product, ProductDto> {
	List<Product> getSample(int size);
}
