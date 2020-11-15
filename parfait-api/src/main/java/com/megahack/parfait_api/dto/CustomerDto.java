package com.megahack.parfait_api.dto;

import java.util.List;

import com.megahack.parfait_api.enums.Sex;

public class CustomerDto {
	
	private String name;
	private String lastName;
	private String email;
	private String password;
	private long height;
	private long weight;
	private Sex sex;
	private String frontPic;
	private String sidePic;
	private List<String> chosenProductsIds;

	public CustomerDto() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getHeight() {
		return height;
	}

	public void setHeight(long height) {
		this.height = height;
	}

	public long getWeight() {
		return weight;
	}

	public void setWeight(long weight) {
		this.weight = weight;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public String getFrontPic() {
		return frontPic;
	}

	public void setFrontPic(String frontPic) {
		this.frontPic = frontPic;
	}

	public String getSidePic() {
		return sidePic;
	}

	public void setSidePic(String sidePic) {
		this.sidePic = sidePic;
	}

	public List<String> getChosenProductsIds() {
		return chosenProductsIds;
	}

	public void setChosenProductsIds(List<String> chosenProductsIds) {
		this.chosenProductsIds = chosenProductsIds;
	}

	
}
