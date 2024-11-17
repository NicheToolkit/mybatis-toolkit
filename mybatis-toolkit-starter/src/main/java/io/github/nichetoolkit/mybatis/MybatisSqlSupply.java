package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.rice.builder.SqlBuilder;
import io.github.nichetoolkit.rest.RestException;
import org.springframework.lang.Nullable;

/**
 * <code>MybatisSqlSupply</code>
 * <p>The mybatis sql supply interface.</p>
 * @see  io.github.nichetoolkit.mybatis.MybatisOrder
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
public interface MybatisSqlSupply extends MybatisOrder {

    /**
     * <code>supply</code>
     * <p>The supply method.</p>
     * @param tablename {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param table {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The table parameter is <code>MybatisTable</code> type.</p>
     * @param sqlScript {@link io.github.nichetoolkit.mybatis.MybatisSqlScript} <p>The sql script parameter is <code>MybatisSqlScript</code> type.</p>
     * @param sqlBuilder {@link io.github.nichetoolkit.rice.builder.SqlBuilder} <p>The sql builder parameter is <code>SqlBuilder</code> type.</p>
     * @param parameters {@link java.lang.Object} <p>The parameters parameter is <code>Object</code> type.</p>
     * @see  java.lang.String
     * @see  org.springframework.lang.Nullable
     * @see  io.github.nichetoolkit.mybatis.MybatisTable
     * @see  io.github.nichetoolkit.mybatis.MybatisSqlScript
     * @see  io.github.nichetoolkit.rice.builder.SqlBuilder
     * @see  java.lang.Object
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link java.lang.String} <p>The supply return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    String supply(@Nullable String tablename, MybatisTable table, MybatisSqlScript sqlScript, SqlBuilder sqlBuilder, Object... parameters) throws RestException;

    /**
     * <code>AlertSqlSupply</code>
     * <p>The alert sql supply interface.</p>
     * @see  io.github.nichetoolkit.mybatis.MybatisSqlSupply.ParameterSqlSupply
     * @author Cyan (snow22314@outlook.com)
     * @since Jdk1.8
     */
    interface AlertSqlSupply extends ParameterSqlSupply {

        @Override
        default String supply(String tablename, MybatisTable mybatisTable, SqlBuilder sqlBuilder, Object... parameters) throws RestException {
            return supply(tablename, mybatisTable, sqlBuilder, parameters[0]);
        }

        /**
         * <code>supply</code>
         * <p>The supply method.</p>
         * @param tablename {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
         * @param mybatisTable {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The mybatis table parameter is <code>MybatisTable</code> type.</p>
         * @param sqlBuilder {@link io.github.nichetoolkit.rice.builder.SqlBuilder} <p>The sql builder parameter is <code>SqlBuilder</code> type.</p>
         * @param statusParameter {@link java.lang.Object} <p>The status parameter parameter is <code>Object</code> type.</p>
         * @see  java.lang.String
         * @see  org.springframework.lang.Nullable
         * @see  io.github.nichetoolkit.mybatis.MybatisTable
         * @see  io.github.nichetoolkit.rice.builder.SqlBuilder
         * @see  java.lang.Object
         * @see  io.github.nichetoolkit.rest.RestException
         * @return  {@link java.lang.String} <p>The supply return object is <code>String</code> type.</p>
         * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
         */
        String supply(@Nullable String tablename, MybatisTable mybatisTable, SqlBuilder sqlBuilder, Object statusParameter) throws RestException;
    }

    /**
     * <code>ParameterSqlSupply</code>
     * <p>The parameter sql supply interface.</p>
     * @author Cyan (snow22314@outlook.com)
     * @since Jdk1.8
     */
    interface ParameterSqlSupply extends MybatisSqlSupply {

        @Override
        default String supply(String tablename, MybatisTable mybatisTable, MybatisSqlScript sqlScript, SqlBuilder sqlBuilder, Object... parameters) throws RestException {
            return supply(tablename, mybatisTable, sqlBuilder, parameters);
        }

        /**
         * <code>supply</code>
         * <p>The supply method.</p>
         * @param tablename {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
         * @param mybatisTable {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The mybatis table parameter is <code>MybatisTable</code> type.</p>
         * @param sqlBuilder {@link io.github.nichetoolkit.rice.builder.SqlBuilder} <p>The sql builder parameter is <code>SqlBuilder</code> type.</p>
         * @param parameters {@link java.lang.Object} <p>The parameters parameter is <code>Object</code> type.</p>
         * @see  java.lang.String
         * @see  org.springframework.lang.Nullable
         * @see  io.github.nichetoolkit.mybatis.MybatisTable
         * @see  io.github.nichetoolkit.rice.builder.SqlBuilder
         * @see  java.lang.Object
         * @see  io.github.nichetoolkit.rest.RestException
         * @return  {@link java.lang.String} <p>The supply return object is <code>String</code> type.</p>
         * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
         */
        String supply(@Nullable String tablename, MybatisTable mybatisTable, SqlBuilder sqlBuilder, Object... parameters) throws RestException;
    }

    /**
     * <code>SupplySqlSupply</code>
     * <p>The supply sql supply interface.</p>
     * @author Cyan (snow22314@outlook.com)
     * @since Jdk1.8
     */
    interface SupplySqlSupply extends MybatisSqlSupply {

        @Override
        default String supply(String tablename, MybatisTable mybatisTable, MybatisSqlScript sqlScript, SqlBuilder sqlBuilder, Object... parameters) throws RestException {
            return supply(tablename, mybatisTable, sqlScript, this, sqlBuilder);
        }

        /**
         * <code>supply</code>
         * <p>The supply method.</p>
         * @param tablename {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
         * @param mybatisTable {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The mybatis table parameter is <code>MybatisTable</code> type.</p>
         * @param sqlScript {@link io.github.nichetoolkit.mybatis.MybatisSqlScript} <p>The sql script parameter is <code>MybatisSqlScript</code> type.</p>
         * @param sqlSupply {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply} <p>The sql supply parameter is <code>MybatisSqlSupply</code> type.</p>
         * @param sqlBuilder {@link io.github.nichetoolkit.rice.builder.SqlBuilder} <p>The sql builder parameter is <code>SqlBuilder</code> type.</p>
         * @see  java.lang.String
         * @see  org.springframework.lang.Nullable
         * @see  io.github.nichetoolkit.mybatis.MybatisTable
         * @see  io.github.nichetoolkit.mybatis.MybatisSqlScript
         * @see  io.github.nichetoolkit.rice.builder.SqlBuilder
         * @see  io.github.nichetoolkit.rest.RestException
         * @return  {@link java.lang.String} <p>The supply return object is <code>String</code> type.</p>
         * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
         */
        String supply(@Nullable String tablename, MybatisTable mybatisTable, MybatisSqlScript sqlScript, MybatisSqlSupply sqlSupply, SqlBuilder sqlBuilder) throws RestException;
    }

    /**
     * <code>SimpleSqlSupply</code>
     * <p>The simple sql supply interface.</p>
     * @author Cyan (snow22314@outlook.com)
     * @since Jdk1.8
     */
    interface SimpleSqlSupply extends MybatisSqlSupply {

        @Override
        default String supply(String tablename, MybatisTable mybatisTable, MybatisSqlScript sqlScript, SqlBuilder sqlBuilder, Object... parameters) throws RestException {
            return supply(tablename, mybatisTable, sqlBuilder);
        }

        /**
         * <code>supply</code>
         * <p>The supply method.</p>
         * @param tablename {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
         * @param mybatisTable {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The mybatis table parameter is <code>MybatisTable</code> type.</p>
         * @param sqlBuilder {@link io.github.nichetoolkit.rice.builder.SqlBuilder} <p>The sql builder parameter is <code>SqlBuilder</code> type.</p>
         * @see  java.lang.String
         * @see  org.springframework.lang.Nullable
         * @see  io.github.nichetoolkit.mybatis.MybatisTable
         * @see  io.github.nichetoolkit.rice.builder.SqlBuilder
         * @see  io.github.nichetoolkit.rest.RestException
         * @return  {@link java.lang.String} <p>The supply return object is <code>String</code> type.</p>
         * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
         */
        String supply(@Nullable String tablename, MybatisTable mybatisTable, SqlBuilder sqlBuilder) throws RestException;
    }

}
