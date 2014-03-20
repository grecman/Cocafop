package vwg.skoda.cocafop.controllers;


import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import vwg.skoda.cocafop.entities.Protokol;

import vwg.skoda.cocafop.services.CocafopService;

@Controller
@RequestMapping("/index")
public class IndexController {
	
	static Logger log = Logger.getLogger(ModelsController.class);

	@Autowired
	private CocafopService service;
	
	
	@RequestMapping("/index")
	public String showIndex(HttpSession session, HttpServletRequest req) {
		log.debug("###\t showIndex");
		
//		Timto zpusobem mohu pro celou sessions aplikace naplnit promennou "userInfo" pak v kteremkoliv JSP mohu vypsat hodnotu jako ${userInfo}
//		session.setAttribute("userInfo", new Date());
		
//		Timto zpusobem zaprotokolujeme do protokolacni tabulky kazdy pristup do tohoto controlleru. Provede se s kazdym requestem
		Protokol p = new Protokol(); 
		p.setNetUserName(req.getUserPrincipal().getName().toUpperCase());
		p.setTime(new Date());
		p.setAction("Home page");
		p.setInfo("/index");
		p.setSessionId(session.getId());
		service.addProtokol(p);
	
		return "/index";
	}
}
