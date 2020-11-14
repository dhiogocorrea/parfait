package com.megahack.parfait_api.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.megahack.parfait_api.dto.PictureDto;
import com.megahack.parfait_api.enums.Status;

@Entity
@Table(name = "picture")
@SequenceGenerator(name = "PICTURE_SEQ", sequenceName = "PICTURE_SEQ", initialValue = 1, allocationSize = 1)
public class Picture {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PICTURE_SEQ")
	private long pictureId;
	
	@Column(length=10485760)
	private String imageB64;
	
	@Column(length=10485760)
	private String poseEstimation;
	
	@Enumerated(EnumType.STRING)
	private Status poseEstimationStatus;

	@Temporal(TemporalType.DATE)
	private Date createdIn;
	
	public Picture() {}
	
	public Picture(PictureDto picture) {
		this.imageB64 = picture.getImageB64();
		this.poseEstimationStatus = Status.CREATING;
	}

	@PrePersist
	void onCreate() {
		this.createdIn = new Date();
	}

	public long getPictureId() {
		return pictureId;
	}

	public void setPictureId(long pictureId) {
		this.pictureId = pictureId;
	}

	public String getImageB64() {
		return imageB64;
	}

	public void setImageB64(String imageB64) {
		this.imageB64 = imageB64;
	}

	public String getPoseEstimation() {
		return poseEstimation;
	}

	public void setPoseEstimation(String poseEstimation) {
		this.poseEstimation = poseEstimation;
	}

	public Status getPoseEstimationStatus() {
		return poseEstimationStatus;
	}

	public void setPoseEstimationStatus(Status poseEstimationStatus) {
		this.poseEstimationStatus = poseEstimationStatus;
	}

	public Date getCreatedIn() {
		return createdIn;
	}

	public void setCreatedIn(Date createdIn) {
		this.createdIn = createdIn;
	}
}
