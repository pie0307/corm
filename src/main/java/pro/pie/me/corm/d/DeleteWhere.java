package pro.pie.me.corm.d;

import pro.pie.me.corm.mapper.CMapper;
import pro.pie.me.corm.model.Condition;

public class DeleteWhere implements DeleteExec {

    private CMapper cMapper;

    private DeleteParam param;

    public DeleteWhere(CMapper cMapper, DeleteParam param) {
        this.cMapper = cMapper;
        this.param = param;
    }

    public DeleteWhere and(Condition condition) {
        param.addCondition(condition);
        return this;
    }

    @Override
    public CMapper getCMapper() {
        return cMapper;
    }

    @Override
    public DeleteParam getParam() {
        return param;
    }
}
