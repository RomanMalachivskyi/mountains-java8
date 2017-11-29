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
import com.home.education.mountains.common.exception.CategoryDoesNotExistsException;
import com.home.education.mountains.common.exception.MountainDoesNotExistsException;
import com.home.education.mountains.common.exception.ResourceException;
import com.home.education.mountains.dao.RouteDao;
import com.home.education.mountains.resource.impl.Route;
import com.home.education.mountains.service.impl.RouteServiceImpl;

@Test
public class RouteServiceImplTest {

	private Mockery mockery;
	private RouteDao dao;
	private RouteServiceImpl service;
	private MountainService mountainService;
	private CategoryService categoryService;

	@BeforeSuite
	public void before() {
		mockery = new Mockery();
		dao = mockery.mock(RouteDao.class);
		mountainService = mockery.mock(MountainService.class);
		categoryService = mockery.mock(CategoryService.class);
		service = new RouteServiceImpl(dao, mountainService, categoryService);
	}

	@Test
	public void testGetByCategoryIds() {
		final Route route = TestData.newRoute(1);
		mockery.checking(new Expectations() {
			{
				oneOf(dao).getViaMontainIdsAndCategoryIds(null, Lists.newArrayList(route.getCategoryId()));
				will(returnValue(Lists.newArrayList(route)));
			}
		});
		Collection<Route> results = service.getByCategoryIds(Lists.newArrayList(route.getCategoryId()));
		Assert.assertFalse(results.isEmpty());
		Assert.assertEquals(results.size(), 1);
		Route result = Iterables.getOnlyElement(results);
		Assert.assertEquals(result.getCategoryId(), TestData.CATEGORY_ID);

	}

	@Test
	public void testGetByMountainIds() {
		final Route route = TestData.newRoute(1);
		mockery.checking(new Expectations() {
			{
				oneOf(dao).getViaMontainIdsAndCategoryIds(Lists.newArrayList(route.getMountainId()), null);
				will(returnValue(Lists.newArrayList(route)));
			}
		});
		Collection<Route> results = service.getByMountainIds(Lists.newArrayList(route.getMountainId()));
		Assert.assertFalse(results.isEmpty());
		Assert.assertEquals(results.size(), 1);
		Route result = Iterables.getOnlyElement(results);
		Assert.assertEquals(result.getMountainId(), TestData.MOUNTAIN_ID);

	}

	@Test
	public void testGetByMountainIdsAndCategoryIds() {
		final Route route = TestData.newRoute(1);
		mockery.checking(new Expectations() {
			{
				oneOf(dao).getViaMontainIdsAndCategoryIds(Lists.newArrayList(route.getMountainId()),
						Lists.newArrayList(route.getCategoryId()));
				will(returnValue(Lists.newArrayList(route)));
			}
		});
		Collection<Route> results = service.getByMountainIdsAndCategoryIds(Lists.newArrayList(route.getMountainId()),
				Lists.newArrayList(route.getCategoryId()));
		Assert.assertFalse(results.isEmpty());
		Assert.assertEquals(results.size(), 1);
		Route result = Iterables.getOnlyElement(results);
		Assert.assertEquals(result.getMountainId(), TestData.MOUNTAIN_ID);

	}

	@Test
	public void testCreate() throws ResourceException {
		final Route route = TestData.newRoute(1);
		mockery.checking(new Expectations() {
			{
				oneOf(mountainService).getById(route.getMountainId());
				will(returnValue(TestData.newMountain(1)));
				oneOf(categoryService).getById(route.getCategoryId());
				will(returnValue(TestData.newCategory(1)));
				oneOf(dao).create(route);
				will(returnValue(route));
			}
		});
		Route result = service.create(route);
		Assert.assertEquals(result.getMountainId(), TestData.MOUNTAIN_ID);
		Assert.assertEquals(result.getCategoryId(), TestData.CATEGORY_ID);
		Assert.assertEquals(result.getName(), TestData.ROUTE_NAME);
	}

	@Test(expectedExceptions = MountainDoesNotExistsException.class)
	public void testCreateInvalidMountain() throws ResourceException {
		final Route route = TestData.newRoute(1);
		mockery.checking(new Expectations() {
			{
				oneOf(mountainService).getById(route.getMountainId());
				will(throwException(new MountainDoesNotExistsException("")));
			}
		});
		service.create(route);
	}

	@Test(expectedExceptions = CategoryDoesNotExistsException.class)
	public void testCreateInvalidCategory() throws ResourceException {
		final Route route = TestData.newRoute(1);
		mockery.checking(new Expectations() {
			{
				oneOf(mountainService).getById(route.getMountainId());
				will(returnValue(TestData.newMountain(1)));
				oneOf(categoryService).getById(route.getCategoryId());
				will(throwException(new CategoryDoesNotExistsException("")));
			}
		});
		service.create(route);
	}
	
	@Test
	public void testUpdate() throws ResourceException {
		final Route route = TestData.newRoute(1);
		mockery.checking(new Expectations() {
			{
				oneOf(mountainService).getById(route.getMountainId());
				will(returnValue(TestData.newMountain(1)));
				oneOf(categoryService).getById(route.getCategoryId());
				will(returnValue(TestData.newCategory(1)));
				oneOf(dao).update(route);
				will(returnValue(route));
			}
		});
		Route result = service.update(route);
		Assert.assertEquals(result.getMountainId(), TestData.MOUNTAIN_ID);
		Assert.assertEquals(result.getCategoryId(), TestData.CATEGORY_ID);
		Assert.assertEquals(result.getName(), TestData.ROUTE_NAME);
	}

	@Test(expectedExceptions = MountainDoesNotExistsException.class)
	public void testUpdateInvalidMountain() throws ResourceException {
		final Route route = TestData.newRoute(1);
		mockery.checking(new Expectations() {
			{
				oneOf(mountainService).getById(route.getMountainId());
				will(throwException(new MountainDoesNotExistsException("")));
			}
		});
		service.update(route);
	}

	@Test(expectedExceptions = CategoryDoesNotExistsException.class)
	public void testUpdateInvalidCategory() throws ResourceException {
		final Route route = TestData.newRoute(1);
		mockery.checking(new Expectations() {
			{
				oneOf(mountainService).getById(route.getMountainId());
				will(returnValue(TestData.newMountain(1)));
				oneOf(categoryService).getById(route.getCategoryId());
				will(throwException(new CategoryDoesNotExistsException("")));
			}
		});
		service.update(route);
	}
	
	@Test
	public void testDeleteRoute() throws ResourceException{
		final int routeId = 1;
		final Route route = TestData.newRoute(routeId);
		mockery.checking(new Expectations() {
			{
				oneOf(dao).delete(route);
				will(returnValue(route));
			}
		});
		Route result = service.delete(route);
		Assert.assertNotNull(result);
		Assert.assertEquals(result.getName(), TestData.ROUTE_NAME);
		Assert.assertEquals(result.getId(), routeId);
		Assert.assertEquals(result.getCategoryId(), TestData.CATEGORY_ID);
		Assert.assertEquals(result.getMountainId(), TestData.MOUNTAIN_ID);
	}
	
	@AfterTest
	public void after() {
		mockery.assertIsSatisfied();
	}
}
