package io.github.nichetoolkit.mybatis.configure;

import io.github.nichetoolkit.mybatis.enums.DatabaseType;
import io.github.nichetoolkit.mybatis.enums.StyleType;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@ConfigurationProperties(prefix = "nichetoolkit.mybatis.table")
public class MybatisTableProperties {
    private String catalog;
    private String schema;
    private DatabaseType databaseType = DatabaseType.POSTGRESQL;
    private StyleType styleType = StyleType.LOWER_UNDERLINE;
    private Map<String, String> properties = new HashMap<>();
    private String[] excludes;
    private String[] ignores;

    public MybatisTableProperties() {
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public DatabaseType getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(DatabaseType databaseType) {
        this.databaseType = databaseType;
    }

    public StyleType getStyleType() {
        return styleType;
    }

    public void setStyleType(StyleType styleType) {
        this.styleType = styleType;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public List<String> getExcludes() {
        if (GeneralUtils.isNotEmpty(this.excludes)) {
            return new ArrayList<>(Arrays.asList(this.excludes));
        }
        return null;
    }

    public void setExcludes(String... excludes) {
        this.excludes = excludes;
    }

    public List<String> getIgnores() {
        if (GeneralUtils.isNotEmpty(this.ignores)) {
            return new ArrayList<>(Arrays.asList(this.ignores));
        }
        return null;
    }

    public void setIgnores(String... ignores) {
        this.ignores = ignores;
    }
}
