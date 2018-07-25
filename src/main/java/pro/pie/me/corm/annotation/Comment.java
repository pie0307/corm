package pro.pie.me.corm.annotation;


import java.lang.annotation.*;

/**
 * 标注类 方法 说明
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Comment {
    /**
     * 说明信息
     */
    String value();
}
