/*
 * 创建日期 2005-9-16
 *
 */
package pro.pie.me.utils;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BeanConvertor {
    static Map<Class, Converter> converts = new HashMap<Class, Converter>();

    static {
        boolean booleanArray[] = new boolean[0];
        byte byteArray[] = new byte[0];
        char charArray[] = new char[0];
        double doubleArray[] = new double[0];
        float floatArray[] = new float[0];
        int intArray[] = new int[0];
        long longArray[] = new long[0];
        short shortArray[] = new short[0];
        String stringArray[] = new String[0];
//        converts.put(UFDate.class, new UFDateConvertor());
//        converts.put(UFTime.class, new UFTimeConvertor());
//        converts.put(Calendar.class, new CalenderConvertor());
//        converts.put(UFDateTime.class, new UFDateTimeConvertor());
//        converts.put(UFDouble.class, new UFDoubleConvertor());
//        converts.put(UFBoolean.class, new UFBooleanConvertor());
//        converts.put(Object.class, new ObjectConvertor());
        converts.put(BigDecimal.class, new BigDecimalConverter());
        converts.put(BigInteger.class, new BigIntegerConverter());
//        converts.put(Boolean.TYPE, new com.cheng.jdbc.util.BooleanConverter());
//        converts.put(Boolean.class, new com.cheng.jdbc.util.BooleanConverter());
//        converts.put(booleanArray.getClass(), new BooleanArrayConverter());
        converts.put(Byte.TYPE, new ByteConverter());
        converts.put(Byte.class, new ByteConverter());
//        converts.put(byteArray.getClass(), new ByteArrayConverter(byteArray));
        converts.put(Character.TYPE, new CharacterConverter());
        converts.put(Character.class, new CharacterConverter());
//        converts.put(charArray.getClass(), new CharacterArrayConverter(charArray));
        converts.put(Class.class, new ClassConverter());
        converts.put(Double.TYPE, new DoubleConverter());
        converts.put(Double.class, new DoubleConverter());
        converts.put(doubleArray.getClass(), new DoubleArrayConverter(doubleArray));
        converts.put(Float.TYPE, new FloatConverter());
        converts.put(Float.class, new FloatConverter());
        converts.put(floatArray.getClass(), new FloatArrayConverter(floatArray));
        converts.put(Integer.TYPE, new IntegerConverter());
        converts.put(Integer.class, new IntegerConverter());
        converts.put(intArray.getClass(), new IntegerArrayConverter(intArray));
        converts.put(Long.TYPE, new LongConverter());
        converts.put(Long.class, new LongConverter());
        converts.put(longArray.getClass(), new LongArrayConverter(longArray));
        converts.put(Short.TYPE, new ShortConverter());
        converts.put(Short.class, new ShortConverter());
        converts.put(shortArray.getClass(), new ShortArrayConverter(shortArray));
//        converts.put(stringArray.getClass(), new com.cheng.jdbc.util.StringArrayConverter(stringArray));
        converts.put(Date.class, new SqlDateConverter());
        converts.put(Time.class, new SqlTimeConverter());
        converts.put(Timestamp.class, new SqlTimestampConverter());
//        converts.put(File.class, new FileConverter());
        converts.put(URL.class, new URLConverter());
//        converts.put(List.class, new ObjectConvertor());
//        converts.put(String.class, new StringConvertor());
    }

    static public Object convert(Object obj, Class theClass) {
        Converter converter = converts.get(theClass);
        if (converter != null) {
            return converter.convert(theClass, obj);
        }
        return obj;
    }

    public static Converter getConVerter(Class<?> name) {
        return converts.get(name);
    }

}
