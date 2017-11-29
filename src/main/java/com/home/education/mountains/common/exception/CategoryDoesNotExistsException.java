package com.home.education.mountains.common.exception;

import com.home.education.mountains.dao.impl.CategoryDaoImpl;

public class CategoryDoesNotExistsException extends ResourceException {

	private static final long serialVersionUID = 8130524273130358930L;

	public CategoryDoesNotExistsException(String message) {
		super(CategoryDaoImpl.TABLE_NAME + message);
	}

}
