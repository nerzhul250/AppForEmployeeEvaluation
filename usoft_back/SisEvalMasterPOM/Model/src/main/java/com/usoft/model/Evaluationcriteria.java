package com.usoft.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the EVALUATIONCRITERIA database table.
 * 
 */
@Entity
@IdClass(EvaluationcriteriaPK.class)
@NamedQuery(name="Evaluationcriteria.findAll", query="SELECT e FROM Evaluationcriteria e")
public class Evaluationcriteria implements Serializable {
	private static final long serialVersionUID = 1L;

	private String comentario;

	private BigDecimal isdeleted;

	private BigDecimal valor;

	@Id
	@Column(name="CRITERIA_ID")
	private long criteriaId;

	@Id
	@Column(name="EVALUATION_ID")
	private long evaluationId;
	
	//bi-directional many-to-one association to Criteria
	@ManyToOne
	@JoinColumn(name="CRITERIA_ID", insertable = false, updatable = false)
	private Criteria criteria;

	//bi-directional many-to-one association to Evaluation
	
	@ManyToOne
	@JoinColumn(name="EVALUATION_ID", insertable = false, updatable = false)
	private Evaluation evaluation;

	public Evaluationcriteria() {
	}

	public String getComentario() {
		return this.comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public BigDecimal getIsdeleted() {
		return this.isdeleted;
	}

	public void setIsdeleted(BigDecimal isdeleted) {
		this.isdeleted = isdeleted;
	}

	public BigDecimal getValor() {
		return this.valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Criteria getCriteria() {
		return this.criteria;
	}

	public void setCriteria(Criteria criteria) {
		this.criteria = criteria;
	}

	public Evaluation getEvaluation() {
		return this.evaluation;
	}

	public void setEvaluation(Evaluation evaluation) {
		this.evaluation = evaluation;
	}
	public long getCriteriaId() {
		return criteriaId;
	}
	
	public void setCriteriaId(long criteriaId) {
		this.criteriaId = criteriaId;
	}
	
	public long getEvaluationId() {
		return evaluationId;
	}
	
	public void setEvaluationId(long evaluationId) {
		this.evaluationId = evaluationId;
	}

}