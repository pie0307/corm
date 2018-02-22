package com.ziroom.bsrd.corm.u;

import com.ziroom.bsrd.basic.vo.Condition;
import com.ziroom.bsrd.corm.mapper.CMapper;

/**
 * Created by cheshun on 17/8/9.
 */
public class UpdateWhere implements UpdateExec {

    private CMapper cMapper;

    private UpdateParam param;

    public UpdateWhere(CMapper cMapper, UpdateParam param) {
        this.cMapper = cMapper;
        this.param = param;
    }

    public UpdateWhere and(Condition condition) {
        param.addCondition(condition);
        return this;
    }

    @Override
    public UpdateParam getParam() {
        return param;
    }

    @Override
    public CMapper getCMapper() {
        return cMapper;
    }
}
