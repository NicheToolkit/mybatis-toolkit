package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.mybatis.stereotype.RestProperty;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
        return this.properties.get(property);
    }

    public String getProperty(String property, String defaultValue) {
        String value = getProperty(property);
        return GeneralUtils.isNotEmpty(value) ? value : defaultValue;
    }

    public Integer getPropertyInt(String property) {
        String value = getProperty(property);
        return GeneralUtils.toInteger(value);
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

    public void setProperty(String property, String value) {
        this.properties.put(property, value);
    }

    public void setProperty(RestProperty property) {
        setProperty(property.name(), property.value());
    }

    @SuppressWarnings(value = "unchecked")
    public P setProperties(Map<String, String> properties) {
        if (GeneralUtils.isNotEmpty(properties)) {
            this.properties.putAll(properties);
        }
        return (P) this;
    }

    @SuppressWarnings(value = "unchecked")
    public P setProperties(RestProperty... properties) {
        if (GeneralUtils.isNotEmpty(properties)) {
            Map<String, String> propertiesMap = Arrays.stream(properties).collect(Collectors.toMap(RestProperty::name, RestProperty::value));
            this.properties.putAll(propertiesMap);
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
