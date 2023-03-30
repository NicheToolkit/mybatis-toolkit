package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.helper.ServiceLoaderHelper;
import io.github.nichetoolkit.rest.RestException;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.mapping.MappedStatement;

import java.util.List;

/**
 * <p>MybatisCustomize</p>
 * 支持定制化处理 {@link MappedStatement}
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public interface MybatisCustomize {

    /**
     * 支持 SPI 扩展的默认实现
     */
    MybatisCustomize MYBATIS_CUSTOMIZE = new MybatisCustomize() {
        private final List<MybatisCustomize> customizes = ServiceLoaderHelper.instances(MybatisCustomize.class);

        @Override
        public void customize(MybatisTable mybatisTable, MappedStatement mappedStatement, ProviderContext context){
            for (MybatisCustomize customize : customizes) {
                customize.customize(mybatisTable, mappedStatement, context);
            }
        }
    };

    /**
     * 定制化
     * @param mybatisTable    MybatisTable
     * @param mappedStatement MappedStatement
     * @param context         ProviderContext
     */
    void customize(MybatisTable mybatisTable, MappedStatement mappedStatement, ProviderContext context);
}
