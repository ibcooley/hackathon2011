package com.sparcedge;

import java.util.EmptyStackException;
import java.util.List;

import com.sparcedge.dataManager.DeviceManager;
import com.sparcedge.dataManager.RoomManager;
import com.sparcedge.model.api.Device;
import com.sparcedge.model.api.Room;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class RoomsListActivity extends ListActivity {

	private static String[] data = new String[] { "0", "1" };

	private EfficientAdapter adap;

	public class ButtonHandler implements View.OnClickListener {
		public void onClick(View v) {
			handleButtonClick();
		}
	}
	public class EmptyButtonHandler implements View.OnClickListener {
		public void onClick(View v) {
			handleEmptyButtonClick();
		}
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.device_list);
		adap = new EfficientAdapter(this);
		setListAdapter(adap);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Toast.makeText(this, "Click-" + String.valueOf(position),
				Toast.LENGTH_SHORT).show();
	}

	public class EfficientAdapter extends BaseAdapter implements
			Filterable {
		private LayoutInflater mInflater;
		private Context context;

		public EfficientAdapter(Context context) {
			// Cache the LayoutInflate to avoid asking for a new one each time.
			mInflater = LayoutInflater.from(context);
			this.context = context;
		}

		/**
		 * Make a view to hold each row.
		 * 
		 * @see android.widget.ListAdapter#getView(int, android.view.View,
		 *      android.view.ViewGroup)
		 */
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// A ViewHolder keeps references to children views to avoid
			// unneccessary calls
			// to findViewById() on each row.
			ViewHolder holder;

			RoomManager roomsManagerService = new RoomManager(context);

			List<Room> roomList = roomsManagerService.loadRooms();

			convertView = mInflater.inflate(R.layout.device_adapter, null);

			// Creates a ViewHolder and store references to the two children
			// views
			// we want to bind data to.
			holder = new ViewHolder();
			holder.textLine = (TextView) convertView
					.findViewById(R.id.textLine);
			holder.textLine.setText(roomList.get(position).getName());
			holder.iconLine = (ImageView) convertView
					.findViewById(R.id.iconLine);
			holder.buttonLine = (Button) convertView
					.findViewById(R.id.checkBox);

			if(roomList.get(position).getName().equals("Kitchen") ){
				holder.textLine.setOnClickListener(new ButtonHandler());
			} 




			convertView.setTag(holder);

			// Get flag name and id
			String filename = "flag_" + String.valueOf(position);
			int id = context.getResources().getIdentifier(filename, "drawable",
					context.getString(R.string.package_name));

			// Bind the data efficiently with the holder.
			holder.textLine.setText(roomList.get(position).getName());

			return convertView;
		}

		class ViewHolder {
			TextView textLine;
			ImageView iconLine;
			Button buttonLine;
		}

		public Filter getFilter() {
			// TODO Auto-generated method stub
			return null;
		}

		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		public int getCount() {
			RoomManager roomManagerService = new RoomManager(context);
			List<Room> deviceList = roomManagerService.loadRooms();
			return deviceList.size();
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return data[position];
		}

	}
	public void handleButtonClick() {
		startActivity(new Intent(this, DeviceListActivity.class));
	}
	public void handleEmptyButtonClick() {
		startActivity(new Intent(this, EmptyListActivity.class));
	}
}