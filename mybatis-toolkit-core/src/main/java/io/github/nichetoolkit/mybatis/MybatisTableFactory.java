package io.github.nichetoolkit.mybatis;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * <code>MybatisTableFactory</code>
 * <p>The type mybatis table factory interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisOrder
 * @since Jdk1.8
 */
public interface MybatisTableFactory extends MybatisOrder {

    /**
     * <code>supports</code>
     * <p>the method.</p>
     * @param entityType {@link java.lang.Class} <p>the entity type parameter is <code>Class</code> type.</p>
     * @return boolean <p>the return object is <code>boolean</code> type.</p>
     * @see java.lang.Class
     */
    boolean supports(@NonNull Class<?> entityType);

    /**
     * <code>createTable</code>
     * <p>the table method.</p>
     * @param entityType      {@link java.lang.Class} <p>the entity type parameter is <code>Class</code> type.</p>
     * @param identityType {@link java.lang.Class} <p>the identity key type parameter is <code>Class</code> type.</p>
     * @param chain           {@link io.github.nichetoolkit.mybatis.MybatisTableFactory.Chain} <p>the chain parameter is <code>Chain</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>the table return object is <code>MybatisTable</code> type.</p>
     * @see java.lang.Class
     * @see org.springframework.lang.NonNull
     * @see org.springframework.lang.Nullable
     * @see io.github.nichetoolkit.mybatis.MybatisTableFactory.Chain
     */
    MybatisTable createTable(@NonNull Class<?> entityType, @Nullable Class<?> identityType, Chain chain);

    /**
     * <code>Chain</code>
     * <p>The type chain interface.</p>
     * @author Cyan (snow22314@outlook.com)
     * @since Jdk1.8
     */
    interface Chain {
        /**
         * <code>createTable</code>
         * <p>the table method.</p>
         * @param entityType      {@link java.lang.Class} <p>the entity type parameter is <code>Class</code> type.</p>
         * @param identityType {@link java.lang.Class} <p>the identity key type parameter is <code>Class</code> type.</p>
         * @return {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>the table return object is <code>MybatisTable</code> type.</p>
         * @see java.lang.Class
         * @see org.springframework.lang.NonNull
         * @see org.springframework.lang.Nullable
         */
        MybatisTable createTable(@NonNull Class<?> entityType, @Nullable Class<?> identityType);
    }
}
