package pro.pie.me.corm.c;

import pro.pie.me.corm.mapper.CMapper;
import pro.pie.me.corm.model.IdEntity;

/**
 * Insert
 */
public class Insert<T extends IdEntity> {

    private CMapper cMapper;

    public Insert(CMapper cMapper) {
        this.cMapper = cMapper;
    }

    /**
     * 保存数据对象
     * 需要设置数据主键
     */
    public int obj(T t) {
        return cMapper.insert(t);
    }

    /**
     * 数据库自增主键方式需要调用
     */
    public IncIdOpt<T> incId() {
        return new IncIdOpt<>(cMapper);
    }

}
