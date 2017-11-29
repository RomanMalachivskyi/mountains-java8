package com.home.education.restapi;

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.home.education.mountains.config.AppConfig;
import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;

@ContextConfiguration(classes = AppConfig.class)
@WebAppConfiguration
@Test
public class CategoryRestApiTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private WebApplicationContext context;
	
	@BeforeMethod
	public void before(){
		RestAssuredMockMvc.webAppContextSetup(context, springSecurity());
		RestAssuredMockMvc.postProcessors(httpBasic("admin", "123456"));
	}
	
	@Test
	public void testCategoryGetById(){
		given().
		when().
			get("category/1").
		then().
			statusCode(200).
			body("id", equalTo(1));
	}
	
	@Test
	public void testGetategoryByName(){
		given().
		when().
			get("category?categoryName=2a").
		then().
			statusCode(200).
			header("Content-Type", "application/json;charset=UTF-8").
			body("name", equalTo("2a"));
			
	}
	@AfterSuite
    public void restRestAssured() {
        RestAssuredMockMvc.reset();
    }
}
