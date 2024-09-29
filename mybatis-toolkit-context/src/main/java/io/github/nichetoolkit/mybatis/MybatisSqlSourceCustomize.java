package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.helper.ServiceLoaderHelper;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import java.util.List;

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

    SqlSource customize(SqlSource sqlSource, MybatisTable table, MappedStatement statement, ProviderContext context);
}
