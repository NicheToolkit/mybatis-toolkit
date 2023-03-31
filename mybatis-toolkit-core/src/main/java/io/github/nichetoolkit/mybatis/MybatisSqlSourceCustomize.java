package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.helper.ServiceLoaderHelper;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import java.util.List;

/**
 * <p>MybatisSourceCustomize</p>
 * 定制化处理
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public interface MybatisSqlSourceCustomize {


    MybatisSqlSourceCustomize DEFAULT_SQL_SOURCE_CUSTOMIZE = new MybatisSqlSourceCustomize() {
        private final List<MybatisSqlSourceCustomize> sqlSourceCustomizes = ServiceLoaderHelper.instances(MybatisSqlSourceCustomize.class);

        @Override
        public SqlSource customize(SqlSource sqlSource, MybatisTable table, MappedStatement statement, ProviderContext context) {
            for (MybatisSqlSourceCustomize sqlSourceCustomize : sqlSourceCustomizes) {
                sqlSource = sqlSourceCustomize.customize(sqlSource, table, statement, context);
            }
            return sqlSource;
        }
    };

    /**
     * 定制化 sqlSource
     * @param sqlSource 原始 sqlSource
     * @param table     实体 table
     * @param statement MappedStatement
     * @param context   调用方法上下文
     */
    SqlSource customize(SqlSource sqlSource, MybatisTable table, MappedStatement statement, ProviderContext context);
}
