package vwg.skoda.cocafop.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
import vwg.skoda.cocafop.entities.UserApl;
import vwg.skoda.cocafop.objects.BomFilter;
import vwg.skoda.cocafop.objects.ModelFilter;
import vwg.skoda.cocafop.objects.OrdersGroup;
import vwg.skoda.cocafop.output.ExportXls;
import vwg.skoda.cocafop.services.CocafopService;

@Controller
@RequestMapping("/calculations")
public class CalculationsController {

	static Logger log = Logger.getLogger(CalculationsController.class);

	@Autowired
	private CocafopService service;

	@RequestMapping(value = "/calculations")
	public String choosePermissionPlant(@ModelAttribute ModelFilter modelFilter, Model model, HttpServletRequest req) {
		log.debug("###\t choosePermissionPlant(" + req.getUserPrincipal().getName().toUpperCase() + ")");
		
		//----------------------------------------------------------------------------------------------------------
		// Vytvoreni noveho DZCecka v UserApl
		if (service.getOneUser(req.getUserPrincipal().getName().toUpperCase()) == null) {
			log.debug("###\t Zalozeni noveho uctu UserApl: " + req.getUserPrincipal().getName().toUpperCase() + "   do GZ38T_USER");
			UserApl userApl = new UserApl();
			userApl.setNetUserName(req.getUserPrincipal().getName().toUpperCase());
			userApl.setUuser(req.getUserPrincipal().getName().toUpperCase());
			userApl.setUtime(new Date());
			service.addUser(userApl);
		}
		//----------------------------------------------------------------------------------------------------------
		// Ziskani seznamu zavodu pro které se budou vytvaret opravneni, protoze jeste neexistuji
		// tzn: existuje zavod, ale neexistuje pro daneho uzivatele zadny zaznam v PermissionPlant 
		List<Plant> plantsListPrac = service.getPlants();
		List<String> zavody = new ArrayList<String>();
		for (Plant p : plantsListPrac) {
			zavody.add(p.getId());
		}
		List<PermissionPlant> permPlants = service.getPermissionPlant();
		List<String> opraveniZavodu = new ArrayList<String>();
		for (PermissionPlant p : permPlants) {
			if (req.getUserPrincipal().getName().toUpperCase().equals(p.getUserApl().getNetUserName())) {
				opraveniZavodu.add(p.getPlant().getId());
			}
		}
		zavody.removeAll(opraveniZavodu);
		
		// Vytvoreni opravneni (PermissionPlant)
		if (zavody.size() > 0) {
			log.debug("###\t Zavody pro které se budou vytvaret opravneni (PermissionPlant): " + zavody + "\t pro uzivatele: " + req.getUserPrincipal().getName().toUpperCase());
			for (String g : zavody) {
				log.debug("###\t Vytroreni PermissionPlant pro ... Plant: " + g + "\t a User: " + req.getUserPrincipal().getName().toUpperCase());
				Plant pl = new Plant();
				UserApl userApl = service.getOneUser(req.getUserPrincipal().getName().toUpperCase());
				pl = service.getOnePlant(g);
				PermissionPlant perPlant = new PermissionPlant();
				perPlant.setUserApl(userApl);
				perPlant.setPlant(pl);
				perPlant.setRead(false);
				perPlant.setWrite(false);
				perPlant.setApprove(false);
				perPlant.setUuser(req.getUserPrincipal().getName().toUpperCase());
				perPlant.setUtime(new Date());
				service.addPermissionPlant(perPlant);
			}
		}
		//----------------------------------------------------------------------------------------------------------
	
		List<Plant> plantList = service.getPlants();
		model.addAttribute("plantList", plantList);
		return "/calculations";
	}

	@RequestMapping(value = "/choosePermissionBrand")
	public String choosePermissionBrand(@ModelAttribute ModelFilter modelFilter, Model model, HttpServletRequest req) {
		log.debug("###\t choosePermissionBrand(" + modelFilter.getIdPlant() + "-" + modelFilter.getBrandMark() + ")");
		choosePermissionPlant(modelFilter, model, req);
		
		//----------------------------------------------------------------------------------------------------------
		// Ziskani seznamu znacek pro dany zavod, pro které se budou vytvaret opravneni, protoze jeste neexistuji
		// tzn: existuje znacka, ale neexistuje pro daneho uzivatele zadny zaznam v PermissionBrand 
		List<Brand> brandsListPrac = service.getBrands(modelFilter.getIdPlant());
		List<Integer> znacky = new ArrayList<Integer>();
		for (Brand p : brandsListPrac) {
			znacky.add(p.getId());
		}
		List<PermissionBrand> permBrands = service.getPermissionBrand(req.getUserPrincipal().getName().toUpperCase());
		List<Integer> oprZnacek = new ArrayList<Integer>();
		for (PermissionBrand p : permBrands) {
			if (req.getUserPrincipal().getName().toUpperCase().equals(p.getUserApl().getNetUserName())) {
				oprZnacek.add(p.getBrand().getId());
			}
		}
		znacky.removeAll(oprZnacek);

		// Vytvoreni opravneni (PermissionBrand)
		if (znacky.size() > 0) {
			log.debug("###\t Znacky pro které se budou vytvaret opravneni (PermissionBrand): " + znacky + "\t pro uzivatele: " + req.getUserPrincipal().getName().toUpperCase());
			for (Integer g : znacky) {
				log.debug("###\t Vytroreni PermissionBrand pro ... Brand: " + g + "\t a User: " + req.getUserPrincipal().getName().toUpperCase());
				Brand pl = new Brand();
				pl = service.getOneBrand(g);
				UserApl userApl = service.getOneUser(req.getUserPrincipal().getName().toUpperCase());
				PermissionBrand perBrand = new PermissionBrand();
				perBrand.setUserApl(userApl);
				perBrand.setBrand(pl);
				perBrand.setRead(false);
				perBrand.setWrite(false);
				perBrand.setApprove(false);
				perBrand.setUuser(req.getUserPrincipal().getName().toUpperCase());
				perBrand.setUtime(new Date());
				service.addPermissionBrand(perBrand);
			}
		}
		//----------------------------------------------------------------------------------------------------------

		
		List<Brand> brandList = service.getBrands(modelFilter.getIdPlant());
		model.addAttribute("brandList", brandList);
		model.addAttribute("plantId", modelFilter.getIdPlant());
		return "/calculations";
	}

