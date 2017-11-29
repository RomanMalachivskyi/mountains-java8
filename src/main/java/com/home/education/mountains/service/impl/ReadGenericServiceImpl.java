package com.home.education.mountains.service.impl;

import java.util.Collection;

import com.google.common.collect.Iterables;
import com.home.education.mountains.common.exception.ResourceException;
import com.home.education.mountains.dao.ReadGenericDao;
import com.home.education.mountains.resource.GenericResource;
import com.home.education.mountains.service.ReadGenericService;

public abstract class ReadGenericServiceImpl<R extends GenericResource, D extends ReadGenericDao<R>>
		implements ReadGenericService<R> {

	protected final D dao;

	public ReadGenericServiceImpl(final D dao) {
		this.dao = dao;
	}

	@Override
	public R getById(int resourceId) throws ResourceException {
		Collection<R> result = dao.getById(resourceId);
		if (result == null || result.isEmpty()) {
			throwDoesNotExistsException(" resource does not exists with id:" + resourceId);
		}
		return Iterables.getOnlyElement(result);
	}

	protected abstract void throwDoesNotExistsException(String msg) throws ResourceException;

	@Override
	public Collection<R> getAll() {
		return dao.getAll();
	}
	
	protected boolean isValidIdentifier(int id) {
		return (id != 0 && id > 0);
	}
}
