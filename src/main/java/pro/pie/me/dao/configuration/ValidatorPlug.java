package pro.pie.me.dao.configuration;

import pro.pie.me.exception.BusinessException;
import pro.pie.me.exception.ErrorCode;
import pro.pie.me.corm.model.SuperModel;
import pro.pie.me.dao.itf.IValidate;
import pro.pie.me.utils.ValidatorToolUtil;
import org.apache.commons.lang3.StringUtils;

public class ValidatorPlug implements IValidate {
    @Override
    public void validate(SuperModel superModel) {
        String errormage = ValidatorToolUtil.getInstance().validate(superModel);
        if (StringUtils.isNotBlank(errormage)) {
            throw new BusinessException(ErrorCode.PARAMS_VALIDA_ERROR, errormage);
        }
    }
}
