package com.ziroom.bsrd.dao.itf;

import com.ziroom.bsrd.basic.vo.SuperVO;

/**
 * @author chengys4
 *         2017-12-07 19:00
 **/
public interface IFillingDefault {
    public static final String INSERT = "insert";
    public static final String UPDATE = "update";

    void filling(String type, SuperVO superVO);
}
