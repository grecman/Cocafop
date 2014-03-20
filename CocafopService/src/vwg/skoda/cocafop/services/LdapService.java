package vwg.skoda.cocafop.services;

import java.util.List;

import vwg.skoda.ldapws.User;

public interface LdapService {

	List<String> getAllUsers();
	User getUser(String dzc);
	
}
