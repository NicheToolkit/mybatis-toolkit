package io.github.nichetoolkit.mybatis;

import org.apache.ibatis.builder.annotation.ProviderContext;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.util.List;

/**
 * <code>MybatisSqlScriptResolver</code>
 * <p>The mybatis sql script resolver interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisOrder
 * @since Jdk1.8
 */
public interface MybatisSqlScriptResolver extends MybatisOrder {

    /**
     * <code>ofResolve</code>
     * <p>The of resolve method.</p>
     * @param context   {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The context parameter is <code>ProviderContext</code> type.</p>
     * @param table     {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The table parameter is <code>MybatisTable</code> type.</p>
     * @param sqlScript {@link io.github.nichetoolkit.mybatis.MybatisSqlScript} <p>The sql script parameter is <code>MybatisSqlScript</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.MybatisSqlScript} <p>The of resolve return object is <code>MybatisSqlScript</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     */
    static MybatisSqlScript ofResolve(ProviderContext context, MybatisTable table, MybatisSqlScript sqlScript) {
        for (MybatisSqlScriptResolver resolver : Instance.sqlScriptResolvers()) {
            sqlScript = resolver.resolve(context, table, sqlScript);
        }
        return sqlScript;
    }

    /**
     * <code>resolve</code>
     * <p>The resolve method.</p>
     * @param context   {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The context parameter is <code>ProviderContext</code> type.</p>
     * @param table     {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The table parameter is <code>MybatisTable</code> type.</p>
     * @param sqlScript {@link io.github.nichetoolkit.mybatis.MybatisSqlScript} <p>The sql script parameter is <code>MybatisSqlScript</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.MybatisSqlScript} <p>The resolve return object is <code>MybatisSqlScript</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     */
    MybatisSqlScript resolve(ProviderContext context, MybatisTable table, MybatisSqlScript sqlScript);

    /**
     * <code>Instance</code>
     * <p>The instance class.</p>
     * @author Cyan (snow22314@outlook.com)
     * @since Jdk1.8
     */
    class Instance {
        /**
         * <code>SQL_SCRIPT_RESOLVERS</code>
         * {@link java.util.List} <p>The constant <code>SQL_SCRIPT_RESOLVERS</code> field.</p>
         * @see java.util.List
         */
        private static volatile List<MybatisSqlScriptResolver> SQL_SCRIPT_RESOLVERS;

        /**
         * <code>sqlScriptResolvers</code>
         * <p>The sql script resolvers method.</p>
         * @return {@link java.util.List} <p>The sql script resolvers return object is <code>List</code> type.</p>
         * @see java.util.List
         */
        public static List<MybatisSqlScriptResolver> sqlScriptResolvers() {
            if (SQL_SCRIPT_RESOLVERS == null) {
                synchronized (MybatisFactory.class) {
                    if (SQL_SCRIPT_RESOLVERS == null) {
                        SQL_SCRIPT_RESOLVERS = SpringFactoriesLoader.loadFactories(MybatisSqlScriptResolver.class, null);

                    }
                }
            }
            return SQL_SCRIPT_RESOLVERS;
        }
    }
}
