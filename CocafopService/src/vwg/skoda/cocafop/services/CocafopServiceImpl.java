package vwg.skoda.cocafop.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;

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
import vwg.skoda.cocafop.repositories.BomRepository;
import vwg.skoda.cocafop.repositories.BrandRepository;
import vwg.skoda.cocafop.repositories.DesRozpadRepository;
import vwg.skoda.cocafop.repositories.ExchangeRateRepository;
import vwg.skoda.cocafop.repositories.LocalContentRepository;
import vwg.skoda.cocafop.repositories.MkOrderRepository;
import vwg.skoda.cocafop.repositories.ModelKeyRepository;
import vwg.skoda.cocafop.repositories.OrderRepository;
import vwg.skoda.cocafop.repositories.PermissionBrandRepository;
import vwg.skoda.cocafop.repositories.PermissionPlantRepository;
import vwg.skoda.cocafop.repositories.PlantRepository;
import vwg.skoda.cocafop.repositories.PriceListRepository;
import vwg.skoda.cocafop.repositories.PriceListSkodaRepository;
import vwg.skoda.cocafop.repositories.ProtokolRepository;
import vwg.skoda.cocafop.repositories.UserRepository;

public class CocafopServiceImpl implements CocafopService {

	private UserRepository userRepository;
	private PlantRepository plantRepository;
	private BrandRepository brandRepository;
	private PermissionPlantRepository permissionPlantRepository;
	private PermissionBrandRepository permissionBrandRepository;
	private ExchangeRateRepository exchangeRateRepository;
	private ModelKeyRepository modelKeyRepository;
	private LocalContentRepository localContentRepository;
	private OrderRepository orderRepository;
	private MkOrderRepository mkOrderRepository;
	private BomRepository bomRepository;
	private DesRozpadRepository desRozpadRepository;
	private PriceListRepository priceListRepository;
	private PriceListSkodaRepository priceListSkodaRepository;
	private ProtokolRepository protokolRepository;
	
	
	static Logger log = Logger.getLogger(CocafopServiceImpl.class);

	@Required
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Required
	public void setPlantRepository(PlantRepository plantRepository) {
		this.plantRepository = plantRepository;
	}
	
	@Required
	public void setBrandRepository(BrandRepository brandRepository) {
		this.brandRepository = brandRepository;
	}

	@Required
	public void setPermissionPlantRepository(PermissionPlantRepository permissionPlantRepository){
		this.permissionPlantRepository = permissionPlantRepository;
	}
	
	@Required
	public void setPermissionBrandRepository(PermissionBrandRepository permissionBrandRepository) {
		this.permissionBrandRepository = permissionBrandRepository;
	}
	
	@Required
	public void setExchangeRateRepository(ExchangeRateRepository exchangeRateRepository) {
		this.exchangeRateRepository = exchangeRateRepository;
	}

	@Required
	public void setModelKeyRepository(ModelKeyRepository modelKeyRepository) {
		this.modelKeyRepository = modelKeyRepository;
	}

	@Required
	public void setLocalContentRepository(LocalContentRepository localContentRepository) {
		this.localContentRepository = localContentRepository;
	}
	
	@Required
	public void setOrderRepository(OrderRepository orderRepository){
		this.orderRepository = orderRepository;
	}
	
	@Required
	public void setMkOrderRepository(MkOrderRepository mkOrderRepository){
		this.mkOrderRepository = mkOrderRepository;
	}
	
	@Required
	public void setPriceListRepository(PriceListRepository priceListRepository) {
		this.priceListRepository = priceListRepository;
	}
	
	@Required
	public void setPriceListSkodaRepository(PriceListSkodaRepository priceListSkodaRepository) {
		this.priceListSkodaRepository = priceListSkodaRepository;
	}

	@Required
	public void setDesRozpadRepository(DesRozpadRepository desRozpadRepository) {
		this.desRozpadRepository = desRozpadRepository;
	}

	@Required
	public void setBomRepository(BomRepository bomRepository) {
		this.bomRepository = bomRepository;
	}

	@Required
	public void setProtokolRepository(ProtokolRepository protokolRepository) {
		this.protokolRepository = protokolRepository;
	}

	
//---------------------------------------------------------------------------------------------------------------	

