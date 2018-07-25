package pro.pie.me.test;


import pro.pie.me.corm.annotation.Table;
import pro.pie.me.corm.model.IdEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(value = "en_resblock")
public class ResblockEntity extends IdEntity {
    /**
     * 楼盘ID
     */
    private String resblockId;
    /**
     * 楼盘所属城区
     */
    private String districtId;
    /**
     * 楼盘名称
     */
    private String resblockName;
    /**
     * 楼盘别名
     */
    private String resblockAlias;
    /**
     * 楼盘名称首字字母
     */
    private String firstLetter;
    /**
     * 楼盘名称简拼
     */
    private String simpleSpell;
    /**
     * 楼盘名称全拼
     */
    private String fullSpell;
    /**
     * 行政地址
     */
    private String executiveAddress;
    /**
     * 产权地址
     */
    private String propertyAddress;
    /**
     * 楼栋数量
     */
    private Integer buildingCnt;
    /**
     * 房屋数量
     */
    private Integer houseCnt;
    /**
     * 所属城市（国标）
     */
    private String cityCode;
    /**
     * 数据来源
     */
    private String dataSources;
    /**
     * 创建时间
     */
    private long createTime;
    /**
     * 最后更新时间
     */
    private long updateTime;
    /**
     * 创建人姓名
     */
    private String createUserName;
    /**
     * 创建人系统号
     */
    private String createUserCode;
    /**
     * 修改人姓名
     */
    private String updateUserName;
    /**
     * 修改人系统号
     */
    private String updateUserCode;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 纬度
     */
    private String pointLat;
    /**
     * 经度
     */
    private String pointLng;
    /**
     * 地址描述
     */
    private String addrDesc;
}