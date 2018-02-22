package com.ziroom.bsrd.corm.r;


import com.ziroom.bsrd.corm.mapper.CMapper;
import com.ziroom.bsrd.corm.result.PageResult;

/**
 * Created by cheshun on 17/8/14.
 */
public class PageOpt<T> implements PageResult<T> {

    private CMapper cMapper;

    private SelectParam param;

    public PageOpt(CMapper cMapper, SelectParam param) {
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
