package pro.pie.me.corm;

import pro.pie.me.corm.mapper.CMapper;

import java.util.List;
import java.util.Random;

/**
 * Corm 上下文环境
 */
public class CormContext {
    /**
     * 强制使用master的标识
     */
    public static final ThreadLocal<Boolean> IS_MASTER = new ThreadLocal<>();

    static final NullableImmutableHolder<List<CMapper>> MASTER_MAPPER_LIST = new NullableImmutableHolder<>();

    static final NullableImmutableHolder<List<CMapper>> SLAVE_MAPPER_LIST = new NullableImmutableHolder<>();

    /**
     * 查询获取一个CMapper
     */
    public static CMapper getCMapper() {

        List<CMapper> mapperList;
        // 1.如果设置了IS_MASTER=true会使用master mapper
        // 2.如果没有设置IS_MASTER，但是设置了SelectParam.userMaster=true会使用master mapper
        if ((IS_MASTER.get() != null && IS_MASTER.get())) {
            mapperList = MASTER_MAPPER_LIST.get();
        } else {
            mapperList = SLAVE_MAPPER_LIST.get();
            // 如果没有配置slave mapper就使用master mapper
            if (mapperList == null || mapperList.isEmpty()) {
                mapperList = MASTER_MAPPER_LIST.get();
            }
        }

        return getCMapper(mapperList);
    }

    /**
     * 指定主从获取一个CMapper
     *
     * @param useMaster 是否使用master
     */
    public static CMapper getCMapper(boolean useMaster) {
        if (useMaster) {
            return getCMapper(MASTER_MAPPER_LIST.get());
        } else {
            return getCMapper();
        }
    }

    private static CMapper getCMapper(List<CMapper> mapperList) {
        if (mapperList == null || mapperList.isEmpty()) {
            throw new RuntimeException("mapper not set.");
        }
        // 随机获取mapper list中的一条mapper数据
        return mapperList.get(new Random().nextInt(mapperList.size()));
    }
}
