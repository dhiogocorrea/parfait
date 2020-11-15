package com.megahack.parfait_api.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import com.megahack.parfait_api.config.TesseractConfiguration;
import com.megahack.parfait_api.dao.Image;

import net.sourceforge.tess4j.*;

@Component
public class ImageUtils {
	
	private RestOperations restTemplate;
	TesseractConfiguration tesseractConfiguration;

	@Autowired
	public ImageUtils(TesseractConfiguration tesseractConfiguration, RestOperations restTemplate) {
		this.tesseractConfiguration = tesseractConfiguration;
		this.restTemplate = restTemplate;

	}

	public boolean isCertificate(Image image) {
		ITesseract tesseract  = new Tesseract();
		tesseract.setDatapath(tesseractConfiguration.getTessdataPath());
		tesseract.setLanguage("por");
		tesseract.setOcrEngineMode(1);
		
		String imageUrl = image.getSmallImageUrl();
		
		byte[] data = getImage(imageUrl);
		String extension = imageUrl.substring(imageUrl.lastIndexOf("."));

		try (FileOutputStream fos = new FileOutputStream(image.getId() + extension)) {
			fos.write(data);
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		
		File imageFile = new File(image.getId() + extension);
		try {
			String imageContent = tesseract.doOCR(imageFile);
			return imageContent.split(" ").length > 20;
		} catch (TesseractException e) {
			System.out.println("Deu ruim no tesseract");
		}
		
		return false;
	}
	
	private byte[] getImage(String imageUrl) {
		imageUrl = "http:" + imageUrl;
		ResponseEntity<byte[]> response = restTemplate.exchange(imageUrl, HttpMethod.GET, null, byte[].class);
		if (response.getStatusCode() == HttpStatus.OK) {
			return response.getBody();
		} else {
			return null;
		}
	}
}
