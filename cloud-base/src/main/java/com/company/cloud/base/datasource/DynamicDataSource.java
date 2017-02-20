package com.company.cloud.base.datasource;


import com.company.cloud.base.common.ContextHolder;
import com.company.cloud.base.common.DataSourceEnum;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.logging.Logger;

/**
 * 动态数据库，不同城市用不同的数据源 
 * Created by yoara on 2015/12/21.
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
	@Override
	protected Object determineCurrentLookupKey() {
		DataSourceEnum datasource = ContextHolder.getDataSource();
		if (datasource == null) {
			return null;
		}
		return datasource.name();
	}

	@Override
	public Logger getParentLogger(){
		return null;
	}
}
