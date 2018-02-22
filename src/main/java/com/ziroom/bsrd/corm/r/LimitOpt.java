package com.ziroom.bsrd.corm.r;

import com.ziroom.bsrd.corm.mapper.CMapper;
import com.ziroom.bsrd.corm.result.ListResult;

/**
 * Created by cheshun on 17/9/26.
 */
public class LimitOpt<T> implements ListResult<T> {

    private CMapper cMapper;

    private SelectParam param;

    public LimitOpt(CMapper cMapper, SelectParam param) {
        this.cMapper = cMapper;
        this.param = param;
    }

    @Override
    public CMapper getCMapper() {
        return cMapper;
    }

    @Override
    public SelectParam getParam() {
        return param;
    }
}
