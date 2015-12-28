package ru.factus.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.Session;

/**
 * @author <a href="mailto:artifex.i@gmail.com">artifex</a>
 *         created: 15.04.2008 23:36:51
 */
public class GenericDAOImpl<T> implements GenericDAO<T>, InitializingBean {
// ------------------------------ FIELDS ------------------------------

  protected final Log log = LogFactory.getLog(getClass());

  private SessionFactory factory;
  private Class<T> persistentClass;
  private DAOFactory daoFactory;

// --------------------------- CONSTRUCTORS ---------------------------

  public GenericDAOImpl() {
    this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
        .getGenericSuperclass()).getActualTypeArguments()[0];

    log.info(getClass().getSimpleName()+" for class "+persistentClass.getSimpleName()+" initializing..");
  }

  public GenericDAOImpl(DAOFactory daoFactory, SessionFactory factory, Class clazz) {
    this.persistentClass = clazz;
    this.factory = factory;
    this.daoFactory = daoFactory;
    log.info(getClass().getSimpleName()+" for class "+persistentClass.getSimpleName()+" initializing..");
  }

// --------------------- GETTER / SETTER METHODS ---------------------

  public DAOFactory getDaoFactory() {
    return daoFactory;
  }

  public void setDaoFactory(DAOFactory daoFactory) {
    this.daoFactory = daoFactory;
  }

  public SessionFactory getFactory() {
    return factory;
  }

  public void setFactory(SessionFactory factory) {
    this.factory = factory;
  }

  public Class<T> getPersistentClass() {
    return persistentClass;
  }

// ------------------------ CANONICAL METHODS ------------------------

  public String toString() {
    return getClass().getSimpleName() + '{' +
        "persistentClass=" + persistentClass +
        '}';
  }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface GenericDAO ---------------------


  public T findById(Long id) {
    return (T) getSession().get(persistentClass, id);
  }

  public List<T> findAll() {
    return getSession().createCriteria(persistentClass).list();
  }

  public FilterContext<T> getFilterContext() {
    return new FilterContext<T>(getSession(), persistentClass);
  }

  public void update(T o) {
    getSession().update(o);
  }

  public void merge(T o) {
    getSession().merge(o);
  }

  public void persist(T o) {
    getSession().saveOrUpdate(o);
  }

  public void delete(T o) {
    getSession().delete(o);
  }

  public Session getSession() {
    return factory.getCurrentSession();
  }

// --------------------- Interface InitializingBean ---------------------


  public void afterPropertiesSet() throws Exception {
    daoFactory.register(this);
  }
}
