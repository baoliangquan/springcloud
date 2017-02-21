package com.company.cloud.base.solr.local.core;


import org.apache.solr.client.solrj.SolrClient;

/**
 * SolrServer回调
 */
public interface SolrCallback {
	Object doInSolr(SolrClient solrServer) throws Exception;
}
