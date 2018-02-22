package com.ziroom.bsrd.corm.result;

import com.ziroom.bsrd.basic.vo.Page;
import com.ziroom.bsrd.basic.vo.Skipped;
import com.ziroom.bsrd.corm.Utils;
import com.ziroom.bsrd.corm.r.SelectParam;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * PageResult
 * Created by cheshun on 17/8/23.
 */
public interface PageResult<T> extends IResult {

    @SuppressWarnings("unchecked")
    default Page<T> page() {
        SelectParam param = getParam();
        Skipped skipped = param.getSkipped();
        List<Map<String, Object>> result = getCMapper().selectMapListPage(param, skipped);
        List<T> ts = null;
        if (result == null || result.isEmpty()) {
            ts = Collections.emptyList();
        } else {
            Class<?> type = param.getResultType();
            if (type == Map.class) {
                ts = (List<T>) result;
            } else {
                ts = Utils.entityList((Class<T>) type, result);
            }
        }
        long count = getCMapper().selectCount(param);
        return new Page<T>(ts, count, skipped.getSkip() / skipped.getCount() + 1, skipped.getCount());
    }
}
