package com.usoft.dtos;

import java.io.Serializable;
import java.math.BigDecimal;

import java.util.List;
import com.usoft.model.Indicator;
import com.usoft.model.Position; 

public class IndicatorpositionDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private long id;
	private BigDecimal orden;
	private BigDecimal percent;
	private IndicatorDTO indicator;
	private PositionDTO position;
	private List<CriteriaDTO> criterias;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public BigDecimal getOrden() {
		return orden;
	}
	

	public void setOrden(BigDecimal orden) {
		this.orden = orden;
	}
	public BigDecimal getPercent() {
		return percent;
	}
	public void setPercent(BigDecimal percent) {
		this.percent = percent;
	}
	public IndicatorDTO getIndicator() {
		return indicator;
	}
	public void setIndicator(IndicatorDTO indicatorDTO) {
		this.indicator = indicatorDTO;
	}
	public PositionDTO getPosition() {
		return position;
	}
	public void setPosition(PositionDTO positionDTO) {
		this.position = positionDTO;
	}
	public List<CriteriaDTO> getCriterias() {
		return criterias;
	}
	public void setCriterias(List<CriteriaDTO> criterias) {
		this.criterias = criterias;
	}	
	
	
	
}
