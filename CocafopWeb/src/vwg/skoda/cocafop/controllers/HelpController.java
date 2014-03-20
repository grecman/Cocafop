package vwg.skoda.cocafop.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/help")
public class HelpController {

	static Logger log = Logger.getLogger(HelpController.class);
	
	@RequestMapping("/help")
	public String showHelp() {
		log.debug("###\t showHelp()");
		return "/help";
	}

}
