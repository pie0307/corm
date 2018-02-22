package com.ziroom.bsrd.corm.r;

import com.ziroom.bsrd.basic.vo.Condition;
import com.ziroom.bsrd.corm.mapper.CMapper;
import com.ziroom.bsrd.corm.result.CountResult;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by cheshun on 17/9/18.
 */
public class CountOpt implements CountResult {

    private CMapper cMapper;

    private SelectParam param;

    public CountOpt(CMapper cMapper, SelectParam param) {
        this.cMapper = cMapper;
        this.param = param;
    }

    public CountWhere where(Condition condition) {
        param.addCondition(condition);
        return new CountWhere(cMapper, param);
    }

    public CountWhere where(Collection<Condition> conditions) {
        param.addCondition(conditions);
        return this.new CountWhere(cMapper, param);
    }

    public CountWhere where(Condition... conditions) {
        param.addCondition(Arrays.asList(conditions));
        return this.new CountWhere(cMapper, param);
    }

    @Override
    public CMapper getCMapper() {
        return cMapper;
    }

    @Override
    public SelectParam getParam() {
        return param;
    }

    public class CountWhere implements CountResult {
        private CMapper cMapper;

        private SelectParam param;

        public CountWhere(CMapper cMapper, SelectParam param) {
            this.cMapper = cMapper;
            this.param = param;
        }

        public CountWhere and(Condition condition) {
            param.addCondition(condition);
            return this;
        }

        @Override
        public CMapper getCMapper() {
            return cMapper;
        }

        @Override
        public SelectParam getParam() {
            return param;
        }
    }

}
