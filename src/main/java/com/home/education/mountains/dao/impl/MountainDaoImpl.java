package com.home.education.mountains.dao.impl;

import java.util.Collection;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;
import com.home.education.mountains.dao.MountainDao;
import com.home.education.mountains.resource.impl.Mountain;

@Repository("mountainDao")
public class MountainDaoImpl extends ReadWriteGenericDaoImpl<Mountain>implements MountainDao {

	public final static String TABLE_NAME = "Mountain";

	public MountainDaoImpl() {
		super(TABLE_NAME);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Mountain> getByName(String name) {
		return (Collection<Mountain>) getHibernateTemplate().find("from " + tableName + " where name=?", name);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Mountain> getViaLocationIds(Collection<Integer> locationIds) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		if (locationIds == null || locationIds.isEmpty()){
			return Lists.newArrayList();
		}
		mapSqlParameterSource.addValue("locationId", locationIds);
		return (Collection<Mountain>) getHibernateTemplate().find(buildSql(mapSqlParameterSource));
	}
}
