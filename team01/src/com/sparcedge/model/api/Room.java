package com.sparcedge.model.api;

import java.util.List;

import com.sparcedge.model.api.Room;
import com.sparcedge.model.api.Device;

public class Room {

	private int id;
	private String name;
	private List<Device> deviceList;
	private int cflLightCount;
	private int incandescantLightCount;
	private int fanCount;
	private String description;
	
	
	public Room() {
		//add method here for implementing loading "default" values
	}
	
	public Room loadRoomDefault(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Room loadRoom(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveRoom() {
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

	public List<Device> getDeviceList() {
		return deviceList;
	}

	public void setDeviceList(List<Device> deviceList) {
		this.deviceList = deviceList;
	}

	public int getFanCount() {
		return fanCount;
	}

	public void setFanCount(int fanCount) {
		this.fanCount = fanCount;
	}

	public int getIncandescantLightCount() {
		return incandescantLightCount;
	}

	public void setIncandescantLightCount(int incandescantLightCount) {
		this.incandescantLightCount = incandescantLightCount;
	}

	public int getCflLightCount() {
		return cflLightCount;
	}

	public void setCflLightCount(int cflLightCount) {
		this.cflLightCount = cflLightCount;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
