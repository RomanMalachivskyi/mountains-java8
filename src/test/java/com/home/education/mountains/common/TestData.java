package com.home.education.mountains.common;

import com.google.common.collect.Range;
import com.home.education.mountains.resource.impl.Category;
import com.home.education.mountains.resource.impl.Location;
import com.home.education.mountains.resource.impl.Mountain;
import com.home.education.mountains.resource.impl.Route;

public class TestData {

	public static final String COUNTRY = "Ukraine";
	public static final String MOUNTAIN_RANGE = "Karpaty";
	public static final String DESCRIPTION = "description";
	public static final int LOCATION_ID = 1;
	public static final int HEIGHT = 2060;
	public static final String MOUNTAIN_NAME = "Goverla";
	public static final int MOUNTAIN_ID = 2;
	public static final String ROUTE_NAME = "classic";
	public static final int CATEGORY_ID = 3;
	public static final String CATEGORY_NAME = "2a";
	public static final int MAX_HEIGHT = 6000;
	public static final int MIN_HEIGHT = 1000;
	public static final Range<Integer> RANGE = Range.openClosed(MIN_HEIGHT, MAX_HEIGHT);
	public static final int ROUTE_ID = 4;

	public static Location newLocation (int id){
		Location location = new Location();
		location.setId(id);
		location.setCountry(COUNTRY);
		location.setMountainRange(MOUNTAIN_RANGE);
		location.setDescription(DESCRIPTION);
		return location;
	}
	
	public static Mountain newMountain (int id){
		Mountain mountain = new Mountain();
		mountain.setId(id);
		mountain.setLocationId(LOCATION_ID);
		mountain.setHeight(HEIGHT);
		mountain.setName(MOUNTAIN_NAME);
		mountain.setDescription(DESCRIPTION);
		return mountain;
	}
	
	public static Route newRoute (int id){
		Route route = new Route();
		route.setId(id);
		route.setMountainId(MOUNTAIN_ID);
		route.setName(ROUTE_NAME);
		route.setDescription(DESCRIPTION);
		route.setCategoryId(CATEGORY_ID);
		return route;
	}
	
	public static Category newCategory (int id){
		Category category = new Category();
		category.setId(id);
		category.setName(CATEGORY_NAME);
		category.setDescription(DESCRIPTION);
		return category;
	}
}
