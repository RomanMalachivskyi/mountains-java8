package com.home.education.mountains.dao;

import java.util.Collection;

import com.home.education.mountains.resource.impl.Location;

public interface LocationDao extends ReadWriteGenericDao<Location> {

	Collection<Location> getByMountainRange(String mountainRange) ;
}
