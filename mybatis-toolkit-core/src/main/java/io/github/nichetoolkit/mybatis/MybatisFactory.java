package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.defaults.DefaultMybatisColumnFactoryChain;
import io.github.nichetoolkit.mybatis.defaults.DefaultMybatisTableFactoryChain;
import io.github.nichetoolkit.mybatis.helper.ServiceLoaderHelper;
import io.github.nichetoolkit.rest.RestException;

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
    public static MybatisTable create(Class<?> mapperType, Method mapperMethod) throws RestException {
        Optional<Class<?>> optionalClass = MybatisClassFinder.find(mapperType, mapperMethod);
        if (optionalClass.isPresent()) {
            return create(optionalClass.get());
        }
        throw new RuntimeException("Can't obtain " + (mapperMethod != null ?
                mapperMethod.getName() + " method" : mapperType.getSimpleName() + " interface") + " corresponding mybatis class");
    }

    /**
     * 获取类型对应的实体信息
     * @param clazz 实体类类型
     * @return 实体类信息
     */
    public static MybatisTable create(Class<?> clazz) throws RestException {
        /** 处理MybatisTable */
        MybatisTableFactory.Chain mybatisTableFactoryChain = Instance.getMybatisTableFactoryChain();
        /** 创建 MybatisTable，不处理列（字段），此时返回的 MybatisTable 已经经过了所有处理链的加工 */
        MybatisTable mybatisTable = mybatisTableFactoryChain.createMybatisTable(clazz);
        if (mybatisTable == null) {
            throw new NullPointerException("Unable to get " + clazz.getName() + " mybatis class information");
        }
        /** 如果实体表已经处理好，直接返回 */
        if (!mybatisTable.ready()) {
            synchronized (clazz) {
                if (!mybatisTable.ready()) {
                    /** 处理MybatisColumn */
                    MybatisColumnFactory.Chain mybatisColumnFactoryChain = Instance.getMybatisColumnFactoryChain();
                    /** 未处理的需要获取字段 */
                    Class<?> declaredClass = clazz;
                    boolean isSuperclass = false;
                    while (declaredClass != null && declaredClass != Object.class) {
                        Field[] declaredFields = declaredClass.getDeclaredFields();
                        if (isSuperclass) {
                            reverse(declaredFields);
                        }
                        for (Field field : declaredFields) {
                            int modifiers = field.getModifiers();
                            /** 排除 static 和 transient 修饰的字段 */
                            if (!Modifier.isStatic(modifiers) && !Modifier.isTransient(modifiers)) {
                                MybatisField mybatisField = new MybatisField(clazz, field);
                                /** 是否需要排除字段 */
                                if (mybatisTable.isExcludeField(mybatisField)) {
                                    continue;
                                }
                                Optional<List<MybatisColumn>> optionalMybatisColumns = mybatisColumnFactoryChain.createMybatisColumn(mybatisTable, mybatisField);
                                optionalMybatisColumns.ifPresent(columns -> columns.forEach(mybatisTable::addColumn));
                            }
                        }
                        /** 迭代获取父类 */
                        declaredClass = declaredClass.getSuperclass();
                        /** 排除父类 */
                        while (mybatisTable.isExcludeSuperClass(declaredClass) && declaredClass != Object.class) {
                            declaredClass = declaredClass.getSuperclass();
                        }
                        isSuperclass = true;
                    }
                    /** 标记处理完成 */
                    mybatisTable.ready(true);
                }
            }
        }
        return mybatisTable;
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
        private static volatile MybatisTableFactory.Chain mybatisTableFactoryChain;
        private static volatile MybatisColumnFactory.Chain mybatisColumnFactoryChain;

        /**
         * 获取处理实体的工厂链
         * @return 实例
         */
        public static MybatisTableFactory.Chain getMybatisTableFactoryChain() {
            if (mybatisTableFactoryChain == null) {
                synchronized (MybatisFactory.class) {
                    if (mybatisTableFactoryChain == null) {
                        List<MybatisTableFactory> mybatisTableFactories = ServiceLoaderHelper.instances(MybatisTableFactory.class);
                        mybatisTableFactoryChain = new DefaultMybatisTableFactoryChain(mybatisTableFactories);
                    }
                }
            }
            return mybatisTableFactoryChain;
        }

        /**
         * 获取处理列的工厂链
         * @return 实例
         */
        public static MybatisColumnFactory.Chain getMybatisColumnFactoryChain() {
            if (mybatisColumnFactoryChain == null) {
                synchronized (MybatisFactory.class) {
                    if (mybatisColumnFactoryChain == null) {
                        List<MybatisColumnFactory> mybatisColumnFactories = ServiceLoaderHelper.instances(MybatisColumnFactory.class);
                        mybatisColumnFactoryChain = new DefaultMybatisColumnFactoryChain(mybatisColumnFactories);
                    }
                }
            }
            return mybatisColumnFactoryChain;
        }
    }
}
