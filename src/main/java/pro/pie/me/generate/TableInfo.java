package pro.pie.me.generate;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 数据库表信息
 **/
@Getter
@Setter
public class TableInfo {

    private String tableName;
    private String primaryKey = "id";
    private String[] foreignKey;
    private String engine = "InnoDB";
    private String comment;
    private String defaultCharset = "utf8mb4";
    private List<ColumnDescribe> columnDescribeList;

    public TableInfo(String tableName) {
        this.tableName = tableName;
    }

    public TableInfo(String tableName, String comment) {
        this.tableName = tableName;
        this.comment = comment;
    }


}
