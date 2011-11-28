package com.sparcedge.groceryscanner;

public class ItemInfo {
	String list_name = null;
	String item_name = null;
	String item_upc = null;
	String item_img =  null;
	String item_qty = null;
	long row = 0;
	
	ItemInfo() {	
	}
	
	ItemInfo(String list, String name, String upc, String img, String qty) {
		list_name = list;
		item_name = name;
		item_upc = upc;
		item_img = img;
		item_qty = qty;
		GroceryScanner.LOG("GOT A LIST NAME: " + list);
	}
	
	public String getList() { return list_name; }
	public String getName() { return item_name; }
	public String getUPC() { return item_upc; }
	public String getImg() { return item_img; }
	public String getQty() { return item_qty; }
	public Long getRow() { return row; }
	public void setRow(long r) { row = r; }
	
	public String toString() {
		return "list: " + list_name + " product: " + item_name + " upc:" + item_upc + 
		" " +		   item_img + " qty: " + item_qty;
	}

}
