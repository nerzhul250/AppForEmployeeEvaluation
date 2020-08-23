package com.usoft.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the EVALUATIONCRITERIA database table.
 * 
 */
public class EvaluationcriteriaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private long criteriaId;
	private long evaluationId;


	public EvaluationcriteriaPK() {
	}
	public long getCriteriaId() {
		return this.criteriaId;
	}
	public void setCriteriaId(long criteriaId) {
		this.criteriaId = criteriaId;
	}
	public long getEvaluationId() {
		return this.evaluationId;
	}
	public void setEvaluationId(long evaluationId) {
		this.evaluationId = evaluationId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EvaluationcriteriaPK)) {
			return false;
		}
		EvaluationcriteriaPK castOther = (EvaluationcriteriaPK)other;
		return 
			(this.criteriaId == castOther.criteriaId)
			&& (this.evaluationId == castOther.evaluationId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.criteriaId ^ (this.criteriaId >>> 32)));
		hash = hash * prime + ((int) (this.evaluationId ^ (this.evaluationId >>> 32)));
		
		return hash;
	}
}