	@Override
	public List<UserApl> getUsers() {
		return userRepository.selectUsers();
	}

	@Override
	public UserApl getOneUser(String netUserName) {
		return userRepository.selectOneUser(netUserName);
	}

	@Override
	@Transactional
	public void addUser(UserApl user) {
		userRepository.addUser(user);
	}
	

	@Override
	public PermissionPlant getPermissionForPlant(UserApl user, Plant plant) {
		return userRepository.getPermissionForPlant(user, plant);
	}
	
	@Override
	public PermissionBrand getPermissionForBrand(UserApl user, Plant plant, Brand brand) {
		return userRepository.getPermissionForBrand(user, plant, brand);
	}


	@Override
	@Transactional(readOnly = true)
	public List<Plant> getPlants() {
		return plantRepository.selectPlants();
	}

	@Override
	@Transactional
	public void addPlant(Plant plant) {
		plantRepository.addPlant(plant);
	}

	@Override
	@Transactional
	public void removePlant(String id) {
		plantRepository.removePlant(id);
	}

	@Override
	public Plant getOnePlant(String id) {
		return plantRepository.selectOnePlant(id);
	}


	@Override
	public List<Brand> getBrands() {
		return brandRepository.selectBrands();
	}
	
	@Override
	public List<Brand> getBrands(String idPlant) {
		return brandRepository.selectBrands(idPlant);
	}

	@Override
	public Brand getOneBrand(Integer id) {
		return brandRepository.selectOneBrand(id);
	}
	

	@Override
	public Brand getOneBrand(String plantId, String brandMark) {
		return brandRepository.selectOneBrand(plantId, brandMark);
	}


	@Override
	@Transactional
	public void addBrand(Brand brand) {
		brandRepository.addBrand(brand);
	}

	@Override
	@Transactional
	public void removeBrand(Integer id) {
		brandRepository.removeBrand(id);
	}

	@Override
	public List<PermissionPlant> getPermissionPlant() {
		return permissionPlantRepository.selectPermissionPlant();
	}

	@Override
	public List<PermissionPlant> getPermissionPlant(String netUserName) {
		return permissionPlantRepository.selectPermissionPlant(netUserName);
	}

	@Override
	public PermissionPlant getOnePermissionPlant(Integer id) {
		return permissionPlantRepository.selectOnePermissionPlant(id);
	}

	@Override
	@Transactional
	public void addPermissionPlant(PermissionPlant permissionPlant) {
		permissionPlantRepository.addPermissionPlant(permissionPlant);		
	}	
	
	@Override
	@Transactional
	public void setPermissionPlant(PermissionPlant permissionPlant) {
		permissionPlantRepository.setPermissionPlant(permissionPlant);
	}

	@Override
	public List<PermissionBrand> getPermissionBrand() {
		return permissionBrandRepository.selectPermissionBrand();
	}

	@Override
	public List<PermissionBrand> getPermissionBrand(String netUserName) {
		return permissionBrandRepository.selectPermissionBrand(netUserName);
	}

	@Override
	public List<PermissionBrand> getPermissionBrand(String netUserName, String idPlant) {
		return permissionBrandRepository.selectPermissionBrand(netUserName, idPlant);
	}

	@Override
	public PermissionBrand getOnePermissionBrand(Integer id) {
		return permissionBrandRepository.selectOnePermissionBrand(id);
	}

	@Override
	@Transactional
	public void addPermissionBrand(PermissionBrand permissionBrand) {
		permissionBrandRepository.addPermissionBrand(permissionBrand);
	}


	@Override
	@Transactional
	public void setPermissionBrand(PermissionBrand permissionBrand) {
		permissionBrandRepository.setPermissionBrand(permissionBrand);
	}

	@Override
	public List<ExchangeRate> getExchangeRates(String idPlant) {
		return exchangeRateRepository.selectExchangeRate(idPlant);
	}

	@Override
	public ExchangeRate getOneExchangeRate(Integer id) {
		return exchangeRateRepository.selectOneExchangeRate(id);
	}

	@Override
	public ExchangeRate getOneExchangeRate(String idPlant, Integer rok, Integer mesic) {
		return exchangeRateRepository.getOneExchangeRate(idPlant,rok,mesic);
	}

