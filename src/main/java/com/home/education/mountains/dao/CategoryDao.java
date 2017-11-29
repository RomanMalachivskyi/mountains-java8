package com.home.education.mountains.dao;

import java.util.Collection;

import com.home.education.mountains.resource.impl.Category;

public interface CategoryDao extends ReadGenericDao<Category> {
	
	Collection<Category> getByName(String name);
}
