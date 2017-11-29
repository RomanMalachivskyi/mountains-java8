package com.home.education.mountains.resource.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "Location")
@JsonSerialize
public class Location extends GenericResourceImpl {

	private static final long serialVersionUID = -2548061502644911318L;
	
	private int id;
	@NotBlank(message = "MountainRange cann't be empty")
	@Length(max = 45, message = "MountainRange cann't be greater than 45 characters")
	private String mountainRange;
	@NotBlank(message = "Country cann't be empty")
	@Length(max = 45, message = "Country cann't be greater than 45 characters")
	private String country;
	@Length(max = 100, message = "Description cann't be greater than 100 characters")
	private String description;

	public Location() {
	}
	
	public Location(String mountainRange, String country) {
		super();
		this.mountainRange = mountainRange;
		this.country = country;
	}

	@Column(name = "mountainRange", nullable = false, unique = false)
	public String getMountainRange() {
		return mountainRange;
	}

	public void setMountainRange(String mountainRange) {
		this.mountainRange = mountainRange;
	}

	@Column(name = "country", nullable = false)
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Column(name = "description", nullable = true)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	};

	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "locationId")
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id=id;
	}
}
