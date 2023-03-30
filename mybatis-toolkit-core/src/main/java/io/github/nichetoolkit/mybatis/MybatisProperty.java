package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.configure.MybatisTableProperties;
import io.github.nichetoolkit.mybatis.helper.MybatisHelper;
import io.github.nichetoolkit.mybatis.stereotype.RestProperty;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rice.helper.PropertyHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>MybatisProperty</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */

public class MybatisProperty<P extends MybatisProperty<P>> {
    protected Map<String, String> properties;

    public MybatisProperty() {
        this.properties = new HashMap<>();
    }

    public MybatisProperty(Map<String, String> properties) {
        this.properties = properties;
    }

    public Map<String, String> properties() {
        return properties;
    }

    public String getProperty(String property) {
        if (GeneralUtils.isEmpty(property) || GeneralUtils.isEmpty(this.properties)) {
            return null;
        }
        String value = this.properties.get(property);
        if (GeneralUtils.isEmpty(value)) {
            MybatisTableProperties tableProperties = MybatisHelper.getTableProperties();
            value = tableProperties.getEntity().getProperties().get(property);
        }
        return value;
    }

    public String getProperty(String property, String defaultValue) {
        String value = getProperty(property);
        return GeneralUtils.isNotEmpty(value) ? value : defaultValue;
    }

    public Integer getPropertyInt(String property) {
        String value = getProperty(property);
        return PropertyHelper.toInteger(value);
    }

    public Integer getPropertyInt(String property, Integer defaultValue) {
        Integer value = getPropertyInt(property);
        return GeneralUtils.isNotEmpty(value) ? value : defaultValue;
    }

    public Boolean getPropertyBoolean(String property) {
        String value = getProperty(property);
        return Boolean.parseBoolean(value);
    }

    public Boolean getPropertyBoolean(String property, Boolean defaultValue) {
        String value = getProperty(property);
        return GeneralUtils.isNotEmpty(value) ? Boolean.parseBoolean(value) : defaultValue;
    }

    @SuppressWarnings(value = "unchecked")
    public P setProperty(String property, String value) {
        this.properties.put(property, value);
        return (P) this;
    }

    public P setProperty(RestProperty property) {
        return setProperty(property.name(), property.value());
    }

    @SuppressWarnings(value = "unchecked")
    public P setProperties(Map<String, String> properties) {
        if (GeneralUtils.isNotEmpty(properties)) {
            this.properties.putAll(properties);
        }
        return (P) this;
    }

    public String removeProperty(String property) {
        if (GeneralUtils.isNotEmpty(this.properties)) {
            String value = getProperty(property);
            if (GeneralUtils.isNotEmpty(value)) {
                this.properties.remove(property);
            }
            return value;
        } else {
            return null;
        }
    }
}
