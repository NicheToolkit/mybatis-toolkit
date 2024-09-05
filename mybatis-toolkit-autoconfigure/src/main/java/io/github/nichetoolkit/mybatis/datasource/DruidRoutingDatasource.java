package io.github.nichetoolkit.mybatis.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

/**
 * <code>DruidRoutingDatasource</code>
 * <p>The type druid routing datasource class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
 * @since Jdk1.8
 */
public class DruidRoutingDatasource extends AbstractRoutingDataSource {

    /**
     * <code>DruidRoutingDatasource</code>
     * Instantiates a new druid routing datasource.
     * @param defaultDatasource {@link javax.sql.DataSource} <p>the default datasource parameter is <code>DataSource</code> type.</p>
     * @param datasourceMap     {@link java.util.Map} <p>the datasource map parameter is <code>Map</code> type.</p>
     * @see javax.sql.DataSource
     * @see java.util.Map
     */
    public DruidRoutingDatasource(DataSource defaultDatasource, Map<Object, Object> datasourceMap) {
        super.setDefaultTargetDataSource(defaultDatasource);
        super.setTargetDataSources(datasourceMap);
        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return DatasourceContextHolder.getDatasourceType();
    }
}
