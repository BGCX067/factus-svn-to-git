package ru.factus.services;

import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.client.solrj.response.QueryResponse;

/**
 * @author <a href="mailto:ziman200@gmail.com">freeman</a>
 *         created 28.05.2008 15:33:29
 */
public interface SearchService extends GenericService {
  void addDoc(SolrInputDocument doc) throws Exception;
  SolrDocumentList search(String query) throws Exception;
  QueryResponse searchInMetadataWithFacets(String query, int start, int count) throws Exception;

}
