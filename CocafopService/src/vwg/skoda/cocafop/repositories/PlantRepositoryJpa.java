package vwg.skoda.cocafop.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import vwg.skoda.cocafop.entities.Plant;

public class PlantRepositoryJpa implements PlantRepository {

	static Logger log = Logger.getLogger(PlantRepositoryJpa.class);

	@PersistenceContext(name = "CocafopService")
	private EntityManager entityManager;

	@Override
	public List<Plant> selectPlants() {
		log.debug("\t###\t selectPlants()");
		return entityManager.createQuery("SELECT p FROM Plant p ORDER BY p.id", Plant.class).getResultList();
	}

	@Override
	public void addPlant(Plant plant) {
		log.debug("\t###\t addPlant("+plant+")");
		entityManager.persist(plant);		
	}

	@Override
	public Plant selectOnePlant(String id) {
		log.debug("\t###\t selectOnePlant(" + id + ")");
		return entityManager.find(Plant.class, id);
	}

	@Override
	public void removePlant(String id) {
		log.debug("\t###\t removePlant(" + id + ")");
		Plant plant = selectOnePlant(id);
		entityManager.remove(entityManager.merge(plant));
	}

}
