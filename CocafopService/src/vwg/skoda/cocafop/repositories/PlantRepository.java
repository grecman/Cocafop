package vwg.skoda.cocafop.repositories;

import java.util.List;

import vwg.skoda.cocafop.entities.Plant;

public interface PlantRepository {
	
	List<Plant> selectPlants();
	Plant selectOnePlant(String id);
	void addPlant(Plant plant);
	void removePlant(String id);
	
}
