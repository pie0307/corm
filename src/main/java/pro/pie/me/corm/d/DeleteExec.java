package pro.pie.me.corm.d;

import pro.pie.me.corm.mapper.CMapper;

/**
 * 删除数据提交操作
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
