package pro.pie.me.dao.itf;


import pro.pie.me.corm.model.SuperModel;

public interface IFillingDefault {
    public static final String INSERT = "insert";
    public static final String UPDATE = "update";

    void filling(String type, SuperModel superModel);
}
