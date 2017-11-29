package com.home.education.mountains.controller;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.home.education.mountains.common.TestData;
import com.home.education.mountains.common.exception.ResourceException;
import com.home.education.mountains.resource.impl.Category;
import com.home.education.mountains.service.CategoryService;

public class CategoryControllerTest {

	private Mockery mockery;
	private CategoryService service;
	private CategoryController controller;
	
	@BeforeSuite
	public void before(){
		mockery = new Mockery();
		service = mockery.mock(CategoryService.class);
		controller = new CategoryController(service);
	}
	
	@Test
	public void testGetById() throws ResourceException{
		final Category category = TestData.newCategory(TestData.CATEGORY_ID);
		mockery.checking(new Expectations() {
			{
				oneOf(service).getById(TestData.CATEGORY_ID);
				will(returnValue(category));
			}
		});
		Category result = controller.getById(TestData.CATEGORY_ID);
		Assert.assertNotNull(result);
		Assert.assertEquals(result.getName(), TestData.CATEGORY_NAME);
		Assert.assertEquals(result.getId(), TestData.CATEGORY_ID);
		mockery.assertIsSatisfied();
	}
}
