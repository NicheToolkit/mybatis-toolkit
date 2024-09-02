package io.github.nichetoolkit.mybatis.helper;

import io.github.nichetoolkit.mybatis.MybatisOrder;
import io.github.nichetoolkit.rest.util.GeneralUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ServiceLoader;

/**
 * <code>ServiceLoaderHelper</code>
 * <p>The type service loader helper class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
public class ServiceLoaderHelper {

    /**
     * <code>instances</code>
     * <p>the method.</p>
     * @param <T>   {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param clazz {@link java.lang.Class} <p>the clazz parameter is <code>Class</code> type.</p>
     * @return {@link java.util.List} <p>the return object is <code>List</code> type.</p>
     * @see java.lang.Class
     * @see java.util.List
     */
    public static <T> List<T> instances(Class<T> clazz) {
        ServiceLoader<T> serviceLoader = ServiceLoader.load(clazz);
        List<T> instances = new ArrayList<>();
        for (T factory : serviceLoader) {
            instances.add(factory);
        }
        if (GeneralUtils.isNotEmpty(instances) && MybatisOrder.class.isAssignableFrom(clazz)) {
            instances.sort(Comparator.comparing(f -> ((MybatisOrder) f).getOrder()).reversed());
        }
        return instances;
    }
}
