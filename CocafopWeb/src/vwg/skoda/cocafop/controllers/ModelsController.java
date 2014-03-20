package vwg.skoda.cocafop.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vwg.skoda.cocafop.entities.Brand;
import vwg.skoda.cocafop.entities.LocalContent;
import vwg.skoda.cocafop.entities.ModelKey;
import vwg.skoda.cocafop.entities.PermissionBrand;
import vwg.skoda.cocafop.entities.PermissionPlant;
import vwg.skoda.cocafop.entities.Plant;
import vwg.skoda.cocafop.entities.UserApl;
import vwg.skoda.cocafop.objects.ModelFilter;
import vwg.skoda.cocafop.services.CocafopService;

@Controller
@RequestMapping("/models")
public class ModelsController {

	static Logger log = Logger.getLogger(ModelsController.class);

	@Autowired
	private CocafopService service;

	@RequestMapping(value = "/models")
	public String choosePlant(@ModelAttribute ModelFilter modelFilter, @ModelAttribute ModelKey modelKey, Model model, HttpServletRequest req) {
		log.debug("###\t choosePlant(" + req.getUserPrincipal().getName().toUpperCase() + ")");
		
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
		return "/models";
	}

	@RequestMapping(value = "/chooseBrand")
	public String chooseBrand(@ModelAttribute ModelFilter modelFilter, @ModelAttribute ModelKey modelKey, Model model, HttpServletRequest req) {
		log.debug("###\t chooseBrand(" + modelFilter.getIdPlant() + "-" + modelFilter.getBrandMark() + ")");
		choosePlant(modelFilter, modelKey, model, req);
		//----------------------------------------------------------------------------------------------------------
		// Ziskani seznamu znacek pro dany zavod) pro které se budou vytvaret opravneni, protoze jeste neexistuji
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
		return "/models";
	}

	@RequestMapping(value = "/chooseYear/{plantId}")
	public String chooseYear(@PathVariable String plantId, @ModelAttribute ModelFilter modelFilter, @ModelAttribute ModelKey modelKey, Model model, HttpServletRequest req) {
		//Plant p = service.getOnePlant(plantId);
		chooseBrand(modelFilter, modelKey, model, req);
		log.debug("###\t chooseYear(" + plantId + ", " + modelFilter.getBrandMark() + ")");

		Brand b = service.getOneBrand(plantId, modelFilter.getBrandMark());
		List<Integer> roky = service.getRok(plantId, b.getId());
		if (roky.size() == 0) {
			Date date = new Date();
			final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy");
			Integer rok = Integer.valueOf(DATE_FORMAT.format(date));
			roky.add(0, rok);
		} else {
			roky.add(roky.size(), roky.get(0) + 1);
		}
		log.debug("###\t combo box - YEAR:   " + roky);

		modelFilter.setIdPlant(plantId);
		model.addAttribute("rokyList", roky);
		model.addAttribute("plantId", plantId);
		model.addAttribute("brandMark", modelFilter.getBrandMark());
		return "/models";
	}

	@RequestMapping(value = "/showModels/{plantId}/{brandMark}")
	public String showModels(@PathVariable String plantId, @PathVariable String brandMark, @RequestParam(required = false) Integer year, @ModelAttribute ModelFilter modelFilter,
			@ModelAttribute ModelKey modelKey, Model model, HttpServletRequest req) {
		log.debug("###\t showModels(" + plantId + ", " + brandMark + ", " + modelFilter.getYear() + ")");
		Plant p = service.getOnePlant(plantId);
		Brand b = service.getOneBrand(plantId, brandMark);
		UserApl u = service.getOneUser(req.getUserPrincipal().getName().toUpperCase());
		PermissionPlant permPlant = service.getPermissionForPlant(u, p);
		PermissionBrand permBrand = service.getPermissionForBrand(u, p, b);
		if (permPlant.getWrite()) {
			model.addAttribute("permWrite", "L");
		} else if (permBrand.getWrite()) {
			model.addAttribute("permWrite", "G");
		}
		List<ModelKey> mk = service.getModelKey(plantId, b.getId(), modelFilter.getYear());

		modelFilter.setIdPlant(plantId);
		modelFilter.setBrandMark(brandMark);
		if (year != null)
			modelFilter.setYear(year);

		model.addAttribute("listOfModels", mk);
		model.addAttribute("plantId", plantId);
		model.addAttribute("brandMark", brandMark);
		model.addAttribute("rok", modelFilter.getYear());
		chooseYear(plantId, modelFilter, modelKey, model, req);
		return "/models";
	}

