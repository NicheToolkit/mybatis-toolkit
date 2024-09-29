package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.actuator.SupplierActuator;
import org.apache.ibatis.builder.annotation.ProviderContext;

public class MybatisSqlCache {
    public static final MybatisSqlCache NULL_SQL_CACHE = new MybatisSqlCache(null, null, null);
    private final ProviderContext context;
    private final MybatisTable table;
    private final SupplierActuator<String> supplier;

    public MybatisSqlCache(ProviderContext context, MybatisTable table, SupplierActuator<String> supplier) {
        this.context = context;
        this.table = table;
        this.supplier = supplier;
    }

    public String sqlScript() throws RestException {
        return supplier.get();
    }

    public ProviderContext context() {
        return context;
    }

    public MybatisTable table() {
        return table;
    }
}
