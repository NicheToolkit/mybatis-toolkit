package io.github.nichetoolkit.mybatis.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

/**
 * <p>DruidDatasource</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public class DruidDatasource extends AbstractRoutingDataSource {

    public DruidDatasource(DataSource defaultDatasource, Map<Object, Object> datasourceMap) {
        super.setDefaultTargetDataSource(defaultDatasource);
        super.setTargetDataSources(datasourceMap);
        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return DatasourceContextHolder.getDatasourceType();
    }
}
