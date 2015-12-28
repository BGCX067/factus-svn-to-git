package ru.factus.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.criterion.*;

/**
 * @author <a href="mailto:artifex.i@gmail.com">artifex</a>
 *         created: 15.04.2008 23:35:30
 */
public class FilterContext<T> {
  private Criteria criteria;
  private Session session;
  private Class<T> persistentClass;

  public FilterContext(Session session, Class<T> persistentClass) {
    this.session = session;
    this.persistentClass = persistentClass;
  }

  public Criteria getCriteria() {
    if(criteria==null){
      criteria = session.createCriteria(persistentClass);
    }
    return criteria;
  }

/*
  public Session getSession() {
    return session;
  }
*/

  public Class<T> getPersistentClass() {
    return persistentClass;
  }

  public FilterContext<T> add(Criterion c){
    this.getCriteria().add(c);
    return this;
  }

  public FilterContext<T> createAlias(String fieldName, String aliasName, int joinPath){
    this.getCriteria().createAlias(fieldName, aliasName, joinPath);
    return this;
  }

  public FilterContext<T> createAlias(String fieldName, String aliasName){
    this.getCriteria().createAlias(fieldName, aliasName);
    return this;
  }

  public FilterContext<T> add(Order order){
    this.getCriteria().addOrder(order);
    return this;
  }

  public T uniqueResult(){
    return (T) this.getCriteria().uniqueResult();
  }

  public Object genericUniqueResult(){
    return this.getCriteria().uniqueResult();
  }


  public List<T> list(){
    return this.getCriteria().list();
  }

  public FilterContext<T> setProjection(Projection p) {
    this.getCriteria().setProjection(p);
    return this;
  }

  public FilterContext<T> setTransformer(ResultTransformer t) {
    this.getCriteria().setResultTransformer(t);
    return this;
  }

  public FilterContext<T> setMaxResults(int n){
	  this.getCriteria().setMaxResults(n);
	  return this;
  }

  public FilterContext<T> setFirstResult(int start){
	  this.getCriteria().setFirstResult(start);
	  return this;
  }

  public FilterContext<T> setCachable(boolean f){
    this.getCriteria().setCacheable(true);
    return this;
  }
}
