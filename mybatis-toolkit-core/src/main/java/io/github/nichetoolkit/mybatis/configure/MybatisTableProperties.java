package io.github.nichetoolkit.mybatis.configure;

import io.github.nichetoolkit.mybatis.driver.DatabaseType;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rice.enums.StyleType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * <p>MybatisTableProperties</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
@Component
@ConfigurationProperties(prefix = "nichetoolkit.mybatis.table")
public class MybatisTableProperties {
    /** 数据库 catalog名 全局配置 */
    private String catalog;
    /** 数据库 schema名 全局配置 */
    private String schema;
    /** 默认数据源 */
    private DatabaseType databaseType = DatabaseType.POSTGRESQL;
    /** 默认样式 */
    private StyleType styleType = StyleType.LOWER_UNDERLINE;
    /** 默认全局属性 */
    private Map<String, String> properties = new HashMap<>();
    /** 默认全局排除属性 */
    private String[] excludes;

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
}
