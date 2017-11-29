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
import com.home.education.mountains.common.exception.LocationDoesNotExistsException;
import com.home.education.mountains.common.exception.LocationValidationFailedException;
import com.home.education.mountains.common.exception.ResourceException;
import com.home.education.mountains.dao.MountainDao;
import com.home.education.mountains.resource.impl.Mountain;
import com.home.education.mountains.service.impl.MountainServiceImpl;

@Test
public class MountainServiceImplTest {

	private Mockery mockery;
	private MountainDao dao;
	private LocationService locationService;
	private MountainServiceImpl service;
	
	@BeforeSuite
	public void before(){
		mockery = new Mockery();
		dao = mockery.mock(MountainDao.class);
		locationService =  mockery.mock(LocationService.class);
		service = new MountainServiceImpl(dao, locationService);
	}
	
	@Test
	public void testGetAllFilteredEmpty() throws LocationValidationFailedException{
		mockery.checking(new Expectations() {
			{
				oneOf(dao).getAll();
				will(returnValue(Lists.newArrayList()));
			}
		});
		Collection<Mountain> result = service.getAllFilterByLocationIdsAndHeight(null, null);
		Assert.assertTrue(result.isEmpty());
	}

	@Test
	public void testGetAllFiltered() throws LocationValidationFailedException{
		final Mountain mountain = TestData.newMountain(1);
		mountain.setLocationId(TestData.LOCATION_ID);
		final Mountain mountain2 = TestData.newMountain(2);
		mountain2.setLocationId(33333);
		mockery.checking(new Expectations() {
			{
				oneOf(dao).getViaLocationIds(Lists.newArrayList(TestData.LOCATION_ID));
				will(returnValue(Lists.newArrayList(mountain)));
			}
		});
		Collection<Integer> locationIds = Lists.newArrayList(TestData.LOCATION_ID);
		Collection<Mountain> results = service.getAllFilterByLocationIdsAndHeight(locationIds, null);
		Assert.assertFalse(results.isEmpty());
		Assert.assertEquals(1, results.size());
		Mountain result = Iterables.getOnlyElement(results);
		Assert.assertEquals(TestData.MOUNTAIN_NAME, result.getName());
		Assert.assertEquals(TestData.HEIGHT, result.getHeight());
		Assert.assertEquals(TestData.LOCATION_ID, result.getLocationId());
	}
	
	@Test
	public void testGetAllFilteredByLocationIds() throws LocationValidationFailedException{
		final Mountain mountain = TestData.newMountain(1);
		mountain.setLocationId(TestData.LOCATION_ID);
		final Mountain mountain2 = TestData.newMountain(2);
		mountain2.setLocationId(33333);
		mockery.checking(new Expectations() {
			{
				oneOf(dao).getViaLocationIds(Lists.newArrayList(TestData.LOCATION_ID));
				will(returnValue(Lists.newArrayList(mountain)));
			}
		});
		Collection<Mountain> results = service.getAllFilterByLocationIds(Lists.newArrayList(TestData.LOCATION_ID));
		Assert.assertFalse(results.isEmpty());
		Assert.assertEquals(1, results.size());
		Mountain result = Iterables.getOnlyElement(results);
		Assert.assertEquals(TestData.MOUNTAIN_NAME, result.getName());
		Assert.assertEquals(TestData.HEIGHT, result.getHeight());
		Assert.assertEquals(TestData.LOCATION_ID, result.getLocationId());
	}
	

	@Test
	public void testGetAllFilteredByLocationIdsEmpty() throws LocationValidationFailedException{
		final Mountain mountain = TestData.newMountain(1);
		mountain.setLocationId(TestData.LOCATION_ID);
		final Mountain mountain2 = TestData.newMountain(2);
		mountain2.setLocationId(33333);
		mockery.checking(new Expectations() {
			{
				oneOf(dao).getViaLocationIds(Lists.newArrayList(TestData.LOCATION_ID));
				will(returnValue(Lists.newArrayList()));
			}
		});
		Collection<Mountain> results = service.getAllFilterByLocationIds(Lists.newArrayList(TestData.LOCATION_ID));
		Assert.assertTrue(results.isEmpty());
	}
	
