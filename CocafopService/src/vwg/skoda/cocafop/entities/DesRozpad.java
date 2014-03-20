package vwg.skoda.cocafop.entities;

import java.io.Serializable;
import javax.persistence.*;

import org.springframework.format.annotation.NumberFormat;

import java.util.Date;


@Entity
@Table(name="GZ38T_DES_ROZPAD")
public class DesRozpad implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;

	@Column(nullable=false, length=1)
	private String bza;

    @Temporal( TemporalType.DATE)
	private Date casopr;

	@Column(nullable=false, length=15)
	private String cdilu;
	
	@Column(nullable=false, length=2)
	private String cizav;

	@Column(nullable=false, length=1)
	private String verze;
	
	@Column(nullable=false, length=15)
	private String kifa;

	@Column(name="KNR_WK", nullable=false, length=2)
	private String knrWk;

	@Column(nullable=false, length=1)
	private String me;

	@Column(nullable=false, precision=15, scale=2)
	private Float mnoz;

	@Column(name="MODEL_TR", nullable=false, length=2)
	private String modelTr;

	@Column(name="PART_DESC_ENG", length=36)
	private String partDescEng;

	@Column(name="SKD_PR", nullable=false, length=3)
	private String skdPr;
	
	@NumberFormat(pattern = "0.00000")
	@Column(name="PRICE_PLANT", nullable=false, precision=126)
	private Float pricePlant;
	
	@NumberFormat(pattern = "0.00000")
	@Column(name="PRICE_BRAND", nullable=false, precision=126)
	private Float priceBrand;

    public DesRozpad() {
    }

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBza() {
		return this.bza;
	}

	public void setBza(String bza) {
		this.bza = bza;
	}

	public Date getCasopr() {
		return this.casopr;
	}

	public void setCasopr(Date casopr) {
		this.casopr = casopr;
	}

	public String getCdilu() {
		return this.cdilu;
	}

	public void setCdilu(String cdilu) {
		this.cdilu = cdilu;
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

	public String getKifa() {
		return this.kifa;
	}

	public void setKifa(String kifa) {
		this.kifa = kifa;
	}

	public String getKnrWk() {
		return this.knrWk;
	}

	public void setKnrWk(String knrWk) {
		this.knrWk = knrWk;
	}

	public String getMe() {
		return this.me;
	}

	public void setMe(String me) {
		this.me = me;
	}

	public Float getMnoz() {
		return this.mnoz;
	}

	public void setMnoz(Float mnoz) {
		this.mnoz = mnoz;
	}

	public String getModelTr() {
		return this.modelTr;
	}

	public void setModelTr(String modelTr) {
		this.modelTr = modelTr;
	}

	public String getPartDescEng() {
		return this.partDescEng;
	}

	public void setPartDescEng(String partDescEng) {
		this.partDescEng = partDescEng;
	}

	public String getSkdPr() {
		return this.skdPr;
	}

	public void setSkdPr(String skdPr) {
		this.skdPr = skdPr;
	}

	public Float getPricePlant() {
		return pricePlant;
	}

	public void setPricePlant(Float pricePlant) {
		this.pricePlant = pricePlant;
	}

	public Float getPriceBrand() {
		return priceBrand;
	}

	public void setPriceBrand(Float priceBrand) {
		this.priceBrand = priceBrand;
	}
	

}