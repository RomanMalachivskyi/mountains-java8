package com.home.education.mountains.service;

import java.util.Collection;

import com.home.education.mountains.common.exception.ResourceException;
import com.home.education.mountains.resource.GenericResource;

public interface ReadGenericService<R extends GenericResource> {

	R getById(int resourceId) throws ResourceException;
	Collection<R> getAll();
}
