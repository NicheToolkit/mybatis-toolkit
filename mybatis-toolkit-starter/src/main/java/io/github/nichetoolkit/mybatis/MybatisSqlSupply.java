package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.builder.SqlBuilder;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.RestOrder;
import org.jspecify.annotations.Nullable;

/**
 * <code>MybatisSqlSupply</code>
 * <p>The mybatis sql supply interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rest.RestOrder
 * @since Jdk17
 */
public interface MybatisSqlSupply extends RestOrder {

    /**
     * <code>supply</code>
     * <p>The supply method.</p>
     * @param tableName       {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param table           {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The table parameter is <code>MybatisTable</code> type.</p>
     * @param sqlScript       {@link io.github.nichetoolkit.mybatis.MybatisSqlScript} <p>The sql script parameter is <code>MybatisSqlScript</code> type.</p>
     * @param keySqlBuilder   {@link io.github.nichetoolkit.mybatis.builder.SqlBuilder} <p>The key sql builder parameter is <code>SqlBuilder</code> type.</p>
     * @param valueSqlBuilder {@link io.github.nichetoolkit.mybatis.builder.SqlBuilder} <p>The value sql builder parameter is <code>SqlBuilder</code> type.</p>
     * @param parameters      {@link java.lang.Object} <p>The parameters parameter is <code>Object</code> type.</p>
     * @return {@link java.lang.String} <p>The supply return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see org.jspecify.annotations.Nullable
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     * @see io.github.nichetoolkit.mybatis.MybatisSqlScript
     * @see io.github.nichetoolkit.mybatis.builder.SqlBuilder
     * @see java.lang.Object
     * @see io.github.nichetoolkit.rest.RestException
     */
    String supply(@Nullable String tableName, MybatisTable table, MybatisSqlScript sqlScript, SqlBuilder keySqlBuilder, SqlBuilder valueSqlBuilder, Object... parameters) throws RestException;

    /**
     * <code>SupplySqlSupply</code>
     * <p>The supply sql supply interface.</p>
     * @author Cyan (snow22314@outlook.com)
     * @since Jdk17
     */
    interface SupplySqlSupply extends MybatisSqlSupply {

        @Override
        default String supply(String tableName, MybatisTable mybatisTable, MybatisSqlScript sqlScript, SqlBuilder keySqlBuilder, SqlBuilder valueSqlBuilder, Object... parameters) throws RestException {
            return supply(tableName, mybatisTable, sqlScript, this, keySqlBuilder, valueSqlBuilder, parameters);
        }

        /**
         * <code>supply</code>
         * <p>The supply method.</p>
         * @param tableName       {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
         * @param mybatisTable    {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The mybatis table parameter is <code>MybatisTable</code> type.</p>
         * @param sqlScript       {@link io.github.nichetoolkit.mybatis.MybatisSqlScript} <p>The sql script parameter is <code>MybatisSqlScript</code> type.</p>
         * @param sqlSupply       {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply} <p>The sql supply parameter is <code>MybatisSqlSupply</code> type.</p>
         * @param keySqlBuilder   {@link io.github.nichetoolkit.mybatis.builder.SqlBuilder} <p>The key sql builder parameter is <code>SqlBuilder</code> type.</p>
         * @param valueSqlBuilder {@link io.github.nichetoolkit.mybatis.builder.SqlBuilder} <p>The value sql builder parameter is <code>SqlBuilder</code> type.</p>
         * @param parameters      {@link java.lang.Object} <p>The parameters parameter is <code>Object</code> type.</p>
         * @return {@link java.lang.String} <p>The supply return object is <code>String</code> type.</p>
         * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
         * @see java.lang.String
         * @see org.jspecify.annotations.Nullable
         * @see io.github.nichetoolkit.mybatis.MybatisTable
         * @see io.github.nichetoolkit.mybatis.MybatisSqlScript
         * @see io.github.nichetoolkit.mybatis.builder.SqlBuilder
         * @see java.lang.Object
         * @see io.github.nichetoolkit.rest.RestException
         */
        String supply(@Nullable String tableName, MybatisTable mybatisTable, MybatisSqlScript sqlScript, MybatisSqlSupply sqlSupply, SqlBuilder keySqlBuilder, SqlBuilder valueSqlBuilder, Object... parameters) throws RestException;
    }

    /**
     * <code>ParameterSqlSupply</code>
     * <p>The parameter sql supply interface.</p>
     * @author Cyan (snow22314@outlook.com)
     * @since Jdk17
     */
    interface ParameterSqlSupply extends MybatisSqlSupply {

        @Override
        default String supply(String tableName, MybatisTable mybatisTable, MybatisSqlScript sqlScript, SqlBuilder keySqlBuilder, SqlBuilder valueSqlBuilder, Object... parameters) throws RestException {
            return supply(tableName, mybatisTable, keySqlBuilder, valueSqlBuilder, parameters);
        }

