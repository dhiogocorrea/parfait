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

import com.megahack.parfait_api.enums.Status;

@Entity
@Table(name = "picture")
@SequenceGenerator(name = "PICTURE_SEQ", sequenceName = "PICTURE_SEQ", initialValue = 1, allocationSize = 1)
public class Picture {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PICTURE_SEQ")
	private long pictureId;
	
	@Column(length=10485760)
	private String frontPic;
	
	@Column(length=10485760)
	private String sidePic;
	
	@Enumerated(EnumType.STRING)
	private Status poseEstimationStatus;

	@Temporal(TemporalType.DATE)
	private Date createdIn;
	
	public Picture() {}

	public Picture(String frontPic, String sidePic) {
		this.frontPic = frontPic;
		this.sidePic = sidePic;
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
