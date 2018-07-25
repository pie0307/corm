package pro.pie.me.corm.result;

import pro.pie.me.corm.Utils;
import pro.pie.me.corm.model.Skipped;
import pro.pie.me.corm.r.SelectParam;

import java.util.List;
import java.util.Map;

/**
 * OneResult
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
