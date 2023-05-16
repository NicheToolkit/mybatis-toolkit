package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.defaults.DefaultColumnFactoryChain;
import io.github.nichetoolkit.mybatis.defaults.DefaultTableFactoryChain;
import io.github.nichetoolkit.mybatis.helper.ServiceLoaderHelper;
import io.github.nichetoolkit.rest.error.lack.InterfaceLackError;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Optional;

/**
 * <p>MybatisFactory</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public abstract class MybatisFactory {
    /**
     * 获取类型对应的实体信息
     * @param mapperType   接口
     * @param mapperMethod 方法
     * @return 实体类信息
     */
    public static MybatisTable createTable(Class<?> mapperType, Method mapperMethod) {
        Optional<Class<?>> optionalClass = MybatisClassFinder.findClass(mapperType, mapperMethod);
        if (optionalClass.isPresent()) {
            return createTable(optionalClass.get());
        }
        throw new InterfaceLackError("Can't obtain " + (mapperMethod != null ?
                mapperMethod.getName() + " method" : mapperType.getSimpleName() + " interface") + " corresponding mybatis class");
    }

    /**
     * 获取类型对应的实体信息
     * @param clazz 实体类类型
     * @return 实体类信息
     */
    public static MybatisTable createTable(Class<?> clazz) {
        /** 处理MybatisTable */
        MybatisTableFactory.Chain tableFactoryChain = Instance.tableFactoryChain();
        /** 创建 MybatisTable，不处理列（字段），此时返回的 MybatisTable 已经经过了所有处理链的加工 */
        MybatisTable table = tableFactoryChain.createTable(clazz);
        if (table == null) {
            throw new NullPointerException("Unable to get " + clazz.getName() + " mybatis class information");
        }
        /** 如果实体表已经处理好，直接返回 */
        if (!table.isReady()) {
            synchronized (clazz) {
                if (!table.isReady()) {
                    /** 处理MybatisColumn */
                    MybatisColumnFactory.Chain columnFactoryChain = Instance.columnFactoryChain();
                    /** 未处理的需要获取字段 */
                    Class<?> declaredClass = clazz;
                    boolean isSuperclass = false;
                    while (declaredClass != null && declaredClass != Object.class) {
                        Field[] declaredFields = declaredClass.getDeclaredFields();
                        if (isSuperclass) {
                            reverse(declaredFields);
                        }
                        for (Field declaredField : declaredFields) {
                            int modifiers = declaredField.getModifiers();
                            /** 排除 static 和 transient 修饰的字段 */
                            if (!Modifier.isStatic(modifiers) && !Modifier.isTransient(modifiers)) {
                                MybatisField field = new MybatisField(clazz, declaredField);
                                /** 是否需要排除字段 */
                                if (table.isExcludeField(field)) {
                                    continue;
                                }
                                Optional<List<MybatisColumn>> optionalColumns = columnFactoryChain.createColumn(table, field);
                                optionalColumns.ifPresent(columns -> columns.forEach(table::addColumn));
                            }
                        }
                        /** 迭代获取父类 */
                        declaredClass = declaredClass.getSuperclass();
                        /** 排除父类 */
                        while (table.isExcludeSuperClass(declaredClass) && declaredClass != Object.class) {
                            declaredClass = declaredClass.getSuperclass();
                        }
                        isSuperclass = true;
                    }
                    table.readyColumns();
                    /** 标记处理完成 */
                    table.setReady(true);
                }
            }
        }
        return table;
    }

    /**
     * 反转排序
     * @param array 数组
     */
    protected static void reverse(Object[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            Object temp = array[i];
            array[i] = array[array.length - i - 1];
            array[array.length - i - 1] = temp;
        }
    }

    /**
     * 实例
     */
    static class Instance {
        private static volatile MybatisTableFactory.Chain TABLE_FACTORY_CHAIN;
        private static volatile MybatisColumnFactory.Chain COLUMN_FACTORY_CHAIN;

        /**
         * 获取处理实体的工厂链
         * @return 实例
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
         * 获取处理列的工厂链
         * @return 实例
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
