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
@Table(name="GZ38T_BRANDS", schema="COCAFOP")
public class Brand implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	Integer id;
	
	@ManyToOne() 
	@JoinColumn(name="ID_PLANT") //Jmeno slouce v databazi v aktualni tabulce (GZ38T_BRANDS)
	Plant plant;				 // Typ a jmeno nadrezeneho objektu (toho foreign Key),kde toto jmeno musi byt namapovano (mappedBy="plant")
	
	@OneToMany(mappedBy="brand")
	Set<PermissionBrand> permissionBrand;
	
	@OneToMany(mappedBy="brand")
	Set<ModelKey> model;
	
	@Column(name="brand")
	String brandMark;
	
	@Column
	String brandName;
	
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

	
	public String getBrandMark() {
		return brandMark;
	}

	public void setBrandMark(String brandMark) {
		this.brandMark = brandMark;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
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
	
	public Plant getPlant() {
		return plant;
	}

	public void setPlant(Plant plant) {
		this.plant = plant;
	}

	public Set<PermissionBrand> getPermissionBrand() {
		return permissionBrand;
	}

	public void setPermissionBrand(Set<PermissionBrand> permissionBrand) {
		this.permissionBrand = permissionBrand;
	}

	public Set<ModelKey> getModel() {
		return model;
	}

	public void setModel(Set<ModelKey> model) {
		this.model = model;
	}



}
