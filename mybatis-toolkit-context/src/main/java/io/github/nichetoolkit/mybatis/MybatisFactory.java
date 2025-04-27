package io.github.nichetoolkit.mybatis;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import io.github.nichetoolkit.mybatis.column.*;
import io.github.nichetoolkit.mybatis.defaults.DefaultColumnFactoryChain;
import io.github.nichetoolkit.mybatis.defaults.DefaultTableFactoryChain;
import io.github.nichetoolkit.mybatis.error.MybatisTableLackError;
import io.github.nichetoolkit.mybatis.fickle.RestFickle;
import io.github.nichetoolkit.mybatis.table.RestFickleness;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.error.lack.InterfaceLackError;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rest.util.OptionalUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.reflect.*;
import java.util.*;
import java.util.function.Function;

/**
 * <code>MybatisFactory</code>
 * <p>The mybatis factory class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see lombok.extern.slf4j.Slf4j
 * @since Jdk1.8
 */
@Slf4j
public abstract class MybatisFactory {

    /**
     * <code>TABLE_CACHES</code>
     * {@link java.util.Map} <p>The <code>TABLE_CACHES</code> field.</p>
     * @see java.util.Map
     */
    private static Map<Class<?>,MybatisTable> TABLE_CACHES;


    /**
     * <code>tableCache</code>
     * <p>The table cache method.</p>
     * @param mapperType {@link java.lang.Class} <p>The mapper type parameter is <code>Class</code> type.</p>
     * @param tableCache {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The table cache parameter is <code>MybatisTable</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The table cache return object is <code>MybatisTable</code> type.</p>
     * @see java.lang.Class
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     */
    private static MybatisTable tableCache(Class<?> mapperType, MybatisTable tableCache) {
        if (TABLE_CACHES == null) {
            TABLE_CACHES = new HashMap<>();
        }
        TABLE_CACHES.put(mapperType,tableCache);
        return tableCache;
    }

    /**
     * <code>tableOfCache</code>
     * <p>The table of cache method.</p>
     * @param mapperType {@link java.lang.Class} <p>The mapper type parameter is <code>Class</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The table of cache return object is <code>MybatisTable</code> type.</p>
     * @see java.lang.Class
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     */
    public static MybatisTable tableOfCache(Class<?> mapperType) {
        return TABLE_CACHES.get(mapperType);
    }

    /**
     * <code>createTable</code>
     * <p>The create table method.</p>
     * @param mapperType   {@link java.lang.Class} <p>The mapper type parameter is <code>Class</code> type.</p>
     * @param mapperMethod {@link java.lang.reflect.Method} <p>The mapper method parameter is <code>Method</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The create table return object is <code>MybatisTable</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.Class
     * @see org.springframework.lang.NonNull
     * @see java.lang.reflect.Method
     * @see org.springframework.lang.Nullable
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     * @see java.lang.SuppressWarnings
     * @see io.github.nichetoolkit.rest.RestException
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
        MybatisTable createTable = createTable(entityClass, identityClass, linkageClass, alertnessClass, ficklenessClass);
        return tableCache(mapperType, createTable);
    }

    /**
     * <code>createTable</code>
     * <p>The create table method.</p>
     * @param entityType     {@link java.lang.Class} <p>The entity type parameter is <code>Class</code> type.</p>
     * @param identityType   {@link java.lang.Class} <p>The identity type parameter is <code>Class</code> type.</p>
     * @param linkageType    {@link java.lang.Class} <p>The linkage type parameter is <code>Class</code> type.</p>
     * @param alertnessType  {@link java.lang.Class} <p>The alertness type parameter is <code>Class</code> type.</p>
     * @param ficklenessType {@link java.lang.Class} <p>The fickleness type parameter is <code>Class</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The create table return object is <code>MybatisTable</code> type.</p>
     * @see java.lang.Class
     * @see org.springframework.lang.NonNull
     * @see org.springframework.lang.Nullable
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     * @see java.lang.SuppressWarnings
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
                                        handleOfFickleValueFields(table, declaredField, columnFactoryChain, fickleField -> MybatisField.ofFickleness(entityType, fickleField), isPresentFickleEntry);
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

    /**
     * <code>handleOfFickleValueFields</code>
     * <p>The handle of fickle value fields method.</p>
     * @param table              {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The table parameter is <code>MybatisTable</code> type.</p>
     * @param declaredField      {@link java.lang.reflect.Field} <p>The declared field parameter is <code>Field</code> type.</p>
     * @param columnFactoryChain {@link io.github.nichetoolkit.mybatis.MybatisColumnFactory.Chain} <p>The column factory chain parameter is <code>Chain</code> type.</p>
     * @param fieldFunction      {@link java.util.function.Function} <p>The field function parameter is <code>Function</code> type.</p>
     * @param isFickleEntry      boolean <p>The is fickle entry parameter is <code>boolean</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     * @see java.lang.reflect.Field
     * @see io.github.nichetoolkit.mybatis.MybatisColumnFactory.Chain
     * @see java.util.function.Function
     */
    protected static void handleOfFickleValueFields(MybatisTable table, Field declaredField, MybatisColumnFactory.Chain columnFactoryChain, Function<Field, MybatisField> fieldFunction, boolean isFickleEntry) {
        handleOfFickleFields(table, declaredField, columnFactoryChain, fieldFunction, isFickleEntry, true);
    }

