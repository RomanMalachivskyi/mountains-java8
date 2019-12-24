package com.home.education.mountains.resource.impl;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.home.education.mountains.resource.WeatherCondition;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "Route")
public class Route extends GenericResourceImpl {
	
	private static final long serialVersionUID = -6139890331397993029L;
	
	private int id;
	@NotNull(message = "Route should have connectivity with Mountain")
	private int mountainId;
	@NotNull(message = "Route should have connectivity with Category")
	private int categoryId;
	@NotBlank(message = "RouteName cann't be empty")
	@Length(max = 45, message = "RouteName cannot be greater than 45 characters")
	private String name;
	@Length(max = 100, message = "Description cannot be greater than 100 characters")
	private String description;
	private WeatherCondition weatherCondition;

	@Column(name = "name", nullable = false)
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

	@Column(name="mountainId")
	public int getMountainId() {
		return mountainId;
	}

	public void setMountainId(int mountainId) {
		this.mountainId = mountainId;
	}

	@Column(name="categoryId")
	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	@Column(name = "WeatherCondition")
	@Enumerated(EnumType.STRING)
	public WeatherCondition getWeatherCondition() {
		return weatherCondition;
	}

	public void setWeatherCondition(WeatherCondition weatherCondition) { this.weatherCondition = weatherCondition;}

	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "routeId", unique = true, nullable = false)
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}
}
