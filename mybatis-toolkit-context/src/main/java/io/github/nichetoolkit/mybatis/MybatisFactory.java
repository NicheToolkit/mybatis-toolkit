package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.builder.SqlUtils;
import io.github.nichetoolkit.mybatis.defaults.DefaultColumnFactoryChain;
import io.github.nichetoolkit.mybatis.defaults.DefaultTableFactoryChain;
import io.github.nichetoolkit.mybatis.error.MybatisTableLackError;
import io.github.nichetoolkit.mybatis.stereotype.column.RestIdentityKey;
import io.github.nichetoolkit.mybatis.stereotype.column.RestLinkKey;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.error.lack.InterfaceLackError;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rest.util.OptionalUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Slf4j
public abstract class MybatisFactory {

    @SuppressWarnings("all")
    public static MybatisTable createTable(@NonNull Class<?> mapperType, @Nullable Method mapperMethod) throws RestException {
        Class<?> entityClass = MybatisClassFinder.findEntityClass(mapperType, mapperMethod);
        String message = "Can not find " + (mapperMethod != null ? mapperMethod.getName() + " method" : mapperType.getSimpleName() + " interface") + " corresponding mybatis class";
        OptionalUtils.ofNullError(entityClass, message, log, InterfaceLackError::new);
        Class<?> identityClass = MybatisClassFinder.findIdentityClass(mapperType, mapperMethod, entityClass);
        Class<?> linkageClass = MybatisClassFinder.findLinkageClass(mapperType, mapperMethod, entityClass);
        Class<?> alertnessClass = MybatisClassFinder.findAlertnessClass(mapperType, mapperMethod, entityClass);
        return createTable(entityClass, identityClass, linkageClass, alertnessClass);
    }

    @SuppressWarnings("all")
    public static MybatisTable createTable(@NonNull Class<?> entityType, @Nullable Class<?> identityType, @Nullable Class<?> linkageType, @Nullable Class<?> alertnessType) {
        /* 处理MybatisTable */
        MybatisTableFactory.Chain tableFactoryChain = Instance.tableFactoryChain();
        /* 创建 MybatisTable，不处理列（字段），此时返回的 MybatisTable 已经经过了所有处理链的加工 */
        MybatisTable table = tableFactoryChain.createTable(entityType, identityType, linkageType, alertnessType);
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
                            SqlUtils.reverseArray(declaredFields);
                        }
                        for (Field declaredField : declaredFields) {
                            int modifiers = declaredField.getModifiers();
                            /* 排除 static 和 transient 修饰的字段 */
                            if (!Modifier.isStatic(modifiers) && !Modifier.isTransient(modifiers)) {
                                MybatisField mybatisField = new MybatisField(entityType, declaredField);
                                /* 是否需要排除字段 */
                                if (table.isExcludeField(mybatisField)) {
                                    continue;
                                }
                                /* 使用了自定义联合主键字 */
                                if (GeneralUtils.isNotEmpty(identityType) && GeneralUtils.isNotEmpty(mybatisField.getAnnotation(RestIdentityKey.class))) {
                                    handleOfFields(table, identityType, columnFactoryChain, identityField -> new MybatisField(entityType, mybatisField, identityField, true, false));
                                } else if (GeneralUtils.isNotEmpty(linkageType) && GeneralUtils.isNotEmpty(mybatisField.getAnnotation(RestLinkKey.class))) {
                                    handleOfFields(table, linkageType, columnFactoryChain, linkField -> new MybatisField(entityType, mybatisField, linkField, false, true));
                                } else {
                                    Optional<List<MybatisColumn>> optionalColumns = columnFactoryChain.createColumn(table, mybatisField);
                                    optionalColumns.ifPresent(columns -> columns.forEach(table::addColumn));
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

    protected static void handleOfFields(MybatisTable table, Class<?> declaredType, MybatisColumnFactory.Chain columnFactoryChain, Function<Field, MybatisField> fieldFunction) {
        /* 未处理的需要获取字段 */
        Class<?> declaredClass = declaredType;
        boolean isSuperclass = true;
        while (declaredClass != null && declaredClass != Object.class) {
            Field[] declaredFields = declaredClass.getDeclaredFields();
            SqlUtils.reverseArray(declaredFields);
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

    static class Instance {
        private static volatile MybatisTableFactory.Chain TABLE_FACTORY_CHAIN;
        private static volatile MybatisColumnFactory.Chain COLUMN_FACTORY_CHAIN;

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
