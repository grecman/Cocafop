package vwg.skoda.cocafop.objects;


public class OrdersGroup {
	
	String modelKeyVersion;
	String colour;
	Integer modelYear;
	String deliveryProgram;
	String options;
	Long count;


	public OrdersGroup(String mkv, String col, Integer my, String dp, String opt, Long c) {
		modelKeyVersion = mkv;
		colour = col;
		modelYear = my;
		deliveryProgram = dp;
		options =opt;
		count = c;
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
		return options == null || options.trim().length() < 1 ? null : options.trim();
	}
	public void setOptions(String options) {
		this.options = options;
	}


	public Long getCount() {
		return count;
	}


	public void setCount(Long count) {
		this.count = count;
	}


	
}
