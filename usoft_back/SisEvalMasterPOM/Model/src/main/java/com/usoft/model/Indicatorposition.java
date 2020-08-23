package com.usoft.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the INDICATORPOSITION database table.
 * 
 */
@Entity
@NamedQuery(name="Indicatorposition.findAll", query="SELECT i FROM Indicatorposition i")
public class Indicatorposition implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	private BigDecimal orden;

	private BigDecimal percent;

	//bi-directional many-to-one association to Criteria
	@OneToMany(mappedBy="indicatorposition")
	private List<Criteria> criterias;

	//bi-directional many-to-one association to Careerplan
	@ManyToOne
	private Careerplan careerplan;

	//bi-directional many-to-one association to Indicator
	@ManyToOne
	private Indicator indicator;

	//bi-directional many-to-one association to Position
	@ManyToOne
	private Position position;

	public Indicatorposition() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getOrden() {
		return this.orden;
	}

	public void setOrden(BigDecimal orden) {
		this.orden = orden;
	}

	public BigDecimal getPercent() {
		return this.percent;
	}

	public void setPercent(BigDecimal percent) {
		this.percent = percent;
	}

	public List<Criteria> getCriterias() {
		return this.criterias;
	}

	public void setCriterias(List<Criteria> criterias) {
		this.criterias = criterias;
	}

	public Criteria addCriteria(Criteria criteria) {
		getCriterias().add(criteria);
		criteria.setIndicatorposition(this);

		return criteria;
	}

	public Criteria removeCriteria(Criteria criteria) {
		getCriterias().remove(criteria);
		criteria.setIndicatorposition(null);

		return criteria;
	}

	public Careerplan getCareerplan() {
		return this.careerplan;
	}

	public void setCareerplan(Careerplan careerplan) {
		this.careerplan = careerplan;
	}

	public Indicator getIndicator() {
		return this.indicator;
	}

	public void setIndicator(Indicator indicator) {
		this.indicator = indicator;
	}

	public Position getPosition() {
		return this.position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

}