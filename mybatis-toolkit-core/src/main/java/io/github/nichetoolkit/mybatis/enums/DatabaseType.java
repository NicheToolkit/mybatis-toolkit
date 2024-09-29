package io.github.nichetoolkit.mybatis.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.nichetoolkit.mybatis.consts.DriverConstants;
import io.github.nichetoolkit.rest.RestValue;

import java.util.Optional;

public enum DatabaseType implements RestValue<String, String> {
    POSTGRESQL("postgresql", DriverConstants.POSTGRESQL),
    GAUSSDB("gaussdb", DriverConstants.POSTGRESQL),
    MYSQL("mysql", DriverConstants.MYSQL),
    MARIADB("mariadb", DriverConstants.MARIADB),
    SQLSEVER("sqlsever", DriverConstants.SQLSEVER),
    ORACLE("oracle", DriverConstants.ORACLE),
    SQLITE("sqlite", DriverConstants.SQLITE),
    H2("h2", DriverConstants.H2),
    HSQLDB("hsqldb", DriverConstants.HSQLDB),
    REDSHIFT("redshift", DriverConstants.REDSHIFT),
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
