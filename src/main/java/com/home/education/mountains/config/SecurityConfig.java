package com.home.education.mountains.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@PropertySource(value = { "classpath:properties/project.properties" })
public class SecurityConfig extends WebSecurityConfigurerAdapter {
 
	@Autowired
	private Environment environment;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		  auth.inMemoryAuthentication().withUser(environment.getRequiredProperty("userReadPermission")).password(environment.getRequiredProperty("userPassword")).roles("USER");
		  auth.inMemoryAuthentication().withUser(environment.getRequiredProperty("userReadWritePermission")).password(environment.getRequiredProperty("userPassword")).roles("ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

	  http.httpBasic().and().authorizeRequests()
		.antMatchers("/Mountains**").hasAnyRole("ADMIN", "USER")
		.and().csrf().disable();	
	}
}
