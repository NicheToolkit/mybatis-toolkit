package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.helper.ServiceLoaderHelper;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import java.util.List;

/**
 * <code>MybatisSqlSourceCustomize</code>
 * <p>The type mybatis sql source customize interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
public interface MybatisSqlSourceCustomize {


    /**
     * <code>DEFAULT_SQL_SOURCE_CUSTOMIZE</code>
     * {@link io.github.nichetoolkit.mybatis.MybatisSqlSourceCustomize} <p>the constant <code>DEFAULT_SQL_SOURCE_CUSTOMIZE</code> field.</p>
     */
    MybatisSqlSourceCustomize DEFAULT_SQL_SOURCE_CUSTOMIZE = new MybatisSqlSourceCustomize() {
        private final List<MybatisSqlSourceCustomize> sqlSourceCustomizes = ServiceLoaderHelper.instances(MybatisSqlSourceCustomize.class);

        @Override
        public SqlSource customize(SqlSource sqlSource, MybatisTable table, MappedStatement statement, ProviderContext context) {
            for (MybatisSqlSourceCustomize sqlSourceCustomize : sqlSourceCustomizes) {
                sqlSource = sqlSourceCustomize.customize(sqlSource, table, statement, context);
            }
            return sqlSource;
        }
    };

    /**
     * <code>customize</code>
     * <p>the method.</p>
     * @param sqlSource {@link org.apache.ibatis.mapping.SqlSource} <p>the sql source parameter is <code>SqlSource</code> type.</p>
     * @param table     {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>the table parameter is <code>MybatisTable</code> type.</p>
     * @param statement {@link org.apache.ibatis.mapping.MappedStatement} <p>the statement parameter is <code>MappedStatement</code> type.</p>
     * @param context   {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the context parameter is <code>ProviderContext</code> type.</p>
     * @return {@link org.apache.ibatis.mapping.SqlSource} <p>the return object is <code>SqlSource</code> type.</p>
     * @see org.apache.ibatis.mapping.SqlSource
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     * @see org.apache.ibatis.mapping.MappedStatement
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     */
    SqlSource customize(SqlSource sqlSource, MybatisTable table, MappedStatement statement, ProviderContext context);
}
