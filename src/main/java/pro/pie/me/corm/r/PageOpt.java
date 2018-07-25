package pro.pie.me.corm.r;


import pro.pie.me.corm.mapper.CMapper;
import pro.pie.me.corm.result.PageResult;

public class PageOpt<T> implements PageResult<T> {

    private CMapper cMapper;

    private SelectParam param;

    public PageOpt(CMapper cMapper, SelectParam param) {
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
