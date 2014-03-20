package vwg.skoda.cocafop.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import vwg.skoda.cocafop.entities.Brand;

public class BrandRepositoryJpa implements BrandRepository{
	
	static Logger log = Logger.getLogger(BrandRepositoryJpa.class);

	@PersistenceContext(name = "CocafopService")
	private EntityManager entityManager;


	@Override
	public List<Brand> selectBrands() {
		log.debug("\t###\t selectBrands()");
		return entityManager.createQuery("SELECT b FROM Brand b ORDER BY b.plant, b.brandMark, b.brandName", Brand.class).getResultList();
	}
	
	@Override
	public List<Brand> selectBrands(String idPlant) {
		log.debug("\t###\t selectBrands("+idPlant+")");
		return entityManager.createQuery("SELECT b FROM Brand b WHERE b.plant.id=:idp ORDER BY b.brandMark, b.brandName", Brand.class).setParameter("idp", idPlant).getResultList();
	}

	@Override
	public Brand selectOneBrand(Integer id) {
		log.debug("\t###\t selectOneBrand("+id+")");
		return entityManager.find(Brand.class, id);
	}

	@Override
	public void addBrand(Brand brand) {
		log.debug("\t###\t addBrand("+brand+")");
		brand = entityManager.merge(brand);
	}

	@Override
	public void removeBrand(Integer id) {
		log.debug("\t###\t removeBrand("+id+")");
		Brand brand = selectOneBrand(id);
		entityManager.remove(brand);
	}

	@Override
	public Brand selectOneBrand(String plantId, String brandMark) {
		log.debug("\t###\t selectBrand("+plantId+", "+brandMark+")");
		List<Brand> ggg = entityManager.createQuery("SELECT b FROM Brand b WHERE b.plant.id=:idp AND b.brandMark=:bm ORDER BY b.brandMark, b.brandName", Brand.class).setParameter("idp", plantId).setParameter("bm", brandMark).getResultList();
		Brand gre = ggg.get(0);
		return gre;
	}


}