	@RequestMapping(value = "/chooseYear/{plantId}")
	public String chooseYear(@PathVariable String plantId, @ModelAttribute ModelFilter modelFilter, Model model, HttpServletRequest req) {

		Brand b = service.getOneBrand(plantId, modelFilter.getBrandMark());
		modelFilter.setIdPlant(plantId);
		choosePermissionBrand(modelFilter, model, req);
		log.debug("###\t chooseYear(" + plantId + ", " + modelFilter.getBrandMark() + ")");

		List<Integer> roky = service.getRok(plantId, b.getId());
		log.debug("\t###\t\t found :" + roky);

		model.addAttribute("rokyList", roky);
		model.addAttribute("plantId", plantId);
		model.addAttribute("brandMark", modelFilter.getBrandMark());
		return "/calculations";
	}

	@RequestMapping(value = "/chooseMonth/{plantId}/{brandMark}")
	public String chooseMonth(@PathVariable String plantId, @PathVariable String brandMark, @ModelAttribute ModelFilter modelFilter, Model model, HttpServletRequest req) {
		log.debug("###\t chooseMonth(" + plantId + ", " + brandMark + ", " + modelFilter.getYear() + ")");
		modelFilter.setBrandMark(brandMark);
		chooseYear(plantId, modelFilter, model, req);

		List<Integer> mesice = new ArrayList<Integer>();
		mesice.add(1);
		mesice.add(2);
		mesice.add(3);
		mesice.add(4);
		mesice.add(5);
		mesice.add(6);
		mesice.add(7);
		mesice.add(8);
		mesice.add(9);
		mesice.add(10);
		mesice.add(11);
		mesice.add(12);
		model.addAttribute("monthList", mesice);

		model.addAttribute("plantId", plantId);
		model.addAttribute("brandMark", brandMark);
		model.addAttribute("rok", modelFilter.getYear());
		return "/calculations";
	}

	@RequestMapping(value = "/showMkOrders/{plantId}/{brandMark}/{rok}")
	public String showMkOrders(@PathVariable String plantId, @PathVariable String brandMark, @PathVariable Integer rok, @RequestParam(required = false) Integer mesic,
			@ModelAttribute ModelFilter modelFilter, Model model, HttpServletRequest req) {
		if (mesic != null)
			modelFilter.setMonth(mesic);
		log.debug("###\t showMkOrders(" + plantId + ", " + brandMark + ", " + rok + ", " + modelFilter.getMonth() + ")");

		chooseMonth(plantId, brandMark, modelFilter, model, req);

		// Vynulovani bomFiltru
		req.getSession().setAttribute("skokNaBom", "bezFiltru");
		req.getSession().setAttribute("partNumberFilter", null);
		req.getSession().setAttribute("typFilter", null);
		req.getSession().setAttribute("partDescEngFilter", null);
		req.getSession().setAttribute("priceBrandFromFilter", null);
		req.getSession().setAttribute("priceBrandToFilter", null);
		req.getSession().setAttribute("pricePlantFromFilter", null);
		req.getSession().setAttribute("pricePlantToFilter", null);


		ExchangeRate exRate = service.getOneExchangeRate(plantId, rok, modelFilter.getMonth());
		if (exRate==null) {
			model.addAttribute("exchangeRateNotExists", true);			
		}
		
		
		Plant p = service.getOnePlant(plantId);
		Brand b = service.getOneBrand(plantId, brandMark);
		UserApl u = service.getOneUser(req.getUserPrincipal().getName().toUpperCase());
		PermissionPlant permPlant = service.getPermissionForPlant(u, p);
		PermissionBrand permBrand = service.getPermissionForBrand(u, p, b);
		if (permPlant.getRead())
			model.addAttribute("permPlantRead", true);
		if (permPlant.getWrite())
			model.addAttribute("permPlantWrite", true);
		if (permPlant.getApprove())
			model.addAttribute("permPlantApprove", true);

		if (permBrand.getRead())
			model.addAttribute("permBrandRead", true);
		if (permBrand.getWrite())
			model.addAttribute("permBrandWrite", true);
		if (permBrand.getApprove())
			model.addAttribute("permBrandApprove", true);
		log.debug("###\t Permission for plant (read/write/approve): " + permPlant.getRead() + "/" + permPlant.getWrite() + "/" + permPlant.getApprove());
		log.debug("###\t Permission for brand (read/write/approve): " + permBrand.getRead() + "/" + permBrand.getWrite() + "/" + permBrand.getApprove());
		
		Boolean zavodJeSchvaleny = service.checkApprovePlant(plantId, b.getId(), rok, modelFilter.getMonth());
		model.addAttribute("zavodJeSchvaleny", zavodJeSchvaleny);
		log.debug("###\t Zachvaleny je zavod: " + zavodJeSchvaleny);
		
		Boolean znackaJeSchvalena = service.checkApproveBrand(p.getId(), b.getId(), rok, modelFilter.getMonth());
		model.addAttribute("znackaJeSchvalena", znackaJeSchvalena);
		log.debug("###\t Zachvaleny je zavod: " + znackaJeSchvalena);
		
		List<MkOrder> listMkO = service.getMkOrder(plantId, b.getId(), rok, modelFilter.getMonth());

		model.addAttribute("listOfMkOrder", listMkO);
		model.addAttribute("plantId", plantId);
		model.addAttribute("brandMark", brandMark);
		model.addAttribute("rok", rok);
		model.addAttribute("mesic", modelFilter.getMonth());
		
		return "/calculations";
	}

	@RequestMapping(value = "/updateMkOrders/{plantId}/{brandMark}/{rok}/{mesic}")
	public String updateMkOrders(@PathVariable String plantId, @PathVariable String brandMark, @PathVariable Integer rok, @PathVariable Integer mesic,
			@ModelAttribute ModelFilter modelFilter, @ModelAttribute ModelKey modelKey, Model model, HttpServletRequest req) {
		log.debug("###\t updateMkOrders(" + plantId + ", " + brandMark + ", " + rok + ", " + mesic + ", " + modelKey + ")");
		Plant p = service.getOnePlant(plantId);
		Brand b = service.getOneBrand(plantId, brandMark);

		modelFilter.setMonth(mesic);
		List<ModelKey> mk = service.getModelKey(p.getId(), b.getId(), rok, mesic);

		/* Insert modelovych klicu do objektu MkOrder
		 * mozna se tady bude muset SETnout i pole USED v objektu ModelKey!!!
		 */
		for (ModelKey m : mk) {
			List<MkOrder> existujiciMkO = service.getOneMkOrder(m.getId(), mesic);
			if (existujiciMkO.size() == 0) {
				log.debug("###\t Insertovano :" + m.getModelKey() + " platne: " + m.getValidFromYear() + "/" + m.getValidFromMonth() + "-" + m.getValidToMonth());
				MkOrder mkOrder = new MkOrder();

				mkOrder.setModelKey(m);
				mkOrder.setMesic(mesic);
				mkOrder.setUuser(req.getUserPrincipal().getName().toUpperCase());
				mkOrder.setUtime(new Date());

				service.addMkOrder(mkOrder);
			} else {
				log.debug("###\t MkOrder pro dany mesic ji existuje :" + m.getId() + " - " + mesic + " - " + existujiciMkO.get(0));
			}
		}
		return "redirect:/srv/calculations/showMkOrders/" + plantId + "/" + brandMark + "/" + rok + "?month=" + mesic;
	}

