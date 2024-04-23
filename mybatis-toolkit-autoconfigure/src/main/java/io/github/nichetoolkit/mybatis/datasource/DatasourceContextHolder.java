package io.github.nichetoolkit.mybatis.datasource;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>DatasourceContextHolder</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
@Slf4j
public class DatasourceContextHolder {

    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    public static void setDatasourceType(String datasourceType) {
        log.debug("the datasource will be change with {} type.", datasourceType);
        CONTEXT_HOLDER.set(datasourceType);
    }

    public static String getDatasourceType() {
        return CONTEXT_HOLDER.get();
    }

    public static void clearDatasourceType() {
        CONTEXT_HOLDER.remove();
    }
}
