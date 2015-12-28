package ru.factus.services.impl;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import ru.factus.bo.RelationStore;
import ru.factus.bo.components.AbstractComponent;
import ru.factus.bo.fetch.FetchedData;
import ru.factus.dao.fetch.FactDAO;
import ru.factus.services.*;

/**
 * @author <a href="mailto:ziman200@gmail.com">freeman</a>
 *         created 19.05.2008 17:59:38
 */

@Service("factService")
public class FactServiceImpl extends AbstractServiceImpl implements FactService {
  private FactDAO factDAO;
  private SearchService searchService;

  public List<RelationStore> search(String query) {

    return factDAO.search(query);
  }

  public List<AbstractComponent> topFacts() {
    return factDAO.topFacts();
  }

  @Resource
  public void setFactDAO(FactDAO factDAO) {
    this.factDAO = factDAO;
  }

  public void rebuildIndex() throws Exception {
    final List<RelationStore> list = factDAO.findAll();
    for (int i = 0; i < list.size(); i++){
      final RelationStore store = list.get(i);
      searchService.addDoc( createSolrDoc(store.getData(), Arrays.asList(store)));
    }
  }

  private SolrInputDocument createSolrDoc(FetchedData fdata, List<RelationStore> store){
    final SolrInputDocument doc = new SolrInputDocument();
    doc.setField("id", fdata.getId());
    for (RelationStore r : store) {
      doc.addField("relation_store_ids", r.getId());
      doc.addField("persons", r.getPerson().getName());
      doc.addField("relations", r.getRelation().getName());
      doc.addField("posts", r.getPost().getName());
      doc.addField("orgs", r.getOrg().getName());
      doc.setField("phrase_markup", r.getPhraseMarkup());
    }

    doc.setField("post_title", fdata.getTitle());
    doc.setField("post_date", fdata.getDate().getTime());
    doc.setField("post_text", fdata.getText());
    doc.setField("feedId", fdata.getFeed().getId());
    //title
    //text - поле для внутренних нужд - по нему можно просто искать

    return doc;
  }

  @Resource
  public void setSearchService(SearchService searchService) {
    this.searchService = searchService;
  }
}