	@RequestMapping(value = "/approvePlant/{plantId}/{brandMark}/{rok}/{mesic}")
	public String approvePlant(@PathVariable String plantId, @PathVariable String brandMark, @PathVariable Integer rok, @PathVariable Integer mesic, @ModelAttribute ModelFilter modelFilter,
			@ModelAttribute ModelKey modelKey, Model model, HttpServletRequest req) {
		log.debug("###\t approvePlant(" + plantId + ", " + brandMark + ", " + rok + ", " + mesic + ", " + modelKey + ")");
		modelFilter.setMonth(mesic);

		List<MkOrder> mko = service.getMkOrder(plantId, rok, mesic);
		for (MkOrder b : mko) {
			MkOrder oneMko = service.getOneMkOrder(b.getId());
			oneMko.setPlantApprove(true);
			service.setMkOrder(oneMko);
		}
		return "redirect:/srv/calculations/showMkOrders/" + plantId + "/" + brandMark + "/" + rok + "?month=" + mesic;
	}

	@RequestMapping(value = "/approveBrand/{plantId}/{brandMark}/{rok}/{mesic}")
	public String approveBrand(@PathVariable String plantId, @PathVariable String brandMark, @PathVariable Integer rok, @PathVariable Integer mesic, @ModelAttribute ModelFilter modelFilter,
			@ModelAttribute ModelKey modelKey, Model model, HttpServletRequest req) {
		log.debug("###\t approveBrand(" + plantId + ", " + brandMark + ", " + rok + ", " + mesic + ", " + modelKey + ")");
		modelFilter.setMonth(mesic);

		Brand brand = service.getOneBrand(plantId, brandMark);
		List<MkOrder> mko = service.getMkOrder(plantId, brand.getId(), rok, mesic);
		for (MkOrder b : mko) {
			MkOrder oneMko = service.getOneMkOrder(b.getId());
			oneMko.setBrandApprove(true);
			service.setMkOrder(oneMko);
		}
		return "redirect:/srv/calculations/showMkOrders/" + plantId + "/" + brandMark + "/" + rok + "?month=" + mesic;
	}

	@RequestMapping(value = "/ordersVolumes/{idMkOrder}")
	public String OrdersVolumes(@PathVariable Integer idMkOrder, @ModelAttribute ModelFilter modelFilter, Model model, HttpServletRequest req) {
		log.debug("###\t OrdersVolumes(" + idMkOrder + ")");
		MkOrder mko = service.getOneMkOrder(idMkOrder);
		List<OrdersGroup> o = service.getOrderGroup(mko.getModelKey().getBrand().getPlant().getId(), mko.getModelKey().getModelKey(), mko.getModelKey().getRok(), mko.getMesic());

		model.addAttribute("mko", mko);
		model.addAttribute("orderGroup", o);
		return "/ordersVolumes";
	}

	@RequestMapping(value = "/chooseKnr/{idMkO}/{modelKeyVersion}/{colour}/{modelYear}/{deliveryProgram}/{options}")
	public String chooseKnr(@PathVariable Integer idMkO, @PathVariable String modelKeyVersion, @PathVariable String colour, @PathVariable Integer modelYear,
			@PathVariable String deliveryProgram, @PathVariable String options, @ModelAttribute ModelFilter modelFilter, Model model, HttpServletRequest req) {
		log.debug("###\t chooseKnr(" + idMkO + "," + modelKeyVersion + "," + colour + "," + "," + modelYear + "," + deliveryProgram + "," + options + ")");
		MkOrder mko = service.getOneMkOrder(idMkO);
		if (options == "noOptions")
			options = null;
		// Vyber pouze zakazek, pro ktere existuje i DESovy rozpad (GZ38T_DES_ROZPAD)
		Order ord = service.getOneOrder(mko.getModelKey().getBrand().getPlant().getId(), mko.getModelKey().getModelKey(), mko.getModelKey().getRok(), mko.getMesic(), modelKeyVersion,
				colour, modelYear, deliveryProgram, options);
		log.debug("###\t\t\t ... order: " + ord.getId());
		mko.setOrder(ord);
		service.setMkOrder(mko);

		// smazani predchoziho BoM (pri zmene zakazky uz nema smysl si ho nechavat). V naslednem kroku uzivatel zalozi novy Bom s aktualne vybranou zakazkou
		service.deleteBom(mko.getId());
		
		// vytvoreni BOMu
		int i = 0;
		List<DesRozpad> dr = service.getDesRozpad(mko.getOrder().getKifa());
		for (DesRozpad d : dr) {
			Bom bom = new Bom();
			bom.setMkOrder(mko);
			bom.setKifa(d.getKifa());
			bom.setPartNumber(d.getCdilu());
			bom.setCizav(d.getCizav());
			bom.setVerze(d.getVerze());
			bom.setPartDescEng(d.getPartDescEng());
			bom.setUom(d.getMe());
			bom.setQuantity(d.getMnoz());
			bom.setTyp("B"); // oznaceni, ze je to nahrano primo z kusovniku (tedy z DESu)
			if (d.getPricePlant() == null){
				bom.setPricePlant(0f);
			} else {
				bom.setPricePlant(d.getPricePlant());
			}
			if (d.getPriceBrand() == null){
				bom.setPriceBrand(0f);
			} else {
				bom.setPriceBrand(d.getPriceBrand());
			}

			bom.setUuser(req.getUserPrincipal().getName().toUpperCase());
			bom.setUtime(new Date());
			service.addBom(bom);
			++i;
		}
		log.debug("###\t Bom - addBom: " + i + " zaznamu pridano z DESu pro kifu: " + mko.getOrder().getKifa());
	
		// prihrani LC k BOMu
		i = 0;
		List<LocalContent> lc = service.getLocalContent(mko.getModelKey().getId());
		for (LocalContent l : lc) {
			Boolean existujeDilVBom = service.existPartNumberInBom(mko.getOrder().getKifa(), l.getPartNumber());
			if (!existujeDilVBom) {

				Bom bom = new Bom();
				bom.setMkOrder(mko);
				bom.setKifa(mko.getOrder().getKifa());
				bom.setPartNumber(l.getPartNumber());
				bom.setPartDescEng(l.getPartDescription());
				bom.setUom(l.getUom());
				bom.setQuantity(l.getQuantity());
				bom.setTyp(l.getTyp()); // oznaceni, ze je to nahrano primo z LC

				// Oceneni SAILPem
				ExchangeRate ex = service.getOneExchangeRate(mko.getModelKey().getBrand().getPlant().getId(),mko.getModelKey().getRok(), mko.getMesic());
				Boolean existujeDilVCeniku = service.existPartNumberInPriceList(mko.getModelKey().getRok(), mko.getMesic(), l.getPartNumber());
				if (existujeDilVCeniku) {
					PriceList pl = service.getOnePriceList(mko.getModelKey().getRok(), mko.getMesic(), l.getPartNumber());
					if (l.getUom().startsWith(pl.getUom())) {
						bom.setPricePlant(pl.getPrice());
						// zde se ocenuje i BRANDacka cena, protoze znacka zadnou jinou lepsi nema!
						bom.setPriceBrand(pl.getPrice()/ex.getRate());
					}
				} else {
					bom.setPricePlant(0f);
					bom.setPriceBrand(0f);
				}
				bom.setUuser(req.getUserPrincipal().getName().toUpperCase());
				bom.setUtime(new Date());
				service.addBom(bom);
				++i;
			}
		}
		log.debug("###\t Bom - addBom: " + i + " zaznamu pridano z LOCAL CONTENTu pro "+mko.getModelKey().getBrand().getPlant().getId()+"-"+mko.getModelKey().getBrand().getBrandMark()+" a MK " + mko.getModelKey().getModelKey());

		return "redirect:/srv/calculations/showMkOrders/" + mko.getModelKey().getBrand().getPlant().getId() + "/" + mko.getModelKey().getBrand().getBrandMark() + "/"
				+ mko.getModelKey().getRok() + "?month=" + mko.getMesic();
	}

