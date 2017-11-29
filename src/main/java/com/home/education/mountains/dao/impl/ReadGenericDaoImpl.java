package com.home.education.mountains.dao.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.home.education.mountains.common.CustomHibernateDaoSupport;
import com.home.education.mountains.dao.ReadGenericDao;
import com.home.education.mountains.resource.GenericResource;

public class ReadGenericDaoImpl<R extends GenericResource> extends CustomHibernateDaoSupport
		implements ReadGenericDao<R> {

	protected final String tableName;

	public ReadGenericDaoImpl(final String tableName) {
		this.tableName = tableName;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<R> getById(int id) {
		return (Collection<R>) getHibernateTemplate()
				.find("from " + tableName + " where " + tableName.toLowerCase() + "Id=?", id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<R> getAll() {
		return (Collection<R>) getHibernateTemplate().find("from " + tableName);
	}
	
	protected String buildSql(MapSqlParameterSource mapSqlParameterSource) {
		StringBuilder sql = new StringBuilder("FROM ");
		sql.append(tableName).append(" WHERE 1=1");
		for (String s : mapSqlParameterSource.getValues().keySet()) {
			Object value = mapSqlParameterSource.getValue(s);
			if (value instanceof List) {
				sql.append(" AND ").append(s).append(" IN (0");
				((List) value).stream().forEach(v -> sql.append("," + v.toString()));
				sql.append(" )");
			} else if (value == null) {
				sql.append(" AND ").append(s).append(" IS NULL");
			} else {
				sql.append(" AND ").append(s).append(" = :").append(s);
			}
		}
		return sql.toString();
	}

}
