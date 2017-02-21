package com.company.cloud.base.solr.local.core;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * SolrServer工厂
 */
public class SolrServerFactory {
	private final static Logger logger = Logger.getLogger(SolrServerFactory.class);
	private Map<String, SolrClient> solrServers = Collections.synchronizedMap(new HashMap<String, SolrClient>());
	public SolrServerFactory() {
	}
	
	/**
	 * 获取CommonsHttpSolrServer
	 * 
	 * @param solrUrl
	 * @return SolrServer
	 */
	public SolrClient getCommonsHttpSolrServer(final String solrUrl) {
		if (!solrServers.containsKey(solrUrl)) {
			try {
				SolrClient solrServer = new HttpSolrClient(solrUrl);
				solrServers.put(solrUrl, solrServer);
				logger.info("SolrServer start successfully : " + solrUrl);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return solrServers.get(solrUrl);
	}
	
	/**
	 * 获取ShardSolrServer
	 * 
	 * @param solrServerUrl 主协调Server
	 * @param shardsUrl Shard Server
	 * @param uniqueKeyField 主键名
	 * @return
	 */
	public SolrClient getShardSolrServer(String solrServerUrl, String shardsUrl, String uniqueKeyField) {
		String serverKey = solrServerUrl + ":" + shardsUrl;
		if (!solrServers.containsKey(serverKey)) {
			try {
				SolrClient solrServer = new ShardSolrServer(solrServerUrl, shardsUrl, uniqueKeyField);
				solrServers.put(serverKey, solrServer);
				logger.info("SolrServer start successfully : " + serverKey);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return solrServers.get(serverKey);
	}
	
	/**
	 * 获取CloudSolrServer
	 * @return SolrServer
	 */
	public SolrClient getCloudSolrServer(final String zkHost, final String defaultCollection, final Integer zkClientTimeout, final Integer zkConnectTimeout) {
		String key = zkHost+defaultCollection;
		if (!solrServers.containsKey(key)) {
			try {
				CloudSolrClient solrServer = new CloudSolrClient(zkHost);
				solrServer.setDefaultCollection(defaultCollection);
				solrServer.setZkClientTimeout(zkClientTimeout);
				solrServer.setZkConnectTimeout(zkConnectTimeout);			
				solrServers.put(key, solrServer);
				logger.info("SolrServer start succefully : " + zkHost);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return solrServers.get(key);
	}

}
