package pro.pie.me.generate;

import lombok.Getter;
import lombok.Setter;

/**
 * 数据库列描述信息
 **/
@Getter
@Setter
public class ColumnDescribe {

    private boolean id;

    private String strategy;
    /**
     * 是否必须
     */
    private boolean require;
    /**
     * 字段编码
     */
    private String field;
    /**
     * 中文名
     */
    private String comment;
    /**
     * 列名
     */
    private String column;
    /**
     * 类型
     */
    private String type;
    /**
     * 长度
     */
    private int length;

}
