package com.home.education.mountains.service;

import com.home.education.mountains.common.exception.ResourceException;
import com.home.education.mountains.resource.impl.Category;

public interface CategoryService extends ReadGenericService<Category> {

	Category getByName(String routeName) throws ResourceException;

}
