package com.home.education.mountains.service.impl;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Iterables;
import com.home.education.mountains.common.exception.ResourceException;
import com.home.education.mountains.common.exception.RouteDoesNotExistsException;
import com.home.education.mountains.common.exception.RouteValidationFailedException;
import com.home.education.mountains.dao.RouteDao;
import com.home.education.mountains.resource.impl.Route;
import com.home.education.mountains.service.CategoryService;
import com.home.education.mountains.service.MountainService;
import com.home.education.mountains.service.RouteService;

@Service("routeService")
@Transactional(rollbackFor = ResourceException.class)
public class RouteServiceImpl extends ReadWriteGenericServiceImpl<Route, RouteDao>implements RouteService {

	private final MountainService mountainService;
	private final CategoryService categoryService;

	@Autowired
	public RouteServiceImpl(RouteDao dao, final MountainService mountainService,
			final CategoryService categoryService) {
		super(dao);
		this.categoryService = categoryService;
		this.mountainService = mountainService;
	}

	@Override
	public Route getByName(String routeName) throws ResourceException {
		if(StringUtils.isBlank(routeName)){
			throw new RouteValidationFailedException("RouteName is not valid");
		}
		Collection<Route> results = dao.getByName(routeName);
		if(results.isEmpty()){
			throwDoesNotExistsException(" does not exists");
		}
		return Iterables.getOnlyElement(results);
	}

	@Override
	public Route create(Route resource) throws ResourceException {
		validateResource(resource);
		return super.create(resource);
	}

	@Override
	protected void validateResource(Route resource) throws ResourceException {
		mountainService.getById(resource.getMountainId());
		categoryService.getById(resource.getCategoryId());
	}

	@Override
	public Route update(Route resource) throws ResourceException {
		validateResource(resource);
		return super.update(resource);
	}

	@Override
	protected void throwDoesNotExistsException(String msg) throws RouteDoesNotExistsException {
		throw new RouteDoesNotExistsException(msg);
	}

	@Override
	public Collection<Route> getByMountainIds(Collection<Integer> mountainIds) {
		return getByMountainIdsAndCategoryIds(mountainIds, null);
	}

	@Override
	public Collection<Route> getByCategoryIds(Collection<Integer> categoryIds) {
		return getByMountainIdsAndCategoryIds(null, categoryIds);
	}

	@Override
	public Collection<Route> getByMountainIdsAndCategoryIds(Collection<Integer> mountainIds,
			Collection<Integer> categoryIds) {
		return dao.getViaMontainIdsAndCategoryIds(mountainIds, categoryIds);
	}
}