	@RequestMapping(value = "/knrDetail/{idMkOrder}")
	public String knrDetail(@PathVariable Integer idMkOrder, @ModelAttribute ModelFilter modelFilter, Model model, HttpServletRequest req) {
		log.debug("###\t knrDetail(" + idMkOrder + ")");
		MkOrder mko = service.getOneMkOrder(idMkOrder);
		Order o = service.getOneOrder(mko.getOrder().getId());

		model.addAttribute("mko", mko);
		model.addAttribute("oneOrder", o);
		return "/knrDetail";
	}

	@RequestMapping(value = "/bom/{idMkOrder}")
	public String Bom(@PathVariable Integer idMkOrder,@ModelAttribute BomFilter bomFilter, Model model, HttpServletRequest req) {
		log.debug("###\t Bom(" + idMkOrder + ") ... idMkOrder");
		log.trace("### bomFilter.pricePlantFrom: " + bomFilter.getPricePlantFrom());
		MkOrder mko = service.getOneMkOrder(idMkOrder);

		//log.debug("###\t Bom - setting filters("+req.getSession().getAttribute("prvniSkokNaBom") + " -  '" + bomFilter.getPartNumber() + "', " + bomFilter.getTyp() + ", " + bomFilter.getPartDescEng() + ", " + bomFilter.getPriceBrandFrom()+"-"+bomFilter.getPriceBrandTo() + ", " + bomFilter.getPricePlantFrom()+"-"+bomFilter.getPricePlantTo()+ ")");
		// Pripadne naSETovani filtru na obrazovce BOM
		if (req.getSession().getAttribute("skokNaBom") == "bezFiltru"){
			req.getSession().setAttribute("skokNaBom", "sFiltrem");
			req.getSession().setAttribute("partNumberFilter", null);
			req.getSession().setAttribute("typFilter", null);
			req.getSession().setAttribute("partDescEngFilter", null);
			req.getSession().setAttribute("pricePlantFromFilter", null);
			req.getSession().setAttribute("pricePlantToFilter", null);
			req.getSession().setAttribute("priceBrandFromFilter", null);
			req.getSession().setAttribute("priceBrandToFilter", null);
		} else {
			req.getSession().setAttribute("partNumberFilter", bomFilter.getPartNumber());
			req.getSession().setAttribute("typFilter", bomFilter.getTyp());
			req.getSession().setAttribute("partDescEngFilter", bomFilter.getPartDescEng());
			req.getSession().setAttribute("pricePlantFromFilter", bomFilter.getPricePlantFrom());
			req.getSession().setAttribute("pricePlantToFilter", bomFilter.getPricePlantTo());
			req.getSession().setAttribute("priceBrandFromFilter", bomFilter.getPriceBrandFrom());
			req.getSession().setAttribute("priceBrandToFilter", bomFilter.getPriceBrandTo());
		}
	
		log.debug("###\t bomFilter setting - "+req.getSession().getAttribute("skokNaBom") +  " -  '" + req.getSession().getAttribute("partNumberFilter") + "', " + req.getSession().getAttribute("typFilter") + ", "
				+ req.getSession().getAttribute("partDescEngFilter") + ", " + req.getSession().getAttribute("priceBrandFromFilter")+ "-" + req.getSession().getAttribute("priceBrandToFilter")  + ", " + req.getSession().getAttribute("pricePlantFromFilter")+ "-" + req.getSession().getAttribute("pricePlantToFilter") );

		Plant p = service.getOnePlant(mko.getModelKey().getBrand().getPlant().getId());
		Brand b = service.getOneBrand(mko.getModelKey().getBrand().getPlant().getId(), mko.getModelKey().getBrand().getBrandMark());
		UserApl u = service.getOneUser(req.getUserPrincipal().getName().toUpperCase());
		PermissionPlant permPlant = service.getPermissionForPlant(u, p);
		PermissionBrand permBrand = service.getPermissionForBrand(u, p, b);

		if (permPlant.getRead())
			model.addAttribute("permPlantRead", true);
		if (permPlant.getWrite())
			model.addAttribute("permPlantWrite", true);
		if (permPlant.getApprove())
			model.addAttribute("permPlantApprove", true);

		if (permBrand.getRead())
			model.addAttribute("permBrandRead", true);
		if (permBrand.getWrite())
			model.addAttribute("permBrandWrite", true);
		if (permBrand.getApprove())
			model.addAttribute("permBrandApprove", true);

		log.debug("###\t Permission for plant (read/write/approve): " + permPlant.getRead() + "/" + permPlant.getWrite() + "/" + permPlant.getApprove());
		log.debug("###\t Permission for brand (read/write/approve): " + permBrand.getRead() + "/" + permBrand.getWrite() + "/" + permBrand.getApprove());

		Boolean zavodJeSchvaleny = service.checkApprovePlant(p.getId(), b.getId(), mko.getModelKey().getRok(), mko.getMesic());
		model.addAttribute("zavodJeSchvaleny", zavodJeSchvaleny);

		Boolean znackaJeSchvalena = service.checkApproveBrand(p.getId(), b.getId(), mko.getModelKey().getRok(), mko.getMesic());
		model.addAttribute("znackaJeSchvalena", znackaJeSchvalena);

		ExchangeRate ex = service.getOneExchangeRate(p.getId(), mko.getModelKey().getRok(), mko.getMesic());
		List<Bom> bomba = service.getBom(idMkOrder, (String)req.getSession().getAttribute("partNumberFilter"), (String)req.getSession().getAttribute("typFilter"),
				(String)req.getSession().getAttribute("partDescEngFilter"),	(Float)req.getSession().getAttribute("priceBrandFromFilter"), (Float)req.getSession().getAttribute("priceBrandToFilter"), (Float)req.getSession().getAttribute("pricePlantFromFilter"), (Float)req.getSession().getAttribute("pricePlantToFilter"), ex.getRate());
		
		Double totalPricePlant = service.getTotalPricePlant(mko.getOrder().getKifa());
		Double totalPriceBrand = service.getTotalPriceBrand(mko.getOrder().getKifa());
		model.addAttribute("totalPricePlant", totalPricePlant);
		model.addAttribute("totalPriceBrand", totalPriceBrand);

		// pro filtr Type - stim, ze jako prvni polozku dam prazdnou (to je ten add)
		List<String> typList = service.getType(mko.getId());
		typList.add(0, "");
		model.addAttribute("typeList", typList);
		
		model.addAttribute("bomList", bomba);
		model.addAttribute("mko", mko);
		model.addAttribute("exchRate", ex);
		return "/bom";
	}

