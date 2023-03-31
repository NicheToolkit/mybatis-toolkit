package io.github.nichetoolkit.mybatis;

import java.util.List;
import java.util.Optional;

/**
 * <p>MybatisColumnFactory</p>
 * 实体类信息工厂
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public interface MybatisColumnFactory extends MybatisOrder {
    /**
     * 创建列信息，一个字段可能不是列，也可能是列，还有可能对应多个列（例如 ValueObject对象）
     * @param table 表实体
     * @param field 字段信息
     * @param chain 调用下一个
     * @return 实体类中列的信息，如果返回空，则不属于实体中的列
     */
    Optional<List<MybatisColumn>> createColumn(MybatisTable table, MybatisField field, Chain chain);

    /**
     * 工厂链
     */
    interface Chain {
        /**
         * 创建列信息，一个字段可能不是列，也可能是列，还有可能对应多个列（例如 ValueObject对象）
         * @param table 表实体
         * @param field 字段信息
         * @return 实体类中列的信息，如果返回空，则不属于实体中的列
         */
        Optional<List<MybatisColumn>> createColumn(MybatisTable table, MybatisField field);
    }
}
