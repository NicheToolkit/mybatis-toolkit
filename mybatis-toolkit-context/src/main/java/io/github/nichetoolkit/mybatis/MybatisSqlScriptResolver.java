package io.github.nichetoolkit.mybatis;

import org.apache.ibatis.builder.annotation.ProviderContext;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.util.List;

public interface MybatisSqlScriptResolver extends MybatisOrder {

    static MybatisSqlScript ofResolve(ProviderContext context, MybatisTable table, MybatisSqlScript sqlScript) {
        for (MybatisSqlScriptResolver resolver : Instance.sqlScriptResolvers()) {
            sqlScript = resolver.resolve(context, table, sqlScript);
        }
        return sqlScript;
    }

    MybatisSqlScript resolve(ProviderContext context, MybatisTable table, MybatisSqlScript sqlScript);

    class Instance {
        private static volatile List<MybatisSqlScriptResolver> SQL_SCRIPT_RESOLVERS;

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
