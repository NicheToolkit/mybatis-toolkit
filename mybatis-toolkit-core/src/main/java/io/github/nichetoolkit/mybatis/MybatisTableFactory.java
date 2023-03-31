package io.github.nichetoolkit.mybatis;

/**
 * <p>MybatisTableFactory</p>
 * 体类信息工厂
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public interface MybatisTableFactory extends MybatisOrder {
    /**
     * 根据实体类创建 MybatisTable，可以使用自己的注解来实现，
     * 这一步只返回 MybatisTable，不处理其中的字段
     * @param clazz 实体类类型
     * @param chain 调用下一个
     * @return 实体类信息
     */
    MybatisTable createTable(Class<?> clazz, Chain chain);

    /**
     * 工厂链
     */
    interface Chain {
        /**
         * 根据实体类创建 MybatisTable，可以使用自己的注解来实现，
         * 这一步只返回 MybatisTable，不处理其中的字段
         * @param clazz 实体类类型
         * @return 实体类信息
         */
        MybatisTable createTable(Class<?> clazz);
    }
}
