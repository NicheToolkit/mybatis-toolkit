package io.github.nichetoolkit.mybatis;

import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * <code>MybatisClassFinder</code>
 * <p>The mybatis class finder interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisOrder
 * @since Jdk1.8
 */
public interface MybatisClassFinder extends MybatisOrder {

    /**
     * <code>findEntityClass</code>
     * <p>The find entity class method.</p>
     * @param mapperType   {@link java.lang.Class} <p>The mapper type parameter is <code>Class</code> type.</p>
     * @param mapperMethod {@link java.lang.reflect.Method} <p>The mapper method parameter is <code>Method</code> type.</p>
     * @return {@link java.lang.Class} <p>The find entity class return object is <code>Class</code> type.</p>
     * @see java.lang.Class
     * @see java.lang.reflect.Method
     */
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

    /**
     * <code>findIdentityClass</code>
     * <p>The find identity class method.</p>
     * @param mapperType   {@link java.lang.Class} <p>The mapper type parameter is <code>Class</code> type.</p>
     * @param mapperMethod {@link java.lang.reflect.Method} <p>The mapper method parameter is <code>Method</code> type.</p>
     * @param entityType   {@link java.lang.Class} <p>The entity type parameter is <code>Class</code> type.</p>
     * @return {@link java.lang.Class} <p>The find identity class return object is <code>Class</code> type.</p>
     * @see java.lang.Class
     * @see java.lang.reflect.Method
     */
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

    /**
     * <code>findLinkageClass</code>
     * <p>The find linkage class method.</p>
     * @param mapperType   {@link java.lang.Class} <p>The mapper type parameter is <code>Class</code> type.</p>
     * @param mapperMethod {@link java.lang.reflect.Method} <p>The mapper method parameter is <code>Method</code> type.</p>
     * @param entityType   {@link java.lang.Class} <p>The entity type parameter is <code>Class</code> type.</p>
     * @return {@link java.lang.Class} <p>The find linkage class return object is <code>Class</code> type.</p>
     * @see java.lang.Class
     * @see java.lang.reflect.Method
     */
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

    /**
     * <code>findAlertnessClass</code>
     * <p>The find alertness class method.</p>
     * @param mapperType   {@link java.lang.Class} <p>The mapper type parameter is <code>Class</code> type.</p>
     * @param mapperMethod {@link java.lang.reflect.Method} <p>The mapper method parameter is <code>Method</code> type.</p>
     * @param entityType   {@link java.lang.Class} <p>The entity type parameter is <code>Class</code> type.</p>
     * @return {@link java.lang.Class} <p>The find alertness class return object is <code>Class</code> type.</p>
     * @see java.lang.Class
     * @see java.lang.reflect.Method
     */
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

    /**
     * <code>findEntity</code>
     * <p>The find entity method.</p>
     * @param mapperType   {@link java.lang.Class} <p>The mapper type parameter is <code>Class</code> type.</p>
     * @param mapperMethod {@link java.lang.reflect.Method} <p>The mapper method parameter is <code>Method</code> type.</p>
     * @return {@link java.util.Optional} <p>The find entity return object is <code>Optional</code> type.</p>
     * @see java.lang.Class
     * @see org.springframework.lang.NonNull
     * @see java.lang.reflect.Method
     * @see org.springframework.lang.Nullable
     * @see java.util.Optional
     */
    Optional<Class<?>> findEntity(@NonNull Class<?> mapperType, @Nullable Method mapperMethod);

    /**
     * <code>findIdentity</code>
     * <p>The find identity method.</p>
     * @param mapperType   {@link java.lang.Class} <p>The mapper type parameter is <code>Class</code> type.</p>
     * @param mapperMethod {@link java.lang.reflect.Method} <p>The mapper method parameter is <code>Method</code> type.</p>
     * @param entityType   {@link java.lang.Class} <p>The entity type parameter is <code>Class</code> type.</p>
     * @return {@link java.util.Optional} <p>The find identity return object is <code>Optional</code> type.</p>
     * @see java.lang.Class
     * @see org.springframework.lang.NonNull
     * @see java.lang.reflect.Method
     * @see org.springframework.lang.Nullable
     * @see java.util.Optional
     */
    Optional<Class<?>> findIdentity(@NonNull Class<?> mapperType, @Nullable Method mapperMethod, @NonNull Class<?> entityType);

