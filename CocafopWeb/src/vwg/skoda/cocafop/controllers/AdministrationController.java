package vwg.skoda.cocafop.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import vwg.skoda.cocafop.entities.Brand;
import vwg.skoda.cocafop.entities.PermissionBrand;
import vwg.skoda.cocafop.entities.PermissionPlant;
import vwg.skoda.cocafop.entities.Plant;
import vwg.skoda.cocafop.entities.UserApl;
import vwg.skoda.ldapws.User;
import vwg.skoda.cocafop.services.CocafopService;
import vwg.skoda.cocafop.services.LdapService;

@Controller
@RequestMapping("/administration")
public class AdministrationController {

	static Logger log = Logger.getLogger(AdministrationController.class);

	@Autowired
	private LdapService serviceLdap;

	@Autowired
	private CocafopService service;

	@RequestMapping(value = "/administration")
	public String chooseUser(@ModelAttribute UserApl userApl, Model model, HttpServletRequest request) {
		log.debug("###\t chooseUser(" + userApl.getNetUserName()+ ", "+ request  + ", " + model +")");
		
		// default USER pri prvnim spusteni zalozky ADMINISTRATIONs
		if (userApl.getNetUserName() == null) {
			userApl.setNetUserName(request.getUserPrincipal().getName().toUpperCase());
		}
		
		List<String> usersList = serviceLdap.getAllUsers();
		model.addAttribute("uzivatelList", usersList);
		return "/administration";
	}

	@RequestMapping(value = "/showUser")
	public String showUser(@ModelAttribute UserApl userApl, Model model, HttpServletRequest request) {
		log.debug("###\t showUser(" + userApl.getNetUserName() + ", "+ request + ", "+ model + ")");
		chooseUser(userApl, model, request);

		User u = serviceLdap.getUser(userApl.getNetUserName());
		log.debug("###\t ldapUser: " + u.getSurname() + " " + u.getName() + " " + u.getEmail());
		
		// Vytvoreni noveho DZCecka v UserApl
		if (service.getOneUser(userApl.getNetUserName()) == null) {
			log.debug("###\t Zalozeni noveho uctu UserApl: " + userApl.getNetUserName() + "   do GZ38T_USER");
			userApl.setUuser(request.getUserPrincipal().getName().toUpperCase());
			userApl.setUtime(new Date());
			service.addUser(userApl);
		}

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
			if (userApl.getNetUserName().equals(p.getUserApl().getNetUserName())) {
				opraveniZavodu.add(p.getPlant().getId());
			}
		}
		zavody.removeAll(opraveniZavodu);

		// Vytvoreni opravneni (PermissionPlant)
		if (zavody.size() > 0) {
			log.debug("###\t Zavody pro které se budou vytvaret opravneni (PermissionPlant): " + zavody + "\t pro uzivatele: " + userApl.getNetUserName());
			for (String g : zavody) {
				log.debug("###\t Vytroreni PermissionPlant pro ... Plant: " + g + "\t a User: " + userApl.getNetUserName());
				Plant pl = new Plant();
				pl = service.getOnePlant(g);
				PermissionPlant perPlant = new PermissionPlant();
				perPlant.setUserApl(userApl);
				perPlant.setPlant(pl);
				perPlant.setRead(false);
				perPlant.setWrite(false);
				perPlant.setApprove(false);
				perPlant.setUuser(request.getUserPrincipal().getName().toUpperCase());
				perPlant.setUtime(new Date());
				service.addPermissionPlant(perPlant);
			}
		}

		// beru zavody z PermissionPlant protoze vyse jsem zajistil to, ze pro vsechny zadane zavody vytvorim prave PermissionPlant, takze se Plant a PermissionPlant rovnaji (z pohledu zavodu)!
		List<PermissionPlant> permissionPlant = service.getPermissionPlant(userApl.getNetUserName());
		model.addAttribute("listOfPermissionPlant", permissionPlant);
		model.addAttribute("ldapUser", u);

