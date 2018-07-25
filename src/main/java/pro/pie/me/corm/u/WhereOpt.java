package pro.pie.me.corm.u;

import pro.pie.me.corm.mapper.CMapper;
import pro.pie.me.corm.model.Condition;

import java.util.Arrays;
import java.util.Collection;

public class WhereOpt {

    private CMapper cMapper;

    private UpdateParam param;

    public WhereOpt(CMapper cMapper, UpdateParam param) {
        this.cMapper = cMapper;
        this.param = param;
    }

    public UpdateWhere where(Condition condition) {
        param.addCondition(condition);
        return new UpdateWhere(cMapper, param);
    }

    public UpdateWhere where(Collection<Condition> conditions) {
        param.addCondition(conditions);
        return new UpdateWhere(cMapper, param);
    }

    public UpdateWhere where(Condition... conditions) {
        param.addCondition(Arrays.asList(conditions));
        return new UpdateWhere(cMapper, param);
    }
}
