package com.megahack.parfait_api.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import com.google.gson.Gson;

import com.megahack.parfait_api.config.InterServiceConfiguration;
import com.megahack.parfait_api.dao.PostEstimationRequestModel;


@Component
public class TryonUtils {

	private RestOperations restTemplate;
	private InterServiceConfiguration config;
	
	private Gson gson = new Gson();
	
	@Autowired
	public TryonUtils(InterServiceConfiguration config, RestOperations restTemplate) {
		this.restTemplate = restTemplate;
		this.config = config;
	}
	
	public boolean poseEstimation(long customerId, long picId, String pictureB64) {
		String customId = customerId + "_" + picId;
		String url = this.config.getTryonUrl() + "/pose_estimation/";

		PostEstimationRequestModel model = new PostEstimationRequestModel();
		model.setId_pessoa(customId);
		model.setImage(pictureB64);
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		String modelSerialized = gson.toJson(model);
		
		HttpEntity<String> entity = new HttpEntity<>(modelSerialized, responseHeaders);

		String response = restTemplate.postForObject(url, entity, String.class);
		return response != null;
	}

	public String tryOn(long customerId, long pictureId, String productId) {
		String customId = customerId + "_" + pictureId;
		String url = this.config.getTryonUrl() + "/match/?id_pessoa=" + customId + "&id_roupa=" + productId;

		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null,
				String.class);
		if (response.getStatusCode() == HttpStatus.OK) {
			return response.getBody();
		} else {
			return null;
		}
	}
}

