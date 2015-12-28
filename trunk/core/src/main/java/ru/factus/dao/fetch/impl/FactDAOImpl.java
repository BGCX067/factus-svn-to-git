package ru.factus.dao.fetch.impl;

import ru.factus.bo.Fact;
import ru.factus.bo.RelationStore;
import ru.factus.bo.components.AbstractComponent;
import ru.factus.dao.GenericDAOImpl;
import ru.factus.dao.fetch.FactDAO;

import java.util.List;
import java.util.Collections;
import java.util.Comparator;

import org.hibernate.Session;
import org.hibernate.Query;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:artifex.i@gmail.com">artifex</a>
 *         created: 16.04.2008 23:44:23
 */
public class FactDAOImpl extends GenericDAOImpl<RelationStore> implements FactDAO {
  public List<RelationStore> search(String queryString) {
    final Session hb = getSession();

    final Query query = hb.createQuery("FROM RelationStore WHERE person like ? OR post like ? OR org.name like ?");
    query.setString(0, "%"+queryString+"%");
    query.setString(1, "%"+queryString+"%");
    query.setString(2, "%"+queryString+"%");

    return query.list();
  }

  public List<AbstractComponent> topFacts() {
    final Session session = getSession();

    final Query query = session.createQuery("FROM AbstractComponent c ORDER BY c.class, c.relationCount");
    final List<AbstractComponent> list = query.list();

    /*Collections.sort(list, new Comparator<AbstractComponent>() {
      public int compare(AbstractComponent o1, AbstractComponent o2) {
        int val1 = o1.getRelationCount();
        int val2 = o2.getRelationCount();

        return o1.getClass().getName().compareTo(o2.getClass().getName()) && (val1<val2 ? -1 : (val1==val2 ? 0 : 1));
      }
    });*/

    return list;
  }
}