	@Override
	@Transactional
	public void addExchangeRate(ExchangeRate exchangeRate) {
		exchangeRateRepository.addExchangeRate(exchangeRate);
	}

	@Override
	@Transactional
	public void removeExchangeRate(Integer id) {
		exchangeRateRepository.removeExchangeRate(id);
	}

	@Override
	public List<ModelKey> getModelKey(String idPlant, Integer idBrand) {
		return modelKeyRepository.getModelKey(idPlant, idBrand);
	}

	@Override
	public List<ModelKey> getModelKey(String idPlant, Integer idBrand, Integer rok) {
		return modelKeyRepository.getModelKey(idPlant, idBrand, rok);
	}
	
	@Override
	public List<ModelKey> getModelKey(String idPlant, Integer idBrand, Integer rok, Integer mesic) {
		return modelKeyRepository.getModelKey(idPlant, idBrand, rok, mesic);
	}

	@Override
	public ModelKey getOneModelKey(Integer id) {
		return modelKeyRepository.getOneModelKey(id);
	}

	@Override
	@Transactional
	public void addModelKey(ModelKey modelKey) {
		modelKeyRepository.addModelKey(modelKey);
		
	}

	@Override
	@Transactional
	public void removeModelKey(Integer id) {
		modelKeyRepository.removeModelKey(id);
	}

	@Override
	@Transactional
	public void setModelKey(ModelKey modelKey) {
		modelKeyRepository.setModelKey(modelKey);
	}

	@Override
	public List<Integer> getRok(String idPlant, Integer idBrand) {
		return modelKeyRepository.getRok(idPlant, idBrand);
	}

	@Override
	public LocalContent getOneLocalContent(Integer id) {
		return localContentRepository.getOneLocalContent(id);
	}

	@Override
	public List<LocalContent> getLocalContent(Integer idModelKey) {
		return localContentRepository.getLocalContent(idModelKey);
	}

	@Override
	@Transactional
	public void addLocalContent(LocalContent localContent) {
		localContentRepository.addLocalContent(localContent);
	}

	@Override
	@Transactional
	public void removeLocalContent(LocalContent localContent) {
		localContentRepository.removeLocalContent(localContent);
	}

	@Override
	public Order getOneOrder(Integer id) {
		return orderRepository.getOneOrder(id);
	}
	
	@Override
	public Order getOneOrder(String wk, String modelKey, Integer rok, Integer mesic, String modelKeyVersion, String colour, Integer modelYear, String deliveryProgram,
			String options) {
		return orderRepository.getOneOrder(wk, modelKey, rok, mesic, modelKeyVersion, colour, modelYear, deliveryProgram, options);
	}

	@Override
	public List<OrdersGroup> getOrderGroup(String idPlant, String modelKey, Integer rok, Integer mesic) {
		return orderRepository.getOrderGroup(idPlant, modelKey, rok, mesic);
	}

	@Override
	public MkOrder getOneMkOrder(Integer id) {
		return mkOrderRepository.getOneMkOrder(id);
	}
	
	@Override
	public List<MkOrder> getMkOrder(Integer modelKey) {
		return mkOrderRepository.getMkOrder(modelKey);
	}

	@Override
	public List<MkOrder> getMkOrder(String plant, Integer rok, Integer mesic) {
		return mkOrderRepository.getMkOrder(plant, rok, mesic);
	}
	
	@Override
	public List<MkOrder> getMkOrder(String plant, Integer brand, Integer rok, Integer mesic) {
		return mkOrderRepository.getMkOrder(plant, brand, rok, mesic);
	}
	

	@Override
	public Boolean checkApprovePlant(String plant, Integer brand, Integer rok, Integer mesic) {
		return mkOrderRepository.checkApprovePlant(plant, brand, rok, mesic);
	}
	
	@Override
	public Boolean checkApproveBrand(String plant, Integer brand, Integer rok, Integer mesic) {
		return mkOrderRepository.checkApproveBrand(plant, brand, rok, mesic);
	}

	@Override
	public List<MkOrder> getOneMkOrder(Integer modelKey, Integer mesic) {
		return mkOrderRepository.getOneMkOrder(modelKey, mesic);
	}

	@Override
	@Transactional
	public void addMkOrder(MkOrder mkOrder) {
		mkOrderRepository.addMkOrder(mkOrder);		
	}

