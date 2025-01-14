package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.column.*;
import io.github.nichetoolkit.mybatis.defaults.DefaultColumnFactoryChain;
import io.github.nichetoolkit.mybatis.defaults.DefaultTableFactoryChain;
import io.github.nichetoolkit.mybatis.error.MybatisTableLackError;
import io.github.nichetoolkit.mybatis.fickle.FickleField;
import io.github.nichetoolkit.mybatis.table.RestFickleness;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.error.lack.InterfaceLackError;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rest.util.OptionalUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.reflect.*;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * <code>MybatisFactory</code>
 * <p>The mybatis factory class.</p>
 * @see  lombok.extern.slf4j.Slf4j
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
@Slf4j
public abstract class MybatisFactory {

    /**
     * <code>createTable</code>
     * <p>The create table method.</p>
     * @param mapperType {@link java.lang.Class} <p>The mapper type parameter is <code>Class</code> type.</p>
     * @param mapperMethod {@link java.lang.reflect.Method} <p>The mapper method parameter is <code>Method</code> type.</p>
     * @see  java.lang.Class
     * @see  org.springframework.lang.NonNull
     * @see  java.lang.reflect.Method
     * @see  org.springframework.lang.Nullable
     * @see  io.github.nichetoolkit.mybatis.MybatisTable
     * @see  java.lang.SuppressWarnings
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The create table return object is <code>MybatisTable</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    @SuppressWarnings("all")
    public static MybatisTable createTable(@NonNull Class<?> mapperType, @Nullable Method mapperMethod) throws RestException {
        Class<?> entityClass = MybatisClassFinder.findEntityClass(mapperType, mapperMethod);
        String message = "Can not find " + (mapperMethod != null ? mapperMethod.getName() + " method" : mapperType.getSimpleName() + " interface") + " corresponding mybatis class";
        OptionalUtils.ofNullError(entityClass, message, log, InterfaceLackError::new);
        Class<?> identityClass = MybatisClassFinder.findIdentityClass(mapperType, mapperMethod, entityClass);
        Class<?> linkageClass = MybatisClassFinder.findLinkageClass(mapperType, mapperMethod, entityClass);
        Class<?> alertnessClass = MybatisClassFinder.findAlertnessClass(mapperType, mapperMethod, entityClass);
        Class<?> ficklenessClass = MybatisClassFinder.findFicklenessClass(mapperType, mapperMethod, entityClass);
        return createTable(entityClass, identityClass, linkageClass, alertnessClass,ficklenessClass);
    }

    /**
     * <code>createTable</code>
     * <p>The create table method.</p>
     * @param entityType {@link java.lang.Class} <p>The entity type parameter is <code>Class</code> type.</p>
     * @param identityType {@link java.lang.Class} <p>The identity type parameter is <code>Class</code> type.</p>
     * @param linkageType {@link java.lang.Class} <p>The linkage type parameter is <code>Class</code> type.</p>
     * @param alertnessType {@link java.lang.Class} <p>The alertness type parameter is <code>Class</code> type.</p>
     * @see  java.lang.Class
     * @see  org.springframework.lang.NonNull
     * @see  org.springframework.lang.Nullable
     * @see  io.github.nichetoolkit.mybatis.MybatisTable
     * @see  java.lang.SuppressWarnings
     * @return  {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The create table return object is <code>MybatisTable</code> type.</p>
     */
    @SuppressWarnings("all")
    public static MybatisTable createTable(@NonNull Class<?> entityType, @Nullable Class<?> identityType, @Nullable Class<?> linkageType, @Nullable Class<?> alertnessType, @Nullable Class<?> ficklenessType) {
        /* 处理MybatisTable */
        MybatisTableFactory.Chain tableFactoryChain = Instance.tableFactoryChain();
        /* 创建 MybatisTable，不处理列（字段），此时返回的 MybatisTable 已经经过了所有处理链的加工 */
        MybatisTable table = tableFactoryChain.createTable(entityType, identityType, linkageType, alertnessType, ficklenessType);
        String message = "Unable to get " + entityType.getName() + " mybatis class information";
        OptionalUtils.ofNullError(table, message, log, MybatisTableLackError::new);
        /* 如果实体表已经处理好，直接返回 */
        if (!table.isReady()) {
            synchronized (entityType) {
                if (!table.isReady()) {
                    /* 处理MybatisColumn */
                    MybatisColumnFactory.Chain columnFactoryChain = Instance.columnFactoryChain();
                    /* 未处理的需要获取字段 */
                    Class<?> declaredClass = entityType;
                    boolean isSuperclass = false;
                    while (declaredClass != null && declaredClass != Object.class) {
                        Field[] declaredFields = declaredClass.getDeclaredFields();
                        if (isSuperclass) {
                            MybatisSqlUtils.reverseArray(declaredFields);
                        }
                        for (Field declaredField : declaredFields) {
                            int modifiers = declaredField.getModifiers();
                            /* 排除 static 和 transient 修饰的字段 */
                            if (!Modifier.isStatic(modifiers) && !Modifier.isTransient(modifiers)) {
                                MybatisField mybatisField = MybatisField.of(entityType, declaredField);
                                /* 是否需要排除字段 */
                                if (table.isExcludeField(mybatisField)) {
                                    continue;
                                }
                                /* 使用了自定义联合主键字 */
                                if (GeneralUtils.isNotEmpty(identityType) && mybatisField.isAnnotationPresent(RestIdentityKey.class)) {
                                    handleOfFields(table, identityType, columnFactoryChain, identityField -> MybatisField.ofIdentity(entityType, mybatisField, identityField));
                                } else if (GeneralUtils.isNotEmpty(linkageType) && mybatisField.isAnnotationPresent(RestLinkKey.class)) {
                                    handleOfFields(table, linkageType, columnFactoryChain, linkField -> MybatisField.ofLinkage(entityType, mybatisField, linkField));
                                } else if (GeneralUtils.isNotEmpty(alertnessType) && mybatisField.isAnnotationPresent(RestAlertKey.class)) {
                                    handleOfFields(table, alertnessType, columnFactoryChain, alertField -> MybatisField.ofAlertness(entityType, mybatisField, alertField));
                                } else {
                                    boolean isPresentFickleKey = mybatisField.isAnnotationPresent(RestFickleKey.class);
                                    boolean isPresentFickleEntry = mybatisField.isAnnotationPresent(RestFickleEntry.class);
                                    boolean isPresentFickleValue = mybatisField.isAnnotationPresent(RestFickleValue.class);
                                    // 使用了RestFickleEntry注解 或者 RestFickleValue注解
                                    if (isPresentFickleEntry | isPresentFickleValue) {
                                        handleOfFickleFields(table, declaredField, columnFactoryChain, fickleField -> MybatisField.ofFickleness(entityType, fickleField));
                                    } else if (isPresentFickleKey) {
                                        Class<?> fieldType = declaredField.getType();
                                        // 使用了RestFickleKey注解，且ficklenessType为空时
                                        if (GeneralUtils.isEmpty(ficklenessType) && fieldType.isAnnotationPresent(RestFickleness.class)) {
                                            ficklenessType = fieldType;
                                            table.refreshFicklenessType(fieldType);
                                        }
                                        if (GeneralUtils.isNotEmpty(ficklenessType)) {
                                            handleOfFickleness(table, ficklenessType, columnFactoryChain, fickleField -> MybatisField.ofFickleness(entityType, mybatisField, fickleField));
                                        }
                                    } else {
                                        Optional<List<MybatisColumn>> optionalColumns = columnFactoryChain.createColumn(table, mybatisField);
                                        optionalColumns.ifPresent(columns -> columns.forEach(table::addColumn));
                                    }
                                }
                            }
                        }
                        /* 迭代获取父类 排除父类 */
                        do {
                            declaredClass = declaredClass.getSuperclass();
                        } while (table.isExcludeSuperClass(declaredClass) && declaredClass != Object.class);
                        isSuperclass = true;
                    }
                    table.readyColumns();
                    /* 标记处理完成 */
                    table.setReady(true);
                }
            }
        }
        return table;
    }

