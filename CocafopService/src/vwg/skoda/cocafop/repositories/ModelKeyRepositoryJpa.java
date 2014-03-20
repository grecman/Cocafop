package vwg.skoda.cocafop.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import vwg.skoda.cocafop.entities.ModelKey;

public class ModelKeyRepositoryJpa implements ModelKeyRepository {

	static Logger log = Logger.getLogger(ModelKeyRepositoryJpa.class);

	@PersistenceContext(name = "CocafopService")
	private EntityManager entityManager;
	

	@Override
	public List<ModelKey> getModelKey(String idPlant, Integer idBrand) {
		log.debug("\t###\t getModelKey(" + idPlant + ", " + idBrand + ")");
		return entityManager.createQuery("SELECT m FROM ModelKey m WHERE m.brand.plant.id=:idp AND m.brand.id=:idb ORDER BY m.rok DESC, m.modelNumber", ModelKey.class)
				.setParameter("idp", idPlant).setParameter("idb", idBrand).getResultList();
	}

	@Override
	public List<ModelKey> getModelKey(String idPlant, Integer idBrand, Integer rok) {
		log.debug("\t###\t getModelKey(" + idPlant + ", " + idBrand + ", " + rok + ")");
		return entityManager.createQuery("SELECT m FROM ModelKey m WHERE m.brand.plant.id=:idp AND m.brand.id=:idb AND m.rok=:rok ORDER BY m.modelNumber", ModelKey.class)
				.setParameter("idp", idPlant).setParameter("idb", idBrand).setParameter("rok", rok).getResultList();
	}

	@Override
	public List<ModelKey> getModelKey(String idPlant, Integer idBrand, Integer rok, Integer mesic) {
		log.debug("\t###\t getModelKey(" + idPlant + ", " + idBrand + ", " + rok + ", " + mesic + ")");
		return entityManager
				.createQuery(
						"SELECT m FROM ModelKey m WHERE m.brand.plant.id=:idp AND m.brand.id=:idb AND m.rok=:rok AND m.validFromMonth<=:mesic AND m.validToMonth>=:mesic ORDER BY m.modelNumber",
						ModelKey.class).setParameter("idp", idPlant).setParameter("idb", idBrand).setParameter("rok", rok).setParameter("mesic", mesic).setParameter("mesic", mesic)
				.getResultList();
	}

	@Override
	public ModelKey getOneModelKey(Integer id) {
		log.debug("\t###\t getOneModelKey(" + id + ")");
		return entityManager.find(ModelKey.class, id);
	}

	@Override
	public void addModelKey(ModelKey modelKey) {
		log.debug("\t###\t addModelKey(" + modelKey + ")");
		entityManager.persist(modelKey);
	}

	@Override
	public void removeModelKey(Integer id) {
		log.debug("\t###\t removeModelKey(" + id + ")");
		ModelKey neco = getOneModelKey(id);
		entityManager.remove(neco);
	}

	@Override
	public void setModelKey(ModelKey modelKey) {
		log.debug("\t###\t setModelKey(" + modelKey + ")");
		modelKey = entityManager.merge(modelKey);
	}

	@Override
	public List<Integer> getRok(String idPlant, Integer idBrand) {
		log.debug("\t###\t getRok(" + idPlant + ", " + idBrand + ")");
		return entityManager.createQuery("SELECT distinct m.rok FROM ModelKey m WHERE m.brand.plant.id=:idp AND m.brand.id=:idb ORDER BY m.rok desc", Integer.class).setParameter("idp", idPlant)
				.setParameter("idb", idBrand).getResultList();
	}


}
