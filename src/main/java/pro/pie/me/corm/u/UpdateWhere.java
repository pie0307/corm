package pro.pie.me.corm.u;

import pro.pie.me.corm.mapper.CMapper;
import pro.pie.me.corm.model.Condition;

public class UpdateWhere implements UpdateExec {

    private CMapper cMapper;

    private UpdateParam param;

    public UpdateWhere(CMapper cMapper, UpdateParam param) {
        this.cMapper = cMapper;
        this.param = param;
    }

    public UpdateWhere and(Condition condition) {
        param.addCondition(condition);
        return this;
    }

    @Override
    public UpdateParam getParam() {
        return param;
    }

    @Override
    public CMapper getCMapper() {
        return cMapper;
    }
}
