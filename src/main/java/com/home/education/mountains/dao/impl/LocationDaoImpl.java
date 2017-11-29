package com.home.education.mountains.dao.impl;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import com.home.education.mountains.dao.LocationDao;
import com.home.education.mountains.resource.impl.Location;

@Repository("locationDao")
public class LocationDaoImpl extends ReadWriteGenericDaoImpl<Location>implements LocationDao {

	public final static String TABLE_NAME = "Location";

	public LocationDaoImpl() {
		super(TABLE_NAME);
	}

	@Override
	public Collection<Location> getByMountainRange(String mountainRange) {
		@SuppressWarnings("unchecked")
		Collection<Location> result = (Collection<Location>) getHibernateTemplate()
				.find("from Location where mountainRange=?", mountainRange);
		return result;
	}
}
