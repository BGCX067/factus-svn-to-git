package ru.factus.services;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import ru.factus.dao.DAOFactory;
import ru.factus.dao.GenericDAO;
import ru.factus.AbstractEntity;

import javax.annotation.Resource;

/**
 * @author <a href="mailto:artifex.i@gmail.com">artifex</a>
 *         created: 19.10.2007 16:01:12
 */
public abstract class AbstractServiceImpl implements GenericService {
  protected final Log log = LogFactory.getLog(getClass());
  protected DAOFactory daoFactory;

  /**
   * Generic object creation
   * @param obj
   */
  public <T> void persist(T obj){
    final Class<T> clazz = Hibernate.getClass(obj);
    final GenericDAO<T> dao =  daoFactory.getDAObyClass(clazz);
    dao.persist(obj);
  }

  public <T> void update(T obj) {
    final Class<T> clazz = Hibernate.getClass(obj);
    final GenericDAO<T> dao =  daoFactory.getDAObyClass(clazz);
    dao.update(obj);
  }

  public DAOFactory getDaoFactory() {
    return daoFactory;
  }

  @Resource
  public void setDaoFactory(DAOFactory daoFactory) {
    this.daoFactory = daoFactory;
  }

  public <T> T get(Class<T> clazz, Long id) {
    return daoFactory.getDAObyClass(clazz).findById(id);
  }
}
