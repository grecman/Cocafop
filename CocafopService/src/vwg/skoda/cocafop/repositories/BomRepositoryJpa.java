package vwg.skoda.cocafop.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import vwg.skoda.cocafop.entities.Bom;

public class BomRepositoryJpa implements BomRepository {

	static Logger log = Logger.getLogger(BomRepositoryJpa.class);

	@PersistenceContext(name = "CocafopService")
	private EntityManager entityManager;

	@Override
	public Boolean existKifa(String kifa) {
		log.debug("\t###\t existKifa(" + kifa + ")");

		try {
			String nalezenaKifa = null;
			nalezenaKifa = entityManager.createQuery("SELECT o.kifa FROM Bom o WHERE o.kifa=:kifa", String.class).setParameter("kifa", kifa).getSingleResult();
			log.debug("\t###\t Kifa " + nalezenaKifa + " jiz existuje v GZ38T_BOM");
			return true;
		} catch (NoResultException e) {
			log.debug("\t###\t Kifa neni v GZ38T_BOM ... Chycena vyjimka: " + e);
			return false;
		}

	}

	@Override
	public Bom getOneBom(Integer id) {
		log.debug("\t###\t getOneBom(" + id + ")");
		return entityManager.find(Bom.class, id);
	}

	@Override
	public void deleteBom(String kifa) {
		log.debug("\t###\t deleteBom(" + kifa + ") (KIFA)");
		entityManager.createQuery("DELETE Bom o WHERE o.kifa=:kifa").setParameter("kifa", kifa).executeUpdate();
	}

	@Override
	public void deleteBom(Integer idMkOrder) {
		log.debug("\t###\t deleteBom(" + idMkOrder + ") (ID MkOrder)");
		entityManager.createQuery("DELETE Bom o WHERE o.mkOrder.id=:idMkOrder").setParameter("idMkOrder", idMkOrder).executeUpdate();
	}

	
	@Override
	public void deleteOneBom(Integer id) {
		log.debug("\t###\t deleteOneBom(" + id + ")");
		Bom bom = getOneBom(id);
		//entityManager.remove(entityManager.merge(bom));
		entityManager.remove(bom);
	}

	@Override
	public void setBom(Bom bom) {
		log.debug("\t###\t setBom(" + bom + ")");
		bom = entityManager.merge(bom);
	}

	@Override
	public void addBom(Bom bom) {
		//log.debug("\t###\t addBom(" + bom + ")"); // moc ukecane,!!!
		entityManager.persist(bom);
	}

	@Override
	public List<Bom> getBom(Integer idMkOrder) {
		log.debug("\t###\t getBom(" + idMkOrder + ")");
		return entityManager.createQuery("SELECT o FROM Bom o WHERE o.mkOrder.id=:mko ORDER BY o.typ DESC, o.partNumber", Bom.class).setParameter("mko", idMkOrder).getResultList();
	}

