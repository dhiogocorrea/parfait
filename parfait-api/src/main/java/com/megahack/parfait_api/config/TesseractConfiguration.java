package com.megahack.parfait_api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TesseractConfiguration {

	@Value("${parfait.tesseract.tessdata}")
	private String tessdataPath;

	public String getTessdataPath() {
		return tessdataPath;
	}

	public void setTessdataPath(String tessdataPath) {
		this.tessdataPath = tessdataPath;
	}
}