package com.ziroom.bsrd.dao.configuration;

import com.ziroom.bsrd.basic.constant.ErrorCode;
import com.ziroom.bsrd.basic.exception.BusinessException;
import com.ziroom.bsrd.basic.vo.SuperModel;
import com.ziroom.bsrd.basic.vo.SuperVO;
import com.ziroom.bsrd.core.ApplicationEnvironment;
import com.ziroom.bsrd.dao.itf.IFillingDefault;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * @author chengys4
 *         2017-12-07 19:01
 **/
public class DefaultFilling implements IFillingDefault {


    @Override
    public void filling(String type, SuperVO superVO) {
        if (superVO instanceof SuperModel) {
            SuperModel superModel = (SuperModel) superVO;
            if (INSERT.equals(type)) {
                superModel.setCreateCode(ApplicationEnvironment.getEmpCode());
                superModel.setCreateName(ApplicationEnvironment.getUserName());
                superModel.setCreateTime(new Date());
                if (StringUtils.isBlank(superModel.getCityCode())) {
                    superModel.setCityCode(ApplicationEnvironment.getCityCode());
                }
                superModel.setLastModifyCode(ApplicationEnvironment.getEmpCode());
                superModel.setLastModifyName(ApplicationEnvironment.getUserName());
                superModel.setIsDel(0);
                superModel.setLastModifyTime(new Date());
                if (StringUtils.isBlank(superModel.getCityCode())) {
                    throw new BusinessException(ErrorCode.PARAMS_VALIDA_ERROR, "cityCode is null");
                }
            }
            if (UPDATE.equals(type)) {
                superModel.setLastModifyCode(ApplicationEnvironment.getEmpCode());
                superModel.setLastModifyName(ApplicationEnvironment.getUserName());
                superModel.setLastModifyTime(new Date());
            }
        } else {
            superVO.setIsDel(0);
            if (StringUtils.isBlank(superVO.getCityCode())) {
                superVO.setCityCode(ApplicationEnvironment.getCityCode());
            }
        }

    }
}
