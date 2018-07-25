package pro.pie.me.corm.u;

import pro.pie.me.corm.model.Condition;
import pro.pie.me.corm.model.Field;
import pro.pie.me.corm.model.IdEntity;

import java.util.*;

public class UpdateParam {

    private Class<? extends IdEntity> clazz;

    private IdEntity entity;

    private boolean isAppointUpdate;

    private boolean useNull;

    private List<Condition> conditionList;

    private Set<Field> fieldList;

    public Class<? extends IdEntity> getClazz() {
        return clazz;
    }

    public void setClazz(Class<? extends IdEntity> clazz) {
        this.clazz = clazz;
    }

    public IdEntity getEntity() {
        return entity;
    }

    public void setEntity(IdEntity entity) {
        this.entity = entity;
    }

    public void addCondition(Condition condition) {
        if (conditionList == null) {
            conditionList = new ArrayList<>();
        }
        conditionList.add(condition);
    }

    public void addCondition(Collection<Condition> conditions) {
        if (conditionList == null) {
            conditionList = new ArrayList<>();
        }
        conditionList.addAll(conditions);
    }

    public void addField(Field field) {
        if (fieldList == null) {
            fieldList = new HashSet<>();
        }
        fieldList.add(field);
        isAppointUpdate = true;
    }

    public void setUseNull(boolean useNull) {
        this.useNull = useNull;
    }

    public boolean isAppointUpdate() {
        return isAppointUpdate;
    }

    public boolean isUseNull() {
        return useNull;
    }

    public List<Condition> getConditionList() {
        return conditionList;
    }

    public Set<Field> getFieldList() {
        return fieldList;
    }
}
