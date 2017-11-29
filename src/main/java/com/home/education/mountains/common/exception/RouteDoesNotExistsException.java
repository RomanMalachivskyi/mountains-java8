package com.home.education.mountains.common.exception;

import com.home.education.mountains.dao.impl.RouteDaoImpl;

public class RouteDoesNotExistsException extends ResourceException {

	private static final long serialVersionUID = 8130524273130358930L;

	public RouteDoesNotExistsException(String message) {
		super(RouteDaoImpl.TABLE_NAME + message);
	}

}
