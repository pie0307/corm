package pro.pie.me.corm;

import pro.pie.me.constant.DialectEnum;
import pro.pie.me.corm.annotation.Table;
import pro.pie.me.corm.mapper.CMapper;
import pro.pie.me.corm.r.Select;
import pro.pie.me.corm.r.SumOpt;
import pro.pie.me.corm.u.Update;
import pro.pie.me.corm.model.*;

import java.util.List;

/**
 * C Service
 */
@SuppressWarnings("unchecked")
public class CService {

    private DialectEnum dialectEnum;

    private CMapper mapper;

    public CService() {
    }

    public CService(CMapper mapper) {
        this.mapper = mapper;
    }

    public CService(CMapper mapper, DialectEnum dialectEnum) {
        this.mapper = mapper;
        this.dialectEnum = dialectEnum;
    }

    /**
     * 保存实体
     *
     * @param entity 实体对象
     * @return int
     */
    public <T extends IdEntity> int save(T entity) {
        if (DialectEnum.ORACLE.equals(dialectEnum)) {
            Class<? extends IdEntity> entityClass = entity.getClass();
            Table table = entityClass.getAnnotation(Table.class);
            long id = mapper.selectSeq(table.comment());
            entity.setId(id);
            return mapper.insert(entity);
        }

        return Corm.switchM(mapper).insert((Class<IdEntity>) entity.getClass()).incId().obj(entity);
    }

    /**
     * 更新实体
     *
     * @param entity 实体对象
     * @return int
     */
    public <T extends IdEntity> int update(T entity) {
        return Corm.switchM(mapper).update((Class<IdEntity>) entity.getClass()).obj(entity);
    }

    /**
     * 更新实体,{useNUll=true}时可以把对应字段更新为null
     *
     * @param entity  实体对象
     * @param useNull 是否要更新null字段
     * @return int
     */
    public <T extends IdEntity> int update(T entity, boolean useNull) {
        if (useNull) {
            return Corm.switchM(mapper).update((Class<IdEntity>) entity.getClass()).useNull().obj(entity);
        }
        return update(entity);
    }

    /**
     * 条件更新
     *
     * @param entity     实体对象
     * @param conditions 条件 {@link Condition}
     * @return int
     */
    public <T extends IdEntity> int update(T entity, Condition... conditions) {
        return Corm.switchM(mapper).update((Class<IdEntity>) entity.getClass()).entity(entity).where(conditions).exec();
    }

    /**
     * 条件更新
     *
     * @param entity     实体对象
     * @param useNull    是否要更新null字段
     * @param conditions 条件 {@link Condition}
     * @return int
     */
    public <T extends IdEntity> int update(T entity, boolean useNull, Condition... conditions) {
        Update update = Corm.switchM(mapper).update((Class<IdEntity>) entity.getClass());
        if (useNull) {
            update.useNull();
        }
        return update.entity(entity).where(conditions).exec();
    }

    /**
     * 查询单个实体对象
     *
     * @param id id
     * @return entity
     */
    public <T extends IdEntity> T find(Class<T> clazz, Long id) {
        return (T) Corm.switchM(mapper).select((Class<IdEntity>) clazz).where(Condition.eq(IdEntity.ID_PN, id)).one();
    }

    /**
     * 查询单个实体对象
     *
     * @param conditions 条件 {@link Condition}
     * @return entity
     */
    public <T extends IdEntity> T find(Class<T> clazz, Condition... conditions) {
        return (T) Corm.switchM(mapper).select((Class<IdEntity>) clazz).where(conditions).one();
    }

    /**
     * 查询所有实体列表
     *
     * @return list
     */
    public <T extends IdEntity> List<T> findAll(Class<T> clazz) {
        return (List<T>) Corm.switchM(mapper).select((Class<IdEntity>) clazz).list();
    }

    /**
     * 查询实体列表
     *
     * @param ids 一组id
     * @return list
     */
    public <T extends IdEntity> List<T> findList(Class<T> clazz, List<Long> ids) {
        return (List<T>) Corm.switchM(mapper).select((Class<IdEntity>) clazz)
                .where(Condition.in(IdEntity.ID_PN, ids)).list();
    }

    /**
     * 查询实体列表
     *
     * @param conditions 条件 {@link Condition}
     * @return list
     */
    public <T extends IdEntity> List<T> findList(Class<T> clazz, Condition... conditions) {
        Select select = Corm.switchM(mapper).select((Class<IdEntity>) clazz);
        for (Condition condition : conditions) {
            select.where(condition);
        }
        return select.list();
    }

    /**
     * 查询实体列表
     *
     * @param conditions 条件 {@link Condition}
     * @param orderBys   排序 {@link OrderBy}
     * @return list
     */
    public <T extends IdEntity> List<T> findList(Class<T> clazz, List<Condition> conditions, List<OrderBy> orderBys) {
        Select select = Corm.switchM(mapper).select((Class<IdEntity>) clazz);
        if (conditions != null) {
            select.where(conditions);
        }
        if (orderBys != null) {
            select.orderBy(orderBys);
        }
        return select.list();
    }

