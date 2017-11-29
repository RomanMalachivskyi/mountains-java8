package com.home.education.mountains.resource.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="Mountain", uniqueConstraints={@UniqueConstraint(columnNames={"name", "locationId"}, name="uq_mountain_name_in_location")})
public class Mountain extends GenericResourceImpl {

	private static final long serialVersionUID = -5942986553948958078L;
	
	private int id;
	@NotBlank(message = "name can't be empty")
	@Length(max = 45, message = "MountainName cannot be greater than 45 characters")
	private String name;
	@Length(max = 100, message = "Description cannot be greater than 100 characters")
	private String description;
	@Min(value = 0,  message = "the mountain cann't has a negative height, please verify your data")
	@Max(value = 8848, message = "the highest peak has a height 8848, please verify your data")
	private int height;
	@NotNull(message = "Mountain should have connectivity with Location")
	private int locationId; 
	
	@Column(name = "name", unique = true, nullable = false)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "height")
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	@Column(name = "locationId")
	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "mountainId", unique = true, nullable = false)
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}
}
