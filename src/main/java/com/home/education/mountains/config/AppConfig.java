package com.home.education.mountains.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@EnableAspectJAutoProxy
@Configuration
@ComponentScan( {"com.home.education.mountains"})
@Import({SecurityConfig.class, HibernateConfiguration.class })
public class AppConfig {

}