	@RequestMapping(value = "/showAddBom/{idMkOrder}")
	public String showAddBom(@PathVariable Integer idMkOrder, @ModelAttribute Bom bom, Model model, HttpServletRequest req) {
		log.debug("###\t showAddBom(" + idMkOrder + ")");
		MkOrder mko = service.getOneMkOrder(idMkOrder);

		model.addAttribute("mko", mko);
		return "/showAddBom";
	}

	@RequestMapping(value = "/addBom/{idMkOrder}")
	public String addBom(@PathVariable Integer idMkOrder, @ModelAttribute Bom bom, @ModelAttribute ModelFilter modelFilter, Model model, HttpServletRequest req) {
		log.debug("###\t addBom(" + idMkOrder + ", " + bom.getPartNumber() + ")");
		MkOrder mko = service.getOneMkOrder(idMkOrder);

		Boolean existujeDilVBom = service.existPartNumberInBom(mko.getOrder().getKifa(), bom.getPartNumber());
		if (!existujeDilVBom) {
			log.debug("###\t\t addBom(" + mko.getOrder().getKifa() + ", " + bom.getPartNumber() + ")");

			bom.setMkOrder(mko);
			bom.setPartNumber(bom.getPartNumber().toUpperCase());
			bom.setPartDescEng(bom.getPartDescEng().toUpperCase());
			bom.setKifa(mko.getOrder().getKifa());
			bom.setTyp("M"); // oznaceni, ze je to vytvoreno manualne (tedy pomoci tlacitka Add Part Number)
			bom.setPricePlant(0f);
			bom.setPriceBrand(0f);
			bom.setUuser(req.getUserPrincipal().getName().toUpperCase());
			bom.setUtime(new Date());
			service.addBom(bom);
		}

		model.addAttribute("mko", mko);
		return "redirect:/srv/calculations/bom/" + mko.getId();
	}

	@RequestMapping(value = "/deleteBom/{idMkOrder}")
	public String deleteBom(@PathVariable Integer idMkOrder, @ModelAttribute Bom bom, @ModelAttribute ModelFilter modelFilter, Model model, HttpServletRequest req) {
		MkOrder mko = service.getOneMkOrder(idMkOrder);
		log.debug("###\t deleteBom(" + mko.getOrder().getKifa() + ")");
		service.deleteBom(mko.getOrder().getKifa());
		model.addAttribute("mko", mko);
		return "redirect:/srv/calculations/bom/" + mko.getId();
	}

	@RequestMapping(value = "/deleteOneBom/{idBom}")
	public String deleteOneBom(@PathVariable Integer idBom, @ModelAttribute Bom bom, @ModelAttribute ModelFilter modelFilter, Model model, HttpServletRequest req) {
		Bom bm = service.getOneBom(idBom);
		MkOrder mko = bm.getMkOrder();
		log.debug("###\t deleteOneBom(" + mko.getOrder().getKifa() + ", " + bm.getPartNumber() + ")");
		service.deleteOneBom(idBom);
		model.addAttribute("mko", mko);
		return "redirect:/srv/calculations/bom/" + mko.getId();
	}

	@RequestMapping(value = "/showQuantityUom/{idBom}")
	public String showQuantityUom(@PathVariable Integer idBom, @ModelAttribute Bom bom, Model model, HttpServletRequest req) {
		Bom oneBom = service.getOneBom(idBom);
		log.debug("###\t showQuantityUom(" + oneBom.getPartNumber() + ")");

		Plant p = service.getOnePlant(oneBom.getMkOrder().getModelKey().getBrand().getPlant().getId());
		Brand b = service.getOneBrand(oneBom.getMkOrder().getModelKey().getBrand().getPlant().getId(), oneBom.getMkOrder().getModelKey().getBrand().getBrandMark());
		UserApl u = service.getOneUser(req.getUserPrincipal().getName().toUpperCase());
		PermissionPlant permPlant = service.getPermissionForPlant(u, p);
		PermissionBrand permBrand = service.getPermissionForBrand(u, p, b);

		if (permPlant.getRead())
			model.addAttribute("permPlantRead", true);
		if (permPlant.getWrite())
			model.addAttribute("permPlantWrite", true);
		if (permPlant.getApprove())
			model.addAttribute("permPlantApprove", true);

		if (permBrand.getRead())
			model.addAttribute("permBrandRead", true);
		if (permBrand.getWrite())
			model.addAttribute("permBrandWrite", true);
		if (permBrand.getApprove())
			model.addAttribute("permBrandApprove", true);

		model.addAttribute("oneBom", oneBom);
		return "/showQuantityUom";
	}

