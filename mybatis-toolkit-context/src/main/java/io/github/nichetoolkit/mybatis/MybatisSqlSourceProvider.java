package io.github.nichetoolkit.mybatis;

import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.util.List;

public interface MybatisSqlSourceProvider extends MybatisOrder {

    static SqlSource ofProvide(SqlSource sqlSource, MybatisTable table, MappedStatement statement, ProviderContext context) {
        for (MybatisSqlSourceProvider provider : Instance.sqlSourceProviders()) {
            sqlSource = provider.provide(sqlSource, table, statement, context);
        }
        return sqlSource;
    }

    SqlSource provide(SqlSource sqlSource, MybatisTable table, MappedStatement statement, ProviderContext context);

    class Instance {

        private static List<MybatisSqlSourceProvider> SQL_SOURCE_PROVIDERS;

        public static List<MybatisSqlSourceProvider> sqlSourceProviders() {
            if (SQL_SOURCE_PROVIDERS == null) {
                SQL_SOURCE_PROVIDERS = SpringFactoriesLoader.loadFactories(MybatisSqlSourceProvider.class, null);
            }
            return SQL_SOURCE_PROVIDERS;
        }
    }
}