	@RequestMapping(value = "/addModel/{plantId}/{brandMark}/{permWrite}/{rok}")
	public String addModel(@PathVariable String plantId, @PathVariable String brandMark, @PathVariable String permWrite, @PathVariable Integer rok, @ModelAttribute ModelFilter modelFilter,
			@ModelAttribute ModelKey modelKey, Model model, HttpServletRequest req) {
		log.debug("###\t addModel(" + plantId + ", " + brandMark + ", " + rok + ", " + permWrite + ", " + modelKey + ")");
		Brand b = service.getOneBrand(plantId, brandMark);

		modelFilter.setYear(rok);

		modelKey.setBrand(b);
		modelKey.setTyp(permWrite);
		modelKey.setUuser(req.getUserPrincipal().getName().toUpperCase());
		modelKey.setUtime(new Date());
		modelKey.setRok(rok);
		modelKey.setValidFromYear(rok);
		modelKey.setValidToYear(rok);

		modelKey.setModelKey(modelKey.getModelKey().toUpperCase());
		modelKey.setColour(modelKey.getColour().toUpperCase());
		modelKey.setOptions(modelKey.getOptions().toUpperCase());

		service.addModelKey(modelKey);
		return "redirect:/srv/models/showModels/" + plantId + "/" + brandMark + "?year=" + rok;
	}

	@RequestMapping(value = "/removeModel/{id}/{rok}")
	public String removeModel(@PathVariable Integer id, @PathVariable Integer rok, @ModelAttribute ModelFilter modelFilter, @ModelAttribute ModelKey modelKey, Model model,
			HttpServletRequest req) {
		log.debug("###\t removeModel(" + id + ")");
		ModelKey mk = service.getOneModelKey(id);
		service.removeModelKey(id);
		return "redirect:/srv/models/showModels/" + mk.getBrand().getPlant().getId() + "/" + mk.getBrand().getBrandMark() + "?year=" + rok;
	}

	@RequestMapping(value = "/showModelKey/{id}")
	public String showModelKey(@PathVariable Integer id, @ModelAttribute ModelKey modelKey, Model model, HttpServletRequest req) {
		log.debug("###\t showModelKey(" + id + ")");
		ModelKey mk = service.getOneModelKey(id);
		model.addAttribute("oneMk", mk);
		return "/showModelKey";
	}

	@RequestMapping(value = "/setModelKey/{id}")
	public String setModelKey(@PathVariable Integer id, @ModelAttribute ModelKey modelKey, HttpServletRequest req) {
		log.debug("###\t setModelKey(" + id + ")");
		ModelKey mk = service.getOneModelKey(id);
		modelKey.setId(mk.getId());
		modelKey.setRok(mk.getRok());
		modelKey.setModelNumber(mk.getModelNumber());
		modelKey.setTyp(mk.getTyp());
		modelKey.setModelKey(mk.getModelKey());
		
		if (modelKey.getValidFromYear() == null)
			modelKey.setValidFromYear(mk.getValidFromYear());
		if (modelKey.getValidFromMonth() == null)
			modelKey.setValidFromMonth(mk.getValidFromMonth());
		if (modelKey.getValidToYear() == null)
			modelKey.setValidToYear(mk.getValidToYear());
		if (modelKey.getValidToMonth() == null)
			modelKey.setValidToMonth(mk.getValidToMonth());
		
		if (modelKey.getOptions() == null) {
			modelKey.setOptions(mk.getOptions());
		} else {
			modelKey.setOptions(modelKey.getOptions().toUpperCase().trim());
		}
		
		if (modelKey.getCommentUser() == null) {
			modelKey.setCommentUser(mk.getCommentUser());
		} else {
			modelKey.setCommentUser(modelKey.getCommentUser());
		}
		
		if (modelKey.getColour() == null || modelKey.getColour().isEmpty()) {
			modelKey.setColour(mk.getColour());
		} else {
			modelKey.setColour(modelKey.getColour().toUpperCase().trim());
		}
		

		//modelKey.setColour(mk.getColour());
		//modelKey.setCommentUser(mk.getCommentUser());
		modelKey.setUuser(req.getUserPrincipal().getName().toUpperCase());

		modelKey.setUtime(new Date());
		modelKey.setBrand(mk.getBrand());
		service.setModelKey(modelKey);

		return "redirect:/srv/models/showModels/" + mk.getBrand().getPlant().getId() + "/" + mk.getBrand().getBrandMark() + "?year=" + mk.getRok();
	}

