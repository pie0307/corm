package com.ziroom.bsrd.corm.r;

import com.ziroom.bsrd.basic.vo.Field;
import com.ziroom.bsrd.basic.vo.IdEntity;
import com.ziroom.bsrd.corm.mapper.CMapper;

import java.util.Collection;

/**
 * Created by cheshun on 17/8/11.
 */
public class SelectMulti {

    private CMapper cMapper;

    private SelectParam param;

    public SelectMulti(CMapper cMapper, SelectParam param) {
        this.cMapper = cMapper;
        this.param = param;
    }

    public SelectMulti entity(Class<? extends IdEntity> clazz, String alias) {
        param.addClazzMap(clazz, alias);
        return this;
    }

    public SelectMap field(Field field) {
        param.addField(field);
        return new SelectMap(cMapper, param);
    }

    public SelectMap field(Collection<Field> fields) {
        param.addField(fields);
        return new SelectMap(cMapper, param);
    }

    public SumOpt sum(Field field) {
        param.addField(field);
        return new SumOpt(cMapper, param);
    }

    public CountOpt count() {
        return new CountOpt(cMapper, param);
    }
}