    /**
     * <code>findLinkage</code>
     * <p>The find linkage method.</p>
     * @param mapperType   {@link java.lang.Class} <p>The mapper type parameter is <code>Class</code> type.</p>
     * @param mapperMethod {@link java.lang.reflect.Method} <p>The mapper method parameter is <code>Method</code> type.</p>
     * @param entityType   {@link java.lang.Class} <p>The entity type parameter is <code>Class</code> type.</p>
     * @return {@link java.util.Optional} <p>The find linkage return object is <code>Optional</code> type.</p>
     * @see java.lang.Class
     * @see org.springframework.lang.NonNull
     * @see java.lang.reflect.Method
     * @see org.springframework.lang.Nullable
     * @see java.util.Optional
     */
    Optional<Class<?>> findLinkage(@NonNull Class<?> mapperType, @Nullable Method mapperMethod, @NonNull Class<?> entityType);

    /**
     * <code>findAlertness</code>
     * <p>The find alertness method.</p>
     * @param mapperType   {@link java.lang.Class} <p>The mapper type parameter is <code>Class</code> type.</p>
     * @param mapperMethod {@link java.lang.reflect.Method} <p>The mapper method parameter is <code>Method</code> type.</p>
     * @param entityType   {@link java.lang.Class} <p>The entity type parameter is <code>Class</code> type.</p>
     * @return {@link java.util.Optional} <p>The find alertness return object is <code>Optional</code> type.</p>
     * @see java.lang.Class
     * @see org.springframework.lang.NonNull
     * @see java.lang.reflect.Method
     * @see org.springframework.lang.Nullable
     * @see java.util.Optional
     */
    Optional<Class<?>> findAlertness(@NonNull Class<?> mapperType, @Nullable Method mapperMethod, @NonNull Class<?> entityType);

    /**
     * <code>isEntity</code>
     * <p>The is entity method.</p>
     * @param clazz {@link java.lang.Class} <p>The clazz parameter is <code>Class</code> type.</p>
     * @return boolean <p>The is entity return object is <code>boolean</code> type.</p>
     * @see java.lang.Class
     */
    boolean isEntity(Class<?> clazz);

    /**
     * <code>isIdentity</code>
     * <p>The is identity method.</p>
     * @param clazz {@link java.lang.Class} <p>The clazz parameter is <code>Class</code> type.</p>
     * @return boolean <p>The is identity return object is <code>boolean</code> type.</p>
     * @see java.lang.Class
     */
    boolean isIdentity(Class<?> clazz);

    /**
     * <code>isLinkage</code>
     * <p>The is linkage method.</p>
     * @param clazz {@link java.lang.Class} <p>The clazz parameter is <code>Class</code> type.</p>
     * @return boolean <p>The is linkage return object is <code>boolean</code> type.</p>
     * @see java.lang.Class
     */
    boolean isLinkage(Class<?> clazz);

    /**
     * <code>isAlertness</code>
     * <p>The is alertness method.</p>
     * @param clazz {@link java.lang.Class} <p>The clazz parameter is <code>Class</code> type.</p>
     * @return boolean <p>The is alertness return object is <code>boolean</code> type.</p>
     * @see java.lang.Class
     */
    boolean isAlertness(Class<?> clazz);

    /**
     * <code>ClassFinderInstance</code>
     * <p>The class finder instance class.</p>
     * @author Cyan (snow22314@outlook.com)
     * @since Jdk1.8
     */
    class ClassFinderInstance {
        /**
         * <code>INSTANCES</code>
         * {@link java.util.List} <p>The constant <code>INSTANCES</code> field.</p>
         * @see java.util.List
         */
        private static volatile List<MybatisClassFinder> INSTANCES;

        /**
         * <code>instances</code>
         * <p>The instances method.</p>
         * @return {@link java.util.List} <p>The instances return object is <code>List</code> type.</p>
         * @see java.util.List
         */
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
