package vwg.skoda.cocafop.repositories;

import java.util.List;

import vwg.skoda.cocafop.entities.Brand;
import vwg.skoda.cocafop.entities.PermissionBrand;
import vwg.skoda.cocafop.entities.PermissionPlant;
import vwg.skoda.cocafop.entities.Plant;
import vwg.skoda.cocafop.entities.UserApl;

public interface UserRepository {

	List<UserApl> selectUsers();
	UserApl selectOneUser(String netUserName);
	void addUser(UserApl user);
	PermissionPlant getPermissionForPlant(UserApl user, Plant plant);
	PermissionBrand getPermissionForBrand(UserApl user, Plant plant, Brand brand);

}
