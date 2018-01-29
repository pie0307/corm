package com.ziroom.bsrd.dao.itf;

import com.ziroom.bsrd.basic.exception.BusinessException;
import com.ziroom.bsrd.basic.vo.SuperModel;
import com.ziroom.bsrd.corm.opt.Condition;
import com.ziroom.bsrd.corm.opt.OrderBy;
import com.ziroom.bsrd.corm.opt.Page;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 通用数据访问接口
 *
 * @author chengys4
 *         2017-11-21 10:27
 **/
public interface IDataAccess {

    SuperModel save(final SuperModel superModel) throws BusinessException;

    SuperModel insert(final SuperModel superModel) throws BusinessException;

    List<? extends SuperModel> insert(final List<? extends SuperModel> superModels) throws BusinessException;

    SuperModel[] insert(final SuperModel vo[]) throws BusinessException;

    int update(final SuperModel superModel) throws BusinessException;

    int update(final SuperModel superModel, String[] updateFields) throws BusinessException;

    int[] update(final List<? extends SuperModel> superModels) throws BusinessException;

    int[] update(final List<? extends SuperModel> superModels, String[] updateFields) throws BusinessException;

    int[] update(final SuperModel[] superVOs, String[] fields) throws BusinessException;

    int updateCas(Class<? extends SuperModel> className, String targetFile, Object targetValue, Object oldValue, Condition condition) throws BusinessException;

    int update(Class<? extends SuperModel> className, String targetFile, Object targetValue, List<Condition> conditions) throws BusinessException;

    int update(String sql, Object... params) throws BusinessException;

    int update(Class<? extends SuperModel> className, String[] targetFields, Object[] targetValues, List<Condition> conditions) throws BusinessException;


    <T extends SuperModel> T queryByPK(Class<T> className, Serializable pk) throws BusinessException;

    <T extends SuperModel> Page<T> queryByPage(Class<T> clazz, List<Condition> conditions, List<OrderBy> orderBys, int pn, int pz) throws BusinessException;

    Page<Map<String, Object>> queryMapByPage(Class clazz, String[] selectFields, List<Condition> conditions, List<OrderBy> orderBys, int pn, int pz) throws BusinessException;

    <T extends SuperModel> Page<T> queryByPage(Class<T> clazz, List<Condition> conditions, int pn, int pz) throws BusinessException;

    <T extends SuperModel> List<T> queryByClause(Class<T> className, List<Condition> conditionList) throws BusinessException;

    <T extends SuperModel> List<T> queryByClause(Class<T> className, String[] selectFields, List<Condition> conditionList) throws BusinessException;

    <T extends SuperModel> List<T> queryByClause(Class<T> className, String[] selectFields, List<Condition> conditionList, List<OrderBy> orderByList) throws BusinessException;

    List<Map<String, Object>> queryMapByClause(Class className, String[] selectFields, List<Condition> conditionList) throws BusinessException;

    List<Map<String, Object>> queryMapByClause(Class className, String[] selectFields, List<Condition> conditionList, List<OrderBy> orderByList) throws BusinessException;

    <T extends SuperModel> List<T> queryByClause(Class<T> className, Condition... conditions) throws BusinessException;

    <T extends SuperModel> List<T> queryByClause(Class<T> className, List<Condition> conditions, List<OrderBy> orderBy) throws BusinessException;

    <T extends SuperModel> List<T> queryByIds(Class<T> clazz, List<Long> ids) throws BusinessException;

    <T extends SuperModel> List<T> queryByIds(Class<T> clazz, String[] selectFields, List<Long> ids) throws BusinessException;

    <T extends SuperModel> T queryOne(Class<T> clazz, Condition... condition) throws BusinessException;

    <T extends SuperModel> T queryOne(Class<T> clazz, String[] selectFields, Condition... condition) throws BusinessException;

    <T extends SuperModel> T queryOne(Class<T> clazz, List<Condition> conditions) throws BusinessException;

    <T extends SuperModel> T queryOne(Class<T> clazz, String[] selectFields, List<Condition> conditions) throws BusinessException;

    Map<String, Object> queryOneMap(Class clazz, String[] selectFields, List<Condition> conditions) throws BusinessException;

    Map<String, Object> queryOneMap(Class clazz, String[] selectFields, Condition... condition) throws BusinessException;


    <T> List<T> queryList(String sql, Class<T> clazz, Object... params) throws BusinessException;

    <T> T queryOne(String sql, Class<T> clazz, Object... params) throws BusinessException;

    int delete(Class clazz, Condition... condition);
}
