package com.home.education.mountains.controller;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.home.education.mountains.common.TestData;
import com.home.education.mountains.common.exception.ResourceException;
import com.home.education.mountains.resource.impl.Location;
import com.home.education.mountains.service.LocationService;

@Test
public class LocationControllerTest {

	private Mockery mockery;
	private LocationService locationService;
	private LocationController locationController;
	
	@BeforeSuite
	public void before(){
		mockery = new Mockery();
		locationService = mockery.mock(LocationService.class);
		locationController = new LocationController(locationService);
	}
	
	@Test
	public void testDelete() throws ResourceException{
		final Location location = TestData.newLocation(TestData.LOCATION_ID);
		mockery.checking(new Expectations() {
			{
				oneOf(locationService).getById(TestData.LOCATION_ID);
				will(returnValue(location));
				oneOf(locationService).delete(location);
				will(returnValue(location));
			}
		});
		Location result = locationController.delete(TestData.LOCATION_ID);
		Assert.assertNotNull(result);
		Assert.assertEquals(result.getMountainRange(), TestData.MOUNTAIN_RANGE);
		Assert.assertEquals(result.getCountry(), TestData.COUNTRY);
		mockery.assertIsSatisfied();
	}
	
	@Test
	public void testGetById() throws ResourceException{
		final Location location = TestData.newLocation(TestData.LOCATION_ID);
		mockery.checking(new Expectations() {
			{
				oneOf(locationService).getById(TestData.LOCATION_ID);
				will(returnValue(location));
			}
		});
		Location result = locationController.getById(TestData.LOCATION_ID);
		Assert.assertNotNull(result);
		Assert.assertEquals(result.getMountainRange(), TestData.MOUNTAIN_RANGE);
		Assert.assertEquals(result.getCountry(), TestData.COUNTRY);
		mockery.assertIsSatisfied();
	}
	
	@Test
	public void testUpdate() throws ResourceException{
		final Location location = TestData.newLocation(TestData.LOCATION_ID);
		location.setId(0);
		mockery.checking(new Expectations() {
			{
				oneOf(locationService).update(TestData.newLocation(TestData.LOCATION_ID));
				will(returnValue(location));
			}
		});
		Location result = locationController.modify(location, TestData.LOCATION_ID);
		Assert.assertNotNull(result);
		Assert.assertEquals(result.getMountainRange(), TestData.MOUNTAIN_RANGE);
		Assert.assertEquals(result.getCountry(), TestData.COUNTRY);
		Assert.assertEquals(result.getId(), TestData.LOCATION_ID);
		mockery.assertIsSatisfied();
	}
}
