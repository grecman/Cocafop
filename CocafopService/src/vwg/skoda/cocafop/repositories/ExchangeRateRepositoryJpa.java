package vwg.skoda.cocafop.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import vwg.skoda.cocafop.entities.ExchangeRate;

public class ExchangeRateRepositoryJpa implements ExchangeRateRepository{
	
	static Logger log = Logger.getLogger(ExchangeRateRepositoryJpa.class);

	@PersistenceContext(name = "CocafopService")
	private EntityManager entityManager;

	@Override
	public List<ExchangeRate> selectExchangeRate(String idPlant) {
		log.debug("\t###\t selectExchangeRate("+idPlant+")");
		return entityManager.createQuery("SELECT b FROM ExchangeRate b WHERE b.plant.id=:idp ORDER BY b.rok DESC, b.mesic DESC, b.rate DESC", ExchangeRate.class).setParameter("idp", idPlant).getResultList();
	}

	@Override
	public ExchangeRate selectOneExchangeRate(Integer id) {
		log.debug("\t###\t selectOneExchangeRate("+id+")");
		return entityManager.find(ExchangeRate.class, id);
	}

	@Override
	public void addExchangeRate(ExchangeRate exchangeRate) {
		log.debug("\t###\t addExchangeRate("+exchangeRate+")");
		entityManager.persist(exchangeRate);
	}

	@Override
	public void removeExchangeRate(Integer id) {
		log.debug("\t###\t removeExchangeRate("+id+")");
		ExchangeRate exchangeRate = selectOneExchangeRate(id);
		entityManager.remove(exchangeRate);	
	}

	@Override
	public ExchangeRate getOneExchangeRate(String idPlant, Integer rok, Integer mesic) {
		log.debug("\t###\t getOneExchangeRate(" + idPlant + ", " + rok + ", " + mesic + ")");
		try {
			return entityManager.createQuery("SELECT m FROM ExchangeRate m WHERE m.plant.id=:idp AND m.rok=:rok AND m.mesic=:mesic ",
					ExchangeRate.class).setParameter("idp", idPlant).setParameter("rok", rok).setParameter("mesic", mesic).setParameter("mesic", mesic).getSingleResult();
		} catch (Exception e) {
			log.error("\t###\t Nelze ziskat Exchange rate. Nejspise neexistuje! "+ e);
			return null;
		}
	}

}