	@RequestMapping(value = "/changeQuantityUomPlant/{idBom}")
	public String changeQuantityUomPlant(@PathVariable Integer idBom, @ModelAttribute Bom bom, @ModelAttribute ModelFilter modelFilter, Model model, HttpServletRequest req) {
		log.debug("###\t changeQuantityUomPlant(" + idBom + ")");
		Bom oneBom = service.getOneBom(idBom);
		oneBom.setUomPlant(bom.getUomPlant());
		oneBom.setQuantityPlant(bom.getQuantityPlant());
		oneBom.setUuserQuantityPlant(req.getUserPrincipal().getName().toUpperCase());
		oneBom.setUtimeQuantityPlant(new Date());
		service.setBom(oneBom);
		return "redirect:/srv/calculations/bom/" + oneBom.getMkOrder().getId();
	}

	@RequestMapping(value = "/changeQuantityUomBrand/{idBom}")
	public String changeQuantityUomBrand(@PathVariable Integer idBom, @ModelAttribute Bom bom, @ModelAttribute ModelFilter modelFilter, Model model, HttpServletRequest req) {
		log.debug("###\t changeQuantityUomBrand(" + idBom + ")");
		Bom oneBom = service.getOneBom(idBom);
		oneBom.setUomBrand(bom.getUomBrand());
		oneBom.setQuantityBrand(bom.getQuantityBrand());
		oneBom.setUuserQuantityBrand(req.getUserPrincipal().getName().toUpperCase());
		oneBom.setUtimeQuantityBrand(new Date());
		service.setBom(oneBom);
		return "redirect:/srv/calculations/bom/" + oneBom.getMkOrder().getId();
	}

	@RequestMapping(value = "/showPrice/{idBom}")
	public String showPrice(@PathVariable Integer idBom, @ModelAttribute Bom bom, @ModelAttribute PriceList priceList, Model model, HttpServletRequest req) {
		Bom oneBom = service.getOneBom(idBom);
		log.debug("###\t showPrice(" + oneBom.getPartNumber() + ")");

		Plant p = service.getOnePlant(oneBom.getMkOrder().getModelKey().getBrand().getPlant().getId());
		Brand b = service.getOneBrand(oneBom.getMkOrder().getModelKey().getBrand().getPlant().getId(), oneBom.getMkOrder().getModelKey().getBrand().getBrandMark());
		UserApl u = service.getOneUser(req.getUserPrincipal().getName().toUpperCase());
		PermissionPlant permPlant = service.getPermissionForPlant(u, p);
		PermissionBrand permBrand = service.getPermissionForBrand(u, p, b);

		if (permPlant.getRead())
			model.addAttribute("permPlantRead", true);
		if (permPlant.getWrite())
			model.addAttribute("permPlantWrite", true);
		if (permPlant.getApprove())
			model.addAttribute("permPlantApprove", true);

		if (permBrand.getRead())
			model.addAttribute("permBrandRead", true);
		if (permBrand.getWrite())
			model.addAttribute("permBrandWrite", true);
		if (permBrand.getApprove())
			model.addAttribute("permBrandApprove", true);

		model.addAttribute("oneBom", oneBom);
		return "/showPrice";
	}

	@RequestMapping(value = "/changePricePlant/{idBom}")
	public String changePricePlant(@PathVariable Integer idBom, @ModelAttribute Bom bom, @ModelAttribute ModelFilter modelFilter, Model model,
			HttpServletRequest req) {
		Bom oneBom = service.getOneBom(idBom);
		log.debug("###\t changePricePlant(" + oneBom.getPartNumber() + ", " + oneBom.getPricePlant() + " -> " + bom.getPricePlantNew() + ")");
		
		// Update ceniku (PriceList)
		Boolean existujeDilVCeniku = service.existPartNumberInPriceList(oneBom.getMkOrder().getModelKey().getRok(), oneBom.getMkOrder().getMesic(), oneBom.getPartNumber());
		if (existujeDilVCeniku) {
			PriceList onePrice = service.getOnePriceList(oneBom.getMkOrder().getModelKey().getRok(), oneBom.getMkOrder().getMesic(), oneBom.getPartNumber());
			onePrice.setPriceNew(bom.getPricePlantNew());
			onePrice.setUuser(req.getUserPrincipal().getName().toUpperCase());  // info jen pro me, protoze do ceniku nikdo nevidi !!!
			onePrice.setUtime(new Date());  // info jen pro me, protoze do ceniku nikdo nevidi !!!
			service.setPriceList(onePrice);
		}

		// Update vsech dilu v danem mesici a pro PLANTy
		List<Bom> bomka = service.getBom(oneBom.getMkOrder().getModelKey().getBrand().getId(), oneBom.getMkOrder().getModelKey().getRok(), oneBom.getMkOrder().getMesic(),
				oneBom.getPartNumber());
		for (Bom b : bomka) {
			Bom bbb = service.getOneBom(b.getId());
			bbb.setPricePlantNew(bom.getPricePlantNew());
			bbb.setUuserPricePlant(req.getUserPrincipal().getName().toUpperCase());
			bbb.setUtimePricePlant(new Date());
			service.setBom(bbb);
		}

		return "redirect:/srv/calculations/bom/" + oneBom.getMkOrder().getId();
	}
	
	@RequestMapping(value = "/changePriceBrand/{idBom}")
	public String changePriceBrand(@PathVariable Integer idBom, @ModelAttribute Bom bom, @ModelAttribute ModelFilter modelFilter, Model model,
			HttpServletRequest req) {
		Bom oneBom = service.getOneBom(idBom);
		log.debug("###\t changePriceBrand(" + oneBom.getPartNumber() + ", " + oneBom.getPriceBrand() + " -> " + bom.getPriceBrandNew() + ")");
		
		// Update ceniku (PriceList)
		Boolean existujeDilVCeniku = service.existPartNumberInPriceList(oneBom.getMkOrder().getModelKey().getRok(), oneBom.getMkOrder().getMesic(), oneBom.getPartNumber());
		if (existujeDilVCeniku) {
			PriceListSkoda onePrice = service.getOnePriceListSkoda(oneBom.getMkOrder().getModelKey().getRok(), oneBom.getMkOrder().getMesic(), oneBom.getPartNumber());
			onePrice.setPriceNew(bom.getPriceBrandNew());
			onePrice.setUuser(req.getUserPrincipal().getName().toUpperCase()); // info jen pro me, protoze do ceniku nikdo nevidi !!!
			onePrice.setUtime(new Date()); // info jen pro me, protoze do ceniku nikdo nevidi !!!
			service.setPriceListSkoda(onePrice);
		}

		// Update vsech dilu v danem mesici a pro danou BRANDu
		List<Bom> bomka = service.getBom(oneBom.getMkOrder().getModelKey().getBrand().getId(), oneBom.getMkOrder().getModelKey().getRok(), oneBom.getMkOrder().getMesic(),
				oneBom.getPartNumber());
		for (Bom b : bomka) {
			Bom bbb = service.getOneBom(b.getId());
			bbb.setPriceBrandNew(bom.getPriceBrandNew());
			bbb.setUuserPriceBrand(req.getUserPrincipal().getName().toUpperCase());
			bbb.setUtimePriceBrand(new Date());
			service.setBom(bbb);
		}

		return "redirect:/srv/calculations/bom/" + oneBom.getMkOrder().getId();
	}
	
