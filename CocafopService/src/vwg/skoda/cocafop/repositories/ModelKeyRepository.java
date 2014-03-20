package vwg.skoda.cocafop.repositories;

import java.util.List;

import vwg.skoda.cocafop.entities.ModelKey;

public interface ModelKeyRepository {
	
	List<ModelKey> getModelKey(String idPlant, Integer idBrand);
	List<ModelKey> getModelKey(String idPlant, Integer idBrand, Integer rok);
	List<ModelKey> getModelKey(String idPlant, Integer idBrand, Integer rok, Integer mesic);
	ModelKey getOneModelKey(Integer id);
	void addModelKey(ModelKey modelKey);
	void removeModelKey(Integer id);
	void setModelKey(ModelKey modelKey);
	List<Integer> getRok(String idPlant, Integer idBrand);
	

}
