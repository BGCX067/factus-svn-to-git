package ru.factus.dao.fetch;

import ru.factus.bo.fetch.Feed;
import ru.factus.bo.Fact;
import ru.factus.bo.RelationStore;
import ru.factus.bo.components.AbstractComponent;
import ru.factus.dao.GenericDAO;

import java.util.List;

/**
 * @author <a href="mailto:artifex.i@gmail.com">artifex</a>
 *         created: 16.04.2008 23:44:01
 */
public interface FactDAO extends GenericDAO<RelationStore> {
  List<RelationStore> search(String query);

  List<AbstractComponent> topFacts();
}