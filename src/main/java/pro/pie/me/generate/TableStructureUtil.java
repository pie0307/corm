package pro.pie.me.generate;

import com.google.common.collect.Lists;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import pro.pie.me.corm.annotation.Column;
import pro.pie.me.corm.annotation.Comment;
import pro.pie.me.corm.annotation.Id;
import pro.pie.me.corm.annotation.Table;
import pro.pie.me.corm.model.IdEntity;
import pro.pie.me.corm.model.SuperModel;
import pro.pie.me.exception.DaoException;
import pro.pie.me.utils.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


/**
 * 读取所有表结构的数据生成建表语句
 **/
public class TableStructureUtil {

    public static TableInfo getTableInfo(Class<? extends SuperModel> classModel) {

        TableInfo tableInfo = new TableInfo(getTableName(classModel).value());
        tableInfo.setComment(getTableComment(classModel));

        List<ColumnDescribe> columnDescribes = new LinkedList<>();

        try {
            //该entity所有属性
            List<Field> fieldList = Lists.newArrayList();

            if (IdEntity.class.isAssignableFrom(classModel)) {
                fieldList.addAll(Arrays.asList(IdEntity.class.getDeclaredFields()));
            }

            //本类字段
            Field[] fields = classModel.getDeclaredFields();
            if (fields == null || fields.length == 0) {
                throw new DaoException(classModel.getName() + " have no property");
            }
            fieldList.addAll(Arrays.asList(fields));


            //父类BaseEntity字段
            if (SuperModel.class.isAssignableFrom(classModel)) {
                fieldList.addAll(Arrays.asList(SuperModel.class.getDeclaredFields()));
            }


            for (Field field : fieldList) {
                if (isFinalOrStatic(field)) {
                    continue;
                }

                ColumnDescribe columnDescribe = new ColumnDescribe();
                columnDescribe.setField(field.getName());
                columnDescribe.setColumn(getColumnName(field));
                columnDescribe.setComment(getColumnComment(field));
                columnDescribe.setLength(getColumnLength(field));
                columnDescribe.setRequire(getColumnRequire(field));
                columnDescribe.setType(getColumnType(field));
                //判断是否为主键
                if (IdEntity.ID_PN.equals(field.getName())) {
                    columnDescribe.setId(true);
                    tableInfo.setPrimaryKey(columnDescribe.getColumn());
                }
                columnDescribes.add(columnDescribe);
                tableInfo.setColumnDescribeList(columnDescribes);
            }
        } catch (Exception e) {
            throw new DaoException("生成sql异常", e);
        }
        return tableInfo;


    }

    public static String toSql(TableInfo tableInfo) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DROP TABLE IF EXISTS ").append("`").append(tableInfo.getTableName()).append("`;");
        stringBuilder.append("CREATE TABLE ").append("`").append(tableInfo.getTableName()).append("` (");
        for (ColumnDescribe columnDescribe : tableInfo.getColumnDescribeList()) {
            stringBuilder.append("`").append(columnDescribe.getColumn()).append("` ").append(columnDescribe.getType()).append(" ");
            if (columnDescribe.isRequire()) {
                stringBuilder.append(" NOT NULL ");
            }
            if (columnDescribe.isId()) {
                stringBuilder.append(" AUTO_INCREMENT ");
            }
            stringBuilder.append(" Comment '").append(columnDescribe.getComment()).append("' ,");
        }
        stringBuilder.append("PRIMARY KEY (`" + tableInfo.getPrimaryKey() + "`)");
        stringBuilder.append(") ENGINE=" + tableInfo.getEngine() + " AUTO_INCREMENT=1 DEFAULT CHARSET=" + tableInfo.getDefaultCharset() + " Comment='" + tableInfo.getComment() + "';");

        return stringBuilder.toString();
    }

    private static String getColumnType(Field field) {
        Class<?> c = field.getType();
        if (c.getName().equals(Date.class.getName())) {
            return "datetime";
        }
        if (c.getName().equals(String.class.getName())) {
            return "varchar(255)";
        }
        if ("int".equals(c.getName())) {
            return "int(11)";
        }
        if (c.getName().equals(Integer.class.getName())) {
            return "int(11)";
        }
        if ("long".equals(c.getName())) {
            return "bigint(20)";
        }
        if (c.getName().equals(Long.class.getName())) {
            return "bigint(20)";
        }
        if ("double".equals(c.getName())) {
            return "decimal(20,8)";
        }
        if (c.getName().equals(Double.class.getName())) {
            return "decimal(20,8)";
        }
        return "varchar(255)";
    }

    private static boolean getColumnRequire(Field field) {
        NotBlank notBlank = field.getAnnotation(NotBlank.class);
        if (notBlank == null) {
            return false;
        }
        return true;
    }


    private static int getColumnLength(Field field) {
        Length length = field.getAnnotation(Length.class);
        if (length != null) {
            if (length.max() > 255) {
                return 255;
            } else {
                return length.max();
            }
        }
        return 255;
    }

    private static boolean id(ColumnDescribe columnDescribe, Field field) {
        Id id = field.getAnnotation(Id.class);
        if (id != null) {
            columnDescribe.setStrategy(id.value());
            return true;
        }
        return false;
    }

    private static Table getTableName(Class<? extends SuperModel> classModel) {
        Table tableAnnotation = classModel.getAnnotation(Table.class);
        if (tableAnnotation == null) {
            throw new RuntimeException("not table ann");
        }
        return tableAnnotation;
    }

    private static String getTableComment(Class<? extends SuperModel> classModel) {
        Comment CommentAnnotation = classModel.getAnnotation(Comment.class);
        if (CommentAnnotation == null) {
            return classModel.getSimpleName();
        }
        return CommentAnnotation.value();
    }

    /**
     * 获取字段的编码
     *
     * @param field
     * @return
     */
    public static String getColumnName(Field field) {
        String propertyName = field.getName();
        Column columnAnnotation = field.getAnnotation(Column.class);
        if (columnAnnotation == null || StringUtils.isBlank(columnAnnotation.value())) {
            //column注解没有值,采用驼峰法取字段名
            return StringUtils.underscoreName(propertyName);
        } else {
            //使用column注解定义的字段名
            return columnAnnotation.value().toLowerCase();
        }
    }

    /**
     * 获取字段的注释
     *
     * @param field
     * @return
     */
    public static String getColumnComment(Field field) {
        Comment columnAnnotation = field.getAnnotation(Comment.class);
        if (columnAnnotation != null && StringUtils.isNotBlank(columnAnnotation.value())) {
            return columnAnnotation.value();
        } else {
            return field.getName();
        }
    }

    /**
     * 判断某个field是否常量或静态变量
     *
     * @param field
     * @return
     */
    public static boolean isFinalOrStatic(Field field) {
        int modifiers = field.getModifiers();
        return Modifier.isFinal(modifiers) || Modifier.isStatic(modifiers);
    }
}
