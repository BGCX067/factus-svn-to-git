package ru.factus.dao;

import java.util.IdentityHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;

/**
 * @author <a href="mailto:artifex.i@gmail.com">artifex</a>
 *         created: 15.04.2008 23:33:53
 */
public class DAOFactory {
  protected final Log log = LogFactory.getLog(getClass());
  private IdentityHashMap<Class, GenericDAO> registry;
  private SessionFactory sessionFactory;

  public synchronized <T> GenericDAO<T> getDAObyClass(Class<T> clazz) {
    GenericDAO dao = registry.get(clazz);
    if (dao == null) {
      log.warn("Created general DAO! for " + clazz.getSimpleName());
      register(dao = new GenericDAOImpl<T>(this, sessionFactory, clazz));
    }
    return dao;
  }

  public void register(GenericDAO dao) {
    if (registry == null)
      registry = new IdentityHashMap<Class, GenericDAO>();
    registry.put(dao.getPersistentClass(), dao);
  }

  public SessionFactory getSessionFactory() {
    return sessionFactory;
  }

  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }
}