        /**
         * <code>supply</code>
         * <p>The supply method.</p>
         * @param tableName       {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
         * @param mybatisTable    {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The mybatis table parameter is <code>MybatisTable</code> type.</p>
         * @param keySqlBuilder   {@link io.github.nichetoolkit.mybatis.builder.SqlBuilder} <p>The key sql builder parameter is <code>SqlBuilder</code> type.</p>
         * @param valueSqlBuilder {@link io.github.nichetoolkit.mybatis.builder.SqlBuilder} <p>The value sql builder parameter is <code>SqlBuilder</code> type.</p>
         * @param parameters      {@link java.lang.Object} <p>The parameters parameter is <code>Object</code> type.</p>
         * @return {@link java.lang.String} <p>The supply return object is <code>String</code> type.</p>
         * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
         * @see java.lang.String
         * @see org.jspecify.annotations.Nullable
         * @see io.github.nichetoolkit.mybatis.MybatisTable
         * @see io.github.nichetoolkit.mybatis.builder.SqlBuilder
         * @see java.lang.Object
         * @see io.github.nichetoolkit.rest.RestException
         */
        String supply(@Nullable String tableName, MybatisTable mybatisTable, SqlBuilder keySqlBuilder, SqlBuilder valueSqlBuilder, Object... parameters) throws RestException;
    }

    /**
     * <code>ValueSqlSupply</code>
     * <p>The value sql supply interface.</p>
     * @author Cyan (snow22314@outlook.com)
     * @see io.github.nichetoolkit.mybatis.MybatisSqlSupply.ParameterSqlSupply
     * @since Jdk17
     */
    interface ValueSqlSupply extends ParameterSqlSupply {

        @Override
        default String supply(String tableName, MybatisTable mybatisTable, SqlBuilder keySqlBuilder, SqlBuilder valueSqlBuilder, Object... parameters) throws RestException {
            return supply(tableName, mybatisTable, valueSqlBuilder, parameters);
        }

        /**
         * <code>supply</code>
         * <p>The supply method.</p>
         * @param tableName       {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
         * @param mybatisTable    {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The mybatis table parameter is <code>MybatisTable</code> type.</p>
         * @param sqlBuilder      {@link io.github.nichetoolkit.mybatis.builder.SqlBuilder} <p>The sql builder parameter is <code>SqlBuilder</code> type.</p>
         * @param statusParameter {@link java.lang.Object} <p>The status parameter parameter is <code>Object</code> type.</p>
         * @return {@link java.lang.String} <p>The supply return object is <code>String</code> type.</p>
         * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
         * @see java.lang.String
         * @see org.jspecify.annotations.Nullable
         * @see io.github.nichetoolkit.mybatis.MybatisTable
         * @see io.github.nichetoolkit.mybatis.builder.SqlBuilder
         * @see java.lang.Object
         * @see io.github.nichetoolkit.rest.RestException
         */
        String supply(@Nullable String tableName, MybatisTable mybatisTable, SqlBuilder sqlBuilder, Object... statusParameter) throws RestException;
    }

    /**
     * <code>KeySqlSupply</code>
     * <p>The key sql supply interface.</p>
     * @author Cyan (snow22314@outlook.com)
     * @see io.github.nichetoolkit.mybatis.MybatisSqlSupply.ParameterSqlSupply
     * @since Jdk17
     */
    interface KeySqlSupply extends ParameterSqlSupply {

        @Override
        default String supply(String tableName, MybatisTable mybatisTable, SqlBuilder keySqlBuilder, SqlBuilder valueSqlBuilder, Object... parameters) throws RestException {
            return supply(tableName, mybatisTable, keySqlBuilder, parameters);
        }

        /**
         * <code>supply</code>
         * <p>The supply method.</p>
         * @param tableName       {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
         * @param mybatisTable    {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The mybatis table parameter is <code>MybatisTable</code> type.</p>
         * @param sqlBuilder      {@link io.github.nichetoolkit.mybatis.builder.SqlBuilder} <p>The sql builder parameter is <code>SqlBuilder</code> type.</p>
         * @param statusParameter {@link java.lang.Object} <p>The status parameter parameter is <code>Object</code> type.</p>
         * @return {@link java.lang.String} <p>The supply return object is <code>String</code> type.</p>
         * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
         * @see java.lang.String
         * @see org.jspecify.annotations.Nullable
         * @see io.github.nichetoolkit.mybatis.MybatisTable
         * @see io.github.nichetoolkit.mybatis.builder.SqlBuilder
         * @see java.lang.Object
         * @see io.github.nichetoolkit.rest.RestException
         */
        String supply(@Nullable String tableName, MybatisTable mybatisTable, SqlBuilder sqlBuilder, Object... statusParameter) throws RestException;
    }

