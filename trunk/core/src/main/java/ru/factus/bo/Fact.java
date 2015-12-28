package ru.factus.bo;

import javax.persistence.*;
import java.util.Calendar;

import ru.factus.AbstractEntity;

/**
 * @author <a href="mailto:ziman200@gmail.com">freeman</a>
 *         created 08.05.2008 12:11:05
 */
public class Fact extends AbstractEntity {
  @Id
  @GeneratedValue
  private Long id;

  @Basic
  @Column(length = 255)
  private String person;

  @Basic
  @Column(length = 255)
  private String org;

  @Basic
  @Column(length = 255)
  private String post;

  @Basic
  @Column(length = 255)
  private String relation = "active";

  @Basic
  private String pattern;

  @Basic
  @Column(name = "original_text")
  private String text; //todo

  @Basic
  @Column(length = 20)
  private String user;

  @Basic
  @Column(name = "date_creation")
  private Calendar dateCreation;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPerson() {
    return person;
  }

  public void setPerson(String person) {
    this.person = person;
  }

  public String getOrg() {
    return org;
  }

  public void setOrg(String org) {
    this.org = org;
  }

  public String getPost() {
    return post;
  }

  public void setPost(String post) {
    this.post = post;
  }

  public String getRelation() {
    return relation;
  }

  public void setRelation(String relation) {
    this.relation = relation;
  }

  public String getPattern() {
    return pattern;
  }

  public void setPattern(String pattern) {
    this.pattern = pattern;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public Calendar getDateCreation() {
    return dateCreation;
  }

  public void setDateCreation(Calendar dateCreation) {
    this.dateCreation = dateCreation;
  }
}