	@Override
	@Transactional
	public void setMkOrder(MkOrder mkOrder) {
		mkOrderRepository.setMkOrder(mkOrder);
	}


	@Override
	public List<DesRozpad> getDesRozpad(String kifa) {
		return desRozpadRepository.getDesRozpad(kifa);
	}
	

	@Override
	public Bom getOneBom(Integer id) {
		return bomRepository.getOneBom(id);
	}
	

	@Override
	public List<Bom> getBom(Integer idMkOrder) {
		return bomRepository.getBom(idMkOrder);
	}
	
	@Override
	public List<Bom> getBom(Integer idMkOrder, String partNumber, String typ, String partDescEng, Float priceBrandFrom, Float priceBrandTo, Float pricePlantFrom, Float pricePlantTo, Float exRate) {
		return bomRepository.getBom(idMkOrder, partNumber, typ, partDescEng, priceBrandFrom, priceBrandTo, pricePlantFrom, pricePlantTo, exRate);
	}

	@Override
	public List<Bom> getBom(Integer brandId, Integer rok, Integer mesic, String partNumber) {
		return bomRepository.getBom(brandId, rok, mesic, partNumber);	
	}

	
	@Override
	@Transactional
	public void setBom(Bom bom) {
		bomRepository.setBom(bom);
	}

	@Override
	@Transactional
	public void addBom(Bom bom) {
		bomRepository.addBom(bom);		
	}
	
	@Override
	@Transactional
	public void deleteBom(String kifa) {
		bomRepository.deleteBom(kifa);
	}
	
	@Override
	@Transactional
	public void deleteBom(Integer mkOrder) {
		bomRepository.deleteBom(mkOrder);
	}

	@Override
	@Transactional
	public void deleteOneBom(Integer id) {
		bomRepository.deleteOneBom(id);
	}
	
	@Override
	public List<String> getType(Integer idMkOrder) {
		return bomRepository.getType(idMkOrder);
	}

	@Override
	public Boolean existKifa(String kifa) {
		return bomRepository.existKifa(kifa);
	}


	@Override
	public Boolean existPartNumberInBom(String kifa, String partNumber) {
		return bomRepository.existPartNumberInBom(kifa, partNumber);
	}
	

	@Override
	public Double getTotalPricePlant(String kifa) {
		return bomRepository.getTotalPricePlant(kifa);
	}
	
	@Override
	public Double getTotalPriceBrand(String kifa) {
		return bomRepository.getTotalPriceBrand(kifa);
	}

	
	@Override
	public PriceList getOnePriceList(Integer rok, Integer mesic, String partNumber) {
		return priceListRepository.getOnePriceList(rok, mesic, partNumber);
	}

	@Override
	public Boolean existPartNumberInPriceList(Integer rok, Integer mesic, String partNumber) {
		return priceListRepository.existPartNumberInPriceList(rok, mesic, partNumber);
	}	

	@Override
	public Boolean existPriceList(Integer rok, Integer mesic) {
		return priceListRepository.existPriceList(rok, mesic);
	}

	@Override
	@Transactional
	public void setPriceList(PriceList priceList) {
		priceListRepository.setPriceList(priceList);		
	}

	@Override
	public PriceListSkoda getOnePriceListSkoda(Integer rok, Integer mesic, String partNumber) {
		return priceListSkodaRepository.getOnePriceListSkoda(rok, mesic, partNumber);
	}

	@Override
	public Boolean existPartNumberInPriceListSkoda(Integer rok, Integer mesic, String partNumber) {
		return priceListSkodaRepository.existPartNumberInPriceListSkoda(rok, mesic, partNumber);
	}
	
	@Override
	public Boolean existPriceListSkoda(Integer rok, Integer mesic) {
		return priceListSkodaRepository.existPriceListSkoda(rok, mesic);
	}


	@Override
	public void setPriceListSkoda(PriceListSkoda priceListSkoda) {
		priceListSkodaRepository.setPriceListSkoda(priceListSkoda);		
	}

	@Override
	@Transactional
	public void setProtokol(Protokol protokol) {
		protokolRepository.setProtokol(protokol);		
	}

	@Override
	@Transactional
	public void addProtokol(Protokol protokol) {
		protokolRepository.addProtokol(protokol);		
	}



}
