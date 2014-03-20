package vwg.skoda.cocafop.entities;

import java.io.Serializable;
import javax.persistence.*;

import org.springframework.format.annotation.NumberFormat;

import java.util.Date;


@Entity
@Table(name="GZ38T_BOM", schema="COCAFOP")
public class Bom implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(unique=true, nullable=false, precision=22)
	private Integer id;

	@ManyToOne() 
	@JoinColumn(name="ID_MK_ORDER")
	MkOrder mkOrder;

	@Column(nullable=false, length=15)
	private String kifa;

	@Column(name="PART_DESC_ENG", length=36)
	private String partDescEng;

	@Column(name="PART_NUMBER", nullable=false, length=15)
	private String partNumber;
	
	@Column(nullable=false, length=2)
	private String cizav;

	@Column(nullable=false, length=1)
	private String verze;

	/* tato divna anotace zajisti to, ze flot je mozne zadavat s carkou "," a nikoliv s teckou "." */ 
	@NumberFormat(pattern = "0.00000")
	@Column(name="PRICE_PLANT", precision=126)
	private Float pricePlant;

	@NumberFormat(pattern = "0.00000")
	@Column(name="PRICE_PLANT_NEW", precision=126)
	private Float pricePlantNew;
	
	@NumberFormat(pattern = "0.00000")
	@Column(name="PRICE_BRAND", precision=126)
	private Float priceBrand;
	
	@NumberFormat(pattern = "0.00000")
	@Column(name="PRICE_BRAND_NEW", precision=126)
	private Float priceBrandNew;
	
	@NumberFormat(pattern = "0.00")
	@Column(nullable=false, precision=126)
	private Float quantity;

	@NumberFormat(pattern = "0.00")
	@Column(name="QUANTITY_BRAND", precision=126)
	private Float quantityBrand;

	@NumberFormat(pattern = "0.00")
	@Column(name="QUANTITY_PLANT", precision=126)
	private Float quantityPlant;

	@Column(nullable=false, length=1)
	private String typ;

	@Column(nullable=false, length=1)
	private String uom;

	@Column(name="UOM_BRAND", length=1)
	private String uomBrand;

	@Column(name="UOM_PLANT", length=1)
	private String uomPlant;

    @Temporal( TemporalType.DATE)
	private Date utime;

    @Temporal( TemporalType.DATE)
	@Column(name="UTIME_QUANTITY_BRAND")
	private Date utimeQuantityBrand;

    @Temporal( TemporalType.DATE)
	@Column(name="UTIME_QUANTITY_PLANT")
	private Date utimeQuantityPlant;

	@Column(length=30)
	private String uuser;

	@Column(name="UUSER_QUANTITY_PLANT", length=30)
	private String uuserQuantityPlant;

	@Column(name="UUSER_QUANTITY_BRAND", length=30)
	private String uuserQuantityBrand;
	
    @Temporal( TemporalType.DATE)
	@Column(name="UTIME_PRICE_PLANT")
	private Date utimePricePlant;
    
    @Temporal( TemporalType.DATE)
	@Column(name="UTIME_PRICE_BRAND")
	private Date utimePriceBrand;
	
	@Column(name="UUSER_PRICE_PLANT", length=30)
	private String uuserPricePlant;

	@Column(name="UUSER_PRICE_BRAND", length=30)
	private String uuserPriceBrand;


    public Bom() {
    }

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public MkOrder getMkOrder() {
		return mkOrder;
	}

	public void setMkOrder(MkOrder mkOrder) {
		this.mkOrder = mkOrder;
	}

	public String getKifa() {
		return this.kifa;
	}

	public void setKifa(String kifa) {
		this.kifa = kifa;
	}

	public String getPartDescEng() {
		return this.partDescEng;
	}

	public void setPartDescEng(String partDescEng) {
		this.partDescEng = partDescEng;
	}

	public String getPartNumber() {
		return this.partNumber;
	}
	

	public String getCizav() {
		return cizav;
	}

	public void setCizav(String cizav) {
		this.cizav = cizav;
	}

	public String getVerze() {
		return verze;
	}

	public void setVerze(String verze) {
		this.verze = verze;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public Float getPricePlant() {
		return pricePlant;
	}

	public void setPricePlant(Float pricePlant) {
		this.pricePlant = pricePlant;
	}

	public Float getPricePlantNew() {
		return pricePlantNew;
	}

	public void setPricePlantNew(Float pricePlantNew) {
		this.pricePlantNew = pricePlantNew;
	}

	public Float getPriceBrand() {
		return priceBrand;
	}

	public void setPriceBrand(Float priceBrand) {
		this.priceBrand = priceBrand;
	}

	public Float getPriceBrandNew() {
		return priceBrandNew;
	}

	public void setPriceBrandNew(Float priceBrandNew) {
		this.priceBrandNew = priceBrandNew;
	}

	public Float getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Float quantity) {
		this.quantity = quantity;
	}

	public Float getQuantityBrand() {
		return this.quantityBrand;
	}

	public void setQuantityBrand(Float quantityBrand) {
		this.quantityBrand = quantityBrand;
	}

	public Float getQuantityPlant() {
		return this.quantityPlant;
	}

	public void setQuantityPlant(Float quantityPlant) {
		this.quantityPlant = quantityPlant;
	}

	public String getTyp() {
		return this.typ;
	}

	public void setTyp(String typ) {
		this.typ = typ;
	}

	public String getUom() {
		return this.uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public String getUomBrand() {
		return this.uomBrand;
	}

	public void setUomBrand(String uomBrand) {
		this.uomBrand = uomBrand;
	}

	public String getUomPlant() {
		return this.uomPlant;
	}

	public void setUomPlant(String uomPlant) {
		this.uomPlant = uomPlant;
	}

	public Date getUtime() {
		return this.utime;
	}

	public void setUtime(Date utime) {
		this.utime = utime;
	}

	public Date getUtimeQuantityBrand() {
		return this.utimeQuantityBrand;
	}

	public void setUtimeQuantityBrand(Date utimeQuantityBrand) {
		this.utimeQuantityBrand = utimeQuantityBrand;
	}


	public String getUuser() {
		return this.uuser;
	}

	public void setUuser(String uuser) {
		this.uuser = uuser;
	}

	public String getUuserQuantityPlant() {
		return this.uuserQuantityPlant;
	}

	public void setUuserQuantityPlant(String uuserQuantityPlant) {
		this.uuserQuantityPlant = uuserQuantityPlant;
	}

	public Date getUtimeQuantityPlant() {
		return utimeQuantityPlant;
	}

	public void setUtimeQuantityPlant(Date utimeQuantityPlant) {
		this.utimeQuantityPlant = utimeQuantityPlant;
	}

	public String getUuserQuantityBrand() {
		return uuserQuantityBrand;
	}

	public void setUuserQuantityBrand(String uuserQuantityBrand) {
		this.uuserQuantityBrand = uuserQuantityBrand;
	}

	public Date getUtimePricePlant() {
		return utimePricePlant;
	}

	public void setUtimePricePlant(Date utimePricePlant) {
		this.utimePricePlant = utimePricePlant;
	}

	public Date getUtimePriceBrand() {
		return utimePriceBrand;
	}

	public void setUtimePriceBrand(Date utimePriceBrand) {
		this.utimePriceBrand = utimePriceBrand;
	}

	public String getUuserPricePlant() {
		return uuserPricePlant;
	}

	public void setUuserPricePlant(String uuserPricePlant) {
		this.uuserPricePlant = uuserPricePlant;
	}

	public String getUuserPriceBrand() {
		return uuserPriceBrand;
	}

	public void setUuserPriceBrand(String uuserPriceBrand) {
		this.uuserPriceBrand = uuserPriceBrand;
	}



}