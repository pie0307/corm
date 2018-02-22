package com.ziroom.bsrd.corm.result;

import com.ziroom.bsrd.corm.r.SelectParam;

import java.util.Map;

/**
 * SumResult
 * Created by cheshun on 17/8/23.
 */
public interface SumResult extends IResult {

    default Number number() {
        SelectParam param = getParam();
        Map<String, Object> result = getCMapper().selectSum(param);
        if (result == null || result.isEmpty()) {
            return 0;
        }
        return (Number) result.entrySet().iterator().next().getValue();
    }
}
