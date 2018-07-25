package pro.pie.me.corm.result;

import pro.pie.me.corm.r.SelectParam;

import java.util.Map;

/**
 * SumResult
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
