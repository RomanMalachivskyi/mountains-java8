package com.home.education.mountains.dao.impl;

import java.util.Collection;

import com.home.education.mountains.dao.ReadWriteGenericDao;
import com.home.education.mountains.resource.GenericResource;

public class ReadWriteGenericDaoImpl<R extends GenericResource> extends ReadGenericDaoImpl<R> implements ReadWriteGenericDao<R> {
	
	public ReadWriteGenericDaoImpl(final String tableName) {
		super(tableName);
	}

	@Override
	public R create(R resource) {
		int id =  (Integer) getHibernateTemplate().save(resource);
		resource.setId(id);
		return resource;
	}

	@Override
	public R update(R resource) {
		getHibernateTemplate().update(resource);
		return resource;
	}

	@Override
	public R delete(R resource) {
		getHibernateTemplate().delete(resource);
		return resource;
	}

	@Override
	public Collection<R> delete(Collection<R> resources) {
		getHibernateTemplate().deleteAll(resources);
		return resources;
	}
}
