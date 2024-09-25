package io.github.nichetoolkit.mybatis.defaults;

import io.github.nichetoolkit.mybatis.MybatisProviderSupport;
import io.github.nichetoolkit.mybatis.configure.MybatisTableProperties;

/**
 * <code>DefaultProviderManager</code>
 * <p>The type default provider manager class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisProviderSupport
 * @since Jdk1.8
 */
public class DefaultProviderManager extends MybatisProviderSupport {
    /**
     * <code>DefaultProviderManager</code>
     * Instantiates a new default provider manager.
     * @param tableProperties {@link io.github.nichetoolkit.mybatis.configure.MybatisTableProperties} <p>the table properties parameter is <code>MybatisTableProperties</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.configure.MybatisTableProperties
     */
    public DefaultProviderManager(MybatisTableProperties tableProperties) {
        super(tableProperties);
    }
}
