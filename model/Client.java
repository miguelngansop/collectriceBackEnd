 package com.sarki.micro.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name="clients")
@EntityListeners(AuditingEntityListener.class)
public class Client implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5241588316723078417L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@NotBlank
	private String nom;
	@NotBlank
	private String prenom;
	private String dateDeNaissance;
	private String numcni;
	private String telephone;
	private String email;
	private String residence;
	private String profession;
	private String nomDuPere;
	private String nomDeLaMere;
	private String photo;
	private String lieuDeNaissance;
	private String passport;
	private String cartedesejour;
	
	@OneToMany(mappedBy="client",fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	private Collection<Compte> comptes;
	
	@ManyToOne
	@JoinColumn(name = "CODE_AGENT")
	private Agent  agent;
	
	
	public Client() {}


	public Client(long id, @NotBlank String nom, @NotBlank String prenom, String dateDeNaissance,
			@NotBlank String numcni, String telephone, String email, String residence, String profession,
			String nomDuPere, String nomDeLaMere, String photo, String lieuDeNaissance, String passport,
			String cartedesejour, Collection<Compte> comptes) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.dateDeNaissance = dateDeNaissance;
		this.numcni = numcni;
		this.telephone = telephone;
		this.email = email;
		this.residence = residence;
		this.profession = profession;
		this.nomDuPere = nomDuPere;
		this.nomDeLaMere = nomDeLaMere;
		this.photo = photo;
		this.lieuDeNaissance = lieuDeNaissance;
		this.passport = passport;
		this.cartedesejour = cartedesejour;
		this.comptes = comptes;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getDateDeNaissance() {
		return dateDeNaissance;
	}

	public void setDateDeNaissance(String dateDeNaissance) {
		this.dateDeNaissance = dateDeNaissance;
	}

	public String getNumcni() {
		return numcni;
	}

	public void setNumcni(String numcni) {
		this.numcni = numcni;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getResidence() {
		return residence;
	}

	public void setResidence(String residence) {
		this.residence = residence;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getNomDuPere() {
		return nomDuPere;
	}

	public void setNomDuPere(String nomDuPere) {
		this.nomDuPere = nomDuPere;
	}

	public String getNomDeLaMere() {
		return nomDeLaMere;
	}

	public void setNomDeLaMere(String nomDeLaMere) {
		this.nomDeLaMere = nomDeLaMere;
	}


	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public void setComptes(Collection<Compte> comptes) {
		this.comptes = comptes;
	}

	public String getLieuDeNaissance() {
		return lieuDeNaissance;
	}

	public void setLieuDeNaissance(String lieuDeNaissance) {
		this.lieuDeNaissance = lieuDeNaissance;
	}

	public String getPassport() {
		return passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

	public String getCartedesejour() {
		return cartedesejour;
	}

	public void setCartedesejour(String cartedesejour) {
		this.cartedesejour = cartedesejour;
	}


	public Agent getAgent() {
		return agent;
	}


	public void setAgent(Agent agent) {
		this.agent = agent;
	}
	
	
	}