    /**
     * 查询实体列表
     *
     * @param count      数量
     * @param conditions 条件 {@link Condition}
     * @param orderBys   排序 {@link OrderBy}
     * @return list
     */
    public <T extends IdEntity> List<T> findList(Class<T> clazz, List<Condition> conditions, List<OrderBy> orderBys, int count) {
        Select select = Corm.switchM(mapper).select((Class<IdEntity>) clazz);
        select.limit(0, count);
        if (conditions != null) {
            select.where(conditions);
        }
        if (orderBys != null) {
            select.orderBy(orderBys);
        }
        return select.list();
    }

    /**
     * 查询实体列表
     *
     * @param conditions 条件 {@link Condition}
     * @param orderBys   排序 {@link Skipped}
     * @param skipped    skipped {@link Skipped}
     * @return list
     */
    public <T extends IdEntity> List<T> findList(Class<T> clazz, List<Condition> conditions, List<OrderBy> orderBys, Skipped skipped) {
        Select select = Corm.switchM(mapper).select((Class<IdEntity>) clazz);
        if (skipped != null) {
            select.limit(skipped.getSkip(), skipped.getCount());
        }
        if (conditions != null) {
            select.where(conditions);
        }
        if (orderBys != null) {
            select.orderBy(orderBys);
        }
        return select.list();
    }

    /**
     * 查询 Page 对象
     *
     * @return page {@link Page}
     */
    public <T extends IdEntity> Page<T> findPage(Class<T> clazz, int pn, int pz) {
        Select select = Corm.switchM(mapper).select((Class<IdEntity>) clazz);
        return select.pageable(pn, pz).page();
    }

    public <T extends IdEntity> Page<T> findPage(Class<T> clazz, List<Condition> conditions, List<OrderBy> orderBys, int pn, int pz) {
        Select select = Corm.switchM(mapper).select((Class<IdEntity>) clazz);
        if (conditions != null) {
            select.where(conditions);
        }
        if (orderBys != null) {
            select.orderBy(orderBys);
        }
        return select.pageable(pn, pz).page();
    }

    /**
     * 删除实体
     *
     * @param id id
     * @return int
     */
    public <T extends IdEntity> int delete(Class<T> clazz, Long id) {
        return Corm.switchM(mapper).delete((Class<IdEntity>) clazz).where(Condition.eq(IdEntity.ID_PN, id)).exec();
    }

    /**
     * 删除一组实体
     *
     * @param ids 一组id
     * @return int
     */
    public <T extends IdEntity> int delete(Class<T> clazz, List<Long> ids) {
        return Corm.switchM(mapper).delete((Class<IdEntity>) clazz).where(Condition.in(IdEntity.ID_PN, ids)).exec();
    }

    /**
     * 查询实体数量
     *
     * @return count
     */
    public <T extends IdEntity> long count(Class<T> clazz) {
        return Corm.switchM(mapper).select((Class<IdEntity>) clazz).count().number();
    }

    /**
     * 查询实体数量
     *
     * @param conditions 条件 {@link Condition}
     * @return count
     */
    public <T extends IdEntity> long count(Class<T> clazz, List<Condition> conditions) {
        Select select = Corm.switchM(mapper).select((Class<IdEntity>) clazz);
        if (conditions != null) {
            select.where(conditions);
        }
        return select.count().number();
    }

    /**
     * 查询实体数量
     *
     * @param conditions 条件 {@link Condition}
     * @return count
     */
    public <T extends IdEntity> long count(Class<T> clazz, Condition... conditions) {
        Select select = Corm.switchM(mapper).select((Class<IdEntity>) clazz);
        if (conditions != null) {
            select.where(conditions);
        }
        return select.count().number();
    }

    /**
     * 某属性求和
     *
     * @param sumProperty 属性名
     * @return {@link Number}
     */
    public <T extends IdEntity> Number sum(Class<T> clazz, String sumProperty) {
        return Corm.switchM(mapper).select((Class<IdEntity>) clazz).sum(Field.of(sumProperty)).number();
    }

    /**
     * 某属性求和
     *
     * @param sumProperty 属性名
     * @param conditions  条件 {@link Condition}
     * @return {@link Number}
     */
    public <T extends IdEntity> Number sum(Class<T> clazz, String sumProperty, List<Condition> conditions) {
        SumOpt opt = Corm.switchM(mapper).select((Class<IdEntity>) clazz).sum(Field.of(sumProperty));
        if (conditions != null) {
            opt.where(conditions);
        }
        return opt.number();
    }

    /**
     * 某属性求和
     *
     * @param sumProperty 属性名
     * @param conditions  条件 {@link Condition}
     * @return {@link Number}
     */
    public <T extends IdEntity> Number sum(Class<T> clazz, String sumProperty, Condition... conditions) {
        SumOpt opt = Corm.switchM(mapper).select((Class<IdEntity>) clazz).sum(Field.of(sumProperty));
        if (conditions != null) {
            for (Condition condition : conditions) {
                opt.where(condition);
            }
        }
        return opt.number();
    }

    /**
     * 实体是否存在
     *
     * @param id id
     * @return boolean
     */
    public <T extends IdEntity> boolean exist(Class<T> clazz, Long id) {
        long count = count(clazz, Condition.eq(IdEntity.ID_PN, id));
        return count > 0;
    }

    /**
     * 实体是否存在
     *
     * @param conditions 条件 {@link Condition}
     * @return boolean
     */
    public <T extends IdEntity> boolean exist(Class<T> clazz, Condition... conditions) {
        long count = count(clazz, conditions);
        return count > 0;
    }
}
