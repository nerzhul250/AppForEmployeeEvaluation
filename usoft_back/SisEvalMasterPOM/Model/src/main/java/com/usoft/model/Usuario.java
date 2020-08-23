package com.usoft.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the USUARIO database table.
 * 
 */
@Entity
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	@Temporal(TemporalType.DATE)
	private Date birthday;

	private String email;

	@Column(name="FIRST_NAME")
	private String firstName;

	private BigDecimal isdeleted;

	@Column(name="LAST_NAME")
	private String lastName;

	private String password;

	private String phone;

	//bi-directional many-to-one association to Evaluation
	@OneToMany(mappedBy="usuario1")
	private List<Evaluation> evaluations1;

	//bi-directional many-to-one association to Evaluation
	@OneToMany(mappedBy="usuario2")
	private List<Evaluation> evaluations2;

	//bi-directional many-to-one association to Positionuser
	@OneToMany(mappedBy="usuario")
	private List<Positionuser> positionusers;

	//bi-directional many-to-one association to Token
	@OneToMany(mappedBy="usuario")
	private List<Token> tokens;

	//bi-directional many-to-one association to Userrole
	@OneToMany(mappedBy="usuario")
	private List<Userrole> userroles;

	//bi-directional many-to-one association to Area
	@ManyToOne
	private Area area;

	public Usuario() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public BigDecimal getIsdeleted() {
		return this.isdeleted;
	}

	public void setIsdeleted(BigDecimal isdeleted) {
		this.isdeleted = isdeleted;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<Evaluation> getEvaluations1() {
		return this.evaluations1;
	}

	public void setEvaluations1(List<Evaluation> evaluations1) {
		this.evaluations1 = evaluations1;
	}

	public Evaluation addEvaluations1(Evaluation evaluations1) {
		getEvaluations1().add(evaluations1);
		evaluations1.setUsuario1(this);

		return evaluations1;
	}

	public Evaluation removeEvaluations1(Evaluation evaluations1) {
		getEvaluations1().remove(evaluations1);
		evaluations1.setUsuario1(null);

		return evaluations1;
	}

	public List<Evaluation> getEvaluations2() {
		return this.evaluations2;
	}

	public void setEvaluations2(List<Evaluation> evaluations2) {
		this.evaluations2 = evaluations2;
	}

	public Evaluation addEvaluations2(Evaluation evaluations2) {
		getEvaluations2().add(evaluations2);
		evaluations2.setUsuario2(this);

		return evaluations2;
	}

	public Evaluation removeEvaluations2(Evaluation evaluations2) {
		getEvaluations2().remove(evaluations2);
		evaluations2.setUsuario2(null);

		return evaluations2;
	}

	public List<Positionuser> getPositionusers() {
		return this.positionusers;
	}

	public void setPositionusers(List<Positionuser> positionusers) {
		this.positionusers = positionusers;
	}

	public Positionuser addPositionuser(Positionuser positionuser) {
		getPositionusers().add(positionuser);
		positionuser.setUsuario(this);

		return positionuser;
	}

	public Positionuser removePositionuser(Positionuser positionuser) {
		getPositionusers().remove(positionuser);
		positionuser.setUsuario(null);

		return positionuser;
	}

	public List<Token> getTokens() {
		return this.tokens;
	}

	public void setTokens(List<Token> tokens) {
		this.tokens = tokens;
	}

	public Token addToken(Token token) {
		getTokens().add(token);
		token.setUsuario(this);

		return token;
	}

	public Token removeToken(Token token) {
		getTokens().remove(token);
		token.setUsuario(null);

		return token;
	}

	public List<Userrole> getUserroles() {
		return this.userroles;
	}

	public void setUserroles(List<Userrole> userroles) {
		this.userroles = userroles;
	}

	public Userrole addUserrole(Userrole userrole) {
		getUserroles().add(userrole);
		userrole.setUsuario(this);

		return userrole;
	}

	public Userrole removeUserrole(Userrole userrole) {
		getUserroles().remove(userrole);
		userrole.setUsuario(null);

		return userrole;
	}

	public Area getArea() {
		return this.area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

}