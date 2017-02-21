package com.company.cloud.base.solr.local;

import com.company.cloud.base.solr.local.core.SolrServerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by yoara on 2017/2/21.
 */
@Configuration
public class SolrConfiguration {
    @Bean(name="solrServerFactory")
    public SolrServerFactoryBean solrServerFactory(){
        return new SolrServerFactoryBean();
    }
}
