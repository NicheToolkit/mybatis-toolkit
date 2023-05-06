package io.github.nichetoolkit.mybatis.configure;

import io.github.nichetoolkit.rice.enums.DatabaseType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>MybatisMapperProperties</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
@Component
@ConfigurationProperties(prefix = "nichetoolkit.mybatis.mapper")
public class MybatisMapperProperties {
    private DatabaseType databaseType = DatabaseType.POSTGRESQL;

    public MybatisMapperProperties() {
    }

    public DatabaseType getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(DatabaseType databaseType) {
        this.databaseType = databaseType;
    }
}
