package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.helper.ServiceLoaderHelper;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.mapping.MappedStatement;

import java.util.List;

/**
 * <code>MybatisCustomize</code>
 * <p>The type mybatis customize interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
public interface MybatisCustomize {

    /**
     * <code>DEFAULT_CUSTOMIZE</code>
     * {@link io.github.nichetoolkit.mybatis.MybatisCustomize} <p>the constant <code>DEFAULT_CUSTOMIZE</code> field.</p>
     */
    MybatisCustomize DEFAULT_CUSTOMIZE = new MybatisCustomize() {
        private final List<MybatisCustomize> customizes = ServiceLoaderHelper.instances(MybatisCustomize.class);

        @Override
        public void customize(MybatisTable table, MappedStatement statement, ProviderContext context) {
            for (MybatisCustomize customize : customizes) {
                customize.customize(table, statement, context);
            }
        }
    };

    /**
     * <code>customize</code>
     * <p>the method.</p>
     * @param table     {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>the table parameter is <code>MybatisTable</code> type.</p>
     * @param statement {@link org.apache.ibatis.mapping.MappedStatement} <p>the statement parameter is <code>MappedStatement</code> type.</p>
     * @param context   {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the context parameter is <code>ProviderContext</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     * @see org.apache.ibatis.mapping.MappedStatement
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     */
    void customize(MybatisTable table, MappedStatement statement, ProviderContext context);
}