    /**
     * <code>AlertSqlSupply</code>
     * <p>The alert sql supply interface.</p>
     * @author Cyan (snow22314@outlook.com)
     * @see io.github.nichetoolkit.mybatis.MybatisSqlSupply.ValueSqlSupply
     * @since Jdk17
     */
    interface AlertSqlSupply extends ValueSqlSupply {

        @Override
        default String supply(String tableName, MybatisTable mybatisTable, SqlBuilder sqlBuilder, Object... parameters) throws RestException {
            return supply(tableName, mybatisTable, sqlBuilder, parameters[0]);
        }

        /**
         * <code>supply</code>
         * <p>The supply method.</p>
         * @param tableName       {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
         * @param mybatisTable    {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The mybatis table parameter is <code>MybatisTable</code> type.</p>
         * @param sqlBuilder      {@link io.github.nichetoolkit.mybatis.builder.SqlBuilder} <p>The sql builder parameter is <code>SqlBuilder</code> type.</p>
         * @param statusParameter {@link java.lang.Object} <p>The status parameter parameter is <code>Object</code> type.</p>
         * @return {@link java.lang.String} <p>The supply return object is <code>String</code> type.</p>
         * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
         * @see java.lang.String
         * @see org.jspecify.annotations.Nullable
         * @see io.github.nichetoolkit.mybatis.MybatisTable
         * @see io.github.nichetoolkit.mybatis.builder.SqlBuilder
         * @see java.lang.Object
         * @see io.github.nichetoolkit.rest.RestException
         */
        String supply(@Nullable String tableName, MybatisTable mybatisTable, SqlBuilder sqlBuilder, Object statusParameter) throws RestException;
    }

    /**
     * <code>SimpleSqlSupply</code>
     * <p>The simple sql supply interface.</p>
     * @author Cyan (snow22314@outlook.com)
     * @see io.github.nichetoolkit.mybatis.MybatisSqlSupply.ValueSqlSupply
     * @since Jdk17
     */
    interface SimpleSqlSupply extends ValueSqlSupply {

        @Override
        default String supply(String tableName, MybatisTable mybatisTable, SqlBuilder valueSqlBuilder, Object... parameters) throws RestException {
            return supply(tableName, mybatisTable, valueSqlBuilder);
        }

        /**
         * <code>supply</code>
         * <p>The supply method.</p>
         * @param tableName    {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
         * @param mybatisTable {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The mybatis table parameter is <code>MybatisTable</code> type.</p>
         * @param sqlBuilder   {@link io.github.nichetoolkit.mybatis.builder.SqlBuilder} <p>The sql builder parameter is <code>SqlBuilder</code> type.</p>
         * @return {@link java.lang.String} <p>The supply return object is <code>String</code> type.</p>
         * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
         * @see java.lang.String
         * @see org.jspecify.annotations.Nullable
         * @see io.github.nichetoolkit.mybatis.MybatisTable
         * @see io.github.nichetoolkit.mybatis.builder.SqlBuilder
         * @see io.github.nichetoolkit.rest.RestException
         */
        String supply(@Nullable String tableName, MybatisTable mybatisTable, SqlBuilder sqlBuilder) throws RestException;
    }

    /**
     * <code>EntrySqlSupply</code>
     * <p>The entry sql supply interface.</p>
     * @author Cyan (snow22314@outlook.com)
     * @see io.github.nichetoolkit.mybatis.MybatisSqlSupply.ParameterSqlSupply
     * @since Jdk17
     */
    interface EntrySqlSupply extends ParameterSqlSupply {

        @Override
        default String supply(String tableName, MybatisTable mybatisTable, SqlBuilder keySqlBuilder, SqlBuilder valueSqlBuilder, Object... parameters) throws RestException {
            return supply(tableName, mybatisTable, keySqlBuilder, valueSqlBuilder);
        }

        /**
         * <code>supply</code>
         * <p>The supply method.</p>
         * @param tableName       {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
         * @param mybatisTable    {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The mybatis table parameter is <code>MybatisTable</code> type.</p>
         * @param keySqlBuilder   {@link io.github.nichetoolkit.mybatis.builder.SqlBuilder} <p>The key sql builder parameter is <code>SqlBuilder</code> type.</p>
         * @param valueSqlBuilder {@link io.github.nichetoolkit.mybatis.builder.SqlBuilder} <p>The value sql builder parameter is <code>SqlBuilder</code> type.</p>
         * @return {@link java.lang.String} <p>The supply return object is <code>String</code> type.</p>
         * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
         * @see java.lang.String
         * @see org.jspecify.annotations.Nullable
         * @see io.github.nichetoolkit.mybatis.MybatisTable
         * @see io.github.nichetoolkit.mybatis.builder.SqlBuilder
         * @see io.github.nichetoolkit.rest.RestException
         */
        String supply(@Nullable String tableName, MybatisTable mybatisTable, SqlBuilder keySqlBuilder, SqlBuilder valueSqlBuilder) throws RestException;
    }

}
