package io.github.nichetoolkit.mybatis;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * <code>MybatisTableFactory</code>
 * <p>The mybatis table factory interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisOrder
 * @since Jdk1.8
 */
public interface MybatisTableFactory extends MybatisOrder {

    /**
     * <code>supports</code>
     * <p>The supports method.</p>
     * @param entityType {@link java.lang.Class} <p>The entity type parameter is <code>Class</code> type.</p>
     * @return boolean <p>The supports return object is <code>boolean</code> type.</p>
     * @see java.lang.Class
     * @see org.springframework.lang.NonNull
     */
    boolean supports(@NonNull Class<?> entityType);

    /**
     * <code>createTable</code>
     * <p>The create table method.</p>
     * @param entityType    {@link java.lang.Class} <p>The entity type parameter is <code>Class</code> type.</p>
     * @param identityType  {@link java.lang.Class} <p>The identity type parameter is <code>Class</code> type.</p>
     * @param linkageType   {@link java.lang.Class} <p>The linkage type parameter is <code>Class</code> type.</p>
     * @param alertnessType {@link java.lang.Class} <p>The alertness type parameter is <code>Class</code> type.</p>
     * @param chain         {@link io.github.nichetoolkit.mybatis.MybatisTableFactory.Chain} <p>The chain parameter is <code>Chain</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The create table return object is <code>MybatisTable</code> type.</p>
     * @see java.lang.Class
     * @see org.springframework.lang.NonNull
     * @see org.springframework.lang.Nullable
     * @see io.github.nichetoolkit.mybatis.MybatisTableFactory.Chain
     */
    MybatisTable createTable(@NonNull Class<?> entityType, @Nullable Class<?> identityType, @Nullable Class<?> linkageType, @Nullable Class<?> alertnessType, Chain chain);

    /**
     * <code>Chain</code>
     * <p>The chain interface.</p>
     * @author Cyan (snow22314@outlook.com)
     * @since Jdk1.8
     */
    interface Chain {
        /**
         * <code>createTable</code>
         * <p>The create table method.</p>
         * @param entityType    {@link java.lang.Class} <p>The entity type parameter is <code>Class</code> type.</p>
         * @param identityType  {@link java.lang.Class} <p>The identity type parameter is <code>Class</code> type.</p>
         * @param linkageType   {@link java.lang.Class} <p>The linkage type parameter is <code>Class</code> type.</p>
         * @param alertnessType {@link java.lang.Class} <p>The alertness type parameter is <code>Class</code> type.</p>
         * @return {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The create table return object is <code>MybatisTable</code> type.</p>
         * @see java.lang.Class
         * @see org.springframework.lang.NonNull
         * @see org.springframework.lang.Nullable
         */
        MybatisTable createTable(@NonNull Class<?> entityType, @Nullable Class<?> identityType, @Nullable Class<?> linkageType, @Nullable Class<?> alertnessType);
    }
}
