package com.ziroom.bsrd.corm.r;

import com.ziroom.bsrd.basic.vo.Condition;
import com.ziroom.bsrd.corm.mapper.CMapper;
import com.ziroom.bsrd.corm.result.SumResult;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by cheshun on 17/8/23.
 */
public class SumOpt implements SumResult {

    private CMapper cMapper;

    private SelectParam param;

    public SumOpt(CMapper cMapper, SelectParam param) {
        this.cMapper = cMapper;
        this.param = param;
    }

    public SumWhere where(Condition condition) {
        param.addCondition(condition);
        return this.new SumWhere(cMapper, param);
    }

    public SumWhere where(Collection<Condition> conditions) {
        param.addCondition(conditions);
        return this.new SumWhere(cMapper, param);
    }

    public SumWhere where(Condition... conditions) {
        param.addCondition(Arrays.asList(conditions));
        return this.new SumWhere(cMapper, param);
    }

    @Override
    public CMapper getCMapper() {
        return cMapper;
    }

    @Override
    public SelectParam getParam() {
        return param;
    }

    public class SumWhere implements SumResult {
        private CMapper cMapper;

        private SelectParam param;

        public SumWhere(CMapper cMapper, SelectParam param) {
            this.cMapper = cMapper;
            this.param = param;
        }

        public SumWhere and(Condition condition) {
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