	@Test
	public void testGetAllFilteredByHeight() throws LocationValidationFailedException{
		final Mountain mountain = TestData.newMountain(1);
		mountain.setHeight(3000);
		final Mountain mountain2 = TestData.newMountain(2);
		mountain2.setHeight(7000);
		mockery.checking(new Expectations() {
			{
				oneOf(dao).getAll();
				will(returnValue(Lists.newArrayList(mountain, mountain2)));
			}
		});
		Collection<Mountain> results = service.getAllFilterByHeight(TestData.RANGE);
		Assert.assertFalse(results.isEmpty());
		Assert.assertEquals(1, results.size());
		Mountain result = Iterables.getOnlyElement(results);
		Assert.assertEquals(TestData.MOUNTAIN_NAME, result.getName());
		Assert.assertTrue(result.getHeight() < TestData.RANGE.upperEndpoint());
		Assert.assertTrue(result.getHeight() > TestData.RANGE.lowerEndpoint());
		Assert.assertEquals(TestData.LOCATION_ID, result.getLocationId());
	}
	
	@Test
	public void testGetAll() throws LocationValidationFailedException{
		final Mountain mountain = TestData.newMountain(1);
		final Mountain mountain2 = TestData.newMountain(2);
		mockery.checking(new Expectations() {
			{
				oneOf(dao).getAll();
				will(returnValue(Lists.newArrayList(mountain, mountain2)));
			}
		});
		Collection<Mountain> results = service.getAllFilterByLocationIdsAndHeight(null, null);
		Assert.assertFalse(results.isEmpty());
		Assert.assertEquals(2, results.size());
	}
	
	@Test
	public void testCreateMountain() throws ResourceException{
		final int mountainId = 1;
		final Mountain mountain = TestData.newMountain(mountainId);
		mockery.checking(new Expectations() {
			{
				oneOf(locationService).getById(mountain.getLocationId());
				will(returnValue(TestData.newLocation(1)));
				oneOf(dao).create(mountain);
				will(returnValue(mountain));
			}
		});
		Mountain result = service.create(mountain);
		Assert.assertNotNull(result);
		Assert.assertEquals(TestData.MOUNTAIN_NAME, mountain.getName());
		Assert.assertEquals(mountainId, result.getId());
		Assert.assertEquals(TestData.HEIGHT, result.getHeight());
	}
	
	@Test(expectedExceptions = LocationDoesNotExistsException.class)
	public void testCreateMountainInvalid() throws ResourceException{
		final int mountainId = 1;
		final Mountain mountain = TestData.newMountain(mountainId);
		mockery.checking(new Expectations() {
			{
				oneOf(locationService).getById(mountain.getLocationId());
				will(throwException(new LocationDoesNotExistsException("")));
			}
		});
		service.create(mountain);
	}
	
	@Test
	public void testUpdateMountain() throws ResourceException{
		final int mountainId = 1;
		final Mountain mountain = TestData.newMountain(mountainId);
		mockery.checking(new Expectations() {
			{
				oneOf(locationService).getById(mountain.getLocationId());
				will(returnValue(TestData.newLocation(1)));
				oneOf(dao).update(mountain);
				will(returnValue(mountain));
			}
		});
		Mountain result = service.update(mountain);
		Assert.assertNotNull(result);
		Assert.assertEquals(TestData.MOUNTAIN_NAME, mountain.getName());
		Assert.assertEquals(mountainId, result.getId());
		Assert.assertEquals(TestData.HEIGHT, result.getHeight());
	}
	
	@Test(expectedExceptions = LocationDoesNotExistsException.class)
	public void testUpdateMountainInvalid() throws ResourceException{
		final int mountainId = 1;
		final Mountain mountain = TestData.newMountain(mountainId);
		mockery.checking(new Expectations() {
			{
				oneOf(locationService).getById(mountain.getLocationId());
				will(throwException(new LocationDoesNotExistsException("")));
			}
		});
		service.update(mountain);
	}
	
	@Test
	public void testDeleteMountain() throws ResourceException{
		final int mountainId = 1;
		final Mountain mountain = TestData.newMountain(mountainId);
		mockery.checking(new Expectations() {
			{
				oneOf(dao).delete(mountain);
				will(returnValue(mountain));
			}
		});
		Mountain result = service.delete(mountain);
		Assert.assertNotNull(result);
		Assert.assertEquals(TestData.MOUNTAIN_NAME, mountain.getName());
		Assert.assertEquals(mountainId, result.getId());
		Assert.assertEquals(TestData.HEIGHT, result.getHeight());
	}
	
	@AfterTest
	public void after(){
		mockery.assertIsSatisfied();
	}
}
