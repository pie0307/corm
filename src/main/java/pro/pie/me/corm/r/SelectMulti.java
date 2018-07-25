package pro.pie.me.corm.r;

import pro.pie.me.corm.mapper.CMapper;
import pro.pie.me.corm.model.Field;
import pro.pie.me.corm.model.IdEntity;

import java.util.Collection;

public class SelectMulti {

    private CMapper cMapper;

    private SelectParam param;

    public SelectMulti(CMapper cMapper, SelectParam param) {
        this.cMapper = cMapper;
        this.param = param;
    }

    public SelectMulti entity(Class<? extends IdEntity> clazz, String alias) {
        param.addClazzMap(clazz, alias);
        return this;
    }

    public SelectMap field(Field field) {
        param.addField(field);
        return new SelectMap(cMapper, param);
    }

    public SelectMap field(Collection<Field> fields) {
        param.addField(fields);
        return new SelectMap(cMapper, param);
    }

    public SumOpt sum(Field field) {
        param.addField(field);
        return new SumOpt(cMapper, param);
    }

    public CountOpt count() {
        return new CountOpt(cMapper, param);
    }
}
