package vwg.skoda.cocafop.repositories;

import java.util.List;

import vwg.skoda.cocafop.entities.Brand;

public interface BrandRepository {
	List<Brand> selectBrands();
	List<Brand> selectBrands(String idPlant);
	Brand selectOneBrand(Integer id);
	Brand selectOneBrand(String plantId, String brandMark);
	void addBrand(Brand brand);
	void removeBrand(Integer id);

}
