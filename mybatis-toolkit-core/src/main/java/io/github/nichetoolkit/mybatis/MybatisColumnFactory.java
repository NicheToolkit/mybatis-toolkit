package io.github.nichetoolkit.mybatis;

import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

/**
 * <code>MybatisColumnFactory</code>
 * <p>The mybatis column factory interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisOrder
 * @since Jdk1.8
 */
public interface MybatisColumnFactory extends MybatisOrder {

    /**
     * <code>supports</code>
     * <p>The supports method.</p>
     * @param table {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The table parameter is <code>MybatisTable</code> type.</p>
     * @param field {@link io.github.nichetoolkit.mybatis.MybatisField} <p>The field parameter is <code>MybatisField</code> type.</p>
     * @return boolean <p>The supports return object is <code>boolean</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     * @see org.springframework.lang.NonNull
     * @see io.github.nichetoolkit.mybatis.MybatisField
     */
    boolean supports(@NonNull MybatisTable table, @NonNull MybatisField field);

    /**
     * <code>createColumn</code>
     * <p>The create column method.</p>
     * @param table {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The table parameter is <code>MybatisTable</code> type.</p>
     * @param field {@link io.github.nichetoolkit.mybatis.MybatisField} <p>The field parameter is <code>MybatisField</code> type.</p>
     * @param chain {@link io.github.nichetoolkit.mybatis.MybatisColumnFactory.Chain} <p>The chain parameter is <code>Chain</code> type.</p>
     * @return {@link java.util.Optional} <p>The create column return object is <code>Optional</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     * @see org.springframework.lang.NonNull
     * @see io.github.nichetoolkit.mybatis.MybatisField
     * @see io.github.nichetoolkit.mybatis.MybatisColumnFactory.Chain
     * @see java.util.Optional
     */
    Optional<List<MybatisColumn>> createColumn(@NonNull MybatisTable table, @NonNull MybatisField field, Chain chain);

    /**
     * <code>Chain</code>
     * <p>The chain interface.</p>
     * @author Cyan (snow22314@outlook.com)
     * @since Jdk1.8
     */
    interface Chain {
        /**
         * <code>createColumn</code>
         * <p>The create column method.</p>
         * @param table {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The table parameter is <code>MybatisTable</code> type.</p>
         * @param field {@link io.github.nichetoolkit.mybatis.MybatisField} <p>The field parameter is <code>MybatisField</code> type.</p>
         * @return {@link java.util.Optional} <p>The create column return object is <code>Optional</code> type.</p>
         * @see io.github.nichetoolkit.mybatis.MybatisTable
         * @see org.springframework.lang.NonNull
         * @see io.github.nichetoolkit.mybatis.MybatisField
         * @see java.util.Optional
         */
        Optional<List<MybatisColumn>> createColumn(@NonNull MybatisTable table, @NonNull MybatisField field);
    }
}
