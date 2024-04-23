package io.github.nichetoolkit.mybatis.driver;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.nichetoolkit.rest.RestValue;

import java.util.Optional;

/**
 * <p>DatasourceDriver</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public enum DatabaseType implements RestValue<String, String> {
    /** PostgreSQL OpenGuss GaussDB */
    POSTGRESQL("postgresql", DriverConstants.POSTGRESQL),
    /** PostgreSQL OpenGuss GaussDB */
    GAUSSDB("gaussdb", DriverConstants.POSTGRESQL),
    /** MySQL */
    MYSQL("mysql", DriverConstants.MYSQL),
    /** MariaDB */
    MARIADB("mariadb", DriverConstants.MARIADB),
    /** Microsoft SQL Server */
    SQLSEVER("sqlsever", DriverConstants.SQLSEVER),
    /** MariaDB */
    ORACLE("oracle", DriverConstants.ORACLE),
    /** SQLite */
    SQLITE("sqlite", DriverConstants.SQLITE),
    /** H2 */
    H2("h2", DriverConstants.H2),
    /** HSQLDB */
    HSQLDB("hsqldb", DriverConstants.HSQLDB),
    /** Amazon Redshift */
    REDSHIFT("redshift", DriverConstants.REDSHIFT),
    /** Apache Cassandra */
    CASSANDRA("cassandra", DriverConstants.CASSANDRA),
    ;
    private final String key;
    private final String value;

    DatabaseType(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @JsonValue
    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @JsonCreator
    public static DatabaseType parseKey(String key) {
        DatabaseType datasourceDriver = RestValue.parseKey(DatabaseType.class, key);
        return Optional.ofNullable(datasourceDriver).orElse(DatabaseType.POSTGRESQL);
    }

    public static DatabaseType parseValue(String value) {
        DatabaseType datasourceDriver = RestValue.parseValue(DatabaseType.class, value);
        return Optional.ofNullable(datasourceDriver).orElse(DatabaseType.POSTGRESQL);
    }

}
