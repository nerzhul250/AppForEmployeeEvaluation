package com.usoft.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the CRITERIA database table.
 * 
 */
@Entity
@NamedQuery(name="Criteria.findAll", query="SELECT c FROM Criteria c")
public class Criteria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	private String description;

	private BigDecimal isdeleted;

	private BigDecimal percent;

	//bi-directional many-to-one association to Indicatorposition
	@ManyToOne
	private Indicatorposition indicatorposition;

	//bi-directional many-to-one association to Evaluationcriteria
	@OneToMany(mappedBy="criteria")
	private List<Evaluationcriteria> evaluationcriterias;

	public Criteria() {
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

	public BigDecimal getPercent() {
		return this.percent;
	}

	public void setPercent(BigDecimal percent) {
		this.percent = percent;
	}

	public Indicatorposition getIndicatorposition() {
		return this.indicatorposition;
	}

	public void setIndicatorposition(Indicatorposition indicatorposition) {
		this.indicatorposition = indicatorposition;
	}

	public List<Evaluationcriteria> getEvaluationcriterias() {
		return this.evaluationcriterias;
	}

	public void setEvaluationcriterias(List<Evaluationcriteria> evaluationcriterias) {
		this.evaluationcriterias = evaluationcriterias;
	}

	public Evaluationcriteria addEvaluationcriteria(Evaluationcriteria evaluationcriteria) {
		getEvaluationcriterias().add(evaluationcriteria);
		evaluationcriteria.setCriteria(this);

		return evaluationcriteria;
	}

	public Evaluationcriteria removeEvaluationcriteria(Evaluationcriteria evaluationcriteria) {
		getEvaluationcriterias().remove(evaluationcriteria);
		evaluationcriteria.setCriteria(null);

		return evaluationcriteria;
	}

}