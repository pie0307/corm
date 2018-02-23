package com.ziroom.bsrd.dao.util;


import com.ziroom.bsrd.basic.BeanConvertor;
import com.ziroom.bsrd.basic.BeanHelper;
import com.ziroom.bsrd.basic.vo.SuperModel;
import org.apache.commons.beanutils.Converter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;

/**
 * Created by IntelliJ IDEA. User: 贺扬 Date: 2005-1-14 Time: 15:10:03
 */
public class ProcessorUtils {

    /**
     * 结果集转换成数组
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    static public <T> Object[] toArray(ResultSet rs) throws SQLException {
        ResultSetMetaData meta = rs.getMetaData();
        int cols = meta.getColumnCount();
        Object[] result = new Object[cols];

        for (int i = 0; i < cols; i++) {
            result[i] = rs.getObject(i + 1);
        }

        return result;
    }

    /**
     * 结果集转换成HashMap
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    static public Map toMap(ResultSet rs) throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();
        int cols = metaData.getColumnCount();
        Map<String, Object> rsValues = new HashMap<>();
        for (int i = 1; i <= cols; i++) {
            Object value = getColumnValue(metaData.getColumnType(i), rs, i);
            rsValues.put(colmnToField(metaData.getColumnName(i)), value);
        }
        return rsValues;
    }

    private static String colmnToField(String clomnName) {
        if (clomnName == null) {
            return "";
        } else {
            clomnName = clomnName.toLowerCase();
            StringBuilder result = new StringBuilder();
            char[] chars = clomnName.toCharArray();

            for (int i = 0; i < chars.length; i++) {
                char t = chars[i];
                if (i > 0 && chars[i - 1] == '_') {
                    result.setLength(result.length() - 1);
                    result.append(Character.toUpperCase(t));
                } else {
                    result.append(t);
                }
            }
            return result.toString();
        }
    }

    /**
     * 结果集合转换成java bean
     *
     * @param resultSet
     * @param type
     * @return
     * @throws SQLException
     */
    static public <T> T toBean(ResultSet resultSet, Class<T> type) throws SQLException {
        if (resultSet == null)
            throw new SQLException("toBean(resultSet,type) : resultset is null");
        return toBeanInner(resultSet, type);
    }

    /*
     * 进行Bean的转换
     */
    private static <T> T toBeanInner(ResultSet resultSet, Class<T> type) throws SQLException {
        T target = null;
        MethodAndTypes methodAndTypes = getBeanInvokeAndTypes(type, resultSet, null);
        if (resultSet.next()) {
            target = newInstance(type);
            for (int i = 1; i <= methodAndTypes.types.length; i++) {
                Object value = getColumnValue(methodAndTypes.types[i - 1], resultSet, i);
                if (value == null)
                    continue;
                Method invoke = methodAndTypes.invokes[i - 1];
                if (invoke == null) {
                    // 如果是null则不赋值
                    continue;
                }
                Converter converter = methodAndTypes.converters[i - 1];
                if (converter != null)
                    value = converter.convert(invoke.getParameterTypes()[0], value);
                BeanHelper.invokeMethod(target, invoke, value);
            }
        } // end if
        return target;
    }

