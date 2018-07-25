package pro.pie.me.test;

import pro.pie.me.corm.annotation.Table;
import pro.pie.me.corm.model.IdEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(value = "LOG_ZR_CHANGE")
public class ZrChangeEntity extends IdEntity {

    /**
     * 失效的ID
     */
    private String ljId;
    /**
     * 失效的数据类型
     */
    private String ljInvalidType;
    /**
     * 失效数据的相关描述
     */
    private String ljInvalidDesc;
    /**
     * 有效的ID
     */
    private String zrId;
    /**
     * 有效描述
     */
    private String zrValidDesc;
    /**
     * 所属城市（国标）
     */
    private String cityCode;
    /**
     * 操作类型
     */
    private Integer operation;
    /**
     * 扩展信息
     */
    private String extInfo;
    /**
     * 数据来源
     */
    private String dataSources;
    /**
     * 变更人
     */
    private String changeName;
    /**
     * 变更人系统号
     */
    private String changeCode;
    /**
     * 变更人时间
     */
    private Long changeTime;
    /**
     * 消息ID
     */
    private String msgId;
    /**
     * 消息ID
     */
    private String remark;
}