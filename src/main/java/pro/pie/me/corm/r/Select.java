package pro.pie.me.corm.r;

import pro.pie.me.corm.mapper.CMapper;
import pro.pie.me.corm.model.Condition;
import pro.pie.me.corm.model.Field;
import pro.pie.me.corm.model.OrderBy;
import pro.pie.me.corm.result.ListResult;
import pro.pie.me.corm.result.OneResult;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * 单表查询
 */
public class Select<T> implements OneResult<T>, ListResult<T> {

    private CMapper cMapper;

    private SelectParam param;

    public Select(CMapper cMapper, SelectParam param) {
        this.cMapper = cMapper;
        this.param = param;
    }

    /**
     * 指定字段查询
     *
     * @param field 字段名
     */
    public SelectMap field(Field field) {
        param.addField(field);
        param.setResultType(Map.class);
        return new SelectMap(cMapper, param);
    }

    public SelectMap field(Collection<Field> fields) {
        param.addField(fields);
        param.setResultType(Map.class);
        return new SelectMap(cMapper, param);
    }

    public SelectMap field(Field... fields) {
        param.addField(Arrays.asList(fields));
        param.setResultType(Map.class);
        return new SelectMap(cMapper, param);
    }

    /**
     * 查询 T 所有字段
     *
     * @param condition 条件
     */
    public WhereOpt<T> where(Condition condition) {
        param.addCondition(condition);
        return new WhereOpt<>(cMapper, param);
    }

    public WhereOpt<T> where(Collection<Condition> conditions) {
        param.addCondition(conditions);
        return new WhereOpt<>(cMapper, param);
    }

    public WhereOpt<T> where(Condition... conditions) {
        param.addCondition(Arrays.asList(conditions));
        return new WhereOpt<>(cMapper, param);
    }

    public SumOpt sum(Field field) {
        param.addField(field);
        return new SumOpt(cMapper, param);
    }

    public CountOpt count() {
        return new CountOpt(cMapper, param);
    }

    public LimitOpt<T> limit(int skip, int size) {
        param.limit(skip, size);
        return new LimitOpt<>(cMapper, param);
    }

    public PageOpt<T> pageable(int pn, int pz) {
        param.limit((pn - 1) * pz, pz);
        return new PageOpt<>(cMapper, param);
    }

    public OrderByOpt<T> orderBy(OrderBy orderBy) {
        param.addOrderBy(orderBy);
        return new OrderByOpt<>(cMapper, param);
    }

    public OrderByOpt<T> orderBy(Collection<OrderBy> orderBys) {
        param.addOrderBy(orderBys);
        return new OrderByOpt<>(cMapper, param);
    }

    public OrderByOpt<T> orderBy(OrderBy... orderBys) {
        param.addOrderBy(Arrays.asList(orderBys));
        return new OrderByOpt<>(cMapper, param);
    }

    @Override
    public CMapper getCMapper() {
        return cMapper;
    }

    @Override
    public SelectParam getParam() {
        return param;
    }
}
