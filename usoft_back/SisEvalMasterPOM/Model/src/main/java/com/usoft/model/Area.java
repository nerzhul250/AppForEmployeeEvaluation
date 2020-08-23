package com.usoft.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the AREA database table.
 * 
 */
@Entity
@NamedQuery(name="Area.findAll", query="SELECT a FROM Area a")
public class Area implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	private String description;

	private BigDecimal isdeleted;

	private String name;

	//bi-directional many-to-one association to Careerplan
	@OneToMany(mappedBy="area")
	private List<Careerplan> careerplans;

	//bi-directional many-to-one association to Usuario
	@OneToMany(mappedBy="area")
	private List<Usuario> usuarios;

	public Area() {
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

	public List<Careerplan> getCareerplans() {
		return this.careerplans;
	}

	public void setCareerplans(List<Careerplan> careerplans) {
		this.careerplans = careerplans;
	}

	public Careerplan addCareerplan(Careerplan careerplan) {
		getCareerplans().add(careerplan);
		careerplan.setArea(this);

		return careerplan;
	}

	public Careerplan removeCareerplan(Careerplan careerplan) {
		getCareerplans().remove(careerplan);
		careerplan.setArea(null);

		return careerplan;
	}

	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario addUsuario(Usuario usuario) {
		getUsuarios().add(usuario);
		usuario.setArea(this);

		return usuario;
	}

	public Usuario removeUsuario(Usuario usuario) {
		getUsuarios().remove(usuario);
		usuario.setArea(null);

		return usuario;
	}

}