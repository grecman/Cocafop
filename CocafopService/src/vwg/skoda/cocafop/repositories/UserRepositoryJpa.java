package vwg.skoda.cocafop.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import vwg.skoda.cocafop.entities.Brand;
import vwg.skoda.cocafop.entities.PermissionBrand;
import vwg.skoda.cocafop.entities.PermissionPlant;
import vwg.skoda.cocafop.entities.Plant;
import vwg.skoda.cocafop.entities.UserApl;

public class UserRepositoryJpa implements UserRepository {

	static Logger log = Logger.getLogger(UserRepositoryJpa.class);

	@PersistenceContext(name = "CocafopService")
	private EntityManager entityManager;

	@Override
	public List<UserApl> selectUsers() {
		log.debug("\t###\t selectUsers()");
		return entityManager.createQuery("SELECT p FROM User p ORDER BY p.netUserName", UserApl.class).getResultList();
	}

	@Override
	public UserApl selectOneUser(String netUserName) {
		log.debug("\t###\t selectOneUsert(" + netUserName.toUpperCase() + ")");
		return entityManager.find(UserApl.class, netUserName.toUpperCase());
	}

	@Override
	public void addUser(UserApl user) {
		log.debug("\t###\t addUser(" + user + ")");
		entityManager.persist(user);
	}

	@Override
	public PermissionPlant getPermissionForPlant(UserApl user, Plant plant) {
		log.debug("\t###\t getPermissionForPlant(" + user.getNetUserName() + ", " + plant.getId() + ")");
		List<PermissionPlant> listGre = entityManager.createQuery("SELECT p FROM PermissionPlant p WHERE p.plant.id=:plant AND p.userApl.netUserName=:user", PermissionPlant.class)
				.setParameter("plant", plant.getId()).setParameter("user", user.getNetUserName()).getResultList();
		//log.debug("###\t ... found: "+listGre.size());
		if (listGre.size() == 0){
			log.debug("\t###\t getPermissionForPlant(" + user.getNetUserName() + ", " + plant.getId() + ") ... takove opravneni zatin NEEXISTUJE!");
			return null;
		} else {
		PermissionPlant permissionForPlant = listGre.get(0);
		log.debug("\t###\t ... found: "+ permissionForPlant.getPlant().getId()+" - "+ permissionForPlant.getUserApl().getNetUserName());
		return permissionForPlant;
		}
	}

	@Override
	public PermissionBrand getPermissionForBrand(UserApl user, Plant plant, Brand brand) {
		log.debug("\t###\t getPermissionForBrand(" + user.getNetUserName()+ ", " + plant.getId() + ", " + brand.getBrandMark()+"("+brand.getId()+")" + ")");
		List<PermissionBrand> listGre = entityManager.createQuery("SELECT p FROM PermissionBrand p WHERE p.brand.plant.id=:plant AND p.brand.id=:brand AND p.userApl.netUserName=:user", PermissionBrand.class)
				.setParameter("plant", plant.getId()).setParameter("brand", brand.getId()).setParameter("user", user.getNetUserName()).getResultList();
		if (listGre.size() == 0){
			log.debug("\t###\t getPermissionForPlant(" + user.getNetUserName() + ", " + plant.getId()+ ", " + brand.getBrandMark()+") ... takove opravneni zatin NEEXISTUJE!");
			return null;
		} else {
		PermissionBrand permissionForBrand = listGre.get(0);
		log.debug("\t###\t ... found: "+ permissionForBrand.getBrand().getBrandMark()+" pro "+plant.getId()+" - "+ permissionForBrand.getUserApl().getNetUserName());
		return permissionForBrand;
		}
	}
	

}
