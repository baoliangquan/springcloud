package com.company.seed.module.dao.manager.impl;

import com.company.seed.dao.basic.mybatis.BaseDaoImpl;
import com.company.seed.module.dao.manager.ManagerDao;
import com.company.seed.module.model.manager.ManagerModel;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yoara on 2016/4/25.
 */
@Repository
public class ManagerDaoMysqlImpl extends BaseDaoImpl implements ManagerDao {
    private static String SQL_PREFIX = "com.company.seed.module.dao.manager.ManagerDao.";
    @Override
    public boolean insertManager(ManagerModel manager){
        insert(SQL_PREFIX + "insertManager", manager);
        return true;
    }

    @Override
    public boolean updateManager(ManagerModel manager) {
        update(SQL_PREFIX + "updateManager", manager);
        return true;
    }

    @Override
    public ManagerModel authenticateManager(String userName, String passwordMD5) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("userName",userName);
        params.put("passwordMD5",passwordMD5);
        return (ManagerModel)selectOne(SQL_PREFIX+"authenticateManager",params);
    }

    @Override
    public List<ManagerModel> selectAll() {
        return (List<ManagerModel>)selectList(SQL_PREFIX+"selectAll");
    }
}