	@RequestMapping(value = "/showModelComparison/{idMkOrder}")
	public String showModelComparison(@PathVariable Integer idMkOrder, @ModelAttribute ModelFilter modelFilter, Model model, HttpServletRequest req) {
		log.debug("###\t showModelComparison("+idMkOrder+", "+modelFilter.getIdPlant()+", "+modelFilter.getBrandMark()+", "+modelFilter.getYear()+"/"+modelFilter.getMonth()+")");
		MkOrder mko = service.getOneMkOrder(idMkOrder);
		model.addAttribute("mko", mko);		

		UserApl u = service.getOneUser(req.getUserPrincipal().getName().toUpperCase());
		PermissionPlant permPlant = service.getPermissionForPlant(u, mko.getModelKey().getBrand().getPlant());
		PermissionBrand permBrand = service.getPermissionForBrand(u, mko.getModelKey().getBrand().getPlant(),mko.getModelKey().getBrand());
		if(permPlant.getWrite())
			model.addAttribute("yourPermissionForCurrentModel", "PLANT");
		if(permBrand.getWrite())
			model.addAttribute("yourPermissionForCurrentModel", "BRAND");
				
		List<Plant> plantList = service.getPlants();
		model.addAttribute("plantList", plantList);
		
		model.addAttribute("idMkOrder", mko.getId());
		return "/showModelComparison";
	}
	
	
	@RequestMapping(value = "/showModelComparisonChooseBrand/{idMkOrder}")
	public String showModelComparisonChooseBrand(@PathVariable Integer idMkOrder, @ModelAttribute ModelFilter modelFilter, Model model, HttpServletRequest req) {
		log.debug("###\t showModelComparisonChooseBrand("+idMkOrder+", "+modelFilter.getIdPlant()+", "+modelFilter.getBrandMark()+", "+modelFilter.getYear()+"/"+modelFilter.getMonth()+")");
		MkOrder mko = service.getOneMkOrder(idMkOrder);
		model.addAttribute("mko", mko);		
		
		UserApl u = service.getOneUser(req.getUserPrincipal().getName().toUpperCase());
		PermissionPlant permPlant = service.getPermissionForPlant(u, mko.getModelKey().getBrand().getPlant());
		PermissionBrand permBrand = service.getPermissionForBrand(u, mko.getModelKey().getBrand().getPlant(),mko.getModelKey().getBrand());
		if(permPlant.getWrite())
			model.addAttribute("yourPermissionForCurrentModel", "PLANT");
		if(permBrand.getWrite())
			model.addAttribute("yourPermissionForCurrentModel", "BRAND");
		
		List<Brand> brandList = service.getBrands(modelFilter.getIdPlant());
		model.addAttribute("brandList", brandList);
		model.addAttribute("plantId", modelFilter.getIdPlant());
		model.addAttribute("idMkOrder", mko.getId());
		return "/showModelComparison";
	}
	
	// GRE_Pozn: tim, ze z JSP vracim hodnotu {idPlant} tak z nejakeho duvodu se tato hodnota "sama" namapovala na modelFilter.getIdPlant() (viz.log) ... jak je to mozne nevim :( 
	@RequestMapping(value = "/showModelComparisonChooseYMkMn/{idMkOrder}/{idPlant}")
	public String showModelComparisonChooseYMkMn(@PathVariable Integer idMkOrder, @PathVariable String idPlant, @ModelAttribute ModelFilter modelFilter, Model model, HttpServletRequest req) {
		log.debug("###\t showModelComparisonChooseYMkMn("+idMkOrder+", "+modelFilter.getIdPlant()+", "+modelFilter.getBrandMark()+", "+modelFilter.getYear()+"/"+modelFilter.getMonth()+")");
		MkOrder mko = service.getOneMkOrder(idMkOrder);
		model.addAttribute("mko", mko);	
		
		Brand b = service.getOneBrand(modelFilter.getIdPlant(), modelFilter.getBrandMark());
		List<ModelKey> mkList = service.getModelKey(modelFilter.getIdPlant(), b.getId());
		model.addAttribute("mkList", mkList);

		UserApl u = service.getOneUser(req.getUserPrincipal().getName().toUpperCase());
		PermissionPlant permPlant = service.getPermissionForPlant(u, mko.getModelKey().getBrand().getPlant());
		PermissionBrand permBrand = service.getPermissionForBrand(u, mko.getModelKey().getBrand().getPlant(),mko.getModelKey().getBrand());
		if(permPlant.getWrite())
			model.addAttribute("yourPermissionForCurrentModel", "PLANT");
		if(permBrand.getWrite())
			model.addAttribute("yourPermissionForCurrentModel", "BRAND");
		
		PermissionPlant permPlant2nd = service.getPermissionForPlant(u, b.getPlant());
		PermissionBrand permBrand2nd = service.getPermissionForBrand(u, b.getPlant(), b);
		if(permPlant2nd.getWrite())
			model.addAttribute("yourPermissionFor2ndModel", "PLANT");
		if(permBrand2nd.getWrite())
			model.addAttribute("yourPermissionFor2ndModel", "BRAND");
		
		// nesoulad opravneni WRITE mezi vybranyma kalkulacema
		if(!(permPlant.getWrite() & permPlant2nd.getWrite() || permBrand.getWrite() & permBrand2nd.getWrite())){
			model.addAttribute("wrongPermissions", "wrongPermissions");
		} 
		
		model.addAttribute("plantId", modelFilter.getIdPlant());
		model.addAttribute("brandMark", modelFilter.getBrandMark());
		model.addAttribute("idMkOrder", mko.getId());
		return "/showModelComparison";
	}
	
