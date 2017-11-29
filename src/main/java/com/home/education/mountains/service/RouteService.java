package com.home.education.mountains.service;

import java.util.Collection;

import com.home.education.mountains.common.exception.ResourceException;
import com.home.education.mountains.resource.impl.Route;

public interface RouteService extends ReadWriteGenericService<Route> {

	Route getByName(String routeName) throws ResourceException;

	Collection<Route> getByMountainIds(Collection<Integer> mountainIds);

	Collection<Route> getByCategoryIds(Collection<Integer> categoryIds);

	Collection<Route> getByMountainIdsAndCategoryIds(Collection<Integer> mountainIds, Collection<Integer> categoryIds);

}
