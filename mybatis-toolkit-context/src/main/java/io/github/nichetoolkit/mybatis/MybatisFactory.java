package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.defaults.DefaultColumnFactoryChain;
import io.github.nichetoolkit.mybatis.defaults.DefaultTableFactoryChain;
import io.github.nichetoolkit.mybatis.helper.ServiceLoaderHelper;
import io.github.nichetoolkit.mybatis.stereotype.column.RestIdentityKey;
import io.github.nichetoolkit.rest.error.lack.InterfaceLackError;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Optional;

/**
 * <code>MybatisFactory</code>
 * <p>The type mybatis factory class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
public abstract class MybatisFactory {
    /**
     * <code>createTable</code>
     * <p>the table method.</p>
     * @param mapperType   {@link java.lang.Class} <p>the mapper type parameter is <code>Class</code> type.</p>
     * @param mapperMethod {@link java.lang.reflect.Method} <p>the mapper method parameter is <code>Method</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>the table return object is <code>MybatisTable</code> type.</p>
     * @see java.lang.Class
     * @see org.springframework.lang.NonNull
     * @see java.lang.reflect.Method
     * @see org.springframework.lang.Nullable
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     */
    public static MybatisTable createTable(@NonNull Class<?> mapperType, @Nullable Method mapperMethod) {
        Optional<Class<?>> optionalClass = MybatisClassFinder.findEntityClass(mapperType, mapperMethod);
        if (optionalClass.isPresent()) {
            Class<?> entityType = optionalClass.get();
            Optional<Class<?>> identityKeyClass = MybatisClassFinder.findIdentityClass(mapperType, entityType);
            return identityKeyClass.map(clazz -> createTable(entityType, clazz)).orElseGet(() -> createTable(entityType, (Class<?>) null));
        }
        throw new InterfaceLackError("Can't obtain " + (mapperMethod != null ?
                mapperMethod.getName() + " method" : mapperType.getSimpleName() + " interface") + " corresponding mybatis class");
    }

    /**
     * <code>createTable</code>
     * <p>the table method.</p>
     * @param entityType      {@link java.lang.Class} <p>the entity type parameter is <code>Class</code> type.</p>
     * @param identityType {@link java.lang.Class} <p>the identity key type parameter is <code>Class</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>the table return object is <code>MybatisTable</code> type.</p>
     * @see java.lang.Class
     * @see org.springframework.lang.NonNull
     * @see org.springframework.lang.Nullable
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     */
    public static MybatisTable createTable(@NonNull Class<?> entityType, @Nullable Class<?> identityType) {
        /* 处理MybatisTable */
        MybatisTableFactory.Chain tableFactoryChain = Instance.tableFactoryChain();
        /* 创建 MybatisTable，不处理列（字段），此时返回的 MybatisTable 已经经过了所有处理链的加工 */
        MybatisTable table = tableFactoryChain.createTable(entityType, identityType);
        Class<?> identityClass = identityType;
        if (table == null) {
            throw new NullPointerException("Unable to get " + entityType.getName() + " mybatis class information");
        }
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
                            reverse(declaredFields);
                        }
                        for (Field declaredField : declaredFields) {
                            int modifiers = declaredField.getModifiers();
                            /* 排除 static 和 transient 修饰的字段 */
                            if (!Modifier.isStatic(modifiers) && !Modifier.isTransient(modifiers)) {
                                MybatisField field = new MybatisField(entityType, declaredField);
                                /* 是否需要排除字段 */
                                if (table.isExcludeField(field)) {
                                    continue;
                                }
                                /* 使用了自定义联合主键字 */
                                if (GeneralUtils.isNotEmpty(identityClass)) {
                                    /* 是否主键id字段 */
                                    RestIdentityKey restIdentityKey = field.getAnnotation(RestIdentityKey.class);

//                                    if (restIdentity) {
//                                        identityKeyClass = null;
//                                    }
                                }

                                Optional<List<MybatisColumn>> optionalColumns = columnFactoryChain.createColumn(table, field);
                                optionalColumns.ifPresent(columns -> columns.forEach(table::addColumn));
                            }
                        }
                        /* 迭代获取父类 */
                        /* 排除父类 */
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
     * <code>reverse</code>
     * <p>the method.</p>
     * @param array {@link java.lang.Object} <p>the array parameter is <code>Object</code> type.</p>
     * @see java.lang.Object
     */
    protected static void reverse(Object[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            Object temp = array[i];
            array[i] = array[array.length - i - 1];
            array[array.length - i - 1] = temp;
        }
    }

    /**
     * <code>Instance</code>
     * <p>The type instance class.</p>
     * @author Cyan (snow22314@outlook.com)
     * @since Jdk1.8
     */
    static class Instance {
        /**
         * <code>TABLE_FACTORY_CHAIN</code>
         * {@link io.github.nichetoolkit.mybatis.MybatisTableFactory.Chain} <p>the constant <code>TABLE_FACTORY_CHAIN</code> field.</p>
         * @see io.github.nichetoolkit.mybatis.MybatisTableFactory.Chain
         */
        private static volatile MybatisTableFactory.Chain TABLE_FACTORY_CHAIN;
        /**
         * <code>COLUMN_FACTORY_CHAIN</code>
         * {@link io.github.nichetoolkit.mybatis.MybatisColumnFactory.Chain} <p>the constant <code>COLUMN_FACTORY_CHAIN</code> field.</p>
         * @see io.github.nichetoolkit.mybatis.MybatisColumnFactory.Chain
         */
        private static volatile MybatisColumnFactory.Chain COLUMN_FACTORY_CHAIN;

        /**
         * <code>tableFactoryChain</code>
         * <p>the factory chain method.</p>
         * @return {@link io.github.nichetoolkit.mybatis.MybatisTableFactory.Chain} <p>the factory chain return object is <code>Chain</code> type.</p>
         * @see io.github.nichetoolkit.mybatis.MybatisTableFactory.Chain
         */
        public static MybatisTableFactory.Chain tableFactoryChain() {
            if (TABLE_FACTORY_CHAIN == null) {
                synchronized (MybatisFactory.class) {
                    if (TABLE_FACTORY_CHAIN == null) {
                        List<MybatisTableFactory> mybatisTableFactories = ServiceLoaderHelper.instances(MybatisTableFactory.class);
                        TABLE_FACTORY_CHAIN = new DefaultTableFactoryChain(mybatisTableFactories);
                    }
                }
            }
            return TABLE_FACTORY_CHAIN;
        }

        /**
         * <code>columnFactoryChain</code>
         * <p>the factory chain method.</p>
         * @return {@link io.github.nichetoolkit.mybatis.MybatisColumnFactory.Chain} <p>the factory chain return object is <code>Chain</code> type.</p>
         * @see io.github.nichetoolkit.mybatis.MybatisColumnFactory.Chain
         */
        public static MybatisColumnFactory.Chain columnFactoryChain() {
            if (COLUMN_FACTORY_CHAIN == null) {
                synchronized (MybatisFactory.class) {
                    if (COLUMN_FACTORY_CHAIN == null) {
                        List<MybatisColumnFactory> mybatisColumnFactories = ServiceLoaderHelper.instances(MybatisColumnFactory.class);
                        COLUMN_FACTORY_CHAIN = new DefaultColumnFactoryChain(mybatisColumnFactories);
                    }
                }
            }
            return COLUMN_FACTORY_CHAIN;
        }
    }
}
