package io.github.nichetoolkit.mybatis;

import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.util.List;

/**
 * <code>MybatisSqlSourceProvider</code>
 * <p>The mybatis sql source provider interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisOrder
 * @since Jdk1.8
 */
public interface MybatisSqlSourceProvider extends MybatisOrder {

    /**
     * <code>ofProvide</code>
     * <p>The of provide method.</p>
     * @param sqlSource {@link org.apache.ibatis.mapping.SqlSource} <p>The sql source parameter is <code>SqlSource</code> type.</p>
     * @param table     {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The table parameter is <code>MybatisTable</code> type.</p>
     * @param statement {@link org.apache.ibatis.mapping.MappedStatement} <p>The statement parameter is <code>MappedStatement</code> type.</p>
     * @param context   {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The context parameter is <code>ProviderContext</code> type.</p>
     * @return {@link org.apache.ibatis.mapping.SqlSource} <p>The of provide return object is <code>SqlSource</code> type.</p>
     * @see org.apache.ibatis.mapping.SqlSource
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     * @see org.apache.ibatis.mapping.MappedStatement
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     */
    static SqlSource ofProvide(SqlSource sqlSource, MybatisTable table, MappedStatement statement, ProviderContext context) {
        for (MybatisSqlSourceProvider provider : Instance.sqlSourceProviders()) {
            sqlSource = provider.provide(sqlSource, table, statement, context);
        }
        return sqlSource;
    }

    /**
     * <code>provide</code>
     * <p>The provide method.</p>
     * @param sqlSource {@link org.apache.ibatis.mapping.SqlSource} <p>The sql source parameter is <code>SqlSource</code> type.</p>
     * @param table     {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The table parameter is <code>MybatisTable</code> type.</p>
     * @param statement {@link org.apache.ibatis.mapping.MappedStatement} <p>The statement parameter is <code>MappedStatement</code> type.</p>
     * @param context   {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The context parameter is <code>ProviderContext</code> type.</p>
     * @return {@link org.apache.ibatis.mapping.SqlSource} <p>The provide return object is <code>SqlSource</code> type.</p>
     * @see org.apache.ibatis.mapping.SqlSource
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     * @see org.apache.ibatis.mapping.MappedStatement
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     */
    SqlSource provide(SqlSource sqlSource, MybatisTable table, MappedStatement statement, ProviderContext context);

    /**
     * <code>Instance</code>
     * <p>The instance class.</p>
     * @author Cyan (snow22314@outlook.com)
     * @since Jdk1.8
     */
    class Instance {

        /**
         * <code>SQL_SOURCE_PROVIDERS</code>
         * {@link java.util.List} <p>The <code>SQL_SOURCE_PROVIDERS</code> field.</p>
         * @see java.util.List
         */
        private static List<MybatisSqlSourceProvider> SQL_SOURCE_PROVIDERS;

        /**
         * <code>sqlSourceProviders</code>
         * <p>The sql source providers method.</p>
         * @return {@link java.util.List} <p>The sql source providers return object is <code>List</code> type.</p>
         * @see java.util.List
         */
        public static List<MybatisSqlSourceProvider> sqlSourceProviders() {
            if (SQL_SOURCE_PROVIDERS == null) {
                SQL_SOURCE_PROVIDERS = SpringFactoriesLoader.loadFactories(MybatisSqlSourceProvider.class, null);
            }
            return SQL_SOURCE_PROVIDERS;
        }
    }
}