    /**
     * <code>handleOfFickleKeyFields</code>
     * <p>The handle of fickle key fields method.</p>
     * @param table              {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The table parameter is <code>MybatisTable</code> type.</p>
     * @param declaredField      {@link java.lang.reflect.Field} <p>The declared field parameter is <code>Field</code> type.</p>
     * @param columnFactoryChain {@link io.github.nichetoolkit.mybatis.MybatisColumnFactory.Chain} <p>The column factory chain parameter is <code>Chain</code> type.</p>
     * @param fieldFunction      {@link java.util.function.Function} <p>The field function parameter is <code>Function</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     * @see java.lang.reflect.Field
     * @see io.github.nichetoolkit.mybatis.MybatisColumnFactory.Chain
     * @see java.util.function.Function
     */
    protected static void handleOfFickleKeyFields(MybatisTable table, Field declaredField, MybatisColumnFactory.Chain columnFactoryChain, Function<Field, MybatisField> fieldFunction) {
        handleOfFickleFields(table, declaredField, columnFactoryChain, fieldFunction, true, false);
    }

    /**
     * <code>handleOfFickleFields</code>
     * <p>The handle of fickle fields method.</p>
     * @param table              {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The table parameter is <code>MybatisTable</code> type.</p>
     * @param declaredField      {@link java.lang.reflect.Field} <p>The declared field parameter is <code>Field</code> type.</p>
     * @param columnFactoryChain {@link io.github.nichetoolkit.mybatis.MybatisColumnFactory.Chain} <p>The column factory chain parameter is <code>Chain</code> type.</p>
     * @param fieldFunction      {@link java.util.function.Function} <p>The field function parameter is <code>Function</code> type.</p>
     * @param isFickleKey        boolean <p>The is fickle key parameter is <code>boolean</code> type.</p>
     * @param isFickleValue      boolean <p>The is fickle value parameter is <code>boolean</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     * @see java.lang.reflect.Field
     * @see io.github.nichetoolkit.mybatis.MybatisColumnFactory.Chain
     * @see java.util.function.Function
     */
    private static void handleOfFickleFields(MybatisTable table, Field declaredField, MybatisColumnFactory.Chain columnFactoryChain, Function<Field, MybatisField> fieldFunction, boolean isFickleKey, boolean isFickleValue) {
        Class<?> fieldType = declaredField.getType();
        Type genericType = declaredField.getGenericType();
        boolean isPresentFickleType = false;
        JavaType fickleType = null;
        if (genericType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericType;
            Type rawType = parameterizedType.getRawType();
            if (!(rawType instanceof Class)) {
                return;
            }
            Class<?> rawClass = (Class<?>) rawType;
            if (Collection.class.isAssignableFrom(rawClass)) {
                Type[] typeArguments = parameterizedType.getActualTypeArguments();
                Type actualType = typeArguments[0];
                if (actualType instanceof ParameterizedType) {
                    ParameterizedType actualParameterizedType = (ParameterizedType) actualType;
                    Type actualRawType = actualParameterizedType.getRawType();
                    if (!(actualRawType instanceof Class)) {
                        return;
                    }
                    if (RestFickle.class.isAssignableFrom((Class<?>) actualRawType)) {
                        isPresentFickleType = true;
                        fickleType = TypeFactory.defaultInstance().constructCollectionType(List.class, RestFickle.class);
                    }
                }

            } else if (Map.class.isAssignableFrom(rawClass)) {
                Type[] typeArguments = parameterizedType.getActualTypeArguments();
                Type keyOfActualType = typeArguments[0];
                Type valueOfActualType = typeArguments[1];
                if (!(keyOfActualType instanceof Class)) {
                    return;
                }
                if (valueOfActualType instanceof ParameterizedType) {
                    ParameterizedType actualParameterizedType = (ParameterizedType) valueOfActualType;
                    Type valueActualRawType = actualParameterizedType.getRawType();
                    if (!(valueActualRawType instanceof Class)) {
                        return;
                    }
                    if (String.class.isAssignableFrom((Class<?>) keyOfActualType) && RestFickle.class.isAssignableFrom((Class<?>) valueActualRawType)) {
                        isPresentFickleType = true;
                        fickleType = TypeFactory.defaultInstance().constructMapType(Map.class, String.class, RestFickle.class);
                    }
                }

            } else if (rawClass == RestFickle[].class) {
                fickleType = TypeFactory.defaultInstance().constructType(RestFickle[].class);
            }
        } else if (genericType instanceof GenericArrayType) {
            GenericArrayType genericArrayType = (GenericArrayType) genericType;
            Type genericComponentType = genericArrayType.getGenericComponentType();
            if (genericComponentType instanceof ParameterizedType) {
                ParameterizedType arrayParameterizedType = (ParameterizedType) genericComponentType;
                Type arrayRawType = arrayParameterizedType.getRawType();
                if (!(arrayRawType instanceof Class)) {
                    return;
                }
                if (RestFickle.class.isAssignableFrom((Class<?>) arrayRawType)) {
                    isPresentFickleType = true;
                    fickleType = TypeFactory.defaultInstance().constructType(RestFickle[].class);
                }
            }

        }
        if (isPresentFickleType && GeneralUtils.isNotEmpty(fickleType)) {
            MybatisField field = fieldFunction.apply(declaredField);
            Optional<List<MybatisColumn>> optionalColumns = columnFactoryChain.createColumn(table, field, fickleType, isFickleKey, isFickleValue);
            optionalColumns.ifPresent(columns -> columns.forEach(table::addColumn));
        }
    }

