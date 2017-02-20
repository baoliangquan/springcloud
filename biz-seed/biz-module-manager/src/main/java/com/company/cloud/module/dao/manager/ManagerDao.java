package com.company.cloud.module.dao.manager;

import com.company.cloud.base.datasource.mybatis.MysqlBaseDaoImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yoara on 2017/2/20.
 */
@Repository
public class ManagerDao extends MysqlBaseDaoImpl {
    private final static String SQL_PREFIX = "com.company.cloud.module.dao.manager.ManagerDao.";
    public List selectAll(){
        return selectList(SQL_PREFIX+"selectAll");
    }
}
