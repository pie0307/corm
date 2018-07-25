package pro.pie.me.corm.result;

import pro.pie.me.corm.r.SelectParam;

/**
 * CountResult
 */
public interface CountResult extends IResult {

    default long number() {
        SelectParam param = getParam();
        return getCMapper().selectCount(param);
    }
}
