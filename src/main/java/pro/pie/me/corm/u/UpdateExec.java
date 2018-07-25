package pro.pie.me.corm.u;

import pro.pie.me.corm.mapper.CMapper;

/**
 * 提交操作
 */
public interface UpdateExec {

    default int exec() {
        UpdateParam param = getParam();
        return getCMapper().update(param);
    }

    UpdateParam getParam();

    CMapper getCMapper();
}
