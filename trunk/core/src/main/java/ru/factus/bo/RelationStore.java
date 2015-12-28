package ru.factus.bo;

import ru.factus.AbstractEntity;
import ru.factus.bo.fetch.FetchedData;
import ru.factus.bo.components.Person;
import ru.factus.bo.components.Post;
import ru.factus.bo.components.Organization;

import javax.persistence.*;

/**
 * @author <a href="mailto:ziman200@gmail.com">freeman</a>
 *         created 13.05.2008 14:57:31
 */

//relation_store (id, fetched_data_id, phrase, relation_id, person_id, post_id, org_id)
@Entity
@Table(name = "RELATION_STORE")
public class RelationStore extends AbstractEntity {
  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne
  private FetchedData data;

  @ManyToOne
  private Relation relation;

  @Basic
  @Column(length = 512)
  private String phrase;

  @Basic
  @Column( name = "phrase_markup", length = 512)
  private String phraseMarkup;

  @ManyToOne
  private Person person;

  @ManyToOne
  private Post post;

  @ManyToOne
  private Organization org;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public FetchedData getData() {
    return data;
  }

  public void setData(FetchedData data) {
    this.data = data;
  }

  public Relation getRelation() {
    return relation;
  }

  public void setRelation(Relation relation) {
    this.relation = relation;
  }

  public String getPhrase() {
    return phrase;
  }

  public void setPhrase(String phrase) {
    this.phrase = phrase;
  }

  public Person getPerson() {
    return person;
  }

  public void setPerson(Person person) {
    this.person = person;
  }

  public Post getPost() {
    return post;
  }

  public void setPost(Post post) {
    this.post = post;
  }

  public Organization getOrg() {
    return org;
  }

  public void setOrg(Organization org) {
    this.org = org;
  }

  public String getPhraseMarkup() {
    return phraseMarkup;
  }

  public void setPhraseMarkup(String phraseMarkup) {
    this.phraseMarkup = phraseMarkup;
  }
}
