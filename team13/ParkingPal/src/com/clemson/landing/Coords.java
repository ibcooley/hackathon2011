package com.clemson.landing;

public class Coords {

	private String name;
	private double latitude;
	private double longitude;
	private Boolean isOccupied;
	private Integer accX;
	private Integer accY;
	private Integer accZ;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double d) {
		this.latitude = d;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double d) {
		this.longitude = d;
	}

	public Boolean getIsOccupied() {
		return isOccupied;
	}

	public void setIsOccupied(Boolean isOccupied) {
		this.isOccupied = isOccupied;
	}

	public Integer getAccX() {
		return accX;
	}

	public void setAccX(Integer accX) {
		this.accX = accX;
	}

	public Integer getAccY() {
		return accY;
	}

	public void setAccY(Integer accY) {
		this.accY = accY;
	}

	public Integer getAccZ() {
		return accZ;
	}

	public void setAccZ(Integer accZ) {
		this.accZ = accZ;
	}

}
