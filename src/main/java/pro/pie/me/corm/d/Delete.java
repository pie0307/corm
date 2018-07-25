package pro.pie.me.corm.d;

import pro.pie.me.corm.mapper.CMapper;
import pro.pie.me.corm.model.Condition;

import java.util.Arrays;
import java.util.Collection;

/**
 * Delete
 */
public class Delete implements DeleteExec {

    private CMapper cMapper;

    private DeleteParam param;

    public Delete(CMapper cMapper, DeleteParam param) {
        this.cMapper = cMapper;
        this.param = param;
    }

    public DeleteWhere where(Condition condition) {
        param.addCondition(condition);
        return new DeleteWhere(cMapper, param);
    }

    public DeleteWhere where(Collection<Condition> conditions) {
        param.addCondition(conditions);
        return new DeleteWhere(cMapper, param);
    }

    public DeleteWhere where(Condition... conditions) {
        param.addCondition(Arrays.asList(conditions));
        return new DeleteWhere(cMapper, param);
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
