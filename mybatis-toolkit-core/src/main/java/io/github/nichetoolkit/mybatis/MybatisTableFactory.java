package io.github.nichetoolkit.mybatis;

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
     * @param clazz {@link java.lang.Class} <p>the clazz parameter is <code>Class</code> type.</p>
     * @return boolean <p>the return object is <code>boolean</code> type.</p>
     * @see java.lang.Class
     */
    boolean supports(Class<?> clazz);

    /**
     * <code>createTable</code>
     * <p>the table method.</p>
     * @param clazz {@link java.lang.Class} <p>the clazz parameter is <code>Class</code> type.</p>
     * @param chain {@link io.github.nichetoolkit.mybatis.MybatisTableFactory.Chain} <p>the chain parameter is <code>Chain</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>the table return object is <code>MybatisTable</code> type.</p>
     * @see java.lang.Class
     * @see io.github.nichetoolkit.mybatis.MybatisTableFactory.Chain
     */
    MybatisTable createTable(Class<?> clazz, Chain chain);

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
         * @param clazz {@link java.lang.Class} <p>the clazz parameter is <code>Class</code> type.</p>
         * @return {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>the table return object is <code>MybatisTable</code> type.</p>
         * @see java.lang.Class
         */
        MybatisTable createTable(Class<?> clazz);
    }
}