    protected static void handleOfFickleFields(MybatisTable table, Field declaredField, MybatisColumnFactory.Chain columnFactoryChain, Function<Field, MybatisField> fieldFunction) {
        handleOfFickleFields(table, declaredField, columnFactoryChain, fickleField -> true, fieldFunction);
    }

    protected static void handleOfFickleFields(MybatisTable table, Field declaredField, MybatisColumnFactory.Chain columnFactoryChain, Predicate<Field> fieldPredicate, Function<Field, MybatisField> fieldFunction) {
        Class<?> fieldType = declaredField.getType();
        Type genericType = declaredField.getGenericType();
        if (genericType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericType;
            Type rawType = parameterizedType.getRawType();
            if (!(rawType instanceof Class)) {
                return;
            }
            Class<?> rawClass = (Class<?>) rawType;
            boolean isPresentFickleType = false;
            if (Collection.class.isAssignableFrom(rawClass)) {
                Type[] typeArguments = parameterizedType.getActualTypeArguments();
                Type actualType = typeArguments[0];
                if (actualType instanceof Class && FickleField.class.isAssignableFrom((Class<?>) actualType)) {
                    isPresentFickleType = true;
                }
            } else if (Map.class.isAssignableFrom(rawClass)) {
                Type[] typeArguments = parameterizedType.getActualTypeArguments();
                Type keyOfActualType = typeArguments[0];
                Type valueOfActualType = typeArguments[1];
                if (keyOfActualType instanceof Class
                        && String.class.isAssignableFrom((Class<?>) keyOfActualType)
                        && valueOfActualType instanceof Class
                        && FickleField.class.isAssignableFrom((Class<?>) valueOfActualType)) {
                    isPresentFickleType = true;
                }
            }
            if (isPresentFickleType) {
                MybatisField field = fieldFunction.apply(declaredField);
                Optional<List<MybatisColumn>> optionalColumns = columnFactoryChain.createColumn(table, field);
                optionalColumns.ifPresent(columns -> columns.forEach(table::addColumn));
            }
        }
    }

