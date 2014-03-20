package vwg.skoda.cocafop.aplikace;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class Main {

	public static void main(String[] args) throws Exception {
		System.out.println("Main...");
		
		EntityManager em  = Persistence.createEntityManagerFactory("CocafopService").createEntityManager();
		System.out.println("EntityManager: " + em);
	}

}
