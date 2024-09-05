package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.helper.ServiceLoaderHelper;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * <code>MybatisClassFinder</code>
 * <p>The type mybatis class finder interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisOrder
 * @since Jdk1.8
 */
public interface MybatisClassFinder extends MybatisOrder {

    /**
     * <code>findClass</code>
     * <p>the class method.</p>
     * @param mapperType   {@link java.lang.Class} <p>the mapper type parameter is <code>Class</code> type.</p>
     * @param mapperMethod {@link java.lang.reflect.Method} <p>the mapper method parameter is <code>Method</code> type.</p>
     * @return {@link java.util.Optional} <p>the class return object is <code>Optional</code> type.</p>
     * @see java.lang.Class
     * @see java.lang.reflect.Method
     * @see java.util.Optional
     */
    static Optional<Class<?>> findClass(Class<?> mapperType, Method mapperMethod) {
        Objects.requireNonNull(mapperType);
        List<MybatisClassFinder> instances = ClassFinderInstance.instances();
        for (MybatisClassFinder instance : ClassFinderInstance.instances()) {
            Optional<Class<?>> optionalClass = instance.findEntity(mapperType, mapperMethod);
            if (optionalClass.isPresent()) {
                return optionalClass;
            }
        }
        return Optional.empty();
    }

    /**
     * <code>findEntity</code>
     * <p>the entity method.</p>
     * @param mapperType   {@link java.lang.Class} <p>the mapper type parameter is <code>Class</code> type.</p>
     * @param mapperMethod {@link java.lang.reflect.Method} <p>the mapper method parameter is <code>Method</code> type.</p>
     * @return {@link java.util.Optional} <p>the entity return object is <code>Optional</code> type.</p>
     * @see java.lang.Class
     * @see java.lang.reflect.Method
     * @see java.util.Optional
     */
    Optional<Class<?>> findEntity(Class<?> mapperType, Method mapperMethod);

    /**
     * <code>isEntity</code>
     * <p>the entity method.</p>
     * @param clazz {@link java.lang.Class} <p>the clazz parameter is <code>Class</code> type.</p>
     * @return boolean <p>the entity return object is <code>boolean</code> type.</p>
     * @see java.lang.Class
     */
    boolean isEntity(Class<?> clazz);

    /**
     * <code>ClassFinderInstance</code>
     * <p>The type class finder instance class.</p>
     * @author Cyan (snow22314@outlook.com)
     * @since Jdk1.8
     */
    class ClassFinderInstance {
        /**
         * <code>INSTANCES</code>
         * {@link java.util.List} <p>the constant <code>INSTANCES</code> field.</p>
         * @see java.util.List
         */
        private static volatile List<MybatisClassFinder> INSTANCES;

        /**
         * <code>instances</code>
         * <p>the method.</p>
         * @return {@link java.util.List} <p>the return object is <code>List</code> type.</p>
         * @see java.util.List
         */
        public static List<MybatisClassFinder> instances() {
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
