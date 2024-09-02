package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.helper.ServiceLoaderHelper;
import org.apache.ibatis.builder.annotation.ProviderContext;

import java.util.List;

/**
 * <code>MybatisSqlScriptWrapper</code>
 * <p>The type mybatis sql script wrapper interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisOrder
 * @since Jdk1.8
 */
public interface MybatisSqlScriptWrapper extends MybatisOrder {
    /**
     * <code>wrapSqlScript</code>
     * <p>the sql script method.</p>
     * @param context   {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the context parameter is <code>ProviderContext</code> type.</p>
     * @param table     {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>the table parameter is <code>MybatisTable</code> type.</p>
     * @param sqlScript {@link io.github.nichetoolkit.mybatis.MybatisSqlScript} <p>the sql script parameter is <code>MybatisSqlScript</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.MybatisSqlScript} <p>the sql script return object is <code>MybatisSqlScript</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     */
    static MybatisSqlScript wrapSqlScript(ProviderContext context, MybatisTable table, MybatisSqlScript sqlScript) {
        for (MybatisSqlScriptWrapper wrapper : Instance.sqlScriptWrapperChain()) {
            sqlScript = wrapper.wrap(context, table, sqlScript);
        }
        return sqlScript;
    }

    /**
     * <code>wrap</code>
     * <p>the method.</p>
     * @param context   {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the context parameter is <code>ProviderContext</code> type.</p>
     * @param table     {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>the table parameter is <code>MybatisTable</code> type.</p>
     * @param sqlScript {@link io.github.nichetoolkit.mybatis.MybatisSqlScript} <p>the sql script parameter is <code>MybatisSqlScript</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.MybatisSqlScript} <p>the return object is <code>MybatisSqlScript</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     */
    MybatisSqlScript wrap(ProviderContext context, MybatisTable table, MybatisSqlScript sqlScript);

    /**
     * <code>Instance</code>
     * <p>The type instance class.</p>
     * @author Cyan (snow22314@outlook.com)
     * @since Jdk1.8
     */
    class Instance {
        private static volatile List<MybatisSqlScriptWrapper> SQL_SCRIPT_WRAPPERS;

        /**
         * <code>sqlScriptWrapperChain</code>
         * <p>the script wrapper chain method.</p>
         * @return {@link java.util.List} <p>the script wrapper chain return object is <code>List</code> type.</p>
         * @see java.util.List
         */
        public static List<MybatisSqlScriptWrapper> sqlScriptWrapperChain() {
            if (SQL_SCRIPT_WRAPPERS == null) {
                synchronized (MybatisFactory.class) {
                    if (SQL_SCRIPT_WRAPPERS == null) {
                        SQL_SCRIPT_WRAPPERS = ServiceLoaderHelper.instances(MybatisSqlScriptWrapper.class);
                    }
                }
            }
            return SQL_SCRIPT_WRAPPERS;
        }
    }
}
