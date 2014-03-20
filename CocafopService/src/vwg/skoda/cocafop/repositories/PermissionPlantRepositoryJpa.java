package vwg.skoda.cocafop.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import vwg.skoda.cocafop.entities.PermissionPlant;

public class PermissionPlantRepositoryJpa implements PermissionPlantRepository {

	static Logger log	= Logger.getLogger(PermissionPlantRepositoryJpa.class);

	@PersistenceContext(name = "CocafopService")
	private EntityManager	entityManager;

	@Override
	public PermissionPlant selectOnePermissionPlant(Integer id) {
		log.debug("\t###\t selectOnePermissionPlant(" + id + ")");
		return entityManager.find(PermissionPlant.class, id);
	}

	@Override
	public List<PermissionPlant> selectPermissionPlant() {
		log.debug("\t###\t selectPermissionPlant()");
		return entityManager.createQuery("SELECT b FROM PermissionPlant b", PermissionPlant.class).getResultList();
	}

	@Override
	public List<PermissionPlant> selectPermissionPlant(String netUserName) {
		log.debug("\t###\t selectPermissionPlant(" + netUserName + ")");
		return entityManager.createQuery("SELECT b FROM PermissionPlant b WHERE b.userApl.netUserName=:us ORDER BY b.plant.id", PermissionPlant.class).setParameter("us", netUserName)
				.getResultList();
	}

	@Override
	public void addPermissionPlant(PermissionPlant permissionPlant) {
		log.debug("\t###\t addPermissionPlant(" + permissionPlant + ")");
		entityManager.persist(permissionPlant);
	}

	@Override
	public void setRead(PermissionPlant permissionPlant) {
		log.debug("\t###\t setRead(" + permissionPlant.getId() + ")");
		permissionPlant.setRead(!permissionPlant.getRead());
		
	}

	@Override
	public void setPermissionPlant(PermissionPlant permissionPlant) {
		log.debug("\t###\t setPermissionPlant("+permissionPlant+")");
		permissionPlant = entityManager.merge(permissionPlant);
	}
	

}
