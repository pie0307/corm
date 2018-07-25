package pro.pie.me.corm;

import pro.pie.me.corm.c.Insert;
import pro.pie.me.corm.d.Delete;
import pro.pie.me.corm.d.DeleteParam;
import pro.pie.me.corm.mapper.CMapper;
import pro.pie.me.corm.model.IdEntity;
import pro.pie.me.corm.r.Select;
import pro.pie.me.corm.r.SelectMulti;
import pro.pie.me.corm.r.SelectParam;
import pro.pie.me.corm.u.Update;
import pro.pie.me.corm.u.UpdateParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Corm 使用API
 */
public class Corm {
    private static final Logger logger = LoggerFactory.getLogger(Corm.class);

    /**
     * 单表插入数据
     *
     * @param clazz 对应实体class
     */
    public static <T extends IdEntity> Insert<T> insert(Class<T> clazz) {
        return new CormProxy<T>(CormContext.getCMapper(true)).insert(clazz);
    }

    /**
     * 单表更新数据
     *
     * @param clazz 对应实体class
     */
    public static <T extends IdEntity> Update<T> update(Class<T> clazz) {
        return new CormProxy<T>(CormContext.getCMapper(true), new UpdateParam()).update(clazz);
    }

    /**
     * 单表删除数据
     *
     * @param clazz 对应实体class
     */
    public static <T extends IdEntity> Delete delete(Class<T> clazz) {
        return new CormProxy<T>(CormContext.getCMapper(true), new DeleteParam()).delete(clazz);
    }

    /**
     * 单表查询数据
     *
     * @param clazz 对应实体class
     */
    public static <T extends IdEntity> Select<T> select(Class<T> clazz) {
        return new CormProxy<T>(CormContext.getCMapper(), new SelectParam()).select(clazz);
    }

    /**
     * 多表查询数据
     *
     * @param clazz 对应实体class
     * @param alias 对应实体别名
     */
    public static SelectMulti selectMulti(Class<? extends IdEntity> clazz, String alias) {
        return new CormProxy<>(CormContext.getCMapper(), new SelectParam()).selectMulti(clazz, alias);
    }

    /**
     * 指定master操作数据
     * 在事务中，需要调用该方法，强制指定到master上操作
     */
    public static void master() {
        CormContext.IS_MASTER.set(true);
    }

    /**
     * 释放指定master操作
     * 在事务方法调用完成后，调用commit结束
     */
    public static void commit() {
        CormContext.IS_MASTER.remove();
    }

    public static <T extends IdEntity> CormProxy<T> switchM(CMapper mapper) {
        return new CormProxy<T>(mapper);
    }
}
