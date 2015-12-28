package ru.factus.bo.search;

public class FacetItem {
  private String title;
  private String name;
  private long count;

  public FacetItem(String title, String name, long count) {
    this.title = title;
    this.name = name;
    this.count = count;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }
}
