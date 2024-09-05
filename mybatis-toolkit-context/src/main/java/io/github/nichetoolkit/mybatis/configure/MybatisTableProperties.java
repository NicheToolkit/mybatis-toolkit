package io.github.nichetoolkit.mybatis.configure;

import io.github.nichetoolkit.mybatis.enums.DatabaseType;
import io.github.nichetoolkit.mybatis.enums.StyleType;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * <code>MybatisTableProperties</code>
 * <p>The type mybatis table properties class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see org.springframework.stereotype.Component
 * @see org.springframework.boot.context.properties.ConfigurationProperties
 * @since Jdk1.8
 */
@Component
@ConfigurationProperties(prefix = "nichetoolkit.mybatis.table")
public class MybatisTableProperties {
    /**
     * <code>catalog</code>
     * {@link java.lang.String} <p>the <code>catalog</code> field.</p>
     * @see java.lang.String
     */
    private String catalog;
    /**
     * <code>schema</code>
     * {@link java.lang.String} <p>the <code>schema</code> field.</p>
     * @see java.lang.String
     */
    private String schema;
    /**
     * <code>databaseType</code>
     * {@link io.github.nichetoolkit.mybatis.enums.DatabaseType} <p>the <code>databaseType</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.enums.DatabaseType
     */
    private DatabaseType databaseType = DatabaseType.POSTGRESQL;
    /**
     * <code>styleType</code>
     * {@link io.github.nichetoolkit.mybatis.enums.StyleType} <p>the <code>styleType</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.enums.StyleType
     */
    private StyleType styleType = StyleType.LOWER_UNDERLINE;
    /**
     * <code>properties</code>
     * {@link java.util.Map} <p>the <code>properties</code> field.</p>
     * @see java.util.Map
     */
    private Map<String, String> properties = new HashMap<>();
    /**
     * <code>excludes</code>
     * {@link java.lang.String} <p>the <code>excludes</code> field.</p>
     * @see java.lang.String
     */
    private String[] excludes;

    /**
     * <code>MybatisTableProperties</code>
     * Instantiates a new mybatis table properties.
     */
    public MybatisTableProperties() {
    }

    /**
     * <code>getCatalog</code>
     * <p>the catalog getter method.</p>
     * @return {@link java.lang.String} <p>the catalog return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String getCatalog() {
        return catalog;
    }

    /**
     * <code>setCatalog</code>
     * <p>the catalog setter method.</p>
     * @param catalog {@link java.lang.String} <p>the catalog parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    /**
     * <code>getSchema</code>
     * <p>the schema getter method.</p>
     * @return {@link java.lang.String} <p>the schema return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String getSchema() {
        return schema;
    }

    /**
     * <code>setSchema</code>
     * <p>the schema setter method.</p>
     * @param schema {@link java.lang.String} <p>the schema parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public void setSchema(String schema) {
        this.schema = schema;
    }

    /**
     * <code>getDatabaseType</code>
     * <p>the database type getter method.</p>
     * @return {@link io.github.nichetoolkit.mybatis.enums.DatabaseType} <p>the database type return object is <code>DatabaseType</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.enums.DatabaseType
     */
    public DatabaseType getDatabaseType() {
        return databaseType;
    }

    /**
     * <code>setDatabaseType</code>
     * <p>the database type setter method.</p>
     * @param databaseType {@link io.github.nichetoolkit.mybatis.enums.DatabaseType} <p>the database type parameter is <code>DatabaseType</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.enums.DatabaseType
     */
    public void setDatabaseType(DatabaseType databaseType) {
        this.databaseType = databaseType;
    }

    /**
     * <code>getStyleType</code>
     * <p>the style type getter method.</p>
     * @return {@link io.github.nichetoolkit.mybatis.enums.StyleType} <p>the style type return object is <code>StyleType</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.enums.StyleType
     */
    public StyleType getStyleType() {
        return styleType;
    }

    /**
     * <code>setStyleType</code>
     * <p>the style type setter method.</p>
     * @param styleType {@link io.github.nichetoolkit.mybatis.enums.StyleType} <p>the style type parameter is <code>StyleType</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.enums.StyleType
     */
    public void setStyleType(StyleType styleType) {
        this.styleType = styleType;
    }

    /**
     * <code>getProperties</code>
     * <p>the properties getter method.</p>
     * @return {@link java.util.Map} <p>the properties return object is <code>Map</code> type.</p>
     * @see java.util.Map
     */
    public Map<String, String> getProperties() {
        return properties;
    }

    /**
     * <code>setProperties</code>
     * <p>the properties setter method.</p>
     * @param properties {@link java.util.Map} <p>the properties parameter is <code>Map</code> type.</p>
     * @see java.util.Map
     */
    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    /**
     * <code>getExcludes</code>
     * <p>the excludes getter method.</p>
     * @return {@link java.util.List} <p>the excludes return object is <code>List</code> type.</p>
     * @see java.util.List
     */
    public List<String> getExcludes() {
        if (GeneralUtils.isNotEmpty(this.excludes)) {
            return new ArrayList<>(Arrays.asList(this.excludes));
        }
        return null;
    }

    /**
     * <code>setExcludes</code>
     * <p>the excludes setter method.</p>
     * @param excludes {@link java.lang.String} <p>the excludes parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public void setExcludes(String... excludes) {
        this.excludes = excludes;
    }
}