    protected static void handleOfFickleness(MybatisTable table, Class<?> declaredType, MybatisColumnFactory.Chain columnFactoryChain, Function<Field, MybatisField> fieldFunction) {
        /* 未处理的需要获取字段 */
        Class<?> declaredClass = declaredType;
        boolean isSuperclass = true;
        while (declaredClass != null && declaredClass != Object.class) {
            Field[] declaredFields = declaredClass.getDeclaredFields();
            MybatisSqlUtils.reverseArray(declaredFields);
            for (Field declaredField : declaredFields) {
                int modifiers = declaredField.getModifiers();
                /* 排除 static 和 transient 修饰的字段 */
                if (!Modifier.isStatic(modifiers) && !Modifier.isTransient(modifiers)) {
                    /* 是否需要排除字段 */
                    if (table.isExcludeField(declaredField)) {
                        continue;
                    }
                    handleOfFickleFields(table, declaredField, columnFactoryChain,
                            field -> GeneralUtils.isNotEmpty(AnnotationUtils.getAnnotation(field,RestFickleKey.class)),
                            fieldFunction);
                }
            }
            /* 排除父类,迭代获取父类 */
            do {
                declaredClass = declaredClass.getSuperclass();
            } while (table.isExcludeSuperClass(declaredClass) && declaredClass != Object.class);
        }
    }

