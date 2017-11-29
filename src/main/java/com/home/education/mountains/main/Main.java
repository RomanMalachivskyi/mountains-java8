package com.home.education.mountains.main;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.home.education.mountains.common.exception.ResourceException;
import com.home.education.mountains.resource.impl.Mountain;
import com.home.education.mountains.service.LocationService;
import com.home.education.mountains.service.MountainService;

public class Main {

	public static void main(String[] args) throws SQLException, ResourceException {
		ApplicationContext appContext = 
		    	  new ClassPathXmlApplicationContext("spring/config/BeanLocations.xml");
		
		LocationService locationService = (LocationService) appContext.getBean("locationService");
		System.out.println(locationService.getById(10));
		
		MountainService mountainService  = (MountainService) appContext.getBean("mountainService");
		Mountain resource = new Mountain();
		resource.setHeight(7509);
		resource.setName("Muztagh Ata");
		resource.setLocationId(10);
		mountainService.create(resource);
//		CategoryDao categoryDao = new CategoryDaoImpl();
//		//categoryDao.conn();
//		System.out.println(categoryDao.getById(2));
//		
//		LocationDaoImpl locationDao = new LocationDaoImpl();
//		//categoryDao.conn();
//		System.out.println(locationDao.getById(2));
//		
//		MountainDao mountainDao = new MountainDaoImpl();
//		//categoryDao.conn();
//		System.out.println(mountainDao.getById(2));
	}

}
