package com.ziroom.bsrd.corm.annotation;

import java.lang.annotation.*;

/**
 * Created by cheshun on 17/12/4.
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CormIgnore {
}
