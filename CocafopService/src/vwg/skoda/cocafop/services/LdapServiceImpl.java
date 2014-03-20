package vwg.skoda.cocafop.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import vwg.skoda.cocafop.repositories.LdapRepository;
import vwg.skoda.ldapws.User;

public class LdapServiceImpl implements LdapService{

	private LdapRepository ldapRepository;
	
	static Logger log = Logger.getLogger(CocafopServiceImpl.class);
	
	@Required
	public void setLdapRepository(LdapRepository ldapRepository) {
		this.ldapRepository = ldapRepository;
	}
	
	@Override
	public List<String> getAllUsers() {
		List<String> gre = new ArrayList<String>();
		try {
			gre = ldapRepository.getAllUsers();
		} catch (Exception e) {
			log.error("###\t getAllUsers(): "+e);
		}
		return gre;
	}

	@Override
	public User getUser(String dzc) {
		log.debug("###\t getUser("+dzc+")");
		User gre = new User();
		try {
			gre = ldapRepository.getOneUserObject(dzc);
		} catch (Exception e) {
			log.error("###\t getUser("+dzc+"): "+e);
		}
		return gre;
	}

}
