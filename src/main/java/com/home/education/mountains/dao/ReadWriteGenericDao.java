package com.home.education.mountains.dao;

import java.util.Collection;

import com.home.education.mountains.resource.GenericResource;

public interface ReadWriteGenericDao<R extends GenericResource> extends ReadGenericDao<R> {

	R create(R resource);
	R update(R resource);
	R delete(R resource);
	Collection<R> delete(Collection<R> resources);
}
