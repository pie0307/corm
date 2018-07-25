package pro.pie.me.corm.result;

import pro.pie.me.corm.Utils;
import pro.pie.me.corm.r.SelectParam;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * ListResult
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
