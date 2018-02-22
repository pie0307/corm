package com.ziroom.bsrd.corm.result;

import com.ziroom.bsrd.corm.Utils;
import com.ziroom.bsrd.corm.r.SelectParam;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * ListResult
 * Created by cheshun on 17/8/23.
 */
public interface ListResult<T> extends IResult {

    @SuppressWarnings("unchecked")
    default List<T> list() {
        SelectParam param = getParam();
        List<Map<String, Object>> result = getCMapper().selectMapListPage(param, param.getSkipped());
        if (result == null || result.isEmpty()) {
            return Collections.emptyList();
        }
        Class<?> type = param.getResultType();
        if (type == Map.class) {
            return (List<T>) result;
        }
        return Utils.entityList((Class<T>) type, result);
    }
}
