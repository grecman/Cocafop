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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vwg.skoda.cocafop.entities.ExchangeRate;
import vwg.skoda.cocafop.entities.Plant;
import vwg.skoda.cocafop.services.CocafopService;

@Controller
@RequestMapping("/exchangeRates")
public class ExchangeRatesController {

	static Logger log = Logger.getLogger(CalculationsController.class);

	@Autowired
	private CocafopService service;

	@RequestMapping(value = "/exchangeRates")
	public String choosePlant(@ModelAttribute Plant plant, @ModelAttribute ExchangeRate exCh, Model model, HttpServletRequest req) {
		log.debug("###\t choosePlant(" + plant.getId() + ")");
		List<Plant> plantList = service.getPlants();
		model.addAttribute("plantList", plantList);
		return "/exchangeRates";
	}

	@RequestMapping(value = "/showRates")
	public String showRates(@RequestParam(required = false) String idPlant, @ModelAttribute Plant plant, @ModelAttribute ExchangeRate exCh, Model model, HttpServletRequest request) {
		log.debug("###\t showRates(" + idPlant + "-" + plant.getId() + ")");
		if (plant != null)
			plant.setId(plant.getId());
		if (idPlant != null)
			plant.setId(idPlant);

		choosePlant(plant, exCh, model, request);
		List<ExchangeRate> listEx = service.getExchangeRates(plant.getId());
		model.addAttribute("listOfExchangeRates", listEx);
		model.addAttribute("idPlant", plant.getId());
		return "/exchangeRates";
	}

	@RequestMapping(value = "/addRate/{idPlant}")
	public String addRate(@PathVariable String idPlant, @ModelAttribute Plant plant, @ModelAttribute ExchangeRate exCh, Model model, HttpServletRequest request) {
		log.debug("###\t addRate(" + idPlant + ", " + plant.getId() + ", " + exCh.getRok() + "-" + exCh.getMesic() + ")");
		Plant p = service.getOnePlant(idPlant);
		exCh.setPlant(p);
		exCh.setCurrency(exCh.getCurrency().toUpperCase());
		exCh.setUuser(request.getUserPrincipal().getName().toUpperCase());
		exCh.setUtime(new Date());
		service.addExchangeRate(exCh);
		return "redirect:/srv/exchangeRates/showRates?idPlant=" + idPlant;
	}

	@RequestMapping(value = "/removeExchangeRate/{id}")
	public String removeExchangeRate(@PathVariable Integer id, @ModelAttribute Plant plant, Model model) {
		log.debug("###\t removeExchangeRate(" + id +")");
		ExchangeRate exchangeRate = service.getOneExchangeRate(id);
		service.removeExchangeRate(id);
		return "redirect:/srv/exchangeRates/showRates?idPlant=" + exchangeRate.getPlant().getId();
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
