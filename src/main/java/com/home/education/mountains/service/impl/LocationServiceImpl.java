package com.home.education.mountains.service.impl;

import java.util.Collection;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.home.education.mountains.common.exception.LocationDoesNotExistsException;
import com.home.education.mountains.common.exception.LocationValidationFailedException;
import com.home.education.mountains.common.exception.ResourceException;
import com.home.education.mountains.dao.LocationDao;
import com.home.education.mountains.resource.impl.Location;
import com.home.education.mountains.service.LocationService;

@Service("locationService")
@Transactional(rollbackFor = ResourceException.class)
public class LocationServiceImpl extends ReadWriteGenericServiceImpl<Location, LocationDao>implements LocationService {

	@Autowired
	public LocationServiceImpl(final LocationDao dao) {
		super(dao);
	}

	@Override
	public Collection<Location> getAllLocationFilterByMountainRangeAndCountry(String mountainRange, String country)
			throws LocationValidationFailedException {
		Collection<Location> result = getAll();
		if (StringUtils.isNotBlank(mountainRange)) {
			result = result.stream().filter(l -> l.getMountainRange().equals(mountainRange))
					.collect(Collectors.toList());
		}

		if (StringUtils.isNotBlank(country)) {
			result = result.stream().filter(l -> l.getCountry().equals(country)).collect(Collectors.toList());
		}

		return result;
	}

	@Override
	public Collection<Location> getAllLocationFilterByMountainRange(String mountainRange) throws LocationValidationFailedException{
		return getAllLocationFilterByMountainRangeAndCountry(mountainRange, null);
	}
	
	@Override
	public Collection<Location> getAllLocationFilterByContry(String country) throws LocationValidationFailedException {
		return getAllLocationFilterByMountainRangeAndCountry(null, country);
	}

	
	@Override
	public Location create(Location resource) throws ResourceException {
		validateResource(resource);
		return super.create(resource);
	}

	@Override
	public Location update(Location resource) throws ResourceException {
		validateResource(resource);
		return super.update(resource);
	}

	@Override
	protected void throwDoesNotExistsException(String msg) throws LocationDoesNotExistsException {
		throw new LocationDoesNotExistsException(msg);
	}

	@Override
	public Location delete(Location resource) throws ResourceException {
		return super.delete(resource);
	}

	@Override
	protected void validateResource(Location resource) throws ResourceException {

	}
}
