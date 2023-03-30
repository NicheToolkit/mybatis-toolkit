package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.helper.ServiceLoaderHelper;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>MybatisClassFinder</p>
 * 根据类型和方法等信息获取实体类类型
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public interface MybatisClassFinder extends MybatisOrder {

    /**
     * 查找当前方法对应的实体类
     *
     * @param mapperType   Mapper 接口，不能为空
     * @param mapperMethod Mapper 接口方法，可以为空
     * @return Optional<Class<?>>
     */
    static Optional<Class<?>> find(Class<?> mapperType, Method mapperMethod) {
        Objects.requireNonNull(mapperType);
        for (MybatisClassFinder instance : MybatisClassFinderInstance.getInstances()) {
            Optional<Class<?>> optionalClass = instance.findEntity(mapperType, mapperMethod);
            if (optionalClass.isPresent()) {
                return optionalClass;
            }
        }
        return Optional.empty();
    }

    /**
     * 查找当前方法对应的实体类
     *
     * @param mapperType   Mapper 接口，不能为空
     * @param mapperMethod Mapper 接口方法，可以为空
     * @return Optional<Class<?>> 实体类类型
     */
    Optional<Class<?>> findEntity(Class<?> mapperType, Method mapperMethod);

    /**
     * 指定的类型是否为定义的实体类类型
     *
     * @param clazz 类型
     * @return 是否为实体类类型
     */
    boolean isEntity(Class<?> clazz);

    /**
     * 实例
     */
    class MybatisClassFinderInstance {
        private static volatile List<MybatisClassFinder> INSTANCES;

        /**
         * 通过 SPI 获取扩展的实现或使用默认实现
         *
         * @return 实例
         */
        public static List<MybatisClassFinder> getInstances() {
            if (INSTANCES == null) {
                synchronized (MybatisClassFinder.class) {
                    if (INSTANCES == null) {
                        INSTANCES = ServiceLoaderHelper.instances(MybatisClassFinder.class);
                    }
                }
            }
            return INSTANCES;
        }
    }

}
