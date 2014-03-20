package vwg.skoda.cocafop.repositories;


import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import vwg.skoda.cocafop.entities.PriceList;

public class PriceListRepositoryJpa implements PriceListRepository {

	static Logger log = Logger.getLogger(PriceListRepositoryJpa.class);

	@PersistenceContext(name = "CocafopService")
	private EntityManager entityManager;

	@Override
	public PriceList getOnePriceList(Integer rok, Integer mesic, String partNumber) {
		// log.debug("\t\t###\t getOnePriceList(" + rok + ", " + mesic + ", " + partNumber + ")"); // moc ukecane! provadi se s kazdym dilem
		return entityManager.createQuery("SELECT b FROM PriceList b WHERE b.rok=:rok AND b.mesic=:mesic AND b.partNumber=:dil", PriceList.class).setParameter("rok", rok).setParameter("mesic", mesic).setParameter("dil", partNumber).getSingleResult();
	}

	@Override
	public Boolean existPartNumberInPriceList(Integer rok, Integer mesic, String partNumber) {
		//log.debug("\t###\t existPartNumber(" +   rok+", "+ mesic+", "+ partNumber + ")");  // moc ukecane !!!
		
		try {
			@SuppressWarnings("unused")
			String nalezenaPartNumber = null;
			nalezenaPartNumber = entityManager.createQuery("SELECT b.partNumber FROM PriceList b WHERE b.rok=:rok AND b.mesic=:mesic AND b.partNumber=:dil", String.class).setParameter("rok", rok).setParameter("mesic", mesic).setParameter("dil", partNumber).getSingleResult(); 
			// log.debug("\t\t###\t PartNumber " + nalezenaPartNumber+" existuje v GZ38T_PRICELIST"); // moc ukecane !!!
			return true;
		} catch (NoResultException e) {
			// log.debug("\t\t###\t Dil neni v GZ38T_PriceList ... chycena vyjimka: "+e);  // moc ukecane !!!
			return false;
		}
	}

	@Override
	public void setPriceList(PriceList priceList) {
		log.debug("\t###\t setPriceList(" + priceList + ")");
		priceList = entityManager.merge(priceList);		
	}
	
		
}
