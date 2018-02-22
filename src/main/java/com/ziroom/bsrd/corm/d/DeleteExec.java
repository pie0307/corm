package com.ziroom.bsrd.corm.d;

import com.ziroom.bsrd.corm.mapper.CMapper;

/**
 * 删除数据提交操作
 * Created by cheshun on 17/9/26.
 */
public interface DeleteExec {

    /**
     * 执行数据库操作
     *
     * @return 操作数
     */
    default int exec() {
        return getCMapper().delete(getParam());
    }

    CMapper getCMapper();

    DeleteParam getParam();
}
