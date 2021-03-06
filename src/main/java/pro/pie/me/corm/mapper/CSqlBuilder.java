package pro.pie.me.corm.mapper;

import com.google.common.collect.Lists;
import pro.pie.me.corm.d.DeleteParam;
import pro.pie.me.corm.model.IdEntity;
import pro.pie.me.corm.r.SelectParam;
import pro.pie.me.corm.u.UpdateParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static pro.pie.me.corm.mapper.SqlBuilderUtil.*;

public class CSqlBuilder {

    private static final Logger logger = LoggerFactory.getLogger(CSqlBuilder.class);

    public String insert(IdEntity obj) {
        Map<String, Class<?>> clazzMap = new HashMap<>();
        clazzMap.put("", obj.getClass());
        initEntities(clazzMap);
        String tableName = findTableName(obj.getClass());
        BEGIN();
        INSERT_INTO(tableName);
        VALUES(obj);
        return SQL();
    }

    public String update(Map<String, Object> parameter) {
        UpdateParam param = (UpdateParam) parameter.get("param");
        Map<String, Class<?>> clazzMap = new HashMap<>();
        clazzMap.put("", param.getClazz());
        initEntities(clazzMap);
        String tableName = findTableName(param.getClazz());
        BEGIN();
        UPDATE(tableName);
        if (param.isAppointUpdate()) {
            SET(param.getFieldList(), param.getEntity());
        } else {
            SET(param.getEntity(), param.isUseNull());
        }
        if (param.getConditionList() != null) {
            where(param.getConditionList());
        }
        return SQL();
    }

    public String delete(Map<String, Object> parameter) {
        DeleteParam param = (DeleteParam) parameter.get("param");
        Map<String, Class<?>> clazzMap = new HashMap<>();
        clazzMap.put("", param.getClazz());
        initEntities(clazzMap);
        String tableName = findTableName(param.getClazz());
        BEGIN();
        DELETE_FROM(tableName);
        if (param.getConditionList() != null) {
            where(param.getConditionList());
        }
        return SQL();
    }

    public String selectMapList(Map<String, Object> parameter) {
        SelectParam param = (SelectParam) parameter.get("param");
        Map<String, Class<?>> clazzMap = param.getClazzMap();
        initEntities(clazzMap);
        List<String> tableNameList = findTableNameList();
        BEGIN();
        if (param.isSelectMap()) {
            select(param.getFieldList());
        } else {
            selectAll();
        }
        FORM(tableNameList);
        if (param.getConditionList() != null) {
            where(param.getConditionList());
        }
        if (param.getOrderByList() != null) {
            orderBy(param.getOrderByList());
        }

        return SQL();
    }

    public String selectCount(Map<String, Object> parameter) {
        SelectParam param = (SelectParam) parameter.get("param");
        Map<String, Class<?>> clazzMap = param.getClazzMap();
        initEntities(clazzMap);
        List<String> tableNameList = findTableNameList();
        BEGIN();
        selectCountX();
        FORM(tableNameList);
        if (param.getConditionList() != null) {
            where(param.getConditionList());
        }
        return SQL();
    }

    public String selectSum(Map<String, Object> parameter) {
        SelectParam param = (SelectParam) parameter.get("param");
        Map<String, Class<?>> clazzMap = param.getClazzMap();
        initEntities(clazzMap);
        List<String> tableNameList = findTableNameList();
        BEGIN();
        selectSumX(param.getFieldList());
        FORM(tableNameList);
        if (param.getConditionList() != null) {
            where(param.getConditionList());
        }
        return SQL();
    }

    public String selectSeq(Map<String, Object> parameter) {
        String seq = (String) parameter.get("param");
        BEGIN();
        selectSeqX(seq);
        FORM(Lists.newArrayList("dual"));
        return SQL();
    }
}
