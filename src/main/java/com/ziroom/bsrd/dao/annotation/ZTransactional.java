package com.ziroom.bsrd.dao.annotation;

import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.*;

/**
 * 自定义事务注解
 *
 * @author chengys4
 *         2017-10-30 16:54
 **/
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Transactional(rollbackFor = Exception.class)
public @interface ZTransactional {
}
