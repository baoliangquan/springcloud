package com.company.seed.module.dao.manager.impl;

import com.company.seed.dao.basic.mybatis.BaseDaoImpl;
import com.company.seed.module.dao.manager.PositionDao;
import com.company.seed.module.model.manager.PositionModel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 岗位持久层Dao
 * Created by Administrator on 2016-08-03
 */
@Repository
public class PositionDaoMysqlImpl extends BaseDaoImpl implements PositionDao {
    private String SQL_PREFIX = "com.company.seed.module.dao.manager.PositionDao.";

    @Override
    public boolean insertPosition(PositionModel position){
        insert(SQL_PREFIX + "insertPosition", position);
        return true;
    }

    @Override
    public boolean updatePosition(PositionModel position){
        update(SQL_PREFIX + "updatePosition", position);
        return true;
    }

    @Override
    public List<PositionModel> selectAll() {
        return (List<PositionModel>)selectList(SQL_PREFIX + "selectAll");
    }

}
