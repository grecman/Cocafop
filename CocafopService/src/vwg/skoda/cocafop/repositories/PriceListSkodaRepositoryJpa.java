package vwg.skoda.cocafop.repositories;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import vwg.skoda.cocafop.entities.PriceListSkoda;

public class PriceListSkodaRepositoryJpa implements PriceListSkodaRepository {

	static Logger log = Logger.getLogger(PriceListRepositoryJpa.class);

	@PersistenceContext(name = "CocafopService")
	private EntityManager entityManager;

	@Override
	public PriceListSkoda getOnePriceListSkoda(Integer rok, Integer mesic, String partNumber) {
		// log.debug("\t\t###\t getOnePriceListSkoda(" + rok + ", " + mesic + ", " + partNumber + ")"); // moc ukecane! provadi se s kazdym dilem
		return entityManager.createQuery("SELECT b FROM PriceListSkoda b WHERE b.rok=:rok AND b.mesic=:mesic AND b.partNumber=:dil", PriceListSkoda.class).setParameter("rok", rok).setParameter("mesic", mesic).setParameter("dil", partNumber).getSingleResult();
	}

	@Override
	public Boolean existPartNumberInPriceListSkoda(Integer rok, Integer mesic, String partNumber) {
		//log.debug("\t###\t existPartNumberInPriceListSkoda(" +   rok+", "+ mesic+", "+ partNumber + ")");  // moc ukecane !!!
		
		try {
			@SuppressWarnings("unused")
			String nalezenaPartNumber = null;
			nalezenaPartNumber = entityManager.createQuery("SELECT b.partNumber FROM PriceListSkoda b WHERE b.rok=:rok AND b.mesic=:mesic AND b.partNumber=:dil", String.class).setParameter("rok", rok).setParameter("mesic", mesic).setParameter("dil", partNumber).getSingleResult(); 
			// log.debug("\t\t###\t PartNumber " + nalezenaPartNumber+" existuje v GZ38T_PRICELIST"); // moc ukecane !!!
			return true;
		} catch (NoResultException e) {
			// log.debug("\t\t###\t Dil neni v GZ38T_PriceListSkoda ... chycena vyjimka: "+e);  // moc ukecane !!!
			return false;
		}
	}

	@Override
	public void setPriceListSkoda(PriceListSkoda priceListSkoda) {
		log.debug("\t###\t setPriceListSkoda(" + priceListSkoda + ")");
		priceListSkoda = entityManager.merge(priceListSkoda);		
	}

	
}
