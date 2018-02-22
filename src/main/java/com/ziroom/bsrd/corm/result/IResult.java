package com.ziroom.bsrd.corm.result;

import com.ziroom.bsrd.corm.mapper.CMapper;
import com.ziroom.bsrd.corm.r.SelectParam;

/**
 * Created by cheshun on 17/12/4.
 */
public interface IResult {

    CMapper getCMapper();

    SelectParam getParam();
}
