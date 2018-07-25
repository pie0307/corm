package pro.pie.me.corm.c;

import pro.pie.me.corm.mapper.CMapper;
import pro.pie.me.corm.model.IdEntity;

public class IncIdOpt<T extends IdEntity> {

    private CMapper cMapper;

    public IncIdOpt(CMapper cMapper) {
        this.cMapper = cMapper;
    }

    public int obj(T t) {
        return cMapper.insertIncId(t);
    }
}
