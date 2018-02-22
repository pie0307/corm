package com.ziroom.bsrd.corm;

import com.ziroom.bsrd.corm.mapper.CMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Corm 配置
 * 主要配置CMapper对象
 * Created by cheshun on 17/9/3.
 */
public class CormConfig {

    public void addDefaultMasterMapper(CMapper mapper) {
        List<CMapper> mapperList = CormContext.MASTER_MAPPER_LIST.get();
        if (mapperList == null) {
            mapperList = new ArrayList<>();
            CormContext.MASTER_MAPPER_LIST.set(mapperList);
        }
        mapperList.add(mapper);
    }

    public void addDefaultSlaveMapper(CMapper mapper) {
        List<CMapper> mapperList = CormContext.SLAVE_MAPPER_LIST.get();
        if (mapperList == null) {
            mapperList = new ArrayList<>();
            CormContext.SLAVE_MAPPER_LIST.set(mapperList);
        }
        mapperList.add(mapper);
    }
}
