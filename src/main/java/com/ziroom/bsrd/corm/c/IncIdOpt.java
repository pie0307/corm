package com.ziroom.bsrd.corm.c;

import com.ziroom.bsrd.basic.vo.IdEntity;
import com.ziroom.bsrd.corm.mapper.CMapper;

/**
 * Created by cheshun on 17/9/23.
 */
public class IncIdOpt<T extends IdEntity> {

    private CMapper cMapper;

    public IncIdOpt(CMapper cMapper) {
        this.cMapper = cMapper;
    }

    public int obj(T t) {
        return cMapper.insertIncId(t);
    }
}
