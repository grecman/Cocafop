package vwg.skoda.cocafop.repositories;

import vwg.skoda.cocafop.entities.PriceList;

public interface PriceListRepository {

	PriceList getOnePriceList(Integer rok, Integer mesic, String partNumber);
	Boolean existPartNumberInPriceList(Integer rok, Integer mesic, String partNumber);
	void setPriceList (PriceList priceList);
	Boolean existPriceList(Integer rok, Integer mesic);
}
