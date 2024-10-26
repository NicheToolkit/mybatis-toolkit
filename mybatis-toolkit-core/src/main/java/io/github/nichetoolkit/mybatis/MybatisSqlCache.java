package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.actuator.SupplierActuator;
import org.apache.ibatis.builder.annotation.ProviderContext;

/**
 * <code>MybatisSqlCache</code>
 * <p>The mybatis sql cache class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
public class MybatisSqlCache {
    /**
     * <code>NULL_SQL_CACHE</code>
     * {@link io.github.nichetoolkit.mybatis.MybatisSqlCache} <p>The constant <code>NULL_SQL_CACHE</code> field.</p>
     */
    public static final MybatisSqlCache NULL_SQL_CACHE = new MybatisSqlCache(null, null, null);
    /**
     * <code>context</code>
     * {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The <code>context</code> field.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     */
    private final ProviderContext context;
    /**
     * <code>table</code>
     * {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The <code>table</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     */
    private final MybatisTable table;
    /**
     * <code>sqlScript</code>
     * {@link io.github.nichetoolkit.rest.actuator.SupplierActuator} <p>The <code>sqlScript</code> field.</p>
     * @see io.github.nichetoolkit.rest.actuator.SupplierActuator
     */
    private final SupplierActuator<String> sqlScript;

    /**
     * <code>MybatisSqlCache</code>
     * <p>Instantiates a new mybatis sql cache.</p>
     * @param context   {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The context parameter is <code>ProviderContext</code> type.</p>
     * @param table     {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The table parameter is <code>MybatisTable</code> type.</p>
     * @param sqlScript {@link io.github.nichetoolkit.rest.actuator.SupplierActuator} <p>The sql script parameter is <code>SupplierActuator</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     * @see io.github.nichetoolkit.rest.actuator.SupplierActuator
     */
    public MybatisSqlCache(ProviderContext context, MybatisTable table, SupplierActuator<String> sqlScript) {
        this.context = context;
        this.table = table;
        this.sqlScript = sqlScript;
    }

    /**
     * <code>sqlScript</code>
     * <p>The sql script method.</p>
     * @return {@link java.lang.String} <p>The sql script return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public String sqlScript() throws RestException {
        return sqlScript.get();
    }

    /**
     * <code>context</code>
     * <p>The context method.</p>
     * @return {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The context return object is <code>ProviderContext</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     */
    public ProviderContext context() {
        return context;
    }

    /**
     * <code>table</code>
     * <p>The table method.</p>
     * @return {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The table return object is <code>MybatisTable</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     */
    public MybatisTable table() {
        return table;
    }
}