    /**
     * <code>handleOfFields</code>
     * <p>The handle of fields method.</p>
     * @param table {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The table parameter is <code>MybatisTable</code> type.</p>
     * @param declaredType {@link java.lang.Class} <p>The declared type parameter is <code>Class</code> type.</p>
     * @param columnFactoryChain {@link io.github.nichetoolkit.mybatis.MybatisColumnFactory.Chain} <p>The column factory chain parameter is <code>Chain</code> type.</p>
     * @param fieldFunction {@link java.util.function.Function} <p>The field function parameter is <code>Function</code> type.</p>
     * @see  io.github.nichetoolkit.mybatis.MybatisTable
     * @see  java.lang.Class
     * @see  io.github.nichetoolkit.mybatis.MybatisColumnFactory.Chain
     * @see  java.util.function.Function
     */
    protected static void handleOfFields(MybatisTable table, Class<?> declaredType, MybatisColumnFactory.Chain columnFactoryChain, Function<Field, MybatisField> fieldFunction) {
        /* 未处理的需要获取字段 */
        Class<?> declaredClass = declaredType;
        boolean isSuperclass = true;
        while (declaredClass != null && declaredClass != Object.class) {
            Field[] declaredFields = declaredClass.getDeclaredFields();
            MybatisSqlUtils.reverseArray(declaredFields);
            for (Field declaredField : declaredFields) {
                int modifiers = declaredField.getModifiers();
                /* 排除 static 和 transient 修饰的字段 */
                if (!Modifier.isStatic(modifiers) && !Modifier.isTransient(modifiers)) {
                    MybatisField field = fieldFunction.apply(declaredField);
                    /* 是否需要排除字段 */
                    if (table.isExcludeField(field)) {
                        continue;
                    }
                    Optional<List<MybatisColumn>> optionalColumns = columnFactoryChain.createColumn(table, field);
                    optionalColumns.ifPresent(columns -> columns.forEach(table::addColumn));
                }
            }
            /* 排除父类,迭代获取父类 */
            do {
                declaredClass = declaredClass.getSuperclass();
            } while (table.isExcludeSuperClass(declaredClass) && declaredClass != Object.class);
        }
    }

    /**
     * <code>Instance</code>
     * <p>The instance class.</p>
     * @author Cyan (snow22314@outlook.com)
     * @since Jdk1.8
     */
    static class Instance {
        /**
         * <code>TABLE_FACTORY_CHAIN</code>
         * {@link io.github.nichetoolkit.mybatis.MybatisTableFactory.Chain} <p>The constant <code>TABLE_FACTORY_CHAIN</code> field.</p>
         * @see  io.github.nichetoolkit.mybatis.MybatisTableFactory.Chain
         */
        private static volatile MybatisTableFactory.Chain TABLE_FACTORY_CHAIN;
        /**
         * <code>COLUMN_FACTORY_CHAIN</code>
         * {@link io.github.nichetoolkit.mybatis.MybatisColumnFactory.Chain} <p>The constant <code>COLUMN_FACTORY_CHAIN</code> field.</p>
         * @see  io.github.nichetoolkit.mybatis.MybatisColumnFactory.Chain
         */
        private static volatile MybatisColumnFactory.Chain COLUMN_FACTORY_CHAIN;

        /**
         * <code>tableFactoryChain</code>
         * <p>The table factory chain method.</p>
         * @return  {@link io.github.nichetoolkit.mybatis.MybatisTableFactory.Chain} <p>The table factory chain return object is <code>Chain</code> type.</p>
         * @see  io.github.nichetoolkit.mybatis.MybatisTableFactory.Chain
         */
        public static MybatisTableFactory.Chain tableFactoryChain() {

            if (TABLE_FACTORY_CHAIN == null) {
                synchronized (MybatisFactory.class) {
                    if (TABLE_FACTORY_CHAIN == null) {
                        List<MybatisTableFactory> mybatisTableFactories = SpringFactoriesLoader.loadFactories(MybatisTableFactory.class, null);
                        TABLE_FACTORY_CHAIN = new DefaultTableFactoryChain(mybatisTableFactories);
                    }
                }
            }
            return TABLE_FACTORY_CHAIN;
        }

        /**
         * <code>columnFactoryChain</code>
         * <p>The column factory chain method.</p>
         * @return  {@link io.github.nichetoolkit.mybatis.MybatisColumnFactory.Chain} <p>The column factory chain return object is <code>Chain</code> type.</p>
         * @see  io.github.nichetoolkit.mybatis.MybatisColumnFactory.Chain
         */
        public static MybatisColumnFactory.Chain columnFactoryChain() {
            if (COLUMN_FACTORY_CHAIN == null) {
                synchronized (MybatisFactory.class) {
                    if (COLUMN_FACTORY_CHAIN == null) {
                        List<MybatisColumnFactory> mybatisColumnFactories = SpringFactoriesLoader.loadFactories(MybatisColumnFactory.class, null);
                        COLUMN_FACTORY_CHAIN = new DefaultColumnFactoryChain(mybatisColumnFactories);
                    }
                }
            }
            return COLUMN_FACTORY_CHAIN;
        }
    }
}
