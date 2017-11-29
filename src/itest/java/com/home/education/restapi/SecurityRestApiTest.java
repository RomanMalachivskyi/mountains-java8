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
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.home.education.mountains.config.AppConfig;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;

@ContextConfiguration(classes = AppConfig.class)
@WebAppConfiguration
@Test
public class SecurityRestApiTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private WebApplicationContext context;
	
	@BeforeMethod
	public void before(){
		RestAssuredMockMvc.webAppContextSetup(context, springSecurity());
	}
	
	@Test
	public void testLocationWithotUser(){
		given().
			webAppContextSetup(context, springSecurity()).
		when().
			get("location/2").
		then().
			statusCode(403).
			body(equalTo("Access is denied"));
	}
	
	@Test
	public void testLocationWithInvalidUser(){
		given().
			webAppContextSetup(context, springSecurity()).
			postProcessors(httpBasic("bebebe", "bebebe")).
		when().
			get("location/2").
		then().
			statusCode(401);
	}
	
	@Test
	public void testLocationWithUser(){
		given().
			webAppContextSetup(context, springSecurity()).
			postProcessors(httpBasic("roman", "123456")).
		when().
			get("location/2").
		then().
			statusCode(200);
	}
	
	@Test
	public void testLocationWriteOperation(){
		given().
			webAppContextSetup(context, springSecurity()).
			postProcessors(httpBasic("admin", "123456")).
			contentType(ContentType.JSON).
		when().
			post("location").
		then().
			statusCode(400);
	}
	
	@AfterTest
    public void restRestAssured() {
        RestAssuredMockMvc.reset();
    }
}
