package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.helper.ServiceLoaderHelper;
import org.apache.ibatis.builder.annotation.ProviderContext;

import java.util.List;

public interface MybatisSqlScriptWrapper extends MybatisOrder {
    static MybatisSqlScript wrapSqlScript(ProviderContext context, MybatisTable table, MybatisSqlScript sqlScript) {
        for (MybatisSqlScriptWrapper wrapper : Instance.sqlScriptWrapperChain()) {
            sqlScript = wrapper.wrap(context, table, sqlScript);
        }
        return sqlScript;
    }

    MybatisSqlScript wrap(ProviderContext context, MybatisTable table, MybatisSqlScript sqlScript);

    class Instance {
        private static volatile List<MybatisSqlScriptWrapper> SQL_SCRIPT_WRAPPERS;

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
