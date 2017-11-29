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
public class MountainRestApiTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private WebApplicationContext context;
	
	@BeforeMethod
	public void before(){
		RestAssuredMockMvc.webAppContextSetup(context, springSecurity());
		RestAssuredMockMvc.postProcessors(httpBasic("admin", "123456"));
	}
	
	@Test
	public void testMountainGetById(){
		given().
		when().
			get("mountain/2").
		then().
			statusCode(200).
			body("id", equalTo(2));
	}
	
	@Test
	public void testCreateExistingMountain(){
		String createMountain = "{\"name\":\"tempMountain\",\"height\":2060,\"locationId\":1}";
		given().
			body(createMountain).contentType(ContentType.JSON).
		when().
			post("mountain").
		then().
			statusCode(422).
			body(equalTo("Duplicate entry 'tempMountain-1' for key 'uq_mountain_name_in_location'"));
			
	}
	@Test
	public void testGetMountainByFilter(){
		given().
		when().
			get("mountain?minHeight=1000&maxHeight=8000&locationId=1,2,3,4").
		then().
			statusCode(200).
			header("Content-Type", "application/json;charset=UTF-8");//.
			
	}
	@AfterSuite
    public void restRestAssured() {
        RestAssuredMockMvc.reset();
    }
}
