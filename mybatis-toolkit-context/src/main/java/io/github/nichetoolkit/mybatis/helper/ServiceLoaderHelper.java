package io.github.nichetoolkit.mybatis.helper;

import io.github.nichetoolkit.mybatis.MybatisOrder;
import io.github.nichetoolkit.rest.util.GeneralUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ServiceLoader;

public class ServiceLoaderHelper {

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
