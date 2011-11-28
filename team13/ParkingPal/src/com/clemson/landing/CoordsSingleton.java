package com.clemson.landing;

public class CoordsSingleton {
	private static CoordsSingleton instance = null;
	private Coords[] coords;

	public Coords[] getCoords() {
		return coords;
	}

	public void setCoords(Coords[] coords) {
		this.coords = coords;
	}

	private CoordsSingleton() {
		// Exists only to defeat instantiation.
	}

	public static CoordsSingleton getInstance() {
		if (instance == null) {
			instance = new CoordsSingleton();
		}
		return instance;
	}
}
