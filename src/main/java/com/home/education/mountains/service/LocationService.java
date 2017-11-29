package com.home.education.mountains.service;

import java.util.Collection;

import com.home.education.mountains.common.exception.LocationValidationFailedException;
import com.home.education.mountains.resource.impl.Location;

public interface LocationService extends ReadWriteGenericService<Location> {

	Collection<Location> getAllLocationFilterByMountainRangeAndCountry(String mountainRange, String country) throws LocationValidationFailedException;

	Collection<Location> getAllLocationFilterByMountainRange(String mountainRange) throws LocationValidationFailedException;
	
	Collection<Location> getAllLocationFilterByContry(String country) throws LocationValidationFailedException;
}
