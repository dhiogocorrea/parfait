package com.megahack.parfait_api.dao;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "image")
public class Image {

	@Id
	private String id;
	private String smallImageUrl;
	private String zoomImageUrl;
	private String mediumImageUrl;
	private String thumbImageUrl;
	private String showcaseImageUrl;
	private String largeImageUrl;
	private boolean target;
	
	public Image() {}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSmallImageUrl() {
		return smallImageUrl;
	}

	public void setSmallImageUrl(String smallImageUrl) {
		this.smallImageUrl = smallImageUrl;
	}

	public String getZoomImageUrl() {
		return zoomImageUrl;
	}

	public void setZoomImageUrl(String zoomImageUrl) {
		this.zoomImageUrl = zoomImageUrl;
	}

	public String getMediumImageUrl() {
		return mediumImageUrl;
	}

	public void setMediumImageUrl(String mediumImageUrl) {
		this.mediumImageUrl = mediumImageUrl;
	}

	public String getThumbImageUrl() {
		return thumbImageUrl;
	}

	public void setThumbImageUrl(String thumbImageUrl) {
		this.thumbImageUrl = thumbImageUrl;
	}

	public String getShowcaseImageUrl() {
		return showcaseImageUrl;
	}

	public void setShowcaseImageUrl(String showcaseImageUrl) {
		this.showcaseImageUrl = showcaseImageUrl;
	}

	public String getLargeImageUrl() {
		return largeImageUrl;
	}

	public void setLargeImageUrl(String largeImageUrl) {
		this.largeImageUrl = largeImageUrl;
	}

	public boolean isTarget() {
		return target;
	}

	public void setTarget(boolean target) {
		this.target = target;
	}
}