	@RequestMapping(value = "/showModelComparisonMonth/{idMkOrder}/{idPlant}/{brandMark}")
	public String showModelComparisonMonth(@PathVariable Integer idMkOrder, @PathVariable String idPlant, @ModelAttribute MkOrder mkOrder, @ModelAttribute ModelFilter modelFilter, Model model, HttpServletRequest req) {
		log.debug("###\t showModelComparisonMonth("+idMkOrder+", "+modelFilter.getIdPlant()+", "+modelFilter.getBrandMark()+", "+modelFilter.getMix()+", "+modelFilter.getMonth()+")");
		MkOrder mko = service.getOneMkOrder(idMkOrder);
		model.addAttribute("mko", mko);
		
		int idMk = Integer.parseInt(modelFilter.getMix());
		ModelKey mk = service.getOneModelKey(idMk);
		
		UserApl u = service.getOneUser(req.getUserPrincipal().getName().toUpperCase());
		PermissionPlant permPlant = service.getPermissionForPlant(u, mko.getModelKey().getBrand().getPlant());
		PermissionBrand permBrand = service.getPermissionForBrand(u, mko.getModelKey().getBrand().getPlant(),mko.getModelKey().getBrand());
		if(permPlant.getWrite())
			model.addAttribute("yourPermissionForCurrentModel", "PLANT");
		if(permBrand.getWrite())
			model.addAttribute("yourPermissionForCurrentModel", "BRAND");

		PermissionPlant permPlant2nd = service.getPermissionForPlant(u, mk.getBrand().getPlant());
		PermissionBrand permBrand2nd = service.getPermissionForBrand(u, mk.getBrand().getPlant(), mk.getBrand());
		if(permPlant2nd.getWrite())
			model.addAttribute("yourPermissionFor2ndModel", "PLANT");
		if(permBrand2nd.getWrite())
			model.addAttribute("yourPermissionFor2ndModel", "BRAND");
		
		// nesoulad opravneni WRITE mezi vybranyma kalkulacema
		if(!(permPlant.getWrite() & permPlant2nd.getWrite() || permBrand.getWrite() & permBrand2nd.getWrite())){
			model.addAttribute("wrongPermissions", "wrongPermissions");
		}
		
		List<MkOrder> mko2 = service.getMkOrder(mk.getId());
		model.addAttribute("mkOrderList", mko2);
		
		model.addAttribute("modelKey", mk.getModelKey());
		model.addAttribute("modelNumber", mk.getModelNumber());
		model.addAttribute("year", mk.getRok());
		model.addAttribute("plantId", modelFilter.getIdPlant());
		model.addAttribute("brandMark", modelFilter.getBrandMark());
		model.addAttribute("idMkOrder", mko.getId());
		return "/showModelComparison";
	}
	
	@RequestMapping(value = "/exportXlsComparison/{idMkOrder}")
	public void exportXlsComparison(@PathVariable Integer idMkOrder, @ModelAttribute MkOrder mkOrder, @ModelAttribute BomFilter bomFilter, Model model, HttpServletRequest req, HttpServletResponse res)  throws IOException {
		log.debug("###\t exportXlsComparison("+idMkOrder+", "+mkOrder.getId()+")");

		MkOrder mko1 = service.getOneMkOrder(idMkOrder);		
		MkOrder mko2 = service.getOneMkOrder(mkOrder.getId());
		
		UserApl u = service.getOneUser(req.getUserPrincipal().getName().toUpperCase());
		PermissionPlant permPlant = service.getPermissionForPlant(u, mko1.getModelKey().getBrand().getPlant());
		PermissionBrand permBrand = service.getPermissionForBrand(u, mko1.getModelKey().getBrand().getPlant(),mko1.getModelKey().getBrand());
		
		List<Bom> bomka1 = service.getBom(mko1.getId());
		ExchangeRate ex1 = service.getOneExchangeRate(mko1.getModelKey().getBrand().getPlant().getId(), mko1.getModelKey().getRok(), mko1.getMesic());
		List<Bom> bomka2 = service.getBom(mko2.getId());
		ExchangeRate ex2 = service.getOneExchangeRate(mko2.getModelKey().getBrand().getPlant().getId(), mko2.getModelKey().getRok(), mko2.getMesic());
/*		
		if(bomka1.size()==0 || bomka2.size()==0){
			log.debug(bomka1.size()+"\t"+bomka2.size());	
		} else {
			ExportXls exp = new ExportXls();
			exp.bomComparison(res, bomka1, bomka2, permPlant.getWrite(),permBrand.getWrite(), ex1, ex2);
		}
		*/
		ExportXls exp = new ExportXls();
		exp.bomComparison(res, bomka1, bomka2, permPlant.getWrite(),permBrand.getWrite(), ex1, ex2);

	}
	
	
	@RequestMapping(value = "/exportXls/{idMkOrder}")
	public void exportXls(@PathVariable Integer idMkOrder, @ModelAttribute Bom bom, @ModelAttribute ModelFilter modelFilter, Model model, HttpServletRequest req, HttpServletResponse res)  throws IOException {
		MkOrder mko = service.getOneMkOrder(idMkOrder);
		log.debug("###\t exportXls(" + mko.getId() + ")");
		
		Plant p = service.getOnePlant(mko.getModelKey().getBrand().getPlant().getId());
		Brand b = service.getOneBrand(mko.getModelKey().getBrand().getPlant().getId(), mko.getModelKey().getBrand().getBrandMark());
		UserApl u = service.getOneUser(req.getUserPrincipal().getName().toUpperCase());
		PermissionPlant permPlant = service.getPermissionForPlant(u, p);
		PermissionBrand permBrand = service.getPermissionForBrand(u, p, b);
		if (permPlant.getRead())
			model.addAttribute("permPlantRead", true);
		if (permBrand.getRead())
			model.addAttribute("permBrandRead", true);

		List<Bom> bomka = service.getBom(idMkOrder);
		ExchangeRate ex = service.getOneExchangeRate(p.getId(), mko.getModelKey().getRok(), mko.getMesic());
		
		ExportXls exp = new ExportXls();
		exp.bomDetail(res, bomka,permPlant.getRead(),permBrand.getRead(), ex);
		
		model.addAttribute("mko", mko);
		
	}

	
/*
	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(Exception ex) throws IOException {
		log.error("###\t handleException(): " + ex.getLocalizedMessage());
		ModelAndView mv = new ModelAndView("/error");
		
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PrintWriter w = new PrintWriter(new OutputStreamWriter(os));
		
		ex.printStackTrace(w);
		w.flush();
		w.close();
		String s =  new String(os.toByteArray());
	//	log.debug("error: " + s);
		mv.addObject("exception", s);
		return mv;
	}
*/
}
