package com.home.education.mountains.dao;

import java.util.Collection;

import com.home.education.mountains.resource.GenericResource;

public interface ReadGenericDao <R extends GenericResource>{

	Collection<R> getById(int id);
	Collection<R> getAll();
	
}
