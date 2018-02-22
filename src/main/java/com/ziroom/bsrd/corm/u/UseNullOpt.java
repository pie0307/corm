package com.ziroom.bsrd.corm.u;

import com.ziroom.bsrd.basic.vo.Condition;
import com.ziroom.bsrd.basic.vo.IdEntity;
import com.ziroom.bsrd.corm.mapper.CMapper;

/**
 * Created by cheshun on 17/9/28.
 */
public class UseNullOpt<T extends IdEntity> {

    private CMapper cMapper;

    private UpdateParam param;

    public UseNullOpt(CMapper cMapper, UpdateParam param) {
        this.cMapper = cMapper;
        this.param = param;
    }

    public int obj(T t) {
        param.setEntity(t);
        param.addCondition(Condition.eq(IdEntity.ID_PN, t.getId()));
        return cMapper.update(param);
    }

    public WhereOpt entity(T t) {
        param.setEntity(t);
        param.addCondition(Condition.eq(IdEntity.ID_PN, t.getId()));
        return new WhereOpt(cMapper, param);
    }
}
