package pro.pie.me.dao.service;

import org.apache.commons.lang3.StringUtils;
import pro.pie.me.common.ApplicationEnvironment;
import pro.pie.me.corm.model.SuperModel;
import pro.pie.me.dao.itf.IFillingDefault;
import pro.pie.me.exception.BusinessException;
import pro.pie.me.exception.ErrorCode;

import java.util.Date;

public class DefaultFilling implements IFillingDefault {

    private Boolean isFilling;

    public DefaultFilling(Boolean isFilling) {
        this.isFilling = isFilling;
    }

    @Override
    public void filling(String type, SuperModel superModel) {
        if (isFilling) {
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
            superModel.setIsDel(0);
        }
    }
}
