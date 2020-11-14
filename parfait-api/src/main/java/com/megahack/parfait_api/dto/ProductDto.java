package com.megahack.parfait_api.dto;

import java.util.List;

import javax.persistence.Column;

import com.megahack.parfait_api.dao.Image;

public class ProductDto {

	private String id;
	private String url;
	private String title;
	private String description;
	private String skuId;
	private float percentDiscount;
	private String gender;
	private String brand;
	private float listPrice;
	List<Image> images;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	public float getPercentDiscount() {
		return percentDiscount;
	}
	public void setPercentDiscount(float percentDiscount) {
		this.percentDiscount = percentDiscount;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public float getListPrice() {
		return listPrice;
	}
	public void setListPrice(float listPrice) {
		this.listPrice = listPrice;
	}
	public List<Image> getImages() {
		return images;
	}
	public void setImages(List<Image> images) {
		this.images = images;
	}
}
