package vwg.skoda.cocafop.repositories;

import java.util.List;

import vwg.skoda.cocafop.entities.Bom;

public interface BomRepository {

	Boolean existKifa(String kifa);
	Bom getOneBom(Integer id);
	void setBom(Bom bom);
	void addBom(Bom bom);
	void deleteBom (String kifa);
	void deleteBom (Integer idMkOrder);
	void deleteOneBom (Integer id);
	List<Bom> getBom(Integer idMkOrder);
	List<Bom> getBom(Integer idMkOrder, String partNumber, String typ, String partDescEng,  Float priceBrandFrom, Float priceBrandTo, Float pricePlantFrom, Float pricePlantTo, Float exRate);
	List<Bom> getBom(Integer brandId, Integer rok, Integer mesic, String partNumber);
	Boolean existPartNumberInBom(String kifa, String partNumber);
	Double getTotalPricePlant(String kifa);
	Double getTotalPriceBrand(String kifa);
	List<String> getType (Integer idMkOrder);
	

}
