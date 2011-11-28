package com.sparcedge.dataManager;

import java.util.ArrayList;

import android.content.Context;

import com.sparcedge.database.RoomDBAdaptor;
import com.sparcedge.model.api.Room;

public class RoomManager {
		RoomDBAdaptor dbInterface;
		
		public RoomManager(Context context) {
			dbInterface = new RoomDBAdaptor(context);
			dbInterface.open();
		}
		
		public ArrayList<Room> loadRooms()
		{
			return dbInterface.getAllRooms();
		}
}
