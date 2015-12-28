package ru.factus;

import org.hibernate.Hibernate;

/**
 * @author <a href="mailto:artifex.i@gmail.com">artifex</a>
 *         created: 03.12.2007 14:24:51
 */
public abstract class AbstractEntity {
  public abstract Long getId();

  public abstract void setId(Long id);

  public String getName() {
    return "no name";
  }

  public String toDisplayString() {
    return getName();
  }

  public String toString() {
    return getClass().getSimpleName() + "{id=" + getId() + '}';
  }

  public boolean equals(Object o) {
    if (this == o) return true;

    if(o == null) return false;

    Class c1 = Hibernate.getClass(this);
    Class c2 = Hibernate.getClass(o);
    if (c1 != c2) return false;

    AbstractEntity that = (AbstractEntity) o;

    Long thisId = this.getId();
    Long thatId = that.getId();

    return !(thisId != null ? !thisId.equals(thatId) : thatId != null);
  }

  public int hashCode() {
    Long thisId = this.getId();
    return (thisId != null ? thisId.hashCode() : 0);
  }

}
