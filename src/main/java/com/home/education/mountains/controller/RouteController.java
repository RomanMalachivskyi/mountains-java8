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

import com.home.education.mountains.common.exception.ResourceException;
import com.home.education.mountains.resource.impl.Route;
import com.home.education.mountains.service.RouteService;

@Controller
@RequestMapping("/route")
public class RouteController {

	private final RouteService routeService;
	
	@Autowired
	public RouteController(final RouteService routeService) {
		this.routeService = routeService;
	}

	@RequestMapping(value = "/{routeId}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public Route getById(final @PathVariable int routeId) throws ResourceException {
		return routeService.getById(routeId);
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Route create(@Valid @RequestBody final Route route) throws ResourceException {
		return routeService.create(route);
	}

	@RequestMapping(value = "/{routeId}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Route modify(@Valid @RequestBody final Route route, final @PathVariable int routeId)
			throws ResourceException {
		route.setId(routeId);
		return routeService.update(route);
	}

	@RequestMapping(value = "/{routeId}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Route delete(final @PathVariable int routeId) throws ResourceException {
		return routeService.delete(routeService.getById(routeId));
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public Collection<Route> getFilteredRoute(@RequestParam(required = false, value = "mountainId") int[] mountainIds,
			@RequestParam(required = false, value = "categoryId") int... categoryIds) throws ResourceException {
		Collection<Integer> mountainIdsCollection = Collections.emptyList();
		Collection<Integer> categoryIdsCollection = Collections.emptyList();
		if (mountainIds != null) {
			mountainIdsCollection = Arrays.stream(mountainIds).boxed().collect(Collectors.toList());
		}
		if (categoryIds != null) {
			categoryIdsCollection = Arrays.stream(mountainIds).boxed().collect(Collectors.toList());
		}
		return routeService.getByMountainIdsAndCategoryIds(mountainIdsCollection, categoryIdsCollection);
	}
	
	@RequestMapping(method = RequestMethod.GET, params = {"routeName"})
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public Route getByName(@RequestParam(value = "routeName", required = true) final String routeName) throws ResourceException {
		return routeService.getByName(routeName);
	}
}
