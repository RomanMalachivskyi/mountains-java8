package com.home.education.mountains.controller;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.home.education.mountains.common.exception.LocationValidationFailedException;
import com.home.education.mountains.common.exception.ResourceException;
import com.home.education.mountains.resource.impl.Location;
import com.home.education.mountains.service.LocationService;

@Controller
@RequestMapping("/location")

public class LocationController {

	private final LocationService locationService;

	@Autowired
	public LocationController(LocationService locationService) {
		this.locationService = locationService;
	}

	@RequestMapping(value = "/{locationId}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public Location getById(final @PathVariable int locationId) throws ResourceException {
		return locationService.getById(locationId);
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public Collection<Location> getFilteredLocations(
			@RequestParam(value = "mountainRange", required = false) final String mountainRange,
			@RequestParam(value = "country", required = false) final String country)
					throws LocationValidationFailedException {
		return locationService.getAllLocationFilterByMountainRangeAndCountry(mountainRange, country);
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Location create(@RequestBody final @Valid Location location) throws ResourceException {
		return locationService.create(location);
	}

	@RequestMapping(value = "/{locationId}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Location modify(@RequestBody final @Valid Location location, final @PathVariable int locationId)
			throws ResourceException {
		location.setId(locationId);
		return locationService.update(location);
	}

	@RequestMapping(value = "/{locationId}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Location delete(final @PathVariable int locationId) throws ResourceException {
		return locationService.delete(locationService.getById(locationId));
	}
	
}
