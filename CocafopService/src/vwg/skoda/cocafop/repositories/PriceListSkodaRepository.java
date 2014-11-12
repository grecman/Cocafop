package vwg.skoda.cocafop.repositories;

import vwg.skoda.cocafop.entities.PriceListSkoda;

public interface PriceListSkodaRepository {

	PriceListSkoda getOnePriceListSkoda(Integer rok, Integer mesic, String partNumber);	
	Boolean existPartNumberInPriceListSkoda(Integer rok, Integer mesic, String partNumber);
	void setPriceListSkoda (PriceListSkoda priceListSkoda);
	Boolean existPriceListSkoda(Integer rok, Integer mesic);
}