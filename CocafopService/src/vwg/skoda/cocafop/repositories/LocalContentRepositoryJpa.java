package vwg.skoda.cocafop.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import vwg.skoda.cocafop.entities.LocalContent;

public class LocalContentRepositoryJpa implements LocalContentRepository {
	
	static Logger log = Logger.getLogger(LocalContentRepositoryJpa.class);

	@PersistenceContext(name = "CocafopService")
	private EntityManager entityManager;

	@Override
	public LocalContent getOneLocalContent(Integer id) {
		log.debug("\t###\t getOneLocalContent("+id+")");
		return entityManager.find(LocalContent.class, id);
	}

	@Override
	public List<LocalContent> getLocalContent(Integer idModelKey) {
		log.debug("\t###\t getOneLocalContent("+idModelKey+")");
		return entityManager.createQuery("SELECT b FROM LocalContent b WHERE b.modelKey.id=:id ORDER BY b.partNumber", LocalContent.class).setParameter("id", idModelKey).getResultList();
	}

	@Override
	public void addLocalContent(LocalContent localContent) {
		log.debug("\t###\t addLocalContent("+ localContent.getPartNumber() + ")");
		entityManager.persist(localContent);
	}

	@Override
	public void removeLocalContent(LocalContent localContent) {
		log.debug("\t###\t removeLocalContent("+localContent.getPartNumber()+" - "+localContent.getPartDescription()+")");
		LocalContent neco = getOneLocalContent(localContent.getId());
		entityManager.remove(neco);
	}

}
