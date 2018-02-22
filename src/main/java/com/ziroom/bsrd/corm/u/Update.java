package com.ziroom.bsrd.corm.u;

import com.ziroom.bsrd.basic.vo.Condition;
import com.ziroom.bsrd.basic.vo.Field;
import com.ziroom.bsrd.basic.vo.IdEntity;
import com.ziroom.bsrd.corm.mapper.CMapper;

/**
 * Created by cheshun on 17/8/9.
 */
public class Update<T extends IdEntity> {

    private CMapper cMapper;

    private UpdateParam param;

    public Update(CMapper cMapper, UpdateParam param) {
        this.cMapper = cMapper;
        this.param = param;
    }

    public AppointUpdate<T> field(Field field, Object value) {
        AppointUpdate<T> update = new AppointUpdate<>(cMapper, param);
        update.setField(field, value);
        return update;
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

    public UseNullOpt<T> useNull() {
        param.setUseNull(true);
        return new UseNullOpt<T>(cMapper, param);
    }

}
