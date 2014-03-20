package vwg.skoda.cocafop.repositories;

import java.util.List;

import vwg.skoda.cocafop.entities.LocalContent;

public interface LocalContentRepository {
	LocalContent getOneLocalContent(Integer id);
	List<LocalContent> getLocalContent(Integer idModelKey);
	void addLocalContent(LocalContent localContent);
	void removeLocalContent(LocalContent localContent);

}
