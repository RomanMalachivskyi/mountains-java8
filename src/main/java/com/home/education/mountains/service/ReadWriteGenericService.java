package com.home.education.mountains.service;

import com.home.education.mountains.common.exception.ResourceException;
import com.home.education.mountains.resource.GenericResource;


public interface ReadWriteGenericService<R extends GenericResource> extends ReadGenericService<R> {
	R create (R resource) throws ResourceException;
	R update (R resource) throws ResourceException;
	R delete (R resource) throws ResourceException;
}
