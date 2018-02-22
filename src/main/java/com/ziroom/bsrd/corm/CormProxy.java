package com.ziroom.bsrd.corm;

import com.ziroom.bsrd.basic.vo.IdEntity;
import com.ziroom.bsrd.corm.c.Insert;
import com.ziroom.bsrd.corm.d.Delete;
import com.ziroom.bsrd.corm.d.DeleteParam;
import com.ziroom.bsrd.corm.mapper.CMapper;
import com.ziroom.bsrd.corm.r.Select;
import com.ziroom.bsrd.corm.r.SelectMulti;
import com.ziroom.bsrd.corm.r.SelectParam;
import com.ziroom.bsrd.corm.u.Update;
import com.ziroom.bsrd.corm.u.UpdateParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by cheshun on 17/9/28.
 */
public class CormProxy<T extends IdEntity> {

    private static final Logger logger = LoggerFactory.getLogger(CormProxy.class);

    private CMapper cMapper;

    private SelectParam selectParam;

    private UpdateParam updateParam;

    private DeleteParam deleteParam;

    public CormProxy(CMapper cMapper, SelectParam selectParam) {
        this.cMapper = cMapper;
        this.selectParam = selectParam;
    }

    public CormProxy(CMapper cMapper, UpdateParam updateParam) {
        this.cMapper = cMapper;
        this.updateParam = updateParam;
    }

    public CormProxy(CMapper cMapper, DeleteParam deleteParam) {
        this.cMapper = cMapper;
        this.deleteParam = deleteParam;
    }

    public CormProxy(CMapper cMapper) {
        this.cMapper = cMapper;
    }

    /**
     * 单表插入数据
     *
     * @param clazz 对应实体class
     */
    public Insert<T> insert(Class<T> clazz) {
        logger.debug("insert into class {}.", clazz);
        return new Insert<>(cMapper);
    }

    /**
     * 单表更新数据
     *
     * @param clazz 对应实体class
     */
    public Update<T> update(Class<T> clazz) {
        if (updateParam == null) {
            updateParam = new UpdateParam();
        }
        updateParam.setClazz(clazz);
        return new Update<>(cMapper, updateParam);
    }

    /**
     * 单表删除数据
     *
     * @param clazz 对应实体class
     */
    public Delete delete(Class<T> clazz) {
        if (deleteParam == null) {
            deleteParam = new DeleteParam();
        }
        deleteParam.setClazz(clazz);
        return new Delete(cMapper, deleteParam);
    }

    /**
     * 单表查询数据
     *
     * @param clazz 对应实体class
     */
    public Select<T> select(Class<T> clazz) {
        if (selectParam == null) {
            selectParam = new SelectParam();
        }
        selectParam.setResultType(clazz);
        selectParam.addClazzMap(clazz, "");
        return new Select<>(cMapper, selectParam);
    }

    /**
     * 多表查询数据
     *
     * @param clazz 对应实体class
     * @param alias 对应实体别名
     */
    public SelectMulti selectMulti(Class<? extends IdEntity> clazz, String alias) {
        if (selectParam == null) {
            selectParam = new SelectParam();
        }
        selectParam.setResultType(Map.class);
        selectParam.addClazzMap(clazz, alias);
        return new SelectMulti(cMapper, selectParam);
    }
}
