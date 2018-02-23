package com.ziroom.bsrd.dao.service;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.ziroom.bsrd.basic.BeanHelper;
import com.ziroom.bsrd.basic.BeanUtils;
import com.ziroom.bsrd.basic.Predef;
import com.ziroom.bsrd.basic.constant.ErrorCode;
import com.ziroom.bsrd.basic.exception.BusinessException;
import com.ziroom.bsrd.basic.exception.DaoException;
import com.ziroom.bsrd.basic.vo.*;
import com.ziroom.bsrd.corm.CService;
import com.ziroom.bsrd.corm.Corm;
import com.ziroom.bsrd.corm.r.Select;
import com.ziroom.bsrd.corm.u.AppointUpdate;
import com.ziroom.bsrd.corm.u.Update;
import com.ziroom.bsrd.dao.itf.IDataAccess;
import com.ziroom.bsrd.dao.itf.IFillingDefault;
import com.ziroom.bsrd.dao.itf.IValidate;
import com.ziroom.bsrd.dao.mapper.BeanListRowMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 通用数据访问服务
 *
 * @author chengys4
 *         2017-11-21 10:27
 **/
public class DataAccessService implements IDataAccess {

    private CService cService;

    private JdbcTemplate jdbcTemplate;

    private IValidate validate;

    private IFillingDefault fillingDefault;

