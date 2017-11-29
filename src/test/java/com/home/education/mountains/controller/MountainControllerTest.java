package com.home.education.mountains.controller;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.home.education.mountains.common.TestData;
import com.home.education.mountains.common.exception.ResourceException;
import com.home.education.mountains.common.exception.ValidationFailedException;
import com.home.education.mountains.resource.impl.Mountain;
import com.home.education.mountains.service.MountainService;

public class MountainControllerTest {

	private Mockery mockery;
	private MountainService mountainService; 
	private MountainController controller;
	
	@BeforeSuite
	public void before(){
		mockery = new Mockery();
		mountainService = mockery.mock(MountainService.class);
		controller = new MountainController(mountainService);
	}
	
	@Test
	public void testDelete() throws ResourceException{
		final Mountain mountain = TestData.newMountain(TestData.MOUNTAIN_ID);
		mockery.checking(new Expectations() {
			{
				oneOf(mountainService).getById(TestData.MOUNTAIN_ID);
				will(returnValue(mountain));
				oneOf(mountainService).delete(mountain);
				will(returnValue(mountain));
			}
		});
		Mountain result = controller.delete(TestData.MOUNTAIN_ID);
		Assert.assertNotNull(result);
		Assert.assertEquals(result.getName(), TestData.MOUNTAIN_NAME);
		Assert.assertEquals(result.getHeight(), TestData.HEIGHT);
		Assert.assertEquals(result.getLocationId(), TestData.LOCATION_ID);
		mockery.assertIsSatisfied();
	}
	
	@Test
	public void testGetById() throws ResourceException{
		final Mountain mountain = TestData.newMountain(TestData.MOUNTAIN_ID);
		mockery.checking(new Expectations() {
			{
				oneOf(mountainService).getById(TestData.MOUNTAIN_ID);
				will(returnValue(mountain));
			}
		});
		Mountain result = controller.getById(TestData.MOUNTAIN_ID);
		Assert.assertNotNull(result);
		Assert.assertEquals(result.getName(), TestData.MOUNTAIN_NAME);
		Assert.assertEquals(result.getHeight(), TestData.HEIGHT);
		Assert.assertEquals(result.getLocationId(), TestData.LOCATION_ID);
		mockery.assertIsSatisfied();
	}
	
	@Test
	public void testUpdate() throws ResourceException{
		final Mountain mountain = TestData.newMountain(TestData.MOUNTAIN_ID);
		mountain.setId(0);
		mockery.checking(new Expectations() {
			{
				oneOf(mountainService).update(TestData.newMountain(TestData.MOUNTAIN_ID));
				will(returnValue(TestData.newMountain(TestData.MOUNTAIN_ID)));
			}
		});
		Mountain result = controller.modify(mountain, TestData.MOUNTAIN_ID);
		Assert.assertNotNull(result);
		Assert.assertEquals(result.getName(), TestData.MOUNTAIN_NAME);
		Assert.assertEquals(result.getHeight(), TestData.HEIGHT);
		Assert.assertEquals(result.getLocationId(), TestData.LOCATION_ID);	
		mockery.assertIsSatisfied();
	}
	
	@Test(expectedExceptions = ValidationFailedException.class)
	public void testGetAllFilteredInvalidLowRange() throws ResourceException{
		controller.getFilteredMountains(-2, 1000, 1);
	}
	
	@Test(expectedExceptions = ValidationFailedException.class)
	public void testGetAllFilteredInvalidHighRange() throws ResourceException{
		controller.getFilteredMountains(2, 9000, 1);
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testGetAllFilteredInvalidRange() throws ResourceException{
		controller.getFilteredMountains(4000, 2000, 1);
	}
}
