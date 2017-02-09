package com.company.seed.basic.web.common.validation.pool;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * Created by yoara on 2016/6/13.
 */
public class ValidationPoolConfig extends GenericObjectPoolConfig {
    public ValidationPoolConfig(int maxIdle,int maxTotal) {
        this.setMaxIdle(maxIdle);
        this.setMaxTotal(maxTotal);
    }
}
