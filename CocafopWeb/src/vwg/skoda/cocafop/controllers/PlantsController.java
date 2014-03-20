package vwg.skoda.cocafop.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
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
import org.springframework.web.servlet.ModelAndView;

import vwg.skoda.cocafop.entities.Brand;
import vwg.skoda.cocafop.entities.Plant;
import vwg.skoda.cocafop.services.CocafopService;

@Controller
@RequestMapping("/plants")
public class PlantsController {

	static Logger log = Logger.getLogger(CalculationsController.class);
	
	@Autowired
	private CocafopService service;

	@RequestMapping("/plants")
	public String showPlants(@ModelAttribute Plant plant, @ModelAttribute Brand brand, Model model){
		log.debug("###\t showPlants("+plant+")");
		List<Plant> list = service.getPlants();
		log.debug("###\t showPlants() - found: "+list.size());
		model.addAttribute("listOfPlants", list);	
		return "/plants";	
	}
	
	@RequestMapping(value="/addPlant")
	public String addPlant(@ModelAttribute Plant plant, HttpServletRequest request){
		log.debug("###\t addPlant("+plant+")");
		plant.setUuser(request.getUserPrincipal().getName().toUpperCase());
		plant.setUtime(new Date());
		service.addPlant(plant);
		return "redirect:/srv/plants";
	}
	
	@RequestMapping(value="/removePlant/{id}")
	public String removePlant(@PathVariable String id){
		log.debug("###\t removePlant("+id+")");
		service.removePlant(id);
		return "redirect:/srv/plants";
	}

	
	@RequestMapping(value="/showBrands/{id}")
	public String showBrands(@PathVariable String id, @ModelAttribute Plant plant, @ModelAttribute Brand brand, Model model){
		log.debug("###\t showBrands("+ id + ", " + plant + ", " + brand + ", "+ model + ")");
		showPlants(plant, brand, model);
		List<Brand> list = service.getBrands(id);
		log.debug("###\t showBrands() - found: "+list.size());
		model.addAttribute("listOfBrands", list);	
		model.addAttribute("idPlant", id);
		return "/plants";
	}
	
	
	@RequestMapping(value="/addBrand/{id}")
	public String addBrand(@PathVariable String id, @ModelAttribute Brand brand, Model model, HttpServletRequest request){
		log.debug("###\t addBrand("+id+", "+brand.getBrandMark()+")");
		Plant p = service.getOnePlant(id);
		brand.setPlant(p);
		brand.setBrandMark(brand.getBrandMark().toUpperCase());
		brand.setUuser(request.getUserPrincipal().getName().toUpperCase());
		brand.setUtime(new Date());
		service.addBrand(brand);
		showBrands(id, p, brand, model);
		return "redirect:/srv/plants/showBrands/"+id;
	}
	/*
	@RequestMapping(value="/addBrand/{id}")
	public String addBrand(@PathVariable String id, @ModelAttribute Plant plant, @ModelAttribute Brand brand, Model model, HttpServletRequest request){
		log.debug("###\t addBrand("+id+", "+plant.getId()+", "+brand.getBrandMark()+")");
		Plant p = service.getOnePlant(id);
		brand.setPlant(p);
		brand.setBrandMark(brand.getBrandMark().toUpperCase());
		brand.setUuser(request.getUserPrincipal().getName().toUpperCase());
		brand.setUtime(new Date());
		service.addBrand(brand);
		showBrands(id, plant, brand, model);
		return "redirect:/srv/plants/showBrands/"+id;
	}
	*/
	
	@RequestMapping(value="/removeBrand/{id}")
	public String removeBrand(@PathVariable Integer id, @ModelAttribute Plant plant, @ModelAttribute Brand brand, Model model){
		log.debug("###\t removeBrand("+id+", "+ plant+", "+brand+", "+model+")");
		String i = service.getOneBrand(id).getPlant().getId();
		service.removeBrand(id);
		//showPlants(plant, brand, model);
		return "redirect:/srv/plants/showBrands/"+i;
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
		String s =  new String(os.toByteArray());
	//	log.debug("error: " + s);
		mv.addObject("exception", s);
		return mv;
	}
	
}
