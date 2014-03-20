package vwg.skoda.cocafop.repositories;

import java.util.List;

import vwg.skoda.cocafop.entities.MkOrder;

public interface MkOrderRepository {
	MkOrder getOneMkOrder(Integer id);
	List<MkOrder> getMkOrder(Integer modelKey);
	List<MkOrder> getMkOrder(String plant, Integer rok, Integer mesic);
	List<MkOrder> getMkOrder(String plant, Integer brand, Integer rok, Integer mesic);
	Boolean checkApprovePlant(String plant, Integer brand, Integer rok, Integer mesic);
	Boolean checkApproveBrand(String plant, Integer brand, Integer rok, Integer mesic);
	List<MkOrder> getOneMkOrder(Integer modelKey, Integer mesic);
	void addMkOrder(MkOrder mkOrder);
	void setMkOrder(MkOrder mkOrder);
}
