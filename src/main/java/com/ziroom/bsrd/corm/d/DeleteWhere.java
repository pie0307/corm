package com.ziroom.bsrd.corm.d;

import com.ziroom.bsrd.basic.vo.Condition;
import com.ziroom.bsrd.corm.mapper.CMapper;

/**
 * Created by cheshun on 17/9/26.
 */
public class DeleteWhere implements DeleteExec {

    private CMapper cMapper;

    private DeleteParam param;

    public DeleteWhere(CMapper cMapper, DeleteParam param) {
        this.cMapper = cMapper;
        this.param = param;
    }

    public DeleteWhere and(Condition condition) {
        param.addCondition(condition);
        return this;
    }

    @Override
    public CMapper getCMapper() {
        return cMapper;
    }

    @Override
    public DeleteParam getParam() {
        return param;
    }
}
