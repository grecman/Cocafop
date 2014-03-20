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
@Table(name="GZ38T_EXCHANGERATES", schema="COCAFOP")
public class ExchangeRate implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	Integer id;
	
	@ManyToOne() 
	@JoinColumn(name="ID_PLANT") //Jmeno slouce v databazi v aktualni tabulce (GZ38T_EXCHANGERATES)
	Plant plant;				// Typ a jmeno nadrezeneho objektu (toho foreign Key),kde toto jmeno musi byt namapovano (mappedBy="plant")
	
	@Column
	Integer rok;
	
	@Column
	Integer mesic;

	/* tato divna anotace zajisti to, ze flot je mozne zadavat s carkou "," a nikoliv s teckou "." */ 
	@NumberFormat(pattern = "0.0000")
	@Column
	Float rate;
	
	@Column
	String currency;
	
	/* v aplikaci nepouzito !!! */
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

	public Plant getPlant() {
		return plant;
	}

	public void setPlant(Plant plant) {
		this.plant = plant;
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

	public Float getRate() {
		return rate;
	}

	public void setRate(Float rate) {
		this.rate = rate;
	}
	

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
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
