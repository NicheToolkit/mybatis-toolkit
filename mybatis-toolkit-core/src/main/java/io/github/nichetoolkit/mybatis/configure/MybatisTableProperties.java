package io.github.nichetoolkit.mybatis.configure;

import io.github.nichetoolkit.mybatis.enums.StyleType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>MybatisTableProperties</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
@Component
@ConfigurationProperties(prefix = "nichetoolkit.mybatis.table")
public class MybatisTableProperties {
    private Boolean enabled = true;
    /** 数据库 catalog名 全局配置 */
    private String catalog;
    /** 数据库 schema名 全局配置 */
    private String schema;
    /** 默认样式 */
    private StyleType styleType = StyleType.LOWER_UNDERLINE;
    @NestedConfigurationProperty
    private Entity entity = new Entity();
    @NestedConfigurationProperty
    private SqlCache cache = new SqlCache();

    public MybatisTableProperties() {
    }

    public static class Entity {
        private Map<String, String> properties = new HashMap<>();

        public Entity() {
        }

        public Map<String, String> getProperties() {
            return properties;
        }

        public void setProperties(Map<String, String> properties) {
            this.properties = properties;
        }
    }

    public static class SqlCache {
        /** 默认缓存大小 */
        private Integer initSize = 1024;
        /** 默认缓存使用一次 */
        private boolean useOnce = false;

        public SqlCache() {
        }

        public Integer getInitSize() {
            return initSize;
        }

        public void setInitSize(Integer initSize) {
            this.initSize = initSize;
        }

        public boolean isUseOnce() {
            return useOnce;
        }

        public void setUseOnce(boolean useOnce) {
            this.useOnce = useOnce;
        }
    }

    public StyleType getStyleType() {
        return styleType;
    }

    public void setStyleType(StyleType styleType) {
        this.styleType = styleType;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public SqlCache getCache() {
        return cache;
    }

    public void setCache(SqlCache cache) {
        this.cache = cache;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
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
}
