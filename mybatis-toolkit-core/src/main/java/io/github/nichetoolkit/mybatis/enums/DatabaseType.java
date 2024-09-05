package io.github.nichetoolkit.mybatis.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.nichetoolkit.mybatis.consts.DriverConstants;
import io.github.nichetoolkit.rest.RestValue;

import java.util.Optional;

/**
 * <code>DatabaseType</code>
 * <p>The type database type enumeration.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rest.RestValue
 * @since Jdk1.8
 */
public enum DatabaseType implements RestValue<String, String> {
    /**
     * <code>POSTGRESQL</code>
     * <p>the Postgresql database type field.</p>
     */
    POSTGRESQL("postgresql", DriverConstants.POSTGRESQL),
    /**
     * <code>GAUSSDB</code>
     * <p>the Gaussdb database type field.</p>
     */
    GAUSSDB("gaussdb", DriverConstants.POSTGRESQL),
    /**
     * <code>MYSQL</code>
     * <p>the Mysql database type field.</p>
     */
    MYSQL("mysql", DriverConstants.MYSQL),
    /**
     * <code>MARIADB</code>
     * <p>the Mariadb database type field.</p>
     */
    MARIADB("mariadb", DriverConstants.MARIADB),
    /**
     * <code>SQLSEVER</code>
     * <p>the Sqlsever database type field.</p>
     */
    SQLSEVER("sqlsever", DriverConstants.SQLSEVER),
    /**
     * <code>ORACLE</code>
     * <p>the Oracle database type field.</p>
     */
    ORACLE("oracle", DriverConstants.ORACLE),
    /**
     * <code>SQLITE</code>
     * <p>the Sqlite database type field.</p>
     */
    SQLITE("sqlite", DriverConstants.SQLITE),
    /**
     * <code>H2</code>
     * <p>the H 2 database type field.</p>
     */
    H2("h2", DriverConstants.H2),
    /**
     * <code>HSQLDB</code>
     * <p>the Hsqldb database type field.</p>
     */
    HSQLDB("hsqldb", DriverConstants.HSQLDB),
    /**
     * <code>REDSHIFT</code>
     * <p>the Redshift database type field.</p>
     */
    REDSHIFT("redshift", DriverConstants.REDSHIFT),
    /**
     * <code>CASSANDRA</code>
     * <p>the Cassandra database type field.</p>
     */
    CASSANDRA("cassandra", DriverConstants.CASSANDRA),
    ;
    /**
     * <code>key</code>
     * {@link java.lang.String} <p>the <code>key</code> field.</p>
     * @see java.lang.String
     */
    private final String key;
    /**
     * <code>value</code>
     * {@link java.lang.String} <p>the <code>value</code> field.</p>
     * @see java.lang.String
     */
    private final String value;

    /**
     * <code>DatabaseType</code>
     * Instantiates a new database type.
     * @param key   {@link java.lang.String} <p>the key parameter is <code>String</code> type.</p>
     * @param value {@link java.lang.String} <p>the value parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
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

    /**
     * <code>parseKey</code>
     * <p>the key method.</p>
     * @param key {@link java.lang.String} <p>the key parameter is <code>String</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.enums.DatabaseType} <p>the key return object is <code>DatabaseType</code> type.</p>
     * @see java.lang.String
     * @see com.fasterxml.jackson.annotation.JsonCreator
     */
    @JsonCreator
    public static DatabaseType parseKey(String key) {
        DatabaseType datasourceDriver = RestValue.parseKey(DatabaseType.class, key);
        return Optional.ofNullable(datasourceDriver).orElse(DatabaseType.POSTGRESQL);
    }

    /**
     * <code>parseValue</code>
     * <p>the value method.</p>
     * @param value {@link java.lang.String} <p>the value parameter is <code>String</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.enums.DatabaseType} <p>the value return object is <code>DatabaseType</code> type.</p>
     * @see java.lang.String
     */
    public static DatabaseType parseValue(String value) {
        DatabaseType datasourceDriver = RestValue.parseValue(DatabaseType.class, value);
        return Optional.ofNullable(datasourceDriver).orElse(DatabaseType.POSTGRESQL);
    }

}
