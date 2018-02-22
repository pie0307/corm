package com.ziroom.bsrd.corm.u;

import com.ziroom.bsrd.basic.vo.Condition;
import com.ziroom.bsrd.corm.mapper.CMapper;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by cheshun on 17/10/25.
 */
public class WhereOpt {

    private CMapper cMapper;

    private UpdateParam param;

    public WhereOpt(CMapper cMapper, UpdateParam param) {
        this.cMapper = cMapper;
        this.param = param;
    }

    public UpdateWhere where(Condition condition) {
        param.addCondition(condition);
        return new UpdateWhere(cMapper, param);
    }

    public UpdateWhere where(Collection<Condition> conditions) {
        param.addCondition(conditions);
        return new UpdateWhere(cMapper, param);
    }

    public UpdateWhere where(Condition... conditions) {
        param.addCondition(Arrays.asList(conditions));
        return new UpdateWhere(cMapper, param);
    }
}
