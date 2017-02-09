package com.company.seed.module.service.manager.impl;

import com.company.seed.module.model.manager.PositionModel;
import com.company.seed.module.service.manager.PositionService;
import com.company.seed.module.dao.manager.PositionDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 岗位服务层Service
 * Created by Administrator on 2016-08-03
 */
@Service
public class PositionServiceImpl implements PositionService{
    @Resource
    private PositionDao positionDaoMysql;

    @Override
    public boolean insertPosition(PositionModel position){
        return positionDaoMysql.insertPosition(position);
    }

    @Override
    public boolean updatePosition(PositionModel position){
        return positionDaoMysql.updatePosition(position);
    }

    @Override
    public List<PositionModel> selectAll() {
        return positionDaoMysql.selectAll();
    }

    @Override
    public void savePosition(PositionModel model) {
        if(StringUtils.isEmpty(model.getId())){
            insertPosition(model);
        }else{
            updatePosition(model);
        }
    }

}
