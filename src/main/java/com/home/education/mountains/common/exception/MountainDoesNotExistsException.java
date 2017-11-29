package com.home.education.mountains.common.exception;

import com.home.education.mountains.dao.impl.MountainDaoImpl;

public class MountainDoesNotExistsException extends ResourceException {

	private static final long serialVersionUID = 8130524273130358930L;

	public MountainDoesNotExistsException(String message) {
		super(MountainDaoImpl.TABLE_NAME + message);
	}

}
