package com.company.seed.module.service.manager;

import com.company.seed.module.model.manager.PositionModel;

import java.util.List;

/**
 * 岗位服务层Service
 * Created by Administrator on 2016-08-03
 */
public interface PositionService{
    boolean insertPosition(PositionModel position);

    boolean updatePosition(PositionModel position);

    List<PositionModel> selectAll();

    void savePosition(PositionModel model);
}
