package vwg.skoda.cocafop.repositories;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import vwg.skoda.cocafop.entities.Protokol;

public class ProtokolRepositoryJpa implements ProtokolRepository{
	static Logger log = Logger.getLogger(ProtokolRepositoryJpa.class);

	@PersistenceContext(name = "CocafopService")
	private EntityManager entityManager;

	@Override
	public void setProtokol(Protokol protokol) {
		log.debug("\t###\t setProtokol(" + protokol + ")");
		protokol = entityManager.merge(protokol);
	}
	
	@Override
	public void addProtokol(Protokol protokol) {
		log.debug("\t###\t addProtokol(" + protokol.getNetUserName()+" - "+protokol.getAction() + ")");
		try {
			entityManager.persist(protokol);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	public void init() {
	}
	
}
