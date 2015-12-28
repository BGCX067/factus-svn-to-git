package ru.factus.services;

import ru.factus.bo.Fact;
import ru.factus.bo.RelationStore;
import ru.factus.bo.fetch.FetchedData;
import ru.factus.bo.components.AbstractComponent;
import ru.factus.AbstractEntity;

import java.util.List;

/**
 * @author <a href="mailto:ziman200@gmail.com">freeman</a>
 *         created 19.05.2008 17:58:58
 */
public interface FactService extends GenericService {
  List<RelationStore> search(String query);

  List<AbstractComponent> topFacts();
  
  void rebuildIndex() throws Exception;

}
