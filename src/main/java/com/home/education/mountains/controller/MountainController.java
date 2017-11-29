package com.home.education.mountains.controller;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

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

import com.google.common.collect.Range;
import com.home.education.mountains.common.exception.ResourceException;
import com.home.education.mountains.common.exception.ValidationFailedException;
import com.home.education.mountains.resource.impl.Mountain;
import com.home.education.mountains.service.MountainService;

@Controller
@RequestMapping("/mountain")
public class MountainController {

	private final MountainService mountainService;
	
	@Autowired
	public MountainController(final MountainService mountainService) {
		this.mountainService = mountainService;
	}

	@RequestMapping(value = "/{mountainId}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public Mountain getById(final @PathVariable int mountainId) throws ResourceException {
		return mountainService.getById(mountainId);
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public Collection<Mountain> getFilteredMountains(
			@RequestParam(required = false, value = "minHeight", defaultValue = "0") int minHeight,
			@RequestParam(required = false, value = "maxHeight", defaultValue = "8848") int maxHeight,
			@RequestParam(required = false, value = "locationId") int... locationIds) throws ResourceException {
		if (minHeight < 0 || maxHeight > 8848) {
			throw new ValidationFailedException("Invalid range for height");
		}
		Range<Integer> range = Range.openClosed(minHeight, maxHeight);
		Collection<Integer> locationIdsCollection = Collections.emptyList();
		if (locationIds != null) {
			locationIdsCollection = Arrays.stream(locationIds).boxed().collect(Collectors.toList());
		}
		return mountainService.getAllFilterByLocationIdsAndHeight(locationIdsCollection, range);
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Mountain create(@Valid @RequestBody final Mountain mountain) throws ResourceException {
		return mountainService.create(mountain);
	}

	@RequestMapping(value = "/{mountainId}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Mountain modify(@Valid @RequestBody final Mountain mountain, final @PathVariable int mountainId)
			throws ResourceException {
		mountain.setId(mountainId);
		return mountainService.update(mountain);
	}

	@RequestMapping(value = "/{mountainId}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Mountain delete(final @PathVariable int mountainId) throws ResourceException {
		return mountainService.delete(mountainService.getById(mountainId));
	}

	@RequestMapping(method = RequestMethod.GET, params = { "mountainName" })
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public Collection<Mountain> getByName(
			@RequestParam(value = "mountainName", required = true) final String mountainName) throws ResourceException {
		return mountainService.getByName(mountainName);
	}
}
