package com.ziroom.bsrd.corm.r;

import com.ziroom.bsrd.basic.vo.Condition;
import com.ziroom.bsrd.basic.vo.Field;
import com.ziroom.bsrd.basic.vo.OrderBy;
import com.ziroom.bsrd.corm.mapper.CMapper;
import com.ziroom.bsrd.corm.result.ListResult;
import com.ziroom.bsrd.corm.result.OneResult;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * 查询Map结果
 * Created by cheshun on 17/8/11.
 */
public class SelectMap implements OneResult<Map<String, Object>>, ListResult<Map<String, Object>> {

    private CMapper cMapper;

    private SelectParam param;

    public SelectMap(CMapper cMapper, SelectParam param) {
        this.cMapper = cMapper;
        this.param = param;
    }

    public SelectMap field(Field field) {
        param.addField(field);
        return this;
    }

    public WhereOpt<Map<String, Object>> where(Condition condition) {
        param.addCondition(condition);
        return new WhereOpt<>(cMapper, param);
    }

    public WhereOpt<Map<String, Object>> where(Collection<Condition> condition) {
        param.addCondition(condition);
        return new WhereOpt<>(cMapper, param);
    }

    public WhereOpt<Map<String, Object>> where(Condition... condition) {
        param.addCondition(Arrays.asList(condition));
        return new WhereOpt<>(cMapper, param);
    }

    public OrderByOpt<Map<String, Object>> orderBy(OrderBy orderBy) {
        param.addOrderBy(orderBy);
        return new OrderByOpt<>(cMapper, param);
    }

    public OrderByOpt<Map<String, Object>> orderBy(Collection<OrderBy> orderBys) {
        param.addOrderBy(orderBys);
        return new OrderByOpt<>(cMapper, param);
    }

    public OrderByOpt<Map<String, Object>> orderBy(OrderBy... orderBys) {
        param.addOrderBy(Arrays.asList(orderBys));
        return new OrderByOpt<>(cMapper, param);
    }

    public LimitOpt<Map<String, Object>> limit(int skip, int size) {
        param.limit(skip, size);
        return new LimitOpt<>(cMapper, param);
    }

    public PageOpt<Map<String, Object>> pageable(int pn, int pz) {
        param.limit((pn - 1) * pz, pz);
        return new PageOpt<>(cMapper, param);
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
