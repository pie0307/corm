package pro.pie.me.corm.u;

import pro.pie.me.corm.mapper.CMapper;
import pro.pie.me.corm.model.Condition;
import pro.pie.me.corm.model.IdEntity;

public class UseNullOpt<T extends IdEntity> {

    private CMapper cMapper;

    private UpdateParam param;

    public UseNullOpt(CMapper cMapper, UpdateParam param) {
        this.cMapper = cMapper;
        this.param = param;
    }

    public int obj(T t) {
        param.setEntity(t);
        param.addCondition(Condition.eq(IdEntity.ID_PN, t.getId()));
        return cMapper.update(param);
    }

    public WhereOpt entity(T t) {
        param.setEntity(t);
        param.addCondition(Condition.eq(IdEntity.ID_PN, t.getId()));
        return new WhereOpt(cMapper, param);
    }
}
