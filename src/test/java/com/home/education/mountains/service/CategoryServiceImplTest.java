package com.home.education.mountains.service;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

import com.home.education.mountains.common.TestData;
import com.home.education.mountains.common.exception.CategoryDoesNotExistsException;
import com.home.education.mountains.common.exception.ResourceException;
import com.home.education.mountains.dao.CategoryDao;
import com.home.education.mountains.resource.impl.Category;
import com.home.education.mountains.service.impl.CategoryServiceImpl;

@Test
public class CategoryServiceImplTest {

	private Mockery mockery;
	private CategoryDao dao;
	private CategoryServiceImpl service;
	@BeforeSuite
	public void before() {
		mockery = new Mockery();
		dao = mockery.mock(CategoryDao.class);
		service = new CategoryServiceImpl(dao);
	}

	@Test
	public void getByName() throws ResourceException{
		final Category category = TestData.newCategory(TestData.CATEGORY_ID);
		mockery.checking(new Expectations() {
			{
				oneOf(dao).getByName(TestData.CATEGORY_NAME);
				will(returnValue(Lists.newArrayList(category)));
			}
		});
		Category result = service.getByName(TestData.CATEGORY_NAME);
		Assert.assertNotNull(result);
		Assert.assertEquals(result.getName(), TestData.CATEGORY_NAME);
		Assert.assertEquals(result.getId(), TestData.CATEGORY_ID);
	}
	

	@Test(expectedExceptions = CategoryDoesNotExistsException.class)
	public void getByNameEmpty() throws ResourceException{
		mockery.checking(new Expectations() {
			{
				oneOf(dao).getByName(TestData.CATEGORY_NAME);
				will(returnValue(Lists.newArrayList()));
			}
		});
		service.getByName(TestData.CATEGORY_NAME);
	}
	
	@AfterTest
	public void after() {
		mockery.assertIsSatisfied();
	}
}
