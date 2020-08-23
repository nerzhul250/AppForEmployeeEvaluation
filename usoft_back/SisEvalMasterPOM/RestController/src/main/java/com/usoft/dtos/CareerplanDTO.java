package com.usoft.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.usoft.model.Indicatorposition;

public class CareerplanDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private String description;
	private String name;
	private AreaDTO area;
	private StateDTO state;
	public StateDTO getState() {
		return state;
	}

	public void setState(StateDTO state) {
		this.state = state;
	}

	private BigDecimal positionLevel;
	private List<IndicatorpositionDTO> indicatorpositions;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public AreaDTO getArea() {
		return area;
	}

	public void setArea(AreaDTO area) {
		this.area = area;
	}

	public List<IndicatorpositionDTO> getIndicatorpositions() {
		return indicatorpositions;
	}

	public void setIndicatorpositions(List<IndicatorpositionDTO> indicatorpositions) {
		this.indicatorpositions = indicatorpositions;
	}

	public BigDecimal getPositionLevel() {
		return positionLevel;
	}

	public void setPositionLevel(BigDecimal positionLevel) {
		this.positionLevel = positionLevel;
	}


}
