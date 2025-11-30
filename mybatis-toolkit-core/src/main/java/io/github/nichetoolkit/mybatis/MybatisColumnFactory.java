package io.github.nichetoolkit.mybatis;

import tools.jackson.databind.JavaType;
import io.github.nichetoolkit.rest.RestOrder;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Optional;

/**
 * <code>MybatisColumnFactory</code>
 * <p>The mybatis column factory interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rest.RestOrder
 * @since Jdk17
 */
public interface MybatisColumnFactory extends RestOrder {

    /**
     * <code>supports</code>
     * <p>The supports method.</p>
     * @param table {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The table parameter is <code>MybatisTable</code> type.</p>
     * @param field {@link io.github.nichetoolkit.mybatis.MybatisField} <p>The field parameter is <code>MybatisField</code> type.</p>
     * @return boolean <p>The supports return object is <code>boolean</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     * @see org.jspecify.annotations.NonNull
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
     * @see org.jspecify.annotations.NonNull
     * @see io.github.nichetoolkit.mybatis.MybatisField
     * @see io.github.nichetoolkit.mybatis.MybatisColumnFactory.Chain
     * @see java.util.Optional
     */
    Optional<List<MybatisColumn>> createColumn(@NonNull MybatisTable table, @NonNull MybatisField field, Chain chain);

    /**
     * <code>createColumn</code>
     * <p>The create column method.</p>
     * @param table         {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The table parameter is <code>MybatisTable</code> type.</p>
     * @param field         {@link io.github.nichetoolkit.mybatis.MybatisField} <p>The field parameter is <code>MybatisField</code> type.</p>
     * @param chain         {@link io.github.nichetoolkit.mybatis.MybatisColumnFactory.Chain} <p>The chain parameter is <code>Chain</code> type.</p>
     * @param fickleType    {@link tools.jackson.databind.JavaType} <p>The fickle type parameter is <code>JavaType</code> type.</p>
     * @param isFickleKey   boolean <p>The is fickle key parameter is <code>boolean</code> type.</p>
     * @param isFickleValue boolean <p>The is fickle value parameter is <code>boolean</code> type.</p>
     * @return {@link java.util.Optional} <p>The create column return object is <code>Optional</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     * @see org.jspecify.annotations.NonNull
     * @see io.github.nichetoolkit.mybatis.MybatisField
     * @see io.github.nichetoolkit.mybatis.MybatisColumnFactory.Chain
     * @see tools.jackson.databind.JavaType
     * @see java.util.Optional
     */
    Optional<List<MybatisColumn>> createColumn(@NonNull MybatisTable table, @NonNull MybatisField field, Chain chain, JavaType fickleType, boolean isFickleKey, boolean isFickleValue);

    /**
     * <code>Chain</code>
     * <p>The chain interface.</p>
     * @author Cyan (snow22314@outlook.com)
     * @since Jdk17
     */
    interface Chain {
        /**
         * <code>createColumn</code>
         * <p>The create column method.</p>
         * @param table {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The table parameter is <code>MybatisTable</code> type.</p>
         * @param field {@link io.github.nichetoolkit.mybatis.MybatisField} <p>The field parameter is <code>MybatisField</code> type.</p>
         * @return {@link java.util.Optional} <p>The create column return object is <code>Optional</code> type.</p>
         * @see io.github.nichetoolkit.mybatis.MybatisTable
         * @see org.jspecify.annotations.NonNull
         * @see io.github.nichetoolkit.mybatis.MybatisField
         * @see java.util.Optional
         */
        Optional<List<MybatisColumn>> createColumn(@NonNull MybatisTable table, @NonNull MybatisField field);

        /**
         * <code>createColumn</code>
         * <p>The create column method.</p>
         * @param table         {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The table parameter is <code>MybatisTable</code> type.</p>
         * @param field         {@link io.github.nichetoolkit.mybatis.MybatisField} <p>The field parameter is <code>MybatisField</code> type.</p>
         * @param fickleType    {@link tools.jackson.databind.JavaType} <p>The fickle type parameter is <code>JavaType</code> type.</p>
         * @param isFickleKey   boolean <p>The is fickle key parameter is <code>boolean</code> type.</p>
         * @param isFickleValue boolean <p>The is fickle value parameter is <code>boolean</code> type.</p>
         * @return {@link java.util.Optional} <p>The create column return object is <code>Optional</code> type.</p>
         * @see io.github.nichetoolkit.mybatis.MybatisTable
         * @see org.jspecify.annotations.NonNull
         * @see io.github.nichetoolkit.mybatis.MybatisField
         * @see tools.jackson.databind.JavaType
         * @see java.util.Optional
         */
        Optional<List<MybatisColumn>> createColumn(@NonNull MybatisTable table, @NonNull MybatisField field, JavaType fickleType, boolean isFickleKey, boolean isFickleValue);

    }
}
