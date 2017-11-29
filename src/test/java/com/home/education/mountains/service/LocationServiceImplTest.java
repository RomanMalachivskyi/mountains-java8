package com.home.education.mountains.service;

import java.util.Collection;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.home.education.mountains.common.TestData;
import com.home.education.mountains.common.exception.LocationValidationFailedException;
import com.home.education.mountains.common.exception.ResourceException;
import com.home.education.mountains.dao.LocationDao;
import com.home.education.mountains.resource.impl.Location;
import com.home.education.mountains.service.impl.LocationServiceImpl;

@Test
public class LocationServiceImplTest {

	private Mockery mockery;
	private LocationDao dao;
	private LocationServiceImpl locationService;
	
	@BeforeSuite
	public void before(){
		mockery = new Mockery();
		dao = mockery.mock(LocationDao.class);
		locationService = new LocationServiceImpl(dao);
	}
	
	@Test
	public void testGetAllFilteredEmpty() throws LocationValidationFailedException{
		mockery.checking(new Expectations() {
			{
				oneOf(dao).getAll();
				will(returnValue(Lists.newArrayList()));
			}
		});
		Collection<Location> result = locationService.getAllLocationFilterByMountainRangeAndCountry("Karpaty", "Ukraine");
		Assert.assertTrue(result.isEmpty());
	}
	
	@Test
	public void testGetAllFiltered() throws LocationValidationFailedException{
		final Location location = TestData.newLocation(1);
		location.setCountry("Ukraine");
		location.setMountainRange("Karpaty");
		final Location location2 = TestData.newLocation(2);
		location2.setCountry("Georgia");
		location2.setMountainRange("Kavkaz");
		mockery.checking(new Expectations() {
			{
				oneOf(dao).getAll();
				will(returnValue(Lists.newArrayList(location,location2)));
			}
		});
		Collection<Location> result = locationService.getAllLocationFilterByMountainRangeAndCountry("Karpaty", "Ukraine");
		Assert.assertFalse(result.isEmpty());
		Assert.assertEquals(1, result.size());
		Location expected = Iterables.getOnlyElement(result);
		Assert.assertEquals(TestData.COUNTRY, expected.getCountry());
		Assert.assertEquals(TestData.MOUNTAIN_RANGE, expected.getMountainRange());
	}
	
	@Test
	public void testGetAllByCoutry() throws LocationValidationFailedException{
		final Location location = TestData.newLocation(1);
		location.setCountry("Ukraine");
		location.setMountainRange("Karpaty");
		final Location location2 = TestData.newLocation(2);
		location2.setCountry("Georgia");
		location2.setMountainRange("Kavkaz");
		mockery.checking(new Expectations() {
			{
				oneOf(dao).getAll();
				will(returnValue(Lists.newArrayList(location,location2)));
			}
		});
		Collection<Location> result = locationService.getAllLocationFilterByContry("Ukraine");
		Assert.assertFalse(result.isEmpty());
		Assert.assertEquals(1, result.size());
		Location expected = Iterables.getOnlyElement(result);
		Assert.assertEquals(TestData.COUNTRY, expected.getCountry());
	}
	
	@Test
	public void testGetLocationByMountainRange() throws LocationValidationFailedException{
		final Location location = TestData.newLocation(1);
		location.setCountry("Ukraine");
		location.setMountainRange("Karpaty");
		final Location location2 = TestData.newLocation(2);
		location2.setCountry("Georgia");
		location2.setMountainRange("Kavkaz");
		mockery.checking(new Expectations() {
			{
				oneOf(dao).getAll();
				will(returnValue(Lists.newArrayList(location,location2)));
			}
		});
		Collection<Location> result = locationService.getAllLocationFilterByMountainRange("Karpaty");
		Assert.assertFalse(result.isEmpty());
		Assert.assertEquals(1, result.size());
		Location expected = Iterables.getOnlyElement(result);
		Assert.assertEquals(TestData.MOUNTAIN_RANGE, expected.getMountainRange());
	}
	
	@Test
	public void testCreateLocation() throws ResourceException{
		int locationId = 1;
		final Location location = TestData.newLocation(locationId);
		mockery.checking(new Expectations() {
			{
				oneOf(dao).create(location);
				will(returnValue(location));
			}
		});
		Location result = locationService.create(location);
		Assert.assertNotNull(result);
		Assert.assertEquals(TestData.COUNTRY, location.getCountry());
		Assert.assertEquals(locationId, location.getId());
		Assert.assertEquals(TestData.MOUNTAIN_RANGE, location.getMountainRange());
	}
	
	@Test
	public void testUpdateLocation() throws ResourceException{
		int locationId = 1;
		final Location location = TestData.newLocation(locationId);
		mockery.checking(new Expectations() {
			{
				oneOf(dao).update(location);
				will(returnValue(location));
			}
		});
		Location result = locationService.update(location);
		Assert.assertNotNull(result);
		Assert.assertEquals(TestData.COUNTRY, location.getCountry());
		Assert.assertEquals(locationId, location.getId());
		Assert.assertEquals(TestData.MOUNTAIN_RANGE, location.getMountainRange());
	}
	
	@Test
	public void testDeleteLocation() throws ResourceException{
		int locationId = 1;
		final Location location = TestData.newLocation(locationId);
		mockery.checking(new Expectations() {
			{
				oneOf(dao).delete(location);
				will(returnValue(location));
			}
		});
		Location result = locationService.delete(location);
		Assert.assertNotNull(result);
		Assert.assertEquals(TestData.COUNTRY, result.getCountry());
		Assert.assertEquals(locationId, result.getId());
		Assert.assertEquals(TestData.MOUNTAIN_RANGE, result.getMountainRange());
	}
	
	@AfterTest
	public void after(){
		mockery.assertIsSatisfied();
	}
}
