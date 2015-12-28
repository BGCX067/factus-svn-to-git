package ru.factus.bo.search;

import java.util.List;
import java.util.ArrayList;

public class FacetItemGroup {
  private String title;
  private String name;
  private List<FacetItem> items;

  public FacetItemGroup(String name) {
    if ("persons".equals(name)) {
      this.title = "Люди";
    } else if ("posts".equals(name)) {
      this.title = "Должности";
    } else if ("orgs".equals(name)) {
      this.title = "Организации";
    } else if ("feedId".equals(name)){
      this.title = "Источник";
    } else if("post_date".equals(name)){
      this.title = "Дата";
    }

    this.name = name;
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

  public void addItem(FacetItem item){
    if(items == null)
      items = new ArrayList<FacetItem>();
    items.add(item);
  }

  public List<FacetItem> getItems() {
    return items;
  }

  public void setItems(List<FacetItem> items) {
    this.items = items;
  }
}
