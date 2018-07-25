package pro.pie.me.corm.model;


import pro.pie.me.corm.annotation.Comment;

import java.io.Serializable;

public class SuperVO extends IdEntity implements Serializable {

    /**
     * 城市编码
     */
    @Comment(value = "城市编码")
    private String cityCode;
    /**
     * 删除标记
     */
    @Comment(value = "逻辑删除：0未删除，1删除")
    private Integer isDel;

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }
}
