package ru.factus.bo.components;

import org.hibernate.annotations.Formula;

import javax.persistence.Entity;
import javax.persistence.DiscriminatorValue;

/**
 * @author <a href="mailto:ziman200@gmail.com">freeman</a>
 *         created 08.05.2008 15:53:15
 */

@Entity
@DiscriminatorValue("PERSON")
public class Person extends AbstractComponent {
  @Formula(value = "(SELECT count(r.id) FROM RELATION_STORE r WHERE r.person_id=id)")
  private int relationCount = 0;

  public int getRelationCount() {
    return relationCount;
  }

  public void setRelationCount(int relationCount) {
    this.relationCount = relationCount;
  }
}