	@RequestMapping(value = "/showLocalContent/{idModelKey}")
	public String showLocalContent(@PathVariable Integer idModelKey, @ModelAttribute ModelKey modelKey, @ModelAttribute LocalContent localContent, Model model, HttpServletRequest req) {
		log.debug("###\t showLocalContent(" + idModelKey + ")");
		ModelKey oneMk = service.getOneModelKey(idModelKey);
		List<LocalContent> lc = service.getLocalContent(idModelKey);

		Plant p = service.getOnePlant(oneMk.getBrand().getPlant().getId());
		Brand b = service.getOneBrand(oneMk.getBrand().getId());
		UserApl u = service.getOneUser(req.getUserPrincipal().getName().toUpperCase());
		PermissionPlant permPlant = service.getPermissionForPlant(u, p);
		PermissionBrand permBrand = service.getPermissionForBrand(u, p, b);
		if (permPlant.getWrite()) {
			model.addAttribute("permWrite", "L");
		} else if (permBrand.getWrite()) {
			model.addAttribute("permWrite", "G");
		}

		model.addAttribute("mk", oneMk);
		model.addAttribute("listOfLocalContent", lc);
		return "/localContent";
	}

	@RequestMapping(value = "/addLocalContent/{idModelKey}")
	public String addLocalContent(@PathVariable Integer idModelKey, @ModelAttribute ModelFilter modelFilter, @ModelAttribute LocalContent localContent, @ModelAttribute ModelKey modelKey,
			Model model, HttpServletRequest req) {
		ModelKey mk = service.getOneModelKey(idModelKey);
		log.debug("###\t addLocalContent(" + localContent.getPartNumber() + ", " + mk.getModelKey() + ")");
		
		localContent.setPartNumber(localContent.getPartNumber().toUpperCase());
		localContent.setPartDescription(localContent.getPartDescription().toUpperCase());
		localContent.setTyp("L");
		localContent.setQuantity(localContent.getQuantity() == null ? 0f : localContent.getQuantity());
		localContent.setUuser(req.getUserPrincipal().getName().toUpperCase());
		localContent.setUtime(new Date());
		localContent.setModelKey(mk);

		service.addLocalContent(localContent);

		//modelFilter.setYear(localContent.getModelKey().getRok());
		return "redirect:/srv/models/showLocalContent/" + idModelKey;
	}

	@RequestMapping(value = "/removeLocalContent/{id}")
	public String removeLocalContent(@PathVariable Integer id, @ModelAttribute ModelFilter modelFilter, @ModelAttribute ModelKey modelKey, @ModelAttribute LocalContent localContent,
			Model model, HttpServletRequest req) {
		log.debug("###\t removeLocalContent(" + id + ")");

		LocalContent lc = service.getOneLocalContent(id);
		service.removeLocalContent(lc);
		return "redirect:/srv/models/showLocalContent/" + lc.getModelKey().getId();
	}

	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(Exception ex) throws IOException {
		log.debug("###\t handleException(): " + ex.getLocalizedMessage());
		ModelAndView mv = new ModelAndView("/error");

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PrintWriter w = new PrintWriter(new OutputStreamWriter(os));

		ex.printStackTrace(w);
		w.flush();
		w.close();
		String s = new String(os.toByteArray());
		//	log.debug("error: " + s);
		mv.addObject("exception", s);
		return mv;
	}
}
