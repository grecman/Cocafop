package vwg.skoda.cocafop.objects;

public class ModelFilter {

	String idPlant;
	String brandMark;
	Integer year;
	//Integer yearActual = Calendar.getInstance().get(Calendar.YEAR);
	Integer month;
	String mix;
	
	
	public String getIdPlant() {
		return idPlant;
	}
	public void setIdPlant(String idPlant) {
		this.idPlant = idPlant;
	}

	public String getBrandMark() {
		return brandMark;
	}
	public void setBrandMark(String brandMark) {
		this.brandMark = brandMark;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public String getMix() {
		return mix;
	}
	public void setMix(String mix) {
		this.mix = mix;
	}

	
}
