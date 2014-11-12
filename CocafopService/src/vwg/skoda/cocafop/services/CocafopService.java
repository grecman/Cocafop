package vwg.skoda.cocafop.services;

import java.util.List;

import vwg.skoda.cocafop.entities.Bom;
import vwg.skoda.cocafop.entities.Brand;
import vwg.skoda.cocafop.entities.DesRozpad;
import vwg.skoda.cocafop.entities.ExchangeRate;
import vwg.skoda.cocafop.entities.LocalContent;
import vwg.skoda.cocafop.entities.MkOrder;
import vwg.skoda.cocafop.entities.ModelKey;
import vwg.skoda.cocafop.entities.Order;
import vwg.skoda.cocafop.entities.PermissionBrand;
import vwg.skoda.cocafop.entities.PermissionPlant;
import vwg.skoda.cocafop.entities.Plant;
import vwg.skoda.cocafop.entities.PriceList;
import vwg.skoda.cocafop.entities.PriceListSkoda;
import vwg.skoda.cocafop.entities.Protokol;
import vwg.skoda.cocafop.entities.UserApl;
import vwg.skoda.cocafop.objects.OrdersGroup;

public interface CocafopService {

	// UserApl
	List<UserApl> getUsers();
	UserApl getOneUser(String netUserName);
	void addUser(UserApl user);
	PermissionPlant getPermissionForPlant(UserApl user, Plant plant);
	PermissionBrand getPermissionForBrand(UserApl user, Plant plant, Brand brand);
	
	// Plant
	List<Plant> getPlants();
	Plant getOnePlant(String id);
	void addPlant(Plant plant);
	void removePlant(String id);
	
	// Brand
	List<Brand> getBrands(String idPlant);
	List<Brand> getBrands();
	Brand getOneBrand(Integer id);
	Brand getOneBrand(String plantId, String brandMark);
	void addBrand(Brand brand);
	void removeBrand(Integer id);
	
	// PermissionPlant
	void addPermissionPlant(PermissionPlant permissionPlant);
	List<PermissionPlant> getPermissionPlant();
	List<PermissionPlant> getPermissionPlant(String netUserName);
	PermissionPlant getOnePermissionPlant(Integer id);
	void setPermissionPlant(PermissionPlant permissionPlant);
	
	// PermissionBrand
	void addPermissionBrand(PermissionBrand permissionBrand);
	List<PermissionBrand> getPermissionBrand();
	List<PermissionBrand> getPermissionBrand(String netUserName);
	List<PermissionBrand> getPermissionBrand(String netUserName, String idPlant);
	PermissionBrand getOnePermissionBrand(Integer id);
	void setPermissionBrand(PermissionBrand permissionBrand);
	
	//ExchangeRate
	List<ExchangeRate> getExchangeRates(String idPlant);
	ExchangeRate getOneExchangeRate(Integer id);
	ExchangeRate getOneExchangeRate(String idPlant, Integer rok, Integer mesic);
	void addExchangeRate(ExchangeRate exchangeRate);
	void removeExchangeRate(Integer id);
	
	// ModelKey
	List<ModelKey> getModelKey(String idPlant, Integer idBrand);
	List<ModelKey> getModelKey(String idPlant, Integer idBrand, Integer rok);
	List<ModelKey> getModelKey(String idPlant, Integer idBrand, Integer rok, Integer mesic);
	ModelKey getOneModelKey(Integer id);
	void addModelKey(ModelKey modelKey);
	void removeModelKey(Integer id);
	void setModelKey(ModelKey modelKey);
	List<Integer> getRok(String idPlant, Integer idBrand);
	
	// LocalContent
	LocalContent getOneLocalContent(Integer id);
	List<LocalContent> getLocalContent(Integer idModelKey);
	void addLocalContent(LocalContent localContent);
	void removeLocalContent(LocalContent localContent);
	
	// Order
	Order getOneOrder(Integer id);
	Order getOneOrder(String wk, String modelKey, Integer rok, Integer mesic, String modelKeyVersion, String colour, Integer modelYear, String deliveryProgram, String options);
	List<OrdersGroup> getOrderGroup(String idPlant, String modelKey, Integer rok, Integer mesic);
	
	// MkOrder
	MkOrder getOneMkOrder(Integer id);
	List<MkOrder> getOneMkOrder(Integer modelKey, Integer mesic);
	List<MkOrder> getMkOrder(Integer modelKey);
	List<MkOrder> getMkOrder(String plant, Integer rok, Integer mesic);
	List<MkOrder> getMkOrder(String plant, Integer brand, Integer rok, Integer mesic);
	Boolean checkApprovePlant(String plant, Integer brand, Integer rok, Integer mesic);
	Boolean checkApproveBrand(String plant, Integer brand, Integer rok, Integer mesic);
	void addMkOrder(MkOrder mkOrder);
	void setMkOrder(MkOrder mkOrder);
	
	// Bom
	Boolean existKifa(String kifa);
	Bom getOneBom(Integer id);
	void addBom (Bom bom);
	void setBom (Bom bom);
	void deleteBom (String kifa);
	void deleteBom (Integer idMkOrder);
	void deleteOneBom (Integer id);

	List<Bom> getBom(Integer idMkOrder);
	List<Bom> getBom(Integer idMkOrder, String partNumber, String typ, String partDescEng, Float priceBrandFrom, Float priceBrandTo, Float pricePlantFrom, Float pricePlantTo, Float exRate);
	List<Bom> getBom(Integer brandId, Integer rok, Integer mesic, String partNumber);
	Boolean existPartNumberInBom(String kifa, String partNumber);
	Double getTotalPricePlant(String kifa);
	Double getTotalPriceBrand(String kifa);
	List<String> getType (Integer idMkOrder);

	// DesRozpad
	List<DesRozpad> getDesRozpad(String kifa);
	
	// PriceList
	PriceList getOnePriceList(Integer rok, Integer mesic, String partNumber);	
	Boolean existPartNumberInPriceList(Integer rok, Integer mesic, String partNumber);
	Boolean existPriceList(Integer rok, Integer mesic);
	void setPriceList (PriceList priceList);
	
	// PriceListSkoda
	PriceListSkoda getOnePriceListSkoda(Integer rok, Integer mesic, String partNumber);	
	Boolean existPartNumberInPriceListSkoda(Integer rok, Integer mesic, String partNumber);
	Boolean existPriceListSkoda(Integer rok, Integer mesic);
	void setPriceListSkoda (PriceListSkoda priceListSkoda);
	
	// Protokol
	void setProtokol (Protokol protokol);
	void addProtokol (Protokol protokol);
}
