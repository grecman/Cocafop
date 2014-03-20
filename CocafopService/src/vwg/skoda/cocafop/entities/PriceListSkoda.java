package vwg.skoda.cocafop.entities;

import java.io.Serializable;
import javax.persistence.*;

import org.springframework.format.annotation.NumberFormat;

import java.util.Date;

@Entity
@Table(name="GZ38T_PRICELIST_SKODA", schema="COCAFOP")
public class PriceListSkoda implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false, precision=22)
	private long id;

	@Column(nullable=false, precision=22)
	private Integer mesic;

	@Column(name="PART_NUMBER", nullable=false, length=15)
	private String partNumber;

	/* tato divna anotace zajisti to, ze flot je mozne zadavat s carkou "," a nikoliv s teckou "." */ 
	@NumberFormat(pattern = "0.00000")
	@Column(precision=126)
	private Float price;

	@NumberFormat(pattern = "0.00000")
	@Column(name="PRICE_NEW", nullable=false, precision=126)
	private Float priceNew;
	
	@NumberFormat(pattern = "0.00")
	@Column(nullable=false, precision=126)
	private Float quantity;

	@Column(precision=126)
	private Float rate;

	@Column(nullable=false, precision=22)
	private Integer rok;

	@Column(length=1)
	private String uom;

    @Temporal( TemporalType.DATE)
	private Date utime;

	@Column(length=30)
	private String uuser;

	@Column(nullable=false, length=2)
	private String cizav;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getMesic() {
		return mesic;
	}

	public void setMesic(Integer mesic) {
		this.mesic = mesic;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Float getPriceNew() {
		return priceNew;
	}

	public void setPriceNew(Float priceNew) {
		this.priceNew = priceNew;
	}

	public Float getQuantity() {
		return quantity;
	}

	public void setQuantity(Float quantity) {
		this.quantity = quantity;
	}

	public Float getRate() {
		return rate;
	}

	public void setRate(Float rate) {
		this.rate = rate;
	}

	public Integer getRok() {
		return rok;
	}

	public void setRok(Integer rok) {
		this.rok = rok;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public Date getUtime() {
		return utime;
	}

	public void setUtime(Date utime) {
		this.utime = utime;
	}

	public String getUuser() {
		return uuser;
	}

	public void setUuser(String uuser) {
		this.uuser = uuser;
	}

	public String getWk() {
		return cizav;
	}

	public void setWk(String cizav) {
		this.cizav = cizav;
	}
	
	

}