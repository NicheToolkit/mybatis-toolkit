package io.github.nichetoolkit.mybatis;

import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface MybatisClassFinder extends MybatisOrder {

    static Class<?> findEntityClass(Class<?> mapperType, Method mapperMethod) {
        Objects.requireNonNull(mapperType);
        List<MybatisClassFinder> instances = ClassFinderInstance.instances();
        for (MybatisClassFinder instance : ClassFinderInstance.instances()) {
            Optional<Class<?>> optionalClass = instance.findEntity(mapperType, mapperMethod);
            if (optionalClass.isPresent()) {
                return optionalClass.get();
            }
        }
        return null;
    }

    static Class<?> findIdentityClass(Class<?> mapperType, Method mapperMethod, Class<?> entityType) {
        Objects.requireNonNull(entityType);
        List<MybatisClassFinder> instances = ClassFinderInstance.instances();
        for (MybatisClassFinder instance : ClassFinderInstance.instances()) {
            Optional<Class<?>> optionalClass = instance.findIdentity(mapperType, mapperMethod, entityType);
            if (optionalClass.isPresent()) {
                return optionalClass.get();
            }
        }
        return null;
    }

    static Class<?> findLinkageClass(Class<?> mapperType, Method mapperMethod, Class<?> entityType) {
        Objects.requireNonNull(entityType);
        List<MybatisClassFinder> instances = ClassFinderInstance.instances();
        for (MybatisClassFinder instance : ClassFinderInstance.instances()) {
            Optional<Class<?>> optionalClass = instance.findLinkage(mapperType, mapperMethod, entityType);
            if (optionalClass.isPresent()) {
                return optionalClass.get();
            }
        }
        return null;
    }

    static Class<?> findAlertnessClass(Class<?> mapperType, Method mapperMethod, Class<?> entityType) {
        Objects.requireNonNull(entityType);
        List<MybatisClassFinder> instances = ClassFinderInstance.instances();
        for (MybatisClassFinder instance : ClassFinderInstance.instances()) {
            Optional<Class<?>> optionalClass = instance.findAlertness(mapperType, mapperMethod, entityType);
            if (optionalClass.isPresent()) {
                return optionalClass.get();
            }
        }
        return null;
    }

    Optional<Class<?>> findEntity(@NonNull Class<?> mapperType, @Nullable Method mapperMethod);

    Optional<Class<?>> findIdentity(@NonNull Class<?> mapperType, @Nullable Method mapperMethod, @NonNull Class<?> entityType);

    Optional<Class<?>> findLinkage(@NonNull Class<?> mapperType, @Nullable Method mapperMethod, @NonNull Class<?> entityType);

    Optional<Class<?>> findAlertness(@NonNull Class<?> mapperType, @Nullable Method mapperMethod, @NonNull Class<?> entityType);

    boolean isEntity(Class<?> clazz);

    boolean isIdentity(Class<?> clazz);

    boolean isLinkage(Class<?> clazz);

    boolean isAlertness(Class<?> clazz);

    class ClassFinderInstance {
        private static volatile List<MybatisClassFinder> INSTANCES;

        public static List<MybatisClassFinder> instances() {
            if (INSTANCES == null) {
                synchronized (MybatisClassFinder.class) {
                    if (INSTANCES == null) {
                        INSTANCES = SpringFactoriesLoader.loadFactories(MybatisClassFinder.class, null);
                    }
                }
            }
            return INSTANCES;
        }
    }

}
