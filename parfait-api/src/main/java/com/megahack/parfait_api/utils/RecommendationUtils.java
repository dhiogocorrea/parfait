package com.megahack.parfait_api.utils;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import com.google.common.collect.Lists;
import com.megahack.parfait_api.dao.Product;
import com.megahack.parfait_api.config.InterServiceConfiguration;
import com.megahack.parfait_api.dao.Customer;

@Component
public class RecommendationUtils {

	private RestOperations restTemplate;
	private InterServiceConfiguration config;
	
	@Autowired
	public RecommendationUtils(InterServiceConfiguration config, RestOperations restTemplate) {
		this.restTemplate = restTemplate;
	}

	public List<String> generateRecommendations(Customer customer) {
		List<String> recomm = new ArrayList<String>();
		
		customer.getProducts().stream().forEach(p -> {
			List<String> recommendedClothes = getProductRecommendations(p);
			
			if (recommendedClothes != null)
				recomm.addAll(recommendedClothes);
		});
		
		return recomm;
	}
	
	private List<String> getProductRecommendations(Product targetClothes) {
		String url = config.getRecommendationUrl() + "/?id=";
		ResponseEntity<String[]> response = restTemplate.exchange(url, HttpMethod.GET, null,
				String[].class);
		if (response.getStatusCode() == HttpStatus.OK) {
			return Arrays.asList(response.getBody());
		} else {
			return null;
		}
	}
}
