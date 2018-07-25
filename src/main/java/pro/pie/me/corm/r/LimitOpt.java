package pro.pie.me.corm.r;

import pro.pie.me.corm.mapper.CMapper;
import pro.pie.me.corm.result.ListResult;

public class LimitOpt<T> implements ListResult<T> {

    private CMapper cMapper;

    private SelectParam param;

    public LimitOpt(CMapper cMapper, SelectParam param) {
        this.cMapper = cMapper;
        this.param = param;
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
