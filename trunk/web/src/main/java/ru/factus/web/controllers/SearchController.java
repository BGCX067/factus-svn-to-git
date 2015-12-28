package ru.factus.web.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.HashMap;
import java.util.Collections;
import java.util.ArrayList;

import javax.annotation.Resource;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.util.StringUtils;

import ru.factus.bo.components.AbstractComponent;
import ru.factus.bo.fetch.FetchedData;
import ru.factus.bo.fetch.Feed;
import ru.factus.bo.search.FacetItemGroup;
import ru.factus.bo.search.FacetItem;
import ru.factus.services.FactService;
import ru.factus.services.SearchService;

/**
 * @author <a href="mailto:ziman200@gmail.com">freeman</a>
 *         created 14.05.2008 14:04:45
 */

@Controller
public class SearchController {
  private FactService service;
  private SearchService searchService;

  @Deprecated
  @RequestMapping("/search.do")
  public String doSearch(@RequestParam("query") String query, ModelMap model) throws Exception {

    final SolrDocumentList result = searchService.search(query);
    model.put("result", result);

    findTop(model);

    return "main";
  }

  //TODO Как пример: переименовать в search.do - будет основной метод для поиска
  @RequestMapping("/faseted-search.do")
  public String doFacetedSearch(@RequestParam(value="query", required = false) String queryString,
                                @RequestParam(value="start", required = false) Integer start,
                                @RequestParam(value="posts", required = false) String[] posts,
                                @RequestParam(value="orgs", required = false) String[] orgs,
                                @RequestParam(value="persons", required = false) String[] persons,
                                @RequestParam(value="post_date", required = false) String postDate,
                                @RequestParam(value="feedId", required = false) String feedId,
                                ModelMap model) throws Exception {

    HashMap<String, String[]> facets = new HashMap<String, String[]>();

    start = start == null? 0:start;

    Query query = new Query();
    query.onlyMetaData(queryString);


    if(!isEmpty(posts)){
      query.append("posts", posts);
      facets.put("posts", posts);
    }

    if(!isEmpty(orgs)){
      query.append("orgs", orgs);
      facets.put("orgs", orgs);
    }

    if(!isEmpty(persons)){
      query.append("persons", persons);
      facets.put("persons", persons);
    }

    if(feedId !=null){
      query.append(" feedId: "+feedId);
      facets.put("feedId", new String[]{feedId});
    }

    if(postDate !=null){
      query.append(" post_date:["+ postDate +" TO *]"); //TODO fix * to postDate+1week      
    }

    final QueryResponse qr = searchService.searchInMetadataWithFacets(query.toString(), start, 10);

    model.put("result", qr.getResults());
    model.put("facets", parseResponse(qr));
    model.put("selected", facets);

    parseResponse(qr);

    return "main";
  }

  private boolean isEmpty(String[] string) {
    return string == null || string.length == 0;
  }

  @RequestMapping("/more.do")
  public String doSearch(@RequestParam("id") Long id, ModelMap model){
    final FetchedData data = service.get(FetchedData.class, id);
    model.put("data", data);

    return "more";
  }

  @Deprecated
  @RequestMapping("/main.do")
  public String doMain(ModelMap model) throws Exception {
    final QueryResponse qr = searchService.searchInMetadataWithFacets("*:*", 0, 10);
    //qr.getFacetDates().get(0).get
    //@todo qr.getFacetDates();
    model.put("result", qr.getResults());

    model.put("facets", parseResponse(qr));
    model.put("selected", Collections.EMPTY_MAP);


    /*final List<FacetField.Count> values = qr.getFacetField("feedId").getValues();
    List<Feed> sources = new ArrayList<Feed>();
    for (FacetField.Count value : values) {
      FacetField а = new FacetField("");
      //todo надо создавать оболоч

      sources.add(service.get(Feed.class, Long.valueOf(value.getName())));
    }*/

    //model.put("sources", sources);



    //feedId 

    return "main";
  }

  private List<FacetItemGroup> parseResponse(QueryResponse response){
    final List<FacetItemGroup> groups = new ArrayList<FacetItemGroup>();

    for (FacetField field : response.getFacetFields()) {
      final String groupName = field.getName();
      FacetItemGroup group = new FacetItemGroup(groupName);

      for (FacetField.Count value : field.getValues()) {
        final String name = value.getName();
        String title;

        if("feedId".equals(groupName)){
          final Feed feed = service.get(Feed.class, Long.valueOf(value.getName()));
          title = feed.getDescription();
          if(title == null){
            title = "Неизвестный";
          }
        }else{
          title = name;
        }

        group.addItem( new FacetItem(title, name, value.getCount()));
      }

      groups.add(group);
    }

    final List<FacetField> dates = response.getFacetDates();
    final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    final SimpleDateFormat rusFormat = new SimpleDateFormat("dd.MM.yy");

    if(dates != null){
      for (FacetField field : dates) {
        FacetItemGroup group = new FacetItemGroup(field.getName());

        for (FacetField.Count value : field.getValues()) { //10
          final String dateString = value.getName().substring(0, 10);
          try {
            String ruDate = rusFormat.format(format.parse(dateString));
            group.addItem( new FacetItem(ruDate, ruDate, value.getCount()));
          } catch (ParseException e) {
            e.printStackTrace(System.out);
          }
        }

        groups.add(group);
      }
    }


    return groups;
  }

  private class Query {
    final private StringBuilder buff = new StringBuilder();

    public void append(String s){
      if(buff.length() > 0)
        buff.append(" AND ");

      buff.append(s);
    }

    public void onlyMetaData(String queryString) {
      if(queryString != null && queryString.length() > 0)
        buff.append("onlymetadata:").append(queryString);
    }

    @Override
    public String toString() {
      if(buff.length() == 0)
        return "*:*";
      return buff.toString();
    }

    public void append(String facet, String[] values) {
      append(facet+": "+StringUtils.arrayToDelimitedString(values, " "));
    }
  }

  @Deprecated
  private void findTop(ModelMap model){
    final List<AbstractComponent> list = service.topFacts();
    model.put("top", list);
  }

  @Resource
  public void setService(FactService service) {
    this.service = service;
  }

  @Resource
  public void setSearchService(SearchService searchService) {
    this.searchService = searchService;
  }
}