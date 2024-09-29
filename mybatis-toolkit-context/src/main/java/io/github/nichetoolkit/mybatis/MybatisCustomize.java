package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.helper.ServiceLoaderHelper;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.mapping.MappedStatement;

import java.util.List;

public interface MybatisCustomize {

    MybatisCustomize DEFAULT_CUSTOMIZE = new MybatisCustomize() {
        private final List<MybatisCustomize> customizes = ServiceLoaderHelper.instances(MybatisCustomize.class);

        @Override
        public void customize(MybatisTable table, MappedStatement statement, ProviderContext context) {
            for (MybatisCustomize customize : customizes) {
                customize.customize(table, statement, context);
            }
        }
    };

    void customize(MybatisTable table, MappedStatement statement, ProviderContext context);
}
