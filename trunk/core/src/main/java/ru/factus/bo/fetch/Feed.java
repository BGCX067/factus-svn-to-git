package ru.factus.bo.fetch;

import ru.factus.AbstractEntity;

import javax.persistence.*;
import java.util.Calendar;

/**
 * @author <a href="mailto:ziman200@gmail.com">freeman</a>
 *         created 02.04.2008 18:50:35
 */
@Entity
@Table(name = "FEED")
public class Feed extends AbstractEntity{
  @Id
  @GeneratedValue
  private Long id;

  public Feed() {
  }

  public Feed(String url) {
    this.link = url;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Basic
  @Column(nullable = false, unique = true)
  private String link;

  @Basic
  private String description;

  @Basic
  private Calendar lastVisitTime;

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Calendar getLastVisitTime() {
    return lastVisitTime;
  }

  public void setLastVisitTime(Calendar lastVisitTime) {
    this.lastVisitTime = lastVisitTime;
  }
}
