package com.home.education.mountains.dao.impl;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import com.home.education.mountains.dao.CategoryDao;
import com.home.education.mountains.resource.impl.Category;

@Repository("categoryDao")
public class CategoryDaoImpl extends ReadGenericDaoImpl<Category>implements CategoryDao {

	public final static String TABLE_NAME = "Category";

	public CategoryDaoImpl() {
		super(TABLE_NAME);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Category> getByName(String name) {
		return (Collection<Category>) getHibernateTemplate().find("from " + tableName + " where name=?", name);
	}

}
