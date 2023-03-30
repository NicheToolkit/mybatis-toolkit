package io.github.nichetoolkit.mybatis;

import org.apache.ibatis.builder.annotation.ProviderContext;

import java.util.function.Supplier;

/**
 * <p>MybatisSqlCache</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public class MybatisSqlCache {
    /** 空对象 */
    public static final MybatisSqlCache NULL_CACHE = new MybatisSqlCache(null, null, null);
    /** 执行方法上下文 */
    private final ProviderContext context;
    /** 实体类信息 */
    private final MybatisTable table;
    /** sql 提供者 */
    private final Supplier<String> supplier;

    MybatisSqlCache(ProviderContext context, MybatisTable table, Supplier<String> supplier) {
        this.context = context;
        this.table = table;
        this.supplier = supplier;
    }

    /**
     * 该方法延迟到最终生成 SqlSource 时才执行
     */
    public String sqlScript() {
        return supplier.get();
    }

    /**
     * @return 执行方法上下文
     */
    public ProviderContext context() {
        return context;
    }

    /**
     * @return 实体类信息
     */
    public MybatisTable table() {
        return table;
    }
}
