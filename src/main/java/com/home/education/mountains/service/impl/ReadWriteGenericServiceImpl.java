package com.home.education.mountains.service.impl;

import com.home.education.mountains.common.exception.ResourceException;
import com.home.education.mountains.dao.ReadWriteGenericDao;
import com.home.education.mountains.resource.GenericResource;
import com.home.education.mountains.service.ReadWriteGenericService;

public abstract class ReadWriteGenericServiceImpl<R extends GenericResource, D extends ReadWriteGenericDao<R>>
		extends ReadGenericServiceImpl<R, D>implements ReadWriteGenericService<R> {

	public ReadWriteGenericServiceImpl(final D dao) {
		super(dao);
	}

	@Override
	public R create(R resource) throws ResourceException {
		return dao.create(resource);
	}

	@Override
	public R update(R resource) throws ResourceException {
		return dao.update(resource);
	}

	@Override
	public R delete(R resource) throws ResourceException {
		return dao.delete(resource);
	}

	protected abstract void validateResource(R resource) throws ResourceException;
}