    /**
     * 结果集转换成java bean 数组
     *
     * @param resultSet
     * @param type
     * @return
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    static public <T> List<T> toBeanList(ResultSet resultSet, Class<T> type) throws SQLException {
        if (resultSet == null)
            throw new SQLException("toBeanList(resultSet,type) : resultset is null");
        return toBeanListInner(resultSet, type, null);
    }

    @SuppressWarnings("unchecked")
    private static <T> List<T> toBeanListInner(ResultSet resultSet, Class<T> type, String columns[]) throws SQLException {
        MethodAndTypes methodAndTypes = getBeanInvokeAndTypes(type, resultSet, columns);
        List list = new ArrayList();
        while (resultSet.next()) {
            Object target = newInstance(type);
            for (int i = 1; i <= methodAndTypes.types.length; i++) {
                Object value = getColumnValue(methodAndTypes.types[i - 1], resultSet, i);
                if (value == null)
                    continue;
                Method invoke = methodAndTypes.invokes[i - 1];
                if (invoke == null) {
                    // 如果是null则不赋值
                    continue;
                }
                Converter converter = methodAndTypes.converters[i - 1];
                if (converter != null)
                    value = converter.convert(invoke.getParameterTypes()[0], value);
                BeanHelper.invokeMethod(target, invoke, value);
            }
            list.add(target);
        }
        return list;
    }

    private static MethodAndTypes getBeanInvokeAndTypes(Class type, ResultSet resultSet, String[] columns) throws SQLException {
        MethodAndTypes retResult = new MethodAndTypes();

        Object bean = newInstance(type);
        boolean isSuperBean = false;
        if (bean instanceof SuperModel) {
            isSuperBean = true;
        }
        ResultSetMetaData metaData = resultSet.getMetaData();
        int cols = metaData.getColumnCount();
        Method[] invokes = new Method[cols];
        String[] colName = new String[cols];
        Converter[] converters = new Converter[cols];
        int[] types = new int[cols];
        for (int i = 0; i < cols; i++) {
            types[i] = metaData.getColumnType(i + 1);
            if (columns != null)
                colName[i] = columns[i];
            else
                colName[i] = metaData.getColumnName(i + 1);
            String propName = colName[i];
            if (isSuperBean) {
                invokes[i] = getSuperBeanInvokeMethod(bean, colmnToField(propName));
            } else {
                invokes[i] = BeanHelper.getMethod(bean, colmnToField(propName));
            }
            if (invokes[i] != null)
                converters[i] = BeanConvertor.getConVerter(invokes[i].getParameterTypes()[0]);
        }
        retResult.invokes = invokes;
        retResult.types = types;
        retResult.converters = converters;
        return retResult;
    }

    private static Method getSuperBeanInvokeMethod(Object bean, String colName) {

        Method m = BeanHelper.getMethod(bean, colName);
        if (m != null)
            return m;
        String pkFiledName = "id";
        if (pkFiledName == null)
            return null;
        pkFiledName = pkFiledName.toLowerCase();
        if (pkFiledName.equals(colName)) {
            return BeanHelper.getMethod(bean, "id");
        }
        return null;

    }

    @SuppressWarnings("unchecked")
    static public List toBeanList(ResultSet resultSet, Class type, int skip, int size) throws SQLException {
        MethodAndTypes methodAndTypes = getBeanInvokeAndTypes(type, resultSet, null);
        List list = new ArrayList();
        int index = -1;
        while (index < skip && resultSet.next()) {
            index++;
        }
        if (index < skip)
            return list;
        int offset = 0;
        do {
            offset++;
            Object target = newInstance(type);
            for (int i = 1; i <= methodAndTypes.types.length; i++) {
                Object value = getColumnValue(methodAndTypes.types[i - 1], resultSet, i);
                if (value == null)
                    continue;
                Method invoke = methodAndTypes.invokes[i - 1];
                if (invoke == null) {
                    // 如果是null则不赋值
                    continue;
                }
                Converter converter = methodAndTypes.converters[i - 1];
                if (converter != null) {
                    value = converter.convert(invoke.getParameterTypes()[0], value);
                }
                BeanHelper.invokeMethod(target, invoke, value);
            }
            list.add(target);
        } while (resultSet.next() && offset < size);

        return list;
    }

    /**
     * 结果集转换成向量集合
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    static public Vector toVector(ResultSet rs) throws SQLException {
        Vector v = new Vector();
        ResultSetMetaData rsmd = rs.getMetaData();
        int nColumnCount = rsmd.getColumnCount();

        while (rs.next()) {
            Vector tmpV = new Vector();
            for (int i = 1; i <= nColumnCount; i++) {
                Object o;
                if (rsmd.getColumnType(i) == Types.CHAR || rsmd.getColumnType(i) == Types.VARCHAR) {
                    o = rs.getString(i);

                } else {
                    o = rs.getObject(i);
                }

                tmpV.addElement(o);
            }
            v.addElement(tmpV);
        }
        return v;
    }


    public static Object getColumnValue(int type, ResultSet resultSet, int i) throws SQLException {
        Object value;
        switch (type) {
            case Types.VARCHAR:
            case Types.CHAR:
                value = resultSet.getString(i);
                break;
            case Types.BLOB:
            case Types.LONGVARBINARY:
            case Types.VARBINARY:
            case Types.BINARY:
            case Types.CLOB:
            default:
                value = resultSet.getObject(i);
                break;
        }
        return value;
    }

    /**
     * @param c
     * @return
     * @throws SQLException
     */
    static private <T> T newInstance(Class<T> c) throws SQLException {
        try {
            return c.newInstance();

        } catch (InstantiationException e) {
            throw new SQLException("Cannot create " + c.getName() + ": " + e.getMessage());

        } catch (IllegalAccessException e) {
            throw new SQLException("Cannot create " + c.getName() + ": " + e.getMessage());
        }
    }

    /**
     * @param rs
     * @param columnIndex
     * @return
     * @throws SQLException
     */
    static public String getClob(ResultSet rs, int columnIndex) throws SQLException {
        Reader rsReader = rs.getCharacterStream(columnIndex);
        if (rsReader == null)
            return null;
        BufferedReader reader = new BufferedReader(rsReader);
        StringBuffer buffer = new StringBuffer();
        try {
            while (true) {
                String c = reader.readLine();
                if (c == null) {
                    break;
                }
                buffer.append(c);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }


    private static class MethodAndTypes {
        public Method[] invokes = null;

        public int[] types = null;

        public Converter[] converters = null;

    }
}
