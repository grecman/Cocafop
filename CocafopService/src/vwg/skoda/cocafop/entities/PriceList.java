package vwg.skoda.cocafop.entities;

import java.io.Serializable;
import javax.persistence.*;

import org.springframework.format.annotation.NumberFormat;

import java.util.Date;

@Entity
@Table(name="GZ38T_PRICELIST", schema="COCAFOP")
public class PriceList implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false, precision=22)
	private Integer id;

	@Column(name="CURRENCY_ORIG", length=3)
	private String currencyOrig;

	@Column(name="CURRENCY_WK", nullable=false, length=3)
	private String currencyWk;

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
	
	@NumberFormat(pattern = "0.00000")
	@Column(name="PRICE_SAP", nullable=false, precision=126)
	private Float priceSap;

	@NumberFormat(pattern = "0.00")
	@Column(nullable=false, precision=126)
	private Float quantity;

	@Column(precision=126)
	private Float rate;

	@Column(nullable=false, precision=22)
	private Integer rok;

	@Column(length=1)
	private String uom;

	@Column(name="UOM_SAP", nullable=false, length=3)
	private String uomSap;

    @Temporal( TemporalType.DATE)
	private Date utime;

	@Column(length=30)
	private String uuser;

	@Column(nullable=false, length=2)
	private String wk;

    public PriceList() {
    }

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCurrencyOrig() {
		return this.currencyOrig;
	}

	public void setCurrencyOrig(String currencyOrig) {
		this.currencyOrig = currencyOrig;
	}

	public String getCurrencyWk() {
		return this.currencyWk;
	}

	public void setCurrencyWk(String currencyWk) {
		this.currencyWk = currencyWk;
	}

	public Integer getMesic() {
		return this.mesic;
	}

	public void setMesic(Integer mesic) {
		this.mesic = mesic;
	}

	public String getPartNumber() {
		return this.partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public Float getPrice() {
		return this.price;
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

	public Float getPriceSap() {
		return this.priceSap;
	}

	public void setPriceSap(Float priceSap) {
		this.priceSap = priceSap;
	}

	public Float getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Float quantity) {
		this.quantity = quantity;
	}

	public Float getRate() {
		return this.rate;
	}

	public void setRate(Float rate) {
		this.rate = rate;
	}

	public Integer getRok() {
		return this.rok;
	}

	public void setRok(Integer rok) {
		this.rok = rok;
	}

	public String getUom() {
		return this.uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public String getUomSap() {
		return this.uomSap;
	}

	public void setUomSap(String uomSap) {
		this.uomSap = uomSap;
	}

	public Date getUtime() {
		return this.utime;
	}

	public void setUtime(Date utime) {
		this.utime = utime;
	}

	public String getUuser() {
		return this.uuser;
	}

	public void setUuser(String uuser) {
		this.uuser = uuser;
	}

	public String getWk() {
		return this.wk;
	}

	public void setWk(String wk) {
		this.wk = wk;
	}

}