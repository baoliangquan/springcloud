package com.company.cloud.base.datasource;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.annotation.Resource;

/**
 * Created by yoara on 2017/2/20.
 */
@EnableTransactionManagement
public class TxConfiguration implements TransactionManagementConfigurer {
    @Resource(name="mysqlTxManager")
    private PlatformTransactionManager mysqlTxManager;

    @Bean(name="mysqlTxManager")
    public PlatformTransactionManager mysqlTxManager(DynamicDataSource mysqlDataSource){
        return new DataSourceTransactionManager(mysqlDataSource);
    }

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return mysqlTxManager;
    }
}
