package ru.factus.bo;

import ru.factus.AbstractEntity;

import javax.persistence.*;

/**
 * @author <a href="mailto:ziman200@gmail.com">freeman</a>
 *         created 13.05.2008 15:00:30
 */
@Entity
@Table(name = "RELATION")
public class Relation extends AbstractEntity{
  @Id
  @GeneratedValue
  private Long id;

  @Basic
  private String name;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
