package pro.pie.me.corm.mapper;

import pro.pie.me.corm.d.DeleteParam;
import pro.pie.me.corm.model.IdEntity;
import pro.pie.me.corm.model.Skipped;
import pro.pie.me.corm.r.SelectParam;
import pro.pie.me.corm.u.UpdateParam;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface CMapper {

    /**
     * 插入数据,实体对象必须是指定id的
     *
     * @param t   实体对象,必须继承{@link IdEntity}
     * @param <T> 实体泛型
     * @return 操作数
     */
    @InsertProvider(type = CSqlBuilder.class, method = "insert")
    <T extends IdEntity> int insert(T t);

    /**
     * 插入数据,设置自增id（目前只适用于MySQL）
     *
     * @param t   实体对象,必须继承{@link IdEntity}
     * @param <T> 实体泛型
     * @return 操作数
     */
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = long.class)
    @InsertProvider(type = CSqlBuilder.class, method = "insert")
    <T extends IdEntity> int insertIncId(T t);

    /**
     * 插入数据,设置自增id（oracle）
     *
     * @param t   实体对象,必须继承{@link IdEntity}
     * @param <T> 实体泛型
     * @return 操作数
     */
    @SelectKey(statement = "select SEQ_LOG_ZR_CHANGE.NEXTVAL as id from dual", keyProperty = "id", before = true, resultType = long.class)
    @InsertProvider(type = CSqlBuilder.class, method = "insert")
    <T extends IdEntity> int insertOracleIncId(T t);

    /**
     * 更新数据
     *
     * @param param 更新参数
     * @return 操作数
     */
    @UpdateProvider(type = CSqlBuilder.class, method = "update")
    int update(@Param("param") UpdateParam param);

    /**
     * 删除数据
     *
     * @param param 删除参数
     * @return 操作数
     */
    @DeleteProvider(type = CSqlBuilder.class, method = "delete")
    int delete(@Param("param") DeleteParam param);

    /**
     * 查询列表数据
     *
     * @param param   查询参数
     * @param skipped skip
     * @return list
     */
    @SelectProvider(type = CSqlBuilder.class, method = "selectMapList")
    List<Map<String, Object>> selectMapListPage(@Param("param") SelectParam param, @Param("skipped") Skipped skipped);

    /**
     * 查询条件下的数据条数
     *
     * @param param 查询参数
     * @return num
     */
    @SelectProvider(type = CSqlBuilder.class, method = "selectCount")
    long selectCount(@Param("param") SelectParam param);

    /**
     * 查询sum函数
     *
     * @param param 查询参数
     * @return sum
     */
    @SelectProvider(type = CSqlBuilder.class, method = "selectSum")
    Map<String, Object> selectSum(@Param("param") SelectParam param);

    /**
     * 获取序列id
     *
     * @param seq 序列
     * @return 序列id
     */
    @SelectProvider(type = CSqlBuilder.class, method = "selectSeq")
    long selectSeq(@Param("param") String seq);
}
