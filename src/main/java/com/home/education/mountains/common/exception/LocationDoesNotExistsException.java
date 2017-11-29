package com.home.education.mountains.common.exception;

import com.home.education.mountains.dao.impl.LocationDaoImpl;

public class LocationDoesNotExistsException extends ResourceException {

	private static final long serialVersionUID = 8130524273130358930L;

	public LocationDoesNotExistsException(String message) {
		super(LocationDaoImpl.TABLE_NAME + message);
	}

}
