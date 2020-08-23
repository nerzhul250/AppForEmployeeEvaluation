package com.usoft.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the CAREERPLAN database table.
 * 
 */
@Entity
@NamedQuery(name="Careerplan.findAll", query="SELECT c FROM Careerplan c")
public class Careerplan implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	private String description;

	private BigDecimal isdeleted;

	private String name;

	@Column(name="POSITION_LEVEL")
	private BigDecimal positionLevel;

	//bi-directional many-to-one association to Area
	@ManyToOne
	private Area area;

	//bi-directional many-to-one association to Evaluation
	@OneToMany(mappedBy="careerplan")
	private List<Evaluation> evaluations;

	//bi-directional many-to-one association to Indicatorposition
	@OneToMany(mappedBy="careerplan")
	private List<Indicatorposition> indicatorpositions;

	public Careerplan() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getIsdeleted() {
		return this.isdeleted;
	}

	public void setIsdeleted(BigDecimal isdeleted) {
		this.isdeleted = isdeleted;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPositionLevel() {
		return this.positionLevel;
	}

	public void setPositionLevel(BigDecimal positionLevel) {
		this.positionLevel = positionLevel;
	}

	public Area getArea() {
		return this.area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public List<Evaluation> getEvaluations() {
		return this.evaluations;
	}

	public void setEvaluations(List<Evaluation> evaluations) {
		this.evaluations = evaluations;
	}

	public Evaluation addEvaluation(Evaluation evaluation) {
		getEvaluations().add(evaluation);
		evaluation.setCareerplan(this);

		return evaluation;
	}

	public Evaluation removeEvaluation(Evaluation evaluation) {
		getEvaluations().remove(evaluation);
		evaluation.setCareerplan(null);

		return evaluation;
	}

	public List<Indicatorposition> getIndicatorpositions() {
		return this.indicatorpositions;
	}

	public void setIndicatorpositions(List<Indicatorposition> indicatorpositions) {
		this.indicatorpositions = indicatorpositions;
	}

	public Indicatorposition addIndicatorposition(Indicatorposition indicatorposition) {
		getIndicatorpositions().add(indicatorposition);
		indicatorposition.setCareerplan(this);

		return indicatorposition;
	}

	public Indicatorposition removeIndicatorposition(Indicatorposition indicatorposition) {
		getIndicatorpositions().remove(indicatorposition);
		indicatorposition.setCareerplan(null);

		return indicatorposition;
	}

}