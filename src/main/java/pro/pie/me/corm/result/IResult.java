package pro.pie.me.corm.result;

import pro.pie.me.corm.mapper.CMapper;
import pro.pie.me.corm.r.SelectParam;

public interface IResult {

    CMapper getCMapper();

    SelectParam getParam();
}
