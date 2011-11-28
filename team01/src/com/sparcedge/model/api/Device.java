package com.sparcedge.model.api;

public class Device {

	private int id;
	private String name;
	private String manufacturer;
	private Float usage;
	private String usageType;
	private Float powerUsage;
	private Float minUsage;
	private Float maxUsage;
	private String description;
	
	
	public Device() {
		
		//add method here for implementing loading "default" values
		//adding this to test commit
	}
	
	public Device loadDeviceDefault(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Device loadDevice(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveDevice() {
		// TODO Auto-generated method stub
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Float getUsage() {
		return usage;
	}

	public void setUsage(Float usage) {
		this.usage = usage;
	}

	public String getUsageType() {
		return usageType;
	}

	public void setUsageType(String usageType) {
		this.usageType = usageType;
	}

	public Float getPowerUsage() {
		return powerUsage;
	}

	public void setPowerUsage(Float powerUsage) {
		this.powerUsage = powerUsage;
	}

	public void setMinUsage(Float minUsage) {
		this.minUsage = minUsage;
	}

	public Float getMinUsage() {
		return minUsage;
	}

	public void setMaxUsage(Float maxUsage) {
		this.maxUsage = maxUsage;
	}

	public Float getMaxUsage() {
		return maxUsage;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	
}
