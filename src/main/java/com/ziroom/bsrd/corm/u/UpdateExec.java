package com.ziroom.bsrd.corm.u;

import com.ziroom.bsrd.corm.mapper.CMapper;

/**
 * 提交操作
 * Created by cheshun on 17/8/11.
 */
public interface UpdateExec {

    default int exec() {
        UpdateParam param = getParam();
        return getCMapper().update(param);
    }

    UpdateParam getParam();

    CMapper getCMapper();
}
