package vwg.skoda.cocafop.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import vwg.skoda.cocafop.entities.MkOrder;

public class MkOrderRepositoryJpa implements MkOrderRepository {

	static Logger log = Logger.getLogger(MkOrderRepositoryJpa.class);

	@PersistenceContext(name = "CocafopService")
	private EntityManager entityManager;

	@Override
	public MkOrder getOneMkOrder(Integer id) {
		log.debug("\t###\t getOneMkOrder(" + id + ")");
		return entityManager.find(MkOrder.class, id);
	}
	
	@Override
	public List<MkOrder> getMkOrder(String plant, Integer brand, Integer rok, Integer mesic) {
		log.debug("\t###\t getMkOrder(" + plant + ", " + brand + ", " + rok + "/" + mesic + ")");
		return entityManager
				.createQuery("SELECT b FROM MkOrder b WHERE b.modelKey.brand.plant.id=:pl AND b.modelKey.brand.id=:br AND b.modelKey.rok=:r AND b.mesic=:m ORDER BY b.modelKey.modelNumber",
						MkOrder.class).setParameter("pl", plant).setParameter("br", brand).setParameter("r", rok).setParameter("m", mesic).getResultList();
	}

	@Override
	public List<MkOrder> getMkOrder(String plant, Integer rok, Integer mesic) {
		log.debug("\t###\t getMkOrder(" + plant + ", " + rok + "/" + mesic + ")");
		return entityManager.createQuery("SELECT b FROM MkOrder b WHERE b.modelKey.brand.plant.id=:pl AND b.modelKey.rok=:r AND b.mesic=:m ORDER BY b.modelKey.modelNumber", MkOrder.class)
				.setParameter("pl", plant).setParameter("r", rok).setParameter("m", mesic).getResultList();
	}
	

	@Override
	public List<MkOrder> getMkOrder(Integer modelKey) {
		log.debug("\t###\t getMkOrder(" + modelKey + ")");
		return entityManager.createQuery("SELECT b FROM MkOrder b WHERE b.modelKey.id=:idMK ORDER BY b.mesic", MkOrder.class).setParameter("idMK", modelKey).getResultList();
	}

	@Override
	public List<MkOrder> getOneMkOrder(Integer modelKey, Integer mesic) {
		log.debug("\t###\t getMkOrder(" + modelKey + ", " + mesic + ")");
		return entityManager.createQuery("SELECT b FROM MkOrder b WHERE b.modelKey.id=:idMK AND b.mesic=:mes", MkOrder.class).setParameter("idMK", modelKey).setParameter("mes", mesic)
				.getResultList();
	}

	@Override
	public void addMkOrder(MkOrder mkOrder) {
		log.debug("\t###\t addMkOrder(" + mkOrder.getModelKey().getModelKey() + " v mesici: " + mkOrder.getMesic() + ")");
		entityManager.persist(mkOrder);
	}

	@Override
	public void setMkOrder(MkOrder mkOrder) {
		log.debug("\t###\t setMkOrder(" + mkOrder + ")");
		mkOrder = entityManager.merge(mkOrder);
	}

	@Override
	public Boolean checkApprovePlant(String plant, Integer brand, Integer rok, Integer mesic) {
		log.debug("\t###\t checkApprovePlant(" + plant + ", " + brand + ", " + rok + "/" + mesic + ")");
		List<Boolean> gre = entityManager.createQuery("SELECT b.plantApprove FROM MkOrder b WHERE b.modelKey.brand.plant.id=:pl AND b.modelKey.brand.id=:br AND b.modelKey.rok=:r AND b.mesic=:m ",
						Boolean.class).setParameter("pl", plant).setParameter("br", brand).setParameter("r", rok).setParameter("m", mesic).getResultList();
		
		if(gre.isEmpty()){
			return false;
		} else {
			for(Boolean b: gre){
				if(!b.booleanValue()) return false;
			}
			return true;
		}
	}
	
	@Override
	public Boolean checkApproveBrand(String plant, Integer brand, Integer rok, Integer mesic) {
		log.debug("\t###\t checkApproveBrand(" + plant + ", " + brand + ", " + rok + "/" + mesic + ")");
		List<Boolean> gre = entityManager.createQuery("SELECT b.brandApprove FROM MkOrder b WHERE b.modelKey.brand.plant.id=:pl AND b.modelKey.brand.id=:br AND b.modelKey.rok=:r AND b.mesic=:m ",
						Boolean.class).setParameter("pl", plant).setParameter("br", brand).setParameter("r", rok).setParameter("m", mesic).getResultList();

		if(gre.isEmpty()){
			return false;
		} else {
			for(Boolean b: gre){
				if(!b.booleanValue()) return false;
			}
			return true;
		}
	}


}
