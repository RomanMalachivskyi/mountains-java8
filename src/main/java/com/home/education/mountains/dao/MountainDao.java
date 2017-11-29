package com.home.education.mountains.dao;

import java.util.Collection;

import com.home.education.mountains.resource.impl.Mountain;

public interface MountainDao extends ReadWriteGenericDao<Mountain> {

	Collection<Mountain> getByName(String name);
	Collection<Mountain> getViaLocationIds(Collection<Integer> locationIds);
}
