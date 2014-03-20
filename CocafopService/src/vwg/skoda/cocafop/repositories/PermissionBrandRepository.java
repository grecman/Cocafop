package vwg.skoda.cocafop.repositories;

import java.util.List;

import vwg.skoda.cocafop.entities.PermissionBrand;


public interface PermissionBrandRepository {
	
	PermissionBrand selectOnePermissionBrand (Integer id);
	
	List<PermissionBrand> selectPermissionBrand();
	List<PermissionBrand> selectPermissionBrand(String userNetName);
	List<PermissionBrand> selectPermissionBrand(String userNetName, String idPlant);
	
	void addPermissionBrand(PermissionBrand permissionBrand);

	void setPermissionBrand(PermissionBrand permissionBrand);



}
