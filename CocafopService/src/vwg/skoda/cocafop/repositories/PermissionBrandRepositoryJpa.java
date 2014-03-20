package vwg.skoda.cocafop.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;
import vwg.skoda.cocafop.entities.PermissionBrand;

public class PermissionBrandRepositoryJpa implements PermissionBrandRepository {

	static Logger log = Logger.getLogger(PermissionBrandRepositoryJpa.class);

	@PersistenceContext(name = "CocafopService")
	private EntityManager	entityManager;

	@Override
	public PermissionBrand selectOnePermissionBrand(Integer id) {
		log.debug("\t###\t selectOnePermissionBrand(" + id + ")");
		return entityManager.find(PermissionBrand.class, id);
	}

	@Override
	public List<PermissionBrand> selectPermissionBrand() {
		log.debug("\t###\t selectPermissionBrand()");
		return entityManager.createQuery("SELECT b FROM PermissionBrand b", PermissionBrand.class).getResultList();
	}

	@Override
	public List<PermissionBrand> selectPermissionBrand(String userNetName) {
		log.debug("\t###\t selectPermissionBrand(" + userNetName + ")");
		return entityManager.createQuery("SELECT b FROM PermissionBrand b WHERE b.userApl.netUserName=:us ORDER BY b.userApl.netUserName", PermissionBrand.class).setParameter("us", userNetName)
				.getResultList();
	}

	@Override
	public List<PermissionBrand> selectPermissionBrand(String userNetName, String idPlant) {
		log.debug("\t###\t selectPermissionBrand(" + userNetName +", "+ idPlant+")");
		return entityManager.createQuery("SELECT b FROM PermissionBrand b WHERE b.userApl.netUserName=:us AND b.brand.plant.id=:idP ORDER BY b.brand.brandMark", PermissionBrand.class).setParameter("us", userNetName).setParameter("idP", idPlant)
				.getResultList();
	}

	@Override
	public void addPermissionBrand(PermissionBrand permissionBrand) {
		log.debug("\t###\t addPermissionBrand("+permissionBrand+")");
		entityManager.persist(permissionBrand);	
	}

	@Override
	public void setPermissionBrand(PermissionBrand permissionBrand) {
		log.debug("\t###\t setPermissionBrand("+permissionBrand+")");
		permissionBrand = entityManager.merge(permissionBrand);
		
	}


}
