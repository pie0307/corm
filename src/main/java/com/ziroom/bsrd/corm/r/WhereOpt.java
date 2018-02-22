package com.ziroom.bsrd.corm.r;

import com.ziroom.bsrd.basic.vo.Condition;
import com.ziroom.bsrd.basic.vo.OrderBy;
import com.ziroom.bsrd.corm.mapper.CMapper;
import com.ziroom.bsrd.corm.result.ListResult;
import com.ziroom.bsrd.corm.result.OneResult;

import java.util.Collection;

/**
 * Created by cheshun on 17/8/23.
 */
public class WhereOpt<T> implements OneResult<T>, ListResult<T> {

    private CMapper cMapper;

    private SelectParam param;

    public WhereOpt(CMapper cMapper, SelectParam param) {
        this.cMapper = cMapper;
        this.param = param;
    }

    public WhereOpt<T> and(Condition condition) {
        param.addCondition(condition);
        return this;
    }

    public OrderByOpt<T> orderBy(OrderBy orderBy) {
        param.addOrderBy(orderBy);
        return new OrderByOpt<>(cMapper, param);
    }

    public OrderByOpt<T> orderBy(Collection<OrderBy> orderBys) {
        param.addOrderBy(orderBys);
        return new OrderByOpt<>(cMapper, param);
    }

    public LimitOpt<T> limit(int skip, int size) {
        param.limit(skip, size);
        return new LimitOpt<>(cMapper, param);
    }

    public PageOpt<T> pageable(int pn, int pz) {
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
