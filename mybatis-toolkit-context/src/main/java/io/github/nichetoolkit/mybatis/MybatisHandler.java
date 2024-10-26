package io.github.nichetoolkit.mybatis;

import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.mapping.MappedStatement;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.util.List;

/**
 * <code>MybatisHandler</code>
 * <p>The mybatis handler interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisOrder
 * @since Jdk1.8
 */
public interface MybatisHandler extends MybatisOrder {

    /**
     * <code>ofHandle</code>
     * <p>The of handle method.</p>
     * @param table     {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The table parameter is <code>MybatisTable</code> type.</p>
     * @param statement {@link org.apache.ibatis.mapping.MappedStatement} <p>The statement parameter is <code>MappedStatement</code> type.</p>
     * @param context   {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The context parameter is <code>ProviderContext</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     * @see org.apache.ibatis.mapping.MappedStatement
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     */
    static void ofHandle(MybatisTable table, MappedStatement statement, ProviderContext context) {
        for (MybatisHandler handler : Instance.handlerChain()) {
            handler.handle(table, statement, context);
        }
    }

    /**
     * <code>handle</code>
     * <p>The handle method.</p>
     * @param table     {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The table parameter is <code>MybatisTable</code> type.</p>
     * @param statement {@link org.apache.ibatis.mapping.MappedStatement} <p>The statement parameter is <code>MappedStatement</code> type.</p>
     * @param context   {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The context parameter is <code>ProviderContext</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     * @see org.apache.ibatis.mapping.MappedStatement
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     */
    void handle(MybatisTable table, MappedStatement statement, ProviderContext context);

    /**
     * <code>Instance</code>
     * <p>The instance class.</p>
     * @author Cyan (snow22314@outlook.com)
     * @since Jdk1.8
     */
    class Instance {

        /**
         * <code>HANDLERS</code>
         * {@link java.util.List} <p>The <code>HANDLERS</code> field.</p>
         * @see java.util.List
         */
        private static List<MybatisHandler> HANDLERS;

        /**
         * <code>handlerChain</code>
         * <p>The handler chain method.</p>
         * @return {@link java.util.List} <p>The handler chain return object is <code>List</code> type.</p>
         * @see java.util.List
         */
        public static List<MybatisHandler> handlerChain() {
            if (HANDLERS == null) {
                HANDLERS = SpringFactoriesLoader.loadFactories(MybatisHandler.class, null);
            }
            return HANDLERS;
        }
    }
}
