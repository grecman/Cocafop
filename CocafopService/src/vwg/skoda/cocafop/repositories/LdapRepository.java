package vwg.skoda.cocafop.repositories;

import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.ObjectInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.naming.InitialContext;

import vwg.skoda.ldapws.User;

import org.apache.log4j.Logger;

public class LdapRepository {

	static Logger log = Logger.getLogger(LdapRepository.class);

	// properta uvedena v context.xml na serveru
	public static final String URL_LDAP = "java:comp/env/vwg.skoda.cocafop.ldapUrl";

	public List<String> getAllUsers() throws Exception {

		log.debug("\t###\t getAllUsers()");
		try {
			URL s = (URL) new InitialContext().lookup(URL_LDAP);
			log.debug("\t###\t Zakladni LDAP URL: " + s);
			URL url = new URL(s, "app/cocafop/users");
			//URL url = URI.create("https://testeportal.skoda.vwg/wasssl/LdapWs/srv/app/cocafop/users").toURL();
			log.debug("\t###\t Kompletni LDAP URL: " + url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			LineNumberReader rr = new LineNumberReader(new InputStreamReader(conn.getInputStream()));
			String n;
			List<String> o = new ArrayList<String>();
			while ((n = rr.readLine()) != null) {
				if ((n = n.trim()).length() > 0)
					o.add(n);
			}
			log.debug("\t###\t getAllUsers() ... found : " + o.size());
			rr.close();
			conn.disconnect();
			return o;
		} catch (Exception e) {
			log.debug("\t###\t getAllUsers(): ", e);
			//System.out.println("###\t Unable read config: " + e);
		}
		return null;

	}

	public HashMap<String, String> getOneUser(String netUserName) throws Exception {

		log.debug("\t###\t getOneUser(" + netUserName + ")");
		try {
			URL s = (URL) new InitialContext().lookup(URL_LDAP);
			log.debug("\t###\t Zakladni LDAP URL: " + s);
			URL url = new URL(s, "user/"+ netUserName);
			// URL url = URI.create("https://testeportal.skoda.vwg/wasssl/LdapWs/srv/user/" + netUserName).toURL(); /* TEST */
			// URL url = URI.create("https://eportal.skoda.vwg/wasssl/LdapWs2/srv/user/"+userId).toURL();  /* PRODUKCE */
			log.debug("\t###\t Komplet LDAP URL: " + url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			LineNumberReader rr = new LineNumberReader(new InputStreamReader(conn.getInputStream()));
			String n;
			HashMap<String, String> hm = new HashMap<String, String>();
			while ((n = rr.readLine()) != null) {
				if ((n = n.trim()).length() > 0) {
					hm.put(n.substring(0, n.indexOf(':')), (n.substring(n.indexOf(':') + 1, n.length()))); // parsovani dle vzoru "key:value" 
				}
			}
			rr.close();
			conn.disconnect();
			return hm;
		} catch (Exception e) {
			log.debug("\t###\t Exception: getAllUsers() - ", e);
		}
		return null;
	}

	public User getOneUserObject(String netUserName) throws Exception {
		log.debug("\t###\t getOneUserObject(" + netUserName + ")");
		try {
			URL s = (URL) new InitialContext().lookup(URL_LDAP);
			log.debug("\t###\t Zakladni LDAP URL: " + s);
			URL url = new URL(s, "user/"+ netUserName + "?type=java");
			//URL url = URI.create("https://testeportal.skoda.vwg/wasssl/LdapWs/srv/user/" + netUserName + "?type=java").toURL(); /* TEST */
			// URL url = URI.create("https://eportal.skoda.vwg/wasssl/LdapWs2/srv/user/"+userId).toURL();  /* PRODUKCE */
			//URL url = URI.create("https://esb01.skoda.vwg/wasssl/LdapWs2/srv/user/" + netUserName + "?type=java").toURL(); // nefunguje !!!
			log.debug("\t###\t Kompletni LDAP URL: " + url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			ObjectInputStream ois = new ObjectInputStream(conn.getInputStream());
			User u = (User) ois.readObject();
			conn.disconnect();
			return u;
		} catch (Exception e) {
			log.debug("\t###\t Exception: getOneUserObject() - ", e);
		}
		return null;
	}

	public static void main(String[] args) throws Exception {

		LdapRepository lr = new LdapRepository();

		//System.out.println(lr.getAllUsers());

		System.out.println(lr.getOneUserObject("dzc0grp").getId() + " " + lr.getOneUserObject("dzc0grp").getName() + " " + lr.getOneUserObject("dzc0grp").getSurname() + " "
				+ lr.getOneUserObject("dzc0grp").getPhone());

		/*
		  HashMap<String, String> hm = lr.getOneUser("DZC0GRP"); 
		  Set set = hm.entrySet(); 
		  Iterator i = set.iterator(); 
		  while (i.hasNext()) {
		  	Map.Entry me = (Map.Entry) i.next(); 
		  	System.out.println(me.getKey() + ": " + me.getValue()); 
		  }
		 */
	}
}
