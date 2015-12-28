package ru.factus.dao;

import java.util.List;

import org.hibernate.Session;

/**
 *
 * @author <a href="mailto:artifex.i@gmail.com">artifex</a>
 *         created 03.09.2007 20:20:09
 */
public interface GenericDAO<T> {
  T findById(Long id);
  List<T> findAll();

  void persist(T o);
  void update(T o);
  void merge(T o);
  void delete(T o);

  Session getSession();
  Class<T> getPersistentClass();

  FilterContext<T> getFilterContext();

}
