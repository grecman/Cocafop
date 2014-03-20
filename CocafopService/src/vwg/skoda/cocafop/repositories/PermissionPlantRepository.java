package vwg.skoda.cocafop.repositories;

import java.util.List;

import vwg.skoda.cocafop.entities.PermissionPlant;

public interface PermissionPlantRepository {

	PermissionPlant selectOnePermissionPlant (Integer id);
	List<PermissionPlant> selectPermissionPlant();
	List<PermissionPlant> selectPermissionPlant(String netUserName);
	
	void addPermissionPlant(PermissionPlant permissionPlant);
	void setRead(PermissionPlant permissionPlant);
	void setPermissionPlant(PermissionPlant permissionPlant);
}
