package com.home.education.mountains.dao.impl;

import java.util.Collection;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.home.education.mountains.dao.RouteDao;
import com.home.education.mountains.resource.impl.Route;

@Repository("routeDao")
public class RouteDaoImpl extends ReadWriteGenericDaoImpl<Route>implements RouteDao {

	public final static String TABLE_NAME = "Route";

	public RouteDaoImpl() {
		super(TABLE_NAME);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Route> getByName(String name) {
		return (Collection<Route>) getHibernateTemplate().find("from " + tableName + " where name=?", name);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Route> getViaMontainIdsAndCategoryIds(Collection<Integer> mountainIds,
			Collection<Integer> categoryIds) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		if (mountainIds != null && !mountainIds.isEmpty())
			mapSqlParameterSource.addValue("mountainId", mountainIds);
		if (categoryIds != null && !categoryIds.isEmpty())
			mapSqlParameterSource.addValue("categoryId", categoryIds);
		return (Collection<Route>) getHibernateTemplate().find(buildSql(mapSqlParameterSource));
	}
}
