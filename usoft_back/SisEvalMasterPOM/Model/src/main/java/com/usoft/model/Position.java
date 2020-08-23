package com.usoft.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

/**
 * The persistent class for the POSITION database table.
 * 
 */
@Entity
@NamedQuery(name="Position.findAll", query="SELECT p FROM Position p")
public class Position implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	private String description;

	private BigDecimal isdeleted;

	private String name;

	//bi-directional many-to-one association to Indicatorposition
	@OneToMany(mappedBy="position")
	@JsonProperty(access = Access.WRITE_ONLY)
	private List<Indicatorposition> indicatorpositions;

	//bi-directional many-to-one association to Positionuser
	@OneToMany(mappedBy="position")
	@JsonProperty(access = Access.WRITE_ONLY)
	private List<Positionuser> positionusers;

	public Position() {
		isdeleted=new BigDecimal(0);
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
		indicatorposition.setPosition(this);

		return indicatorposition;
	}

	public Indicatorposition removeIndicatorposition(Indicatorposition indicatorposition) {
		getIndicatorpositions().remove(indicatorposition);
		indicatorposition.setPosition(null);

		return indicatorposition;
	}

	public List<Positionuser> getPositionusers() {
		return this.positionusers;
	}

	public void setPositionusers(List<Positionuser> positionusers) {
		this.positionusers = positionusers;
	}

	public Positionuser addPositionuser(Positionuser positionuser) {
		getPositionusers().add(positionuser);
		positionuser.setPosition(this);

		return positionuser;
	}

	public Positionuser removePositionuser(Positionuser positionuser) {
		getPositionusers().remove(positionuser);
		positionuser.setPosition(null);

		return positionuser;
	}

}