    public void setcService(CService cService) {
        this.cService = cService;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public IValidate getValidate() {
        return validate;
    }

    public void setValidate(IValidate validate) {
        this.validate = validate;
    }

    public IFillingDefault getFillingDefault() {
        return fillingDefault;
    }

    public void setFillingDefault(IFillingDefault fillingDefault) {
        this.fillingDefault = fillingDefault;
    }

    @Override
    public SuperVO save(SuperVO superVO) throws BusinessException {
        Preconditions.checkNotNull(superVO, "插入实体不能为空");
        if (Predef.toLong(superVO.getId()) > 0) {
            update(superVO);
            return superVO;
        } else {
            return insert(superVO);
        }
    }

    @Override
    public SuperVO insert(SuperVO superVO) throws BusinessException {
        Preconditions.checkNotNull(superVO, "插入实体不能为空");
        filling(IFillingDefault.INSERT, superVO);
        validate(superVO);
        cService.save(superVO);
        return superVO;
    }

    private void filling(String type, SuperVO superVO) {
        fillingDefault.filling(type, superVO);
    }

    @Override
    public List<? extends SuperVO> insert(List<? extends SuperVO> superVOs) throws BusinessException {
        Preconditions.checkNotNull(superVOs, "插入实体不能为空");
        for (SuperVO superVO : superVOs) {
            insert(superVO);
        }
        return superVOs;
    }

    @Override
    public SuperVO[] insert(SuperVO[] superVOs) throws BusinessException {
        Preconditions.checkNotNull(superVOs, "插入实体不能为空");
        for (SuperVO superVO : superVOs) {
            insert(superVO);
        }
        return superVOs;
    }

    @Override
    public int update(SuperVO superVO) throws BusinessException {
        Preconditions.checkNotNull(superVO, "更新实体类不能为空");
        filling(IFillingDefault.UPDATE, superVO);
        validate(superVO);
        return cService.update(superVO);
    }

    private void validate(SuperVO superVO) {
        if (validate != null) {
            validate.validate(superVO);
        }
    }

    @Override
    public int update(SuperVO superVO, String[] fields) throws BusinessException {
        Preconditions.checkNotNull(fields, "更新字段不能为空");
        Preconditions.checkNotNull(superVO, "更新实体类不能为空");
        return update(new SuperVO[]{superVO}, fields)[0];
    }

    private Object getValue(SuperVO superVO, String field) {
        Object vaule = null;
        try {
            vaule = BeanHelper.getProperty(superVO, field);
        } catch (Exception e) {
            throw new DaoException("", e);
        }
        return vaule;
    }

    @Override
    public int[] update(List<? extends SuperVO> superVOs) throws BusinessException {
        Preconditions.checkNotNull(superVOs, "更新实体类不能为空");
        int[] r = new int[superVOs.size()];
        for (int i = 0; i < superVOs.size(); i++) {
            r[i] = cService.update(superVOs.get(i));
        }
        return r;
    }

    @Override
    public int[] update(List<? extends SuperVO> vos, String[] fields) throws BusinessException {
        Preconditions.checkNotNull(fields, "更新字段不能为空");
        Preconditions.checkNotNull(vos, "更新实体类不能为空");
        return update(vos.toArray(new SuperVO[vos.size()]), fields);
    }

    @Override
    public int[] update(SuperVO[] superVOs, String[] fields) throws BusinessException {
        Preconditions.checkNotNull(fields, "更新字段不能为空");
        Preconditions.checkNotNull(superVOs, "更新实体类不能为空");
        int[] r = new int[superVOs.length];
        for (int i = 0; i < superVOs.length; i++) {
            SuperVO superVO = superVOs[i];
            if (StringUtils.isBlank(superVO.getCityCode())) {
                throw new BusinessException(ErrorCode.PARAM_REQUIRED, "cityCode is null");
            }
            validate(superVO);
            Update updateA = Corm.update(superVO.getClass());
            AppointUpdate appointUpdate = updateA.field(Field.of(fields[0]), getValue(superVO, fields[0]));
            if (fields.length > 1) {
                for (int j = 1; j < fields.length; j++) {
                    appointUpdate = appointUpdate.field(Field.of(fields[j]), getValue(superVO, fields[j]));
                }
            }
            if(fillingDefault!=null){
                filling(IFillingDefault.UPDATE, superVO);
            }
            if (superVO instanceof SuperModel) {
                SuperModel superModel = (SuperModel) superVO;
                appointUpdate = appointUpdate.field(Field.of("lastModifyCode"), superModel.getLastModifyCode());
                appointUpdate = appointUpdate.field(Field.of("lastModifyName"), superModel.getLastModifyName());
                appointUpdate = appointUpdate.field(Field.of("lastModifyTime"), superModel.getLastModifyTime());
            }
            r[i] = appointUpdate.where(Condition.eq("id", getValue(superVO, "id"))).exec();
        }
        return r;
    }

    @Override
    public int updateCas(Class<? extends SuperVO> className, String targetFile, Object targetValue, Object oldValue, Condition condition) throws BusinessException {
        Preconditions.checkNotNull(condition, "更新条件不能为空");
        Preconditions.checkNotNull(targetFile, "更新字段不能为空");
        Preconditions.checkNotNull(targetValue, "更新值不能为空");
        SuperVO superVO = new SuperVO();
        if(fillingDefault!=null){
            filling(IFillingDefault.UPDATE, superVO);
        }
        AppointUpdate<? extends SuperVO> appointUpdate = Corm.update(className)
                .field(Field.of(targetFile), targetValue);

        if (superVO instanceof SuperModel) {
            SuperModel superModel = (SuperModel) superVO;
            appointUpdate = appointUpdate.field(Field.of("lastModifyCode"), superModel.getLastModifyCode());
            appointUpdate = appointUpdate.field(Field.of("lastModifyName"), superModel.getLastModifyName());
            appointUpdate = appointUpdate.field(Field.of("lastModifyTime"), superModel.getLastModifyTime());
        }
        appointUpdate.where(condition).and(Condition.eq(targetFile, oldValue));
        return appointUpdate.exec();
    }

    @Override
    public int update(Class<? extends SuperVO> className, String targetFile, Object targetValue, List<Condition> conditions) throws BusinessException {
        Preconditions.checkNotNull(conditions, "更新条件不能为空");
        Preconditions.checkNotNull(targetFile, "更新字段不能为空");
        Preconditions.checkNotNull(targetValue, "更新值不能为空");
        SuperVO superVO = new SuperVO();
        if(fillingDefault!=null){
            filling(IFillingDefault.UPDATE, superVO);
        }
        AppointUpdate<? extends SuperVO> appointUpdate = Corm.update(className)
                .field(Field.of(targetFile), targetValue);

        if (superVO instanceof SuperModel) {
            SuperModel superModel = (SuperModel) superVO;
            appointUpdate = appointUpdate.field(Field.of("lastModifyCode"), superModel.getLastModifyCode());
            appointUpdate = appointUpdate.field(Field.of("lastModifyName"), superModel.getLastModifyName());
            appointUpdate = appointUpdate.field(Field.of("lastModifyTime"), superModel.getLastModifyTime());
        }

        appointUpdate.where(conditions);
        return appointUpdate.exec();
    }

    @Override
    public int update(Class<? extends SuperVO> className, String[] targetFields, Object[] targetValues, List<Condition> conditions) throws BusinessException {
        Preconditions.checkNotNull(conditions, "更新条件不能为空");
        Preconditions.checkNotNull(targetFields, "更新字段不能为空");
        Preconditions.checkNotNull(targetValues, "更新值不能为空");
        Preconditions.checkArgument(targetFields.length == targetValues.length, "字段个数和值个数不一致");
        SuperVO superVO = new SuperVO();
        if(fillingDefault!=null){
            filling(IFillingDefault.UPDATE, superVO);
        }
        AppointUpdate<? extends SuperVO> appointUpdate = Corm.update(className)
                .field(Field.of("isDel"), superVO.getIsDel());

        if (superVO instanceof SuperModel) {
            SuperModel superModel = (SuperModel) superVO;
            appointUpdate = appointUpdate.field(Field.of("lastModifyCode"), superModel.getLastModifyCode());
            appointUpdate = appointUpdate.field(Field.of("lastModifyName"), superModel.getLastModifyName());
            appointUpdate = appointUpdate.field(Field.of("lastModifyTime"), superModel.getLastModifyTime());
        }

        for (int i = 0; i < targetFields.length; i++) {
            appointUpdate.field(Field.of(targetFields[i]), targetValues[i]);
        }
        appointUpdate.where(conditions);
        return appointUpdate.exec();
    }

    @Override
    public int update(String sql, Object... params) throws BusinessException {
        throw new RuntimeException("not impl");
    }

    @Override
    public <T extends SuperVO> T queryByPK(Class<T> className, Serializable pk) throws BusinessException {
        Preconditions.checkNotNull(pk, "主键不能为空");
        return cService.find(className, (Long) pk);
    }

    @Override
    public <T extends SuperVO> Page<T> queryByPage(Class<T> clazz, List<Condition> conditions, List<OrderBy> orderBys, int pn, int pz) throws BusinessException {
        if (orderBys == null) {
            orderBys = new ArrayList<>();
            orderBys.add(OrderBy.desc("id"));
        }
        return cService.findPage(clazz, parseCondition(conditions), orderBys, pn, pz);
    }

    @Override
    public Page<Map<String, Object>> queryMapByPage(Class clazz, String[] selectFields, List<Condition> conditions, List<OrderBy> orderBys, int pn, int pz) throws BusinessException {
        return null;
    }


    @Override
    public <T extends SuperVO> Page<T> queryByPage(Class<T> clazz, List<Condition> conditions, int pn, int pz) throws BusinessException {
        return queryByPage(clazz, parseCondition(conditions), Lists.newArrayList(OrderBy.desc("id")), pn, pz);
    }

    @Override
    public <T extends SuperVO> List<T> queryByClause(Class<T> className, List<Condition> conditionList) throws BusinessException {
        conditionList = parseCondition(conditionList);
        return cService.findList(className, conditionList.toArray(new Condition[conditionList.size()]));
    }

    @Override
    public <T extends SuperVO> List<T> queryByClause(Class<T> className, String[] selectFields, List<Condition> conditionList) throws BusinessException {

        return queryByClause(className, selectFields, conditionList, null);
    }

    @Override
    public <T extends SuperVO> List<T> queryByClause(Class<T> className, String[] selectFields, List<Condition> conditionList, List<OrderBy> orderByList) throws BusinessException {
        List<Map<String, Object>> data = queryMapByClause(className, selectFields, conditionList, orderByList);
        if (Predef.size(data) > 0) {
            List<T> superVOs = new ArrayList<>();
            for (Map<String, Object> item : data) {
                T superVO = BeanUtils.mapToBean(item, className);
                superVOs.add(superVO);
            }
            return superVOs;
        }
        return new ArrayList<>();
    }

    @Override
    public List<Map<String, Object>> queryMapByClause(Class className, String[] selectFields, List<Condition> conditionList) throws BusinessException {
        return queryMapByClause(className, selectFields, conditionList, null);
    }

    @Override
    public List<Map<String, Object>> queryMapByClause(Class className, String[] selectFields, List<Condition> conditionList, List<OrderBy> orderByList) throws BusinessException {

        Select select = Corm.select(className);

        if (conditionList == null) {
            throw new BusinessException(ErrorCode.PARAM_REQUIRED, "查询条件为空", null);
        }
        if (selectFields != null) {
            List<Field> fields = new ArrayList<>();
            for (String field : selectFields) {
                fields.add(Field.of(field));
            }
            select.field(fields);
        }
        if (conditionList != null) {
            select.where(conditionList);
        }
        if (orderByList != null) {
            select.orderBy(orderByList);
        }

        List<Map<String, Object>> data = select.list();
        return data;
    }

    @Override
    public <T extends SuperVO> List<T> queryByClause(Class<T> className, Condition... conditions) throws BusinessException {
        return cService.findList(className, parseCondition(conditions));
    }

    @Override
    public <T extends SuperVO> List<T> queryByIds(Class<T> clazz, List<Long> ids) {
        return cService.findList(clazz, ids);
    }

    @Override
    public <T extends SuperVO> List<T> queryByIds(Class<T> clazz, String[] selectFields, List<Long> ids) {
        return queryByClause(clazz, selectFields, Lists.newArrayList(Condition.in(IdEntity.ID_PN, ids)));
    }

    @Override
    public <T extends SuperVO> T queryOne(Class<T> classz, Condition... condition) {
        return cService.find(classz, parseCondition(condition));
    }

    @Override
    public <T extends SuperVO> T queryOne(Class<T> clazz, String[] selectFields, Condition... condition) throws BusinessException {
        if (selectFields == null) {
            List<T> data = queryByClause(clazz, condition);
            if (Predef.size(data) > 0) {
                return data.get(0);
            }
        } else {
            List<Map<String, Object>> data = queryMapByClause(clazz, selectFields, Arrays.asList(condition), null);
            if (Predef.size(data) > 0) {
                T superVO = BeanUtils.mapToBean(data.get(0), clazz);
                return superVO;
            }
        }
        return null;
    }

    @Override
    public <T extends SuperVO> T queryOne(Class<T> clazz, List<Condition> conditions) throws BusinessException {
        return queryOne(clazz, null, conditions.toArray(new Condition[conditions.size()]));
    }

    @Override
    public <T extends SuperVO> T queryOne(Class<T> clazz, String[] selectFields, List<Condition> conditions) throws BusinessException {
        return queryOne(clazz, selectFields, conditions.toArray(new Condition[conditions.size()]));
    }

    @Override
    public Map<String, Object> queryOneMap(Class clazz, String[] selectFields, List<Condition> conditions) throws BusinessException {
        return queryOneMap(clazz, selectFields, conditions.toArray(new Condition[conditions.size()]));
    }

    @Override
    public Map<String, Object> queryOneMap(Class clazz, String[] selectFields, Condition... condition) throws BusinessException {
        if (selectFields == null) {
            throw new BusinessException(ErrorCode.PARAM_REQUIRED, "selectFields not null");
        }
        List<Map<String, Object>> data = queryMapByClause(clazz, selectFields, Arrays.asList(condition));
        if (Predef.size(data) > 0) {
            return data.get(0);
        }
        return null;
    }

    @Override
    public <T> List<T> queryList(String sql, Class<T> clazz, Object... params) throws BusinessException {

        if (clazz.equals(Long.class)) {
            return jdbcTemplate.queryForList(sql, params, clazz);
        }
        if (clazz.equals(Integer.class)) {
            return jdbcTemplate.queryForList(sql, params, clazz);
        }
        if (clazz.equals(String.class)) {
            return jdbcTemplate.queryForList(sql, params, clazz);
        }
        return jdbcTemplate.queryForObject(sql, new BeanListRowMapper<>(clazz), params);
    }

    @Override
    public <T> T queryOne(String sql, Class<T> clazz, Object... params) throws BusinessException {
        List<T> data = queryList(sql, clazz, params);
        if (Predef.size(data) > 0) {
            return data.get(0);
        }
        return null;
    }

    @Override
    public <T extends SuperVO> List<T> queryByClause(Class<T> className, List<Condition> conditions, List<OrderBy> orderBy) {
        return cService.findList(className, parseCondition(conditions), orderBy);
    }


    private Condition[] parseCondition(Condition... conditions) {
        List<Condition> conditions1 = Arrays.asList(conditions);
        return parseCondition(conditions1).toArray(new Condition[conditions.length]);
    }

    private List<Condition> parseCondition(List<Condition> conditions) {
        if (conditions == null) {
            return null;
        }
        for (Condition condition : conditions) {
            if (condition.getOperator().equals(Condition.Operator.like)) {
                if (condition.getValue() == null) {
                    continue;
                }
                if (condition.getValue().toString().endsWith("%")) {
                    continue;
                }
                if (!condition.getValue().toString().endsWith("%")) {
                    condition.setValue(condition.getValue() + "%");
                }
            }
        }
        return conditions;
    }

    @Override
    public int delete(Class clazz, Condition... conditions) {
        List<Condition> conditions1 = Arrays.asList(conditions);
        return Corm.delete(clazz).where(conditions1).exec();

    }
}
