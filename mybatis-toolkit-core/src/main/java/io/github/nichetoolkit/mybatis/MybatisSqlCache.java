package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.actuator.SupplierActuator;
import org.apache.ibatis.builder.annotation.ProviderContext;

/**
 * <code>MybatisSqlCache</code>
 * <p>The type mybatis sql cache class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
public class MybatisSqlCache {
    /**
     * <code>NULL_SQL_CACHE</code>
     * {@link io.github.nichetoolkit.mybatis.MybatisSqlCache} <p>the constant <code>NULL_SQL_CACHE</code> field.</p>
     */
    public static final MybatisSqlCache NULL_SQL_CACHE = new MybatisSqlCache(null, null, null);
    /**
     * <code>context</code>
     * {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the <code>context</code> field.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     */
    private final ProviderContext context;
    /**
     * <code>table</code>
     * {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>the <code>table</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     */
    private final MybatisTable table;
    /**
     * <code>supplier</code>
     * {@link io.github.nichetoolkit.rest.actuator.SupplierActuator} <p>the <code>supplier</code> field.</p>
     * @see io.github.nichetoolkit.rest.actuator.SupplierActuator
     */
    private final SupplierActuator<String> supplier;

    /**
     * <code>MybatisSqlCache</code>
     * Instantiates a new mybatis sql cache.
     * @param context  {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the context parameter is <code>ProviderContext</code> type.</p>
     * @param table    {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>the table parameter is <code>MybatisTable</code> type.</p>
     * @param supplier {@link io.github.nichetoolkit.rest.actuator.SupplierActuator} <p>the supplier parameter is <code>SupplierActuator</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     * @see io.github.nichetoolkit.rest.actuator.SupplierActuator
     */
    public MybatisSqlCache(ProviderContext context, MybatisTable table, SupplierActuator<String> supplier) {
        this.context = context;
        this.table = table;
        this.supplier = supplier;
    }

    /**
     * <code>sqlScript</code>
     * <p>the script method.</p>
     * @return {@link java.lang.String} <p>the script return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public String sqlScript() throws RestException {
        return supplier.get();
    }

    /**
     * <code>context</code>
     * <p>the method.</p>
     * @return {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the return object is <code>ProviderContext</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     */
    public ProviderContext context() {
        return context;
    }

    /**
     * <code>table</code>
     * <p>the method.</p>
     * @return {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>the return object is <code>MybatisTable</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     */
    public MybatisTable table() {
        return table;
    }
}
