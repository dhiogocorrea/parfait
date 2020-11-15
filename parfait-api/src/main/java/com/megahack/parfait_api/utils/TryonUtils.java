package com.megahack.parfait_api.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;

import com.megahack.parfait_api.config.InterServiceConfiguration;

@Component
public class TryonUtils {

	private RestOperations restTemplate;
	private InterServiceConfiguration config;
	
	@Autowired
	public TryonUtils(InterServiceConfiguration config, RestOperations restTemplate) {
		this.restTemplate = restTemplate;
		this.config = config;
	}
	
	public boolean poseEstimation(long customerId, long picId, String pictureB64) {
		String customId = customerId + "_" + picId;
		String url = this.config.getTryonUrl() + "/pose_estimation/?id_pessoa=" + customId;
		
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		String extension = "jpg";
		
		if (pictureB64.substring(0, 50).contains("jpeg")) {
			extension = ".jpeg";
		} else if (pictureB64.substring(0, 50).contains("png")) {
			extension = ".png";
		}
		
	    map.add("name", customId + extension);
	    map.add("filename", customId + extension);

	    byte[] bytes = java.util.Base64.getDecoder().decode(pictureB64);
	    ByteArrayResource contentsAsResource = new ByteArrayResource(bytes) {
	        @Override
	        public String getFilename() {
	            return customId + extension;
	        }
	    };

	    map.add("file", contentsAsResource);
		
		String response = restTemplate.postForObject(url, map, String.class);
		return response != null;
	}

	public byte[] tryOn(long customerId, long pictureId, String productId) {
		String customId = customerId + "_" + pictureId;
		String url = this.config.getTryonUrl() + "/math/?id_pessoa=" + customId + "&id_roupa=" + productId;
		
		ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET, null,
				byte[].class);
		if (response.getStatusCode() == HttpStatus.OK) {
			return response.getBody();
		} else {
			return null;
		}
	}
}
