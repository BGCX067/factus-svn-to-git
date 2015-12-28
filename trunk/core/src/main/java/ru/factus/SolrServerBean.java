package ru.factus;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.SolrParams;

/**
 * @author <a href="mailto:artifex.i@gmail.com">artifex</a>
 *         created: 02.06.2008 22:31:47
 */
public class SolrServerBean {
  protected final Log log = LogFactory.getLog(getClass());
  protected SolrServer server;

  public SolrServerBean() {
  }

  public SolrServerBean(String url) throws MalformedURLException {
    setSolrServerUrl(url);
  }

  public void setSolrServerUrl(String url) throws MalformedURLException {
    this.server = new CommonsHttpSolrServer(url);
    ((CommonsHttpSolrServer) this.server).setConnectionTimeout(100);
    ((CommonsHttpSolrServer) this.server).setDefaultMaxConnectionsPerHost(100);
    ((CommonsHttpSolrServer) this.server).setMaxTotalConnections(100);
  }

  public QueryResponse query(SolrParams params) throws IOException, SolrServerException {
    return server.query(params);
  }

  public void add(SolrInputDocument doc) throws IOException, SolrServerException {
    server.add(doc);
  }

  public void add(Collection<SolrInputDocument> docs) throws IOException, SolrServerException {
    server.add(docs);
  }

  public void commit() throws IOException, SolrServerException {
    server.commit();
  }

  public void optimize() throws IOException, SolrServerException {
    server.optimize();
  }

  public SolrServer getServer() {
    return server;
  }

  public void setServer(SolrServer server) {
    this.server = server;
  }
}
