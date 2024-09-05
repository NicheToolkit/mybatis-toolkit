package io.github.nichetoolkit.mybatis.datasource;

import lombok.extern.slf4j.Slf4j;

/**
 * <code>DatasourceContextHolder</code>
 * <p>The type datasource context holder class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see lombok.extern.slf4j.Slf4j
 * @since Jdk1.8
 */
@Slf4j
public class DatasourceContextHolder {

    /**
     * <code>CONTEXT_HOLDER</code>
     * {@link java.lang.ThreadLocal} <p>the constant <code>CONTEXT_HOLDER</code> field.</p>
     * @see java.lang.ThreadLocal
     */
    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    /**
     * <code>setDatasourceType</code>
     * <p>the datasource type setter method.</p>
     * @param datasourceType {@link java.lang.String} <p>the datasource type parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public static void setDatasourceType(String datasourceType) {
        log.debug("the datasource will be change with {} type.", datasourceType);
        CONTEXT_HOLDER.set(datasourceType);
    }

    /**
     * <code>getDatasourceType</code>
     * <p>the datasource type getter method.</p>
     * @return {@link java.lang.String} <p>the datasource type return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public static String getDatasourceType() {
        return CONTEXT_HOLDER.get();
    }

    /**
     * <code>clearDatasourceType</code>
     * <p>the datasource type method.</p>
     */
    public static void clearDatasourceType() {
        CONTEXT_HOLDER.remove();
    }
}
