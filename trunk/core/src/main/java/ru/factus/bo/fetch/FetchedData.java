package ru.factus.bo.fetch;

import ru.factus.AbstractEntity;

import javax.persistence.*;
import java.util.Calendar;

/**
 * @author <a href="mailto:ziman200@gmail.com">freeman</a>
 *         created 02.04.2008 18:43:48
 */

//fetched_data (id, registered_feeds_id, permanent_link, feed_title, feed_text, feed_date, feed_metadata, processed_flag, ...)
@Entity
@Table(name = "FETCHED_DATA")
public class FetchedData extends AbstractEntity {
  @Id
  @GeneratedValue
  private Long id;

  public FetchedData() {
  }

  public FetchedData(Feed feed) {
    this.feed = feed;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Basic
  private String link;
  @Basic
  private String title;
  @Basic
 
  private String text;

  @Basic
  private Calendar dateCreate;

  @Basic
  private Calendar date;
  @Basic
  private boolean processed = false;

  @Basic
  @Column(name = "processed_time")
  private Calendar processedTime;

  @ManyToOne
  private Feed feed;

  public Feed getFeed() {
    return feed;
  }

  public void setFeed(Feed feed) {
    this.feed = feed;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Calendar getDate() {
    return date;
  }

  public void setDate(Calendar date) {
    this.date = date;
  }

  public boolean isProcessed() {
    return processed;
  }

  public void setProcessed(boolean processed) {
    this.processed = processed;
  }

  public Calendar getDateCreate() {
    return dateCreate;
  }

  public void setDateCreate(Calendar dateCreate) {
    this.dateCreate = dateCreate;
  }

  public Calendar getProcessedTime() {
    return processedTime;
  }

  public void setProcessedTime(Calendar processedTime) {
    this.processedTime = processedTime;
  }
}
