package io.github.nichetoolkit.mybatis;

import java.util.List;
import java.util.Optional;

/**
 * <code>MybatisColumnFactory</code>
 * <p>The type mybatis column factory interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisOrder
 * @since Jdk1.8
 */
public interface MybatisColumnFactory extends MybatisOrder {

    /**
     * <code>supports</code>
     * <p>the method.</p>
     * @param table {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>the table parameter is <code>MybatisTable</code> type.</p>
     * @param field {@link io.github.nichetoolkit.mybatis.MybatisField} <p>the field parameter is <code>MybatisField</code> type.</p>
     * @return boolean <p>the return object is <code>boolean</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     * @see io.github.nichetoolkit.mybatis.MybatisField
     */
    boolean supports(MybatisTable table, MybatisField field);

    /**
     * <code>createColumn</code>
     * <p>the column method.</p>
     * @param table {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>the table parameter is <code>MybatisTable</code> type.</p>
     * @param field {@link io.github.nichetoolkit.mybatis.MybatisField} <p>the field parameter is <code>MybatisField</code> type.</p>
     * @param chain {@link io.github.nichetoolkit.mybatis.MybatisColumnFactory.Chain} <p>the chain parameter is <code>Chain</code> type.</p>
     * @return {@link java.util.Optional} <p>the column return object is <code>Optional</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     * @see io.github.nichetoolkit.mybatis.MybatisField
     * @see io.github.nichetoolkit.mybatis.MybatisColumnFactory.Chain
     * @see java.util.Optional
     */
    Optional<List<MybatisColumn>> createColumn(MybatisTable table, MybatisField field, Chain chain);

    /**
     * <code>Chain</code>
     * <p>The type chain interface.</p>
     * @author Cyan (snow22314@outlook.com)
     * @since Jdk1.8
     */
    interface Chain {
        /**
         * <code>createColumn</code>
         * <p>the column method.</p>
         * @param table {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>the table parameter is <code>MybatisTable</code> type.</p>
         * @param field {@link io.github.nichetoolkit.mybatis.MybatisField} <p>the field parameter is <code>MybatisField</code> type.</p>
         * @return {@link java.util.Optional} <p>the column return object is <code>Optional</code> type.</p>
         * @see io.github.nichetoolkit.mybatis.MybatisTable
         * @see io.github.nichetoolkit.mybatis.MybatisField
         * @see java.util.Optional
         */
        Optional<List<MybatisColumn>> createColumn(MybatisTable table, MybatisField field);
    }
}