	@Override
	public List<Bom> getBom(Integer idMkOrder, String partNumber, String typ, String partDescEng, Float priceBrandFrom, Float priceBrandTo, Float pricePlantFrom, Float pricePlantTo, Float exRate) {
		log.debug("\t###\t getBom(" + idMkOrder + ", '" + partNumber + "', " + typ + ", " + partDescEng + ", " + pricePlantFrom + "-" + pricePlantTo + ", " + priceBrandFrom + "-" + priceBrandTo +", "+exRate+") ... IN");
		if (partNumber == null || partNumber.trim().length() == 0 ) {
			partNumber = "%";
		} else {
			partNumber = partNumber.toUpperCase();
		}
		if (typ == null || (typ = typ.trim()).length() == 0)
			typ = "%";

		if (partDescEng == null || (partDescEng = partDescEng.trim()).length() == 0){
			partDescEng = "%";
		} else {
			partDescEng = partDescEng.toUpperCase();
		}
		if (priceBrandFrom == null)
			priceBrandFrom = -999999999f;		
		if (priceBrandTo == null)
			priceBrandTo = 999999999f;	
		if (pricePlantFrom == null)
			pricePlantFrom = -999999999f;		
		if (pricePlantTo == null)
			pricePlantTo = 999999999f;	
		log.debug("\t###\t getBom(" + idMkOrder + ", '" + partNumber + "', " + typ + ", " + partDescEng + ", od " + pricePlantFrom + " do " + pricePlantTo +", od " + priceBrandFrom + " do " + priceBrandTo + ", "+exRate+") ... SET");
		
		return entityManager.createQuery("SELECT o FROM Bom o WHERE o.mkOrder.id=:mko AND o.partNumber LIKE :partNumber AND o.typ LIKE :typ AND o.partDescEng LIKE :partDescEng AND (o.pricePlant/:exRate) >= :pricePlantFrom AND (o.pricePlant/:exRate) <= :pricePlantTo AND o.priceBrand >= :priceBrandFrom AND o.priceBrand <= :priceBrandTo ORDER BY o.typ DESC, o.partNumber", Bom.class)
				.setParameter("mko", idMkOrder).setParameter("partNumber", partNumber).setParameter("typ", typ).setParameter("partDescEng", partDescEng).setParameter("pricePlantFrom", pricePlantFrom).setParameter("exRate", exRate).setParameter("pricePlantTo", pricePlantTo).setParameter("exRate", exRate).setParameter("priceBrandFrom", priceBrandFrom).setParameter("priceBrandTo", priceBrandTo).getResultList();
	}

	@Override
	public List<Bom> getBom(Integer brandId, Integer rok, Integer mesic, String partNumber) {
		log.debug("\t###\t getBom(" + brandId + ", " + rok + ", " + mesic + ", " + partNumber + ")");
		return entityManager
				.createQuery("SELECT o FROM Bom o WHERE o.mkOrder.modelKey.brand.id=:idBrand AND o.mkOrder.modelKey.rok=:rok AND o.mkOrder.mesic=:mesic AND o.partNumber=:pn ", Bom.class)
				.setParameter("idBrand", brandId).setParameter("rok", rok).setParameter("mesic", mesic).setParameter("pn", partNumber).getResultList();
	}

	@Override
	public Boolean existPartNumberInBom(String kifa, String partNumber) {
		//log.debug("\t###\t existPartNumber(" +   rok+", "+ mesic+", "+ partNumber + ")");  // moc ukecane !!!
		try {
			@SuppressWarnings("unused")
			String nalezenaPartNumber = null;
			nalezenaPartNumber = entityManager.createQuery("SELECT b.partNumber FROM Bom b WHERE b.kifa=:kifa AND b.partNumber=:dil", String.class).setParameter("kifa", kifa)
					.setParameter("dil", partNumber).getSingleResult();
			// log.debug("\t\t###\t existPartNumber " + nalezenaPartNumber+" existuje v GZ38T_BOM"); // moc ukecane !!!
			return true;
		} catch (NoResultException e) {
			// log.debug("\t###\t Dil neni v GZ38T_Bom ... chycena vyjimka: "+e);  // moc ukecane !!!
			return false;
		}
	}

	@Override
	public Double getTotalPricePlant(String kifa) {
		log.debug("\t###\t getTotalPricePlant(" + kifa + ")");
		return entityManager.createQuery("select sum(coalesce(b.pricePlantNew,b.pricePlant)*coalesce(b.quantityPlant,b.quantity)) from Bom b where b.kifa=:kifa", Double.class)
				.setParameter("kifa", kifa).getSingleResult();
	}

	@Override
	public Double getTotalPriceBrand(String kifa) {
		log.debug("\t###\t getTotalPriceBrand(" + kifa + ")");
		return entityManager.createQuery("select sum(coalesce(b.priceBrandNew,b.priceBrand)*coalesce(b.quantityBrand,b.quantity)) from Bom b where b.kifa=:kifa", Double.class)
				.setParameter("kifa", kifa).getSingleResult();
	}

	@Override
	public List<String> getType(Integer idMkOrder) {
		log.debug("\t###\t getType(" + idMkOrder + ")");
		return entityManager.createQuery("SELECT DISTINCT o.typ FROM Bom o WHERE o.mkOrder.id=:mko ORDER BY o.typ DESC", String.class).setParameter("mko", idMkOrder).getResultList();
	}

}
