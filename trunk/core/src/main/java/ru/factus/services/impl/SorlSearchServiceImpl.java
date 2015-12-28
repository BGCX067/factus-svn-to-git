package ru.factus.services.impl;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;
import ru.factus.SolrServerBean;
import ru.factus.services.AbstractServiceImpl;
import ru.factus.services.SearchService;

import java.util.List;


/**
 * @author <a href="mailto:ziman200@gmail.com">freeman</a>
 *         created 28.05.2008 15:34:02
 */

@Service("searchService")
public class SorlSearchServiceImpl extends AbstractServiceImpl implements SearchService {
  private SolrServerBean server;

  public void addDoc(SolrInputDocument doc) throws Exception {
    server.add(doc);
    server.commit();
  }

  public SolrDocumentList search(String query) throws Exception {
    final SolrQuery solrQuery = new SolrQuery();
    solrQuery.setQuery(query);
    QueryResponse rsp = server.query(solrQuery);
    return rsp.getResults();
  }

  /**
   *
   * @param query запрос
   * @param start начало
   * @param count количество на странице, -1 если все
   * @return результаты поиска + фасеты
   * @throws Exception
   */
  public QueryResponse searchInMetadataWithFacets(String query, int start, int count) throws Exception {
    final SolrQuery sq = new SolrQuery();
    //sq.setQuery("onlymetadata:"+query);
    sq.setQuery(query);
    sq.setRows(count);
    sq.setStart(start);

    sq.setFacet(true);
    sq.add("facet.field", "persons");
    sq.add("facet.field", "posts");
    sq.add("facet.field", "orgs");
    sq.add("facet.field", "feedId");

    sq.add("facet.date","post_date");
    sq.add("facet.date.start","NOW/DAY-12YEARS");
    sq.add("facet.date.end","NOW/DAY-1MINUTE");
    sq.add("facet.date.gap","%2B1MONTH"); //по месяцам
    sq.add("facet.date.other","all"); //по неделям

    sq.setFacetLimit(-1);
    sq.setFacetMinCount(1);
    sq.setFacetSort(true);

/*
    final QueryResponse queryResponse = server.query(sq);
    final List<FacetField.Count> list = queryResponse.getFacetField("posts").getValues();
    list.get(0).getName()
    list.get(0).getCount()
*/
    return server.query(sq);
  }

  @Resource
  public void setServer(SolrServerBean server) {
    this.server = server;
  }
}
