package com.megahack.parfait_api.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.megahack.parfait_api.enums.Status;
import com.megahack.parfait_api.enums.Sex;

@Entity
@Table(name = "customer")
@SequenceGenerator(name = "CUSTOMER_SEQ", sequenceName = "CUSTOMER_SEQ", initialValue = 1, allocationSize = 1)
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUSTOMER_SEQ")
	private long customerId;
	private String name;
	private String lastName;
	private String email;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String password;
	
	private long height;
	private long weight;
	
	@Enumerated(EnumType.STRING)
	private Sex sex;
	
	@OneToMany(
        cascade = CascadeType.ALL,
        orphanRemoval = true
	)
	List<Picture> pictures;
	
	@OneToOne
	Metrics metrics;

	@ManyToMany
	List<Product> products;
	
	@Enumerated(EnumType.STRING)
	private Status recommendationStatus;

	@Enumerated(EnumType.STRING)
	private Status metricsStatus;

	@Temporal(TemporalType.DATE)
	private Date createdIn;

	public Customer() {}
	
	@PrePersist
	void onCreate() {
		this.createdIn = new Date();
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

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

	public List<Picture> getPictures() {
		return pictures;
	}

	public void setPictures(List<Picture> pictures) {
		this.pictures = pictures;
	}

	public Metrics getMetrics() {
		return metrics;
	}

	public void setMetrics(Metrics metrics) {
		this.metrics = metrics;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Status getRecommendationStatus() {
		return recommendationStatus;
	}

	public void setRecommendationStatus(Status recommendationStatus) {
		this.recommendationStatus = recommendationStatus;
	}

	public Status getMetricsStatus() {
		return metricsStatus;
	}

	public void setMetricsStatus(Status metricsStatus) {
		this.metricsStatus = metricsStatus;
	}

	public Date getCreatedIn() {
		return createdIn;
	}

	public void setCreatedIn(Date createdIn) {
		this.createdIn = createdIn;
	}
}
