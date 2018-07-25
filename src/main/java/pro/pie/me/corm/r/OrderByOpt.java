package pro.pie.me.corm.r;

import pro.pie.me.corm.mapper.CMapper;
import pro.pie.me.corm.model.OrderBy;
import pro.pie.me.corm.result.ListResult;
import pro.pie.me.corm.result.OneResult;

public class OrderByOpt<T> implements OneResult<T>, ListResult<T> {

    private CMapper cMapper;

    private SelectParam param;

    public OrderByOpt(CMapper cMapper, SelectParam param) {
        this.cMapper = cMapper;
        this.param = param;
    }

    public OrderByOpt<T> orderBy(OrderBy orderBy) {
        param.addOrderBy(orderBy);
        return this;
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
