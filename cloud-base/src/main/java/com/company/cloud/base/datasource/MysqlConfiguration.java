package com.company.cloud.base.datasource;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.FilterEventAdapter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.company.cloud.base.datasource.mybatis.interceptor.MultiDataSourceInterceptor;
import com.company.cloud.base.datasource.mybatis.interceptor.PaginationInterceptor;
import com.google.common.collect.ImmutableMap;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.concurrent.ScheduledExecutorFactoryBean;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by yoara on 2017/2/17.
 */
@Configuration
public class MysqlConfiguration {
    @Resource
    private Environment env;

    @Bean(name="mysqlStatFilter")
    public FilterEventAdapter mysqlStatFilter(){
        StatFilter statFilter = new StatFilter();
        statFilter.setMergeSql(true);
        statFilter.setLogSlowSql(true);
        return statFilter;
    }

    @Bean(name="mysqlCreateScheduler")
    public ScheduledExecutorService mysqlCreateScheduler(){
        ScheduledExecutorFactoryBean scheduledExecutorFactoryBean = new ScheduledExecutorFactoryBean();
        scheduledExecutorFactoryBean.setPoolSize(50);
        return scheduledExecutorFactoryBean.getObject();
    }

    @Bean(name="mysqlDefaultDataSource",initMethod = "init",destroyMethod = "close")
    public DruidDataSource mysqlDefaultDataSource(){
        DruidDataSource source = new DruidDataSource();
        source.setDriverClassName("com.mysql.jdbc.Driver");
        source.setUrl(env.getProperty("basic.mysql.url"));
        source.setUsername(env.getProperty("basic.mysql.username"));
        source.setPassword(env.getProperty("basic.mysql.password"));
        source.setInitialSize(5);
        source.setMinIdle(5);
        source.setMaxActive(1000);
        source.setMaxWait(30000);
        source.setTimeBetweenEvictionRunsMillis(300000);
        source.setMinEvictableIdleTimeMillis(30000);
        source.setPoolPreparedStatements(false);
        List<Filter> filters = new ArrayList<>();
        filters.add(mysqlStatFilter());
        source.setProxyFilters(filters);
        Properties properties = new Properties();
        properties.setProperty("druid.stat.sql.MaxSize","3000");
        source.setConnectProperties(properties);

        source.setCreateScheduler(mysqlCreateScheduler());
        return source;
    }

    @Bean(name="mysqlDataSource")
    public DynamicDataSource mysqlDataSource(){
        DynamicDataSource source = new DynamicDataSource();
        DruidDataSource defaultDataSource = mysqlDefaultDataSource();
        Map<Object, Object> targetDataSources =
                ImmutableMap.of("defaultDataSource",defaultDataSource);
        source.setTargetDataSources(targetDataSources);
        source.setDefaultTargetDataSource(defaultDataSource);
        return source;
    }

    @Bean(name="mysqlSqlSessionFactory")
    public SqlSessionFactory mysqlSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        Properties properties = new Properties();
        properties.setProperty("cacheEnabled","false");
        properties.setProperty("lazyLoadingEnabled","false");
        properties.setProperty("multipleResultSetsEnabled","true");
        properties.setProperty("useColumnLabel","true");
        properties.setProperty("useGeneratedKeys","false");
        properties.setProperty("defaultExecutorType","SIMPLE");
        properties.setProperty("defaultStatementTimeout","25000");
        bean.setConfigurationProperties(properties);

        Interceptor[] plugins = new Interceptor[2];
        MultiDataSourceInterceptor multiDataSourceInterceptor = new MultiDataSourceInterceptor();
        Properties multiProperties = new Properties();
        multiProperties.setProperty("commonDatabase","common");
        multiProperties.setProperty("commonTables","");
        multiDataSourceInterceptor.setProperties(multiProperties);

        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        Properties pageProperties = new Properties();
        pageProperties.setProperty("dialectClass","com.company.cloud.base.datasource.mybatis.dialect.MySqlDialect");
        paginationInterceptor.setProperties(pageProperties);

        plugins[0] = multiDataSourceInterceptor;
        plugins[1] = paginationInterceptor;
        bean.setPlugins(plugins);

        bean.setDataSource(mysqlDataSource());
        bean.setTypeAliasesPackage("com.company.cloud.module.model");
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        bean.setMapperLocations(resolver.getResources("classpath*:mybatis/mysql/**/*.xml"));
        return bean.getObject();
    }

    @Bean(name="mysqlJdbcTemplate")
    public JdbcTemplate mysqlJdbcTemplate(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(mysqlDataSource());
        return jdbcTemplate;
    }

}
