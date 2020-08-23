package com.usoft.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the POSITIONUSER database table.
 * 
 */
@Entity
@NamedQuery(name="Positionuser.findAll", query="SELECT p FROM Positionuser p")
public class Positionuser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	@Temporal(TemporalType.DATE)
	private Date fecha;

	private BigDecimal isdeleted;

	//bi-directional many-to-one association to Position
	@ManyToOne
	private Position position;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="USER_ID")
	private Usuario usuario;

	public Positionuser() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getIsdeleted() {
		return this.isdeleted;
	}

	public void setIsdeleted(BigDecimal isdeleted) {
		this.isdeleted = isdeleted;
	}

	public Position getPosition() {
		return this.position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}