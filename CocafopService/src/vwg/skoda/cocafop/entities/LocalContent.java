package vwg.skoda.cocafop.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.NumberFormat;

@Entity
@Table(name="GZ38T_LOCALCONTENT", schema="COCAFOP")
public class LocalContent implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@ManyToOne()
	@JoinColumn(name="ID_MODELKEY")
	ModelKey modelKey;
	
	@Id
	@GeneratedValue
	Integer id;

	@Column
	String partNumber;

	@Column
	String partDescription;
	
	@Column
	String typ;
	
	
	/* tato divna anotace zajisti to, ze flot je mozne zadavat s carkou "," a nikoliv s teckou "." */ 
	@NumberFormat(pattern = "0.00")
	@Column
	Float quantity;

	@Column
	String uom;
	
	@Column
	Boolean used = false;
	
	@Column
	String uuser;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	Date utime;

	public ModelKey getModelKey() {
		return modelKey;
	}

	public void setModelKey(ModelKey modelKey) {
		this.modelKey = modelKey;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getPartDescription() {
		return partDescription;
	}

	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}

	public String getTyp() {
		return typ;
	}

	public void setTyp(String typ) {
		this.typ = typ;
	}

	public Float getQuantity() {
		return quantity;
	}

	public void setQuantity(Float quantity) {
		this.quantity = quantity;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
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

	
}
