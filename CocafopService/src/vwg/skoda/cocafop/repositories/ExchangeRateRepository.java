package vwg.skoda.cocafop.repositories;

import java.util.List;

import vwg.skoda.cocafop.entities.ExchangeRate;

public interface ExchangeRateRepository {
	
	List<ExchangeRate> selectExchangeRate(String idPlant);
	ExchangeRate selectOneExchangeRate(Integer id);
	ExchangeRate getOneExchangeRate(String idPlant, Integer rok, Integer mesic);
	void addExchangeRate(ExchangeRate exchangeRate);
	void removeExchangeRate(Integer id);

}
