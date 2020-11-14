package com.megahack.parfait_api.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestOperations;

@Configuration
public class InterServiceConfiguration {

	@Bean
	RestOperations restTemplate(RestTemplateBuilder builder) {
		Duration readTimeout = Duration.ofMinutes(10);
		builder.setReadTimeout(readTimeout);
		return builder.build();
	}
	
	@Value("${parfait.recommendation.url}")
	private String recommendationUrl;
	
	@Value("${parfait.tryon.url}")
	private String tryonUrl;

	public String getRecommendationUrl() {
		return recommendationUrl;
	}

	public void setRecommendationUrl(String recommendationUrl) {
		this.recommendationUrl = recommendationUrl;
	}

	public String getTryonUrl() {
		return tryonUrl;
	}

	public void setTryonUrl(String tryonUrl) {
		this.tryonUrl = tryonUrl;
	}
}