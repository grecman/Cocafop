package vwg.skoda.cocafop.objects;

import org.springframework.format.annotation.NumberFormat;

public class BomFilter {
	
	String partNumber;
	String typ;
	String partDescEng;
	
	@NumberFormat(pattern = "0.00000")
	Float priceBrandFrom;
	
	@NumberFormat(pattern = "0.00000")
	Float priceBrandTo;
	
	@NumberFormat(pattern = "0.00000")
	Float pricePlantFrom;
	
	@NumberFormat(pattern = "0.00000")
	Float pricePlantTo;
	
	public String getPartNumber() {
		return partNumber;
	}
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	public String getTyp() {
		return typ;
	}
	public void setTyp(String typ) {
		this.typ = typ;
	}
	public String getPartDescEng() {
		return partDescEng;
	}
	public void setPartDescEng(String partDescEng) {
		this.partDescEng = partDescEng;
	}
	public Float getPriceBrandFrom() {
		return priceBrandFrom;
	}
	public void setPriceBrandFrom(Float priceBrandFrom) {
		this.priceBrandFrom = priceBrandFrom;
	}
	public Float getPriceBrandTo() {
		return priceBrandTo;
	}
	public void setPriceBrandTo(Float priceBrandTo) {
		this.priceBrandTo = priceBrandTo;
	}
	public Float getPricePlantFrom() {
		return pricePlantFrom;
	}
	public void setPricePlantFrom(Float pricePlantFrom) {
		this.pricePlantFrom = pricePlantFrom;
	}
	public Float getPricePlantTo() {
		return pricePlantTo;
	}
	public void setPricePlantTo(Float pricePlantTo) {
		this.pricePlantTo = pricePlantTo;
	}

}

