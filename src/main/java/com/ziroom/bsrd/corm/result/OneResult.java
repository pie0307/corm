package com.ziroom.bsrd.corm.result;

import com.ziroom.bsrd.basic.vo.Skipped;
import com.ziroom.bsrd.corm.Utils;
import com.ziroom.bsrd.corm.r.SelectParam;

import java.util.List;
import java.util.Map;

/**
 * OneResult
 * Created by cheshun on 17/8/23.
 */
public interface OneResult<T> extends IResult {

    @SuppressWarnings("unchecked")
    default T one() {
        SelectParam param = getParam();
        List<Map<String, Object>> result = getCMapper().selectMapListPage(param, new Skipped(0, 1));
        if (result == null || result.isEmpty()) {
            return null;
        }
        Class<?> type = param.getResultType();
        if (type == Map.class) {
            return (T) result.get(0);
        }
        return Utils.hashToObject(result.get(0), (Class<T>) type);
    }
}
