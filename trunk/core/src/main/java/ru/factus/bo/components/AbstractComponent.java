package ru.factus.bo.components;

import ru.factus.AbstractEntity;

import javax.persistence.*;

/**
 * @author <a href="mailto:ziman200@gmail.com">freeman</a>
 *         created 08.05.2008 15:34:53
 */

@Entity
@Table(name = "ENTITY")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
    name="type",
    discriminatorType=DiscriminatorType.STRING
)
public abstract class AbstractComponent extends AbstractEntity {
  
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

  public abstract int getRelationCount();
}
