package pro.pie.me.corm.model;


import pro.pie.me.corm.annotation.Comment;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 业务实体
 **/
@Getter
@Setter
@ToString
public class SuperModel extends SuperVO {

    /**
     * 创建人标识可以存储UserId或UserCode
     */
    @Comment(value = "创建人Code")
    private String createCode;
    /**
     * 创建人名称
     */
    @Comment(value = "创建人名称")
    private String createName;
    /**
     * 创建时间
     */
    @Comment(value = "创建人时间")
    private Date createTime;
    /**
     * 最后修改人标识可以存储UserId或UserCode
     */
    @Comment(value = "最后修改人Code")
    private String lastModifyCode;
    /**
     * 最后修改人姓名
     */
    @Comment(value = "最后修改人姓名")
    private String lastModifyName;
    /**
     * 最后修改时间
     */
    @Comment(value = "最后修改时间")
    private Date lastModifyTime;


}
