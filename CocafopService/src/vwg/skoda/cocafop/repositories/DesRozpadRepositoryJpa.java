package vwg.skoda.cocafop.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import vwg.skoda.cocafop.entities.DesRozpad;

public class DesRozpadRepositoryJpa implements DesRozpadRepository{
	
	static Logger log = Logger.getLogger(DesRozpadRepositoryJpa.class);

	@PersistenceContext(name = "CocafopService")
	private EntityManager entityManager;

	@Override
	public List<DesRozpad> getDesRozpad(String kifa) {
		log.debug("\t###\t getDesRozpad("+kifa+")");
		return entityManager.createQuery("SELECT b FROM DesRozpad b WHERE b.kifa=:kifa", DesRozpad.class).setParameter("kifa", kifa).getResultList();
	}

}
