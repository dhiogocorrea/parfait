package com.megahack.parfait_api.dto;

public class CustomerChangePasswordDto {

	String password;
	String newPassword;
	String newPasswordConfirmation;
	
	public CustomerChangePasswordDto() {}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getNewPasswordConfirmation() {
		return newPasswordConfirmation;
	}
	public void setNewPasswordConfirmation(String newPasswordConfirmation) {
		this.newPasswordConfirmation = newPasswordConfirmation;
	}
	
	
}
