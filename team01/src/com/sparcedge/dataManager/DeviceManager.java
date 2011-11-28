package com.sparcedge.dataManager;

import java.util.ArrayList;

import android.content.Context;

import com.sparcedge.database.DeviceDBAdaptor;
import com.sparcedge.model.api.Device;

public class DeviceManager {
	DeviceDBAdaptor dbInterface;
	
	public DeviceManager(Context context) {
		dbInterface = new DeviceDBAdaptor(context);
		dbInterface.open();
	}
	
	public ArrayList<Device> loadDevices()
	{
		return dbInterface.getAllDevices();
	}
}
