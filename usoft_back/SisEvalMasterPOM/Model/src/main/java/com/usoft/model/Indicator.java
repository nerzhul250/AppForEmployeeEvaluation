package com.usoft.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the INDICATOR database table.
 * 
 */
@Entity
@NamedQuery(name="Indicator.findAll", query="SELECT i FROM Indicator i")
public class Indicator implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	private String description;

	private BigDecimal isdeleted;

	private String name;

	//bi-directional many-to-one association to Indicatorposition
	@OneToMany(mappedBy="indicator")
	private List<Indicatorposition> indicatorpositions;

	public Indicator() {
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

	public List<Indicatorposition> getIndicatorpositions() {
		return this.indicatorpositions;
	}

	public void setIndicatorpositions(List<Indicatorposition> indicatorpositions) {
		this.indicatorpositions = indicatorpositions;
	}

	public Indicatorposition addIndicatorposition(Indicatorposition indicatorposition) {
		getIndicatorpositions().add(indicatorposition);
		indicatorposition.setIndicator(this);

		return indicatorposition;
	}

	public Indicatorposition removeIndicatorposition(Indicatorposition indicatorposition) {
		getIndicatorpositions().remove(indicatorposition);
		indicatorposition.setIndicator(null);

		return indicatorposition;
	}

}