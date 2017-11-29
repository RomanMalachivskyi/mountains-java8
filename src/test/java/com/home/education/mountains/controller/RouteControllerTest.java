package com.home.education.mountains.controller;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.home.education.mountains.common.TestData;
import com.home.education.mountains.common.exception.ResourceException;
import com.home.education.mountains.resource.impl.Route;
import com.home.education.mountains.service.RouteService;

public class RouteControllerTest {

	private Mockery mockery;
	private RouteService service;
	private RouteController controller;
	
	@BeforeSuite
	public void before(){
		mockery = new Mockery();
		service = mockery.mock(RouteService.class);
		controller = new RouteController(service);
	}
	
	@Test
	public void testDelete() throws ResourceException{
		final Route route = TestData.newRoute(TestData.ROUTE_ID);
		mockery.checking(new Expectations() {
			{
				oneOf(service).getById(TestData.ROUTE_ID);
				will(returnValue(route));
				oneOf(service).delete(route);
				will(returnValue(route));
			}
		});
		Route result = controller.delete(TestData.ROUTE_ID);
		Assert.assertNotNull(result);
		Assert.assertEquals(result.getName(), TestData.ROUTE_NAME);
		Assert.assertEquals(result.getMountainId(), TestData.MOUNTAIN_ID);
		Assert.assertEquals(result.getCategoryId(), TestData.CATEGORY_ID);
		mockery.assertIsSatisfied();
	}
	
	@Test
	public void testGetById() throws ResourceException{
		final Route route = TestData.newRoute(TestData.ROUTE_ID);
		mockery.checking(new Expectations() {
			{
				oneOf(service).getById(TestData.ROUTE_ID);
				will(returnValue(route));
			}
		});
		Route result = controller.getById(TestData.ROUTE_ID);
		Assert.assertNotNull(result);
		Assert.assertEquals(result.getName(), TestData.ROUTE_NAME);
		Assert.assertEquals(result.getMountainId(), TestData.MOUNTAIN_ID);
		Assert.assertEquals(result.getCategoryId(), TestData.CATEGORY_ID);
		mockery.assertIsSatisfied();
	}
	
	@Test
	public void testUpdate() throws ResourceException{
		final Route route = TestData.newRoute(TestData.ROUTE_ID);
		route.setId(0);
		mockery.checking(new Expectations() {
			{
				oneOf(service).update(TestData.newRoute(TestData.ROUTE_ID));
				will(returnValue(TestData.newRoute(TestData.ROUTE_ID)));
			}
		});
		Route result = controller.modify(route, TestData.ROUTE_ID);
		Assert.assertNotNull(result);
		Assert.assertEquals(result.getName(), TestData.ROUTE_NAME);
		Assert.assertEquals(result.getMountainId(), TestData.MOUNTAIN_ID);
		Assert.assertEquals(result.getCategoryId(), TestData.CATEGORY_ID);
		Assert.assertEquals(result.getId(), TestData.ROUTE_ID);
		mockery.assertIsSatisfied();
	}
}