    /**
     * <code>handleOfFickleness</code>
     * <p>The handle of fickleness method.</p>
     * @param table              {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The table parameter is <code>MybatisTable</code> type.</p>
     * @param declaredType       {@link java.lang.Class} <p>The declared type parameter is <code>Class</code> type.</p>
     * @param columnFactoryChain {@link io.github.nichetoolkit.mybatis.MybatisColumnFactory.Chain} <p>The column factory chain parameter is <code>Chain</code> type.</p>
     * @param fieldFunction      {@link java.util.function.Function} <p>The field function parameter is <code>Function</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     * @see java.lang.Class
     * @see io.github.nichetoolkit.mybatis.MybatisColumnFactory.Chain
     * @see java.util.function.Function
     */
    private static void handleOfFickleness(MybatisTable table, Class<?> declaredType, MybatisColumnFactory.Chain columnFactoryChain, Function<Field, MybatisField> fieldFunction) {
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
                    boolean isPresentFickleKey = declaredField.isAnnotationPresent(RestFickleKey.class);
                    boolean isPresentFickleEntry = declaredField.isAnnotationPresent(RestFickleEntry.class);
                    if (isPresentFickleKey || isPresentFickleEntry) {
                        handleOfFickleKeyFields(table, declaredField, columnFactoryChain, fieldFunction);
                    }
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
     * @param table              {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The table parameter is <code>MybatisTable</code> type.</p>
     * @param declaredType       {@link java.lang.Class} <p>The declared type parameter is <code>Class</code> type.</p>
     * @param columnFactoryChain {@link io.github.nichetoolkit.mybatis.MybatisColumnFactory.Chain} <p>The column factory chain parameter is <code>Chain</code> type.</p>
     * @param fieldFunction      {@link java.util.function.Function} <p>The field function parameter is <code>Function</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     * @see java.lang.Class
     * @see io.github.nichetoolkit.mybatis.MybatisColumnFactory.Chain
     * @see java.util.function.Function
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
         * @see io.github.nichetoolkit.mybatis.MybatisTableFactory.Chain
         */
        private static volatile MybatisTableFactory.Chain TABLE_FACTORY_CHAIN;
        /**
         * <code>COLUMN_FACTORY_CHAIN</code>
         * {@link io.github.nichetoolkit.mybatis.MybatisColumnFactory.Chain} <p>The constant <code>COLUMN_FACTORY_CHAIN</code> field.</p>
         * @see io.github.nichetoolkit.mybatis.MybatisColumnFactory.Chain
         */
        private static volatile MybatisColumnFactory.Chain COLUMN_FACTORY_CHAIN;

        /**
         * <code>tableFactoryChain</code>
         * <p>The table factory chain method.</p>
         * @return {@link io.github.nichetoolkit.mybatis.MybatisTableFactory.Chain} <p>The table factory chain return object is <code>Chain</code> type.</p>
         * @see io.github.nichetoolkit.mybatis.MybatisTableFactory.Chain
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
         * @return {@link io.github.nichetoolkit.mybatis.MybatisColumnFactory.Chain} <p>The column factory chain return object is <code>Chain</code> type.</p>
         * @see io.github.nichetoolkit.mybatis.MybatisColumnFactory.Chain
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
