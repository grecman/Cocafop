package vwg.skoda.cocafop.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController {

	static Logger log = Logger.getLogger(HelpController.class);
	
	@RequestMapping("/error")
	public String showError() {
		log.debug("###\t showError()");
		return "/error";
	}
	
}
