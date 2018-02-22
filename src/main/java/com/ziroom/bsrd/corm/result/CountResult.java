package com.ziroom.bsrd.corm.result;

import com.ziroom.bsrd.corm.r.SelectParam;

/**
 * CountResult
 * Created by cheshun on 17/8/23.
 */
public interface CountResult extends IResult {

    default long number() {
        SelectParam param = getParam();
        return getCMapper().selectCount(param);
    }
}
