package com.home.education.mountains.service.impl;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Iterables;
import com.home.education.mountains.common.exception.CategoryDoesNotExistsException;
import com.home.education.mountains.common.exception.CategoryValidationFailedException;
import com.home.education.mountains.common.exception.ResourceException;
import com.home.education.mountains.dao.CategoryDao;
import com.home.education.mountains.resource.impl.Category;
import com.home.education.mountains.service.CategoryService;

@Service("categoryService")
public class CategoryServiceImpl extends ReadGenericServiceImpl<Category, CategoryDao> implements CategoryService {

	@Autowired
	public CategoryServiceImpl(CategoryDao dao) {
		super(dao);
	}

	@Override
	public Category getByName(String categoryName) throws ResourceException {
		if(StringUtils.isBlank(categoryName)){
			throw new CategoryValidationFailedException("CategoryName is not valid");
		}
		Collection<Category> results = dao.getByName(categoryName);
		if(results.isEmpty()){
			throwDoesNotExistsException(" does not exists");
		}
		return Iterables.getOnlyElement(results);
	}

	@Override
	protected void throwDoesNotExistsException(String msg) throws CategoryDoesNotExistsException {
		throw new CategoryDoesNotExistsException(msg);
	}
}
