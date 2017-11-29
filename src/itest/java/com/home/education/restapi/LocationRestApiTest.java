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
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;

@ContextConfiguration(classes = AppConfig.class)
@WebAppConfiguration
@Test
public class LocationRestApiTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private WebApplicationContext context;
	
	@BeforeMethod
	public void before(){
		RestAssuredMockMvc.webAppContextSetup(context, springSecurity());
		RestAssuredMockMvc.postProcessors(httpBasic("admin", "123456"));
	}
	
	@Test
	public void testLocationGetById(){
		given().
		when().
			get("location/2").
		then().
			statusCode(200).
			body("id", equalTo(2));
	}
	
	@Test
	public void testCreateExistingLocation(){
		String createLocation = "{\"mountainRange\":\"Karpaty\",\"country\":\"Ukraine\",\"description\":\"The highest mountains in Ukraine\"}";
		given().
			body(createLocation).contentType(ContentType.JSON).
		when().
			post("location").
		then().
			statusCode(422).
			body(equalTo("Duplicate entry 'Karpaty-Ukraine' for key 'uq_location'"));
			
	}
	@Test
	public void testGetLocationByFilter(){
		given().
			param("country", "Ukraine").param("mountainRange", "Karpaty").
		when().
			get("location").
		then().
			statusCode(200).
			header("Content-Type", "application/json;charset=UTF-8");//.
			
	}
	@AfterSuite
    public void restRestAssured() {
        RestAssuredMockMvc.reset();
    }
}
