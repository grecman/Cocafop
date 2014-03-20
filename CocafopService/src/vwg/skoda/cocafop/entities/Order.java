package vwg.skoda.cocafop.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "GZ38T_ORDERS", schema = "COCAFOP")
public class Order implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy="order") // Ukazatel na jmeno podrizeneho objetku
	Set<MkOrder> mkOdred;			 // Typ a jmeno podrizenych objektu
	
	@Id
	@GeneratedValue
	Integer id;

	@Column
	String kifa;

	@Column
	String knr;

	@Column
	String vin;
	
	@Column
	String modelKey;

	@Column
	String modelKeyVersion;

	@Column
	String colour;

	@Column
	Integer modelYear;

	@Column
	String deliveryProgram;

	@Column
	String options;

	@Column
	String PrDescription;

	@Temporal(TemporalType.TIMESTAMP)
	@Column
	Date productionDate;

	@Column
	String wk;
	
	@Column
	Integer rok;

	@Column
	Integer mesic;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getKifa() {
		return kifa;
	}

	public void setKifa(String kifa) {
		this.kifa = kifa;
	}

	public String getKnr() {
		return knr;
	}

	public void setKnr(String knr) {
		this.knr = knr;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getModelKey() {
		return modelKey;
	}

	public void setModelKey(String modelKey) {
		this.modelKey = modelKey;
	}

	public String getModelKeyVersion() {
		return modelKeyVersion;
	}

	public void setModelKeyVersion(String modelKeyVersion) {
		this.modelKeyVersion = modelKeyVersion;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public Integer getModelYear() {
		return modelYear;
	}

	public void setModelYear(Integer modelYear) {
		this.modelYear = modelYear;
	}

	public String getDeliveryProgram() {
		return deliveryProgram;
	}

	public void setDeliveryProgram(String deliveryProgram) {
		this.deliveryProgram = deliveryProgram;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public String getPrDescription() {
		return PrDescription;
	}

	public void setPrDescription(String prDescription) {
		PrDescription = prDescription;
	}

	public Date getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}


	public Set<MkOrder> getMkOdred() {
		return mkOdred;
	}

	public void setMkOdred(Set<MkOrder> mkOdred) {
		this.mkOdred = mkOdred;
	}

	public String getWk() {
		return wk;
	}

	public void setWk(String wk) {
		this.wk = wk;
	}

	public Integer getRok() {
		return rok;
	}

	public void setRok(Integer rok) {
		this.rok = rok;
	}

	public Integer getMesic() {
		return mesic;
	}

	public void setMesic(Integer mesic) {
		this.mesic = mesic;
	}
		
}
