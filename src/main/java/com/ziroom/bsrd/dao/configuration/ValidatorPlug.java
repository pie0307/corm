package com.ziroom.bsrd.dao.configuration;

import com.ziroom.bsrd.basic.constant.ErrorCode;
import com.ziroom.bsrd.basic.exception.BusinessException;
import com.ziroom.bsrd.basic.vo.SuperVO;
import com.ziroom.bsrd.dao.itf.IValidate;
import com.ziroom.bsrd.validator.ValidatorToolUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * @author chengys4
 *         2017-12-07 18:57
 **/
public class ValidatorPlug implements IValidate {
    @Override
    public void validate(SuperVO superVO) {
        String errormage = ValidatorToolUtil.getInstance().validate(superVO);
        if (StringUtils.isNotBlank(errormage)) {
            throw new BusinessException(ErrorCode.PARAMS_VALIDA_ERROR, errormage);
        }
    }
}
