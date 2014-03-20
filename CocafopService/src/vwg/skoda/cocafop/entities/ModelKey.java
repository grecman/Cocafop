package vwg.skoda.cocafop.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="GZ38T_MODELKEYS", schema="COCAFOP")
public class ModelKey implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	Integer id;

	@ManyToOne()
	@JoinColumn(name="ID_BRAND") //Jmeno slouce v databazi v aktualni tabulce 
	Brand brand;				// Typ a jmeno nadrezeneho objektu (toho foreign Key),kde toto jmeno musi byt namapovano (mappedBy="brand")

	@OneToMany(mappedBy="modelKey") // Ukazatel na jmeno podrizeneho objetku
	Set<MkOrder> mkOdred;			 // Typ a jmeno podrizenych objektu
	
	@OneToMany(mappedBy="modelKey") // Ukazatel na jmeno podrizeneho objetku
	Set<LocalContent> localContent;			 // Typ a jmeno podrizenych objektu

	
	@Column
	Integer modelNumber;
	
	@Column
	Integer rok;
	
	@Column
	String typ;
	
	@Column
	String modelKey;
	
	@Column
	String colour;
	
	@Column
	Integer validFromYear;
	
	@Column
	Integer validFromMonth = 1;
	
	@Column
	Integer validToYear;
	
	@Column
	Integer validToMonth = 12;

	@Column
	String options;
	
	@Column
	String commentUser;
	
	@Column
	Boolean used = false;
	
	@Column
	String uuser;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	Date utime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public Integer getModelNumber() {
		return modelNumber;
	}

	public void setModelNumber(Integer modelNumber) {
		this.modelNumber = modelNumber;
	}

		
	public Integer getRok() {
		return rok;
	}

	public void setRok(Integer rok) {
		this.rok = rok;
	}

	public String getTyp() {
		return typ;
	}

	public void setTyp(String typ) {
		this.typ = typ;
	}

	public String getModelKey() {
		return modelKey;
	}

	public void setModelKey(String modelKey) {
		this.modelKey = modelKey.toUpperCase();
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public Integer getValidFromYear() {
		return validFromYear;
	}

	public void setValidFromYear(Integer validFromYear) {
		this.validFromYear = validFromYear;
	}

	public Integer getValidFromMonth() {
		return validFromMonth;
	}

	public void setValidFromMonth(Integer validFromMonth) {
		this.validFromMonth = validFromMonth;
	}

	public Integer getValidToYear() {
		return validToYear;
	}

	public void setValidToYear(Integer validToYear) {
		this.validToYear = validToYear;
	}

	public Integer getValidToMonth() {
		return validToMonth;
	}

	public void setValidToMonth(Integer validToMonth) {
		this.validToMonth = validToMonth;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public String getCommentUser() {
		return commentUser;
	}

	public void setCommentUser(String commentUser) {
		this.commentUser = commentUser;
	}
	
	public Boolean getUsed() {
		return used;
	}

	public void setUsed(Boolean used) {
		this.used = used;
	}

	public String getUuser() {
		return uuser;
	}

	public void setUuser(String uuser) {
		this.uuser = uuser;
	}

	public Date getUtime() {
		return utime;
	}

	public void setUtime(Date utime) {
		this.utime = utime;
	}

	public Set<MkOrder> getMkOdred() {
		return mkOdred;
	}

	public void setMkOdred(Set<MkOrder> mkOdred) {
		this.mkOdred = mkOdred;
	}
	
	
	
}
