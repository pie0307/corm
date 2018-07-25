package pro.pie.me.corm.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Column {
    /**
     * 字段名
     */
    String value() default "";

    /**
     * 对应jdbc类型
     */
    String jdbcType() default "";

    /**
     * 说明
     */
    String comment() default "";
}
