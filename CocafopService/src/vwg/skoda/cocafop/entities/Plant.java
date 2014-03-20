package vwg.skoda.cocafop.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.log4j.Logger;

@Entity
@Table(name="GZ38T_PLANTS", schema="COCAFOP")
public class Plant implements Serializable {
	
	static Logger log = Logger.getLogger(Plant.class);
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column
	String id;

	@OneToMany(mappedBy="plant") // Ukazatel na jmeno podrizeneho objetku
	Set<Brand> brands;			 // Typ a jmeno podrizenych objektu

	@OneToMany(mappedBy="plant")
	Set<PermissionPlant> permissionPlant;
	
	@OneToMany(mappedBy="plant")
	Set<ExchangeRate> exchangeRate;

	@Column
	String plantName;
	
	
	@Column
	String uuser;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	Date utime;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public Set<Brand> getBrands() {
		return brands;
	}

	public void setBrands(Set<Brand> brands) {
		this.brands = brands;
	}
	
	public String getPlantName() {
		return plantName;
	}

	public void setPlantName(String plantName) {
		this.plantName = plantName;
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

	@Override
	public String toString() {
		if (log.isDebugEnabled()) {
			return super.toString() + "{id=" + getId() + ", name=" + getPlantName() + "}";
		}
		return super.toString();
	}

}
