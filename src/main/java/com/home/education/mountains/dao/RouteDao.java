package com.home.education.mountains.dao;

import java.util.Collection;

import com.home.education.mountains.resource.impl.Route;

public interface RouteDao extends ReadWriteGenericDao<Route> {

	Collection<Route> getViaMontainIdsAndCategoryIds(Collection<Integer> mountainIds, Collection<Integer> categoryIds);

	Collection<Route> getByName(String name);

}
