package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.helper.ServiceLoaderHelper;
import org.apache.ibatis.builder.annotation.ProviderContext;

import java.util.List;

/**
 * <p>MybatisSqlScriptWrapper</p>
 * 对最终的SQL进行处理
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public interface MybatisSqlScriptWrapper extends MybatisOrder {
    /**
     * 包装 SQL
     *
     * @param context   当前接口和方法信息
     * @param table    实体类
     * @param sqlScript sql脚本
     * @return
     */
    static MybatisSqlScript wrapSqlScript(ProviderContext context, MybatisTable table, MybatisSqlScript sqlScript) {
        for (MybatisSqlScriptWrapper wrapper : Instance.sqlScriptWrapperChain()) {
            sqlScript = wrapper.wrap(context, table, sqlScript);
        }
        return sqlScript;
    }

    /**
     * 对 script 包装中的 SQL 进行加工处理
     *
     * @param context   当前接口和方法信息
     * @param table    实体类
     * @param sqlScript sql脚本
     * @return MybatisSqlScript
     */
    MybatisSqlScript wrap(ProviderContext context, MybatisTable table, MybatisSqlScript sqlScript);

    /**
     * 实例
     */
    class Instance {
        private static volatile List<MybatisSqlScriptWrapper> SQL_SCRIPT_WRAPPERS;

        /**
         * 获取处理实体的工厂链
         *
         * @return 实例
         */
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
