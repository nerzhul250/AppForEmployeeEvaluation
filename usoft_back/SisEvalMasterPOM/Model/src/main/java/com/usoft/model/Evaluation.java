package com.usoft.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the EVALUATION database table.
 * 
 */
@Entity
@NamedQuery(name="Evaluation.findAll", query="SELECT e FROM Evaluation e")
public class Evaluation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	private String comentario;

	private BigDecimal isdeleted;

	//bi-directional many-to-one association to Careerplan
	@ManyToOne
	private Careerplan careerplan;

	//bi-directional many-to-one association to Period
	@ManyToOne
	private Period period;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="USER_ID2")
	private Usuario usuario1;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="USER_ID")
	private Usuario usuario2;

	//bi-directional many-to-one association to Evaluationcriteria
	@OneToMany(mappedBy="evaluation")
	private List<Evaluationcriteria> evaluationcriterias;

	public Evaluation() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
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

	public Careerplan getCareerplan() {
		return this.careerplan;
	}

	public void setCareerplan(Careerplan careerplan) {
		this.careerplan = careerplan;
	}

	public Period getPeriod() {
		return this.period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	public Usuario getUsuario1() {
		return this.usuario1;
	}

	public void setUsuario1(Usuario usuario1) {
		this.usuario1 = usuario1;
	}

	public Usuario getUsuario2() {
		return this.usuario2;
	}

	public void setUsuario2(Usuario usuario2) {
		this.usuario2 = usuario2;
	}

	public List<Evaluationcriteria> getEvaluationcriterias() {
		return this.evaluationcriterias;
	}

	public void setEvaluationcriterias(List<Evaluationcriteria> evaluationcriterias) {
		this.evaluationcriterias = evaluationcriterias;
	}

	public Evaluationcriteria addEvaluationcriteria(Evaluationcriteria evaluationcriteria) {
		getEvaluationcriterias().add(evaluationcriteria);
		evaluationcriteria.setEvaluation(this);

		return evaluationcriteria;
	}

	public Evaluationcriteria removeEvaluationcriteria(Evaluationcriteria evaluationcriteria) {
		getEvaluationcriterias().remove(evaluationcriteria);
		evaluationcriteria.setEvaluation(null);

		return evaluationcriteria;
	}

}