		return "/administration";
	}

	@RequestMapping(value = "/showBrands/{idPlant}/{user}")
	public String showBrands(@PathVariable String idPlant, @PathVariable String user, @ModelAttribute UserApl userApl, Model model, HttpServletRequest request) {
		log.debug("###\t showBrands(" + idPlant + ", " + user + ")");
		userApl = service.getOneUser(user);
		showUser(userApl, model, request);

		// Ziskani seznamu znacek pro dany zavod) pro které se budou vytvaret opravneni, protoze jeste neexistuji
		// tzn: existuje znacka, ale neexistuje pro daneho uzivatele zadny zaznam v PermissionBrand 
		List<Brand> brandsListPrac = service.getBrands(idPlant);
		List<Integer> znacky = new ArrayList<Integer>();
		for (Brand p : brandsListPrac) {
			znacky.add(p.getId());
		}
		List<PermissionBrand> permBrands = service.getPermissionBrand(user);
		List<Integer> oprZnacek = new ArrayList<Integer>();
		for (PermissionBrand p : permBrands) {
			if (user.equals(p.getUserApl().getNetUserName())) {
				oprZnacek.add(p.getBrand().getId());
			}
		}
		znacky.removeAll(oprZnacek);

		// Vytvoreni opravneni (PermissionBrand)
		if (znacky.size() > 0) {
			log.debug("###\t Znacky pro které se budou vytvaret opravneni (PermissionBrand): " + znacky + "\t pro uzivatele: " + user);
			for (Integer g : znacky) {
				log.debug("###\t Vytroreni PermissionBrand pro ... Brand: " + g + "\t a User: " + user);
				Brand pl = new Brand();
				pl = service.getOneBrand(g);
				PermissionBrand perBrand = new PermissionBrand();
				perBrand.setUserApl(userApl);
				perBrand.setBrand(pl);
				perBrand.setRead(false);
				perBrand.setWrite(false);
				perBrand.setApprove(false);
				perBrand.setUuser(request.getUserPrincipal().getName().toUpperCase());
				perBrand.setUtime(new Date());
				service.addPermissionBrand(perBrand);
			}
		}

		Plant pl = service.getOnePlant(idPlant);
		PermissionPlant ppl = service.getPermissionForPlant(userApl, pl);
		model.addAttribute("onePermPlant", ppl);
		List<PermissionBrand> list = service.getPermissionBrand(user, idPlant);
		model.addAttribute("listOfPermissionBrand", list);
		model.addAttribute("selectedUser", user); // pro naplneni spravneho usera do SELECT:BOXu
		return "/administration";
	}

	@RequestMapping(value = "/showPermission/{idPermPlant}/{idPermBrand}")
	public String showPermission(@PathVariable Integer idPermPlant, @PathVariable Integer idPermBrand, @ModelAttribute PermissionPlant permissionPlant,
			@ModelAttribute PermissionBrand permissionBrands, Model model, HttpServletRequest request) {
		log.debug("###\t showPermission(" + idPermPlant + ", " + idPermBrand + ", " + permissionPlant + ", " + permissionBrands + ")");
		PermissionPlant permP = service.getOnePermissionPlant(idPermPlant);
		PermissionBrand permB = service.getOnePermissionBrand(idPermBrand);
		model.addAttribute("onePermP", permP);
		model.addAttribute("onePermB", permB);
		return "/showPermission";
	}

	@RequestMapping(value = "/setPermissionPlant/{idPermPlant}/{idPermBrand}")
	public String setPermissionPlant(@PathVariable Integer idPermPlant, @PathVariable Integer idPermBrand, @ModelAttribute PermissionPlant permissionPlant,
			@ModelAttribute PermissionBrand permissionBrands, Model model, HttpServletRequest request) {
		log.debug("###\t setPermissionPlant(" + idPermPlant + ", " + idPermBrand + ", " + permissionPlant + ", " + permissionBrands.getId() + ")");
		log.debug("###\t\t read:" + permissionPlant.getRead() + "\t write: "+ permissionPlant.getWrite() + "\t approve: "+ permissionPlant.getApprove());
		PermissionPlant permP = service.getOnePermissionPlant(idPermPlant);
		PermissionBrand permB = service.getOnePermissionBrand(idPermBrand);
		model.addAttribute("onePermP", permP);
		model.addAttribute("onePermB", permB);

		// nelze byt mit WRITE bez READ, bez ohledu na zadani
		if(permissionPlant.getWrite())
			permissionPlant.setRead(true);
		
		// nelze byt mit APPROVE bez READ, bez ohledu na zadani
		if(permissionPlant.getApprove())
			permissionPlant.setRead(true);
		
		permissionPlant.setId(permP.getId());
		permissionPlant.setUserApl(permP.getUserApl());
		permissionPlant.setPlant(permP.getPlant());
		permissionPlant.setUuser(request.getUserPrincipal().getName().toUpperCase());
		permissionPlant.setUtime(new Date());
		service.setPermissionPlant(permissionPlant);

		Boolean info_o_WRITE = permissionPlant.getWrite();
		model.addAttribute("infoWrite", info_o_WRITE);
		String commitPermissionPlant = "jo";
		model.addAttribute("commitPermissionPlant", commitPermissionPlant);
		return "/showPermission";
	}

	@RequestMapping(value = "/setPermissionBrand/{idPermPlant}/{idPermBrand}")
	public String setPermissionBrand(@PathVariable Integer idPermPlant, @PathVariable Integer idPermBrand, @ModelAttribute PermissionPlant permissionPlant,
			@ModelAttribute PermissionBrand permissionBrand, @ModelAttribute UserApl userApl, Model model, HttpServletRequest request) {
		log.debug("###\t setPermissionBrand(" + idPermPlant + ", " + idPermBrand + ", " + permissionPlant + ", " + permissionBrand + ")");
		log.debug("###\t\t read:" + permissionBrand.getRead() + "\t write: "+ permissionBrand.getWrite() + "\t approve: "+ permissionBrand.getApprove());

		PermissionPlant permP = service.getOnePermissionPlant(idPermPlant);
		PermissionBrand permB = service.getOnePermissionBrand(idPermBrand);
		
		permissionBrand.setId(permB.getId());
		permissionBrand.setUserApl(permB.getUserApl());
		permissionBrand.setBrand(permB.getBrand());
		permissionBrand.setUuser(request.getUserPrincipal().getName().toUpperCase());
		permissionBrand.setUtime(new Date());

		// nelze byt mit WRITE bez READ, bez ohledu na zadani
		if(permissionBrand.getWrite())
			permissionBrand.setRead(true);
		
		// nelze byt mit APPROVE bez READ, bez ohledu na zadani
		if(permissionBrand.getApprove())
			permissionBrand.setRead(true);
		
		service.setPermissionBrand(permissionBrand);
		
		// zabranuje moznosti aby jeden uzivatel mohl mit opravneni WRITE jak na PLANT tak na BRAND
		// pri takoveto volbe bude mit vzdy jen na PLANT !!!
		if (permP.getWrite()){
			List<PermissionBrand> brp = service.getPermissionBrand(permB.getUserApl().getNetUserName(), permB.getBrand().getPlant().getId());
			for (PermissionBrand pb : brp){
				pb.setWrite(false);
				service.setPermissionBrand(pb);
			}
		}

		String idPlant = permP.getPlant().getId();
		String user = permP.getUserApl().getNetUserName();
		showBrands(idPlant, user, userApl, model, request);

		model.addAttribute("onePermP", permP);
		model.addAttribute("onePermB", permB);
		return "/administration";
	}

}
