package com.ziroom.bsrd.corm.u;

import com.ziroom.bsrd.basic.vo.Condition;
import com.ziroom.bsrd.basic.vo.Field;
import com.ziroom.bsrd.basic.vo.IdEntity;
import com.ziroom.bsrd.corm.mapper.CMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by cheshun on 17/8/9.
 */
public class AppointUpdate<T> implements UpdateExec {
    private static final Logger logger = LoggerFactory.getLogger(AppointUpdate.class);

    private CMapper cMapper;

    private UpdateParam param;

    public AppointUpdate(CMapper cMapper, UpdateParam param) {
        this.cMapper = cMapper;
        this.param = param;
    }

    void setField(Field field, Object value) {
        param.addField(field);

        IdEntity entity = param.getEntity();
        if (entity == null) {
            try {
                entity = param.getClazz().newInstance();
                param.setEntity(entity);
            } catch (InstantiationException | IllegalAccessException e) {
                logger.error("Can not new instance class[{}]", param.getClazz().getName(), e);
                throw new RuntimeException("set field exception");
            }
        }

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(param.getClazz());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                if (!property.getName().equals(field.getName())) {
                    continue;
                }
                Method setter = property.getWriteMethod();
                if (setter != null) {
                    setter.invoke(entity, value);
                    break;
                }
            }
        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            logger.error("Can not set field[{}]", field.getName(), e);
            throw new RuntimeException("set field exception");
        }
    }

    public AppointUpdate<T> field(Field field, Object value) {
        setField(field, value);
        return this;
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

    @Override
    public UpdateParam getParam() {
        return param;
    }

    @Override
    public CMapper getCMapper() {
        return cMapper;
    }
}
