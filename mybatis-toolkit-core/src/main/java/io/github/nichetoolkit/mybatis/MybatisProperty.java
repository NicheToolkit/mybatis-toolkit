package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rice.table.RestProperty;
import io.github.nichetoolkit.rest.util.ValueUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <code>MybatisProperty</code>
 * <p>The mybatis property class.</p>
 * @param <P>  {@link io.github.nichetoolkit.mybatis.MybatisProperty} <p>The generic parameter is <code>MybatisProperty</code> type.</p>
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
public class MybatisProperty<P extends MybatisProperty<P>> {
    /**
     * <code>properties</code>
     * {@link java.util.Map} <p>The <code>properties</code> field.</p>
     * @see  java.util.Map
     */
    protected Map<String, String> properties;

    /**
     * <code>MybatisProperty</code>
     * <p>Instantiates a new mybatis property.</p>
     */
    public MybatisProperty() {
        this.properties = new HashMap<>();
    }

    /**
     * <code>MybatisProperty</code>
     * <p>Instantiates a new mybatis property.</p>
     * @param properties {@link java.util.Map} <p>The properties parameter is <code>Map</code> type.</p>
     * @see  java.util.Map
     */
    public MybatisProperty(Map<String, String> properties) {
        this.properties = properties;
    }

    /**
     * <code>properties</code>
     * <p>The properties method.</p>
     * @return  {@link java.util.Map} <p>The properties return object is <code>Map</code> type.</p>
     * @see  java.util.Map
     */
    public Map<String, String> properties() {
        return properties;
    }

    /**
     * <code>getProperty</code>
     * <p>The get property getter method.</p>
     * @param property {@link java.lang.String} <p>The property parameter is <code>String</code> type.</p>
     * @see  java.lang.String
     * @return  {@link java.lang.String} <p>The get property return object is <code>String</code> type.</p>
     */
    public String getProperty(String property) {
        if (GeneralUtils.isEmpty(property) || GeneralUtils.isEmpty(this.properties)) {
            return null;
        }
        return this.properties.get(property);
    }

    /**
     * <code>getProperty</code>
     * <p>The get property getter method.</p>
     * @param property {@link java.lang.String} <p>The property parameter is <code>String</code> type.</p>
     * @param defaultValue {@link java.lang.String} <p>The default value parameter is <code>String</code> type.</p>
     * @see  java.lang.String
     * @return  {@link java.lang.String} <p>The get property return object is <code>String</code> type.</p>
     */
    public String getProperty(String property, String defaultValue) {
        String value = getProperty(property);
        return GeneralUtils.isNotEmpty(value) ? value : defaultValue;
    }

    /**
     * <code>getPropertyInt</code>
     * <p>The get property int getter method.</p>
     * @param property {@link java.lang.String} <p>The property parameter is <code>String</code> type.</p>
     * @see  java.lang.String
     * @see  java.lang.Integer
     * @return  {@link java.lang.Integer} <p>The get property int return object is <code>Integer</code> type.</p>
     */
    public Integer getPropertyInt(String property) {
        String value = getProperty(property);
        return ValueUtils.toInteger(value);
    }

    /**
     * <code>getPropertyInt</code>
     * <p>The get property int getter method.</p>
     * @param property {@link java.lang.String} <p>The property parameter is <code>String</code> type.</p>
     * @param defaultValue {@link java.lang.Integer} <p>The default value parameter is <code>Integer</code> type.</p>
     * @see  java.lang.String
     * @see  java.lang.Integer
     * @return  {@link java.lang.Integer} <p>The get property int return object is <code>Integer</code> type.</p>
     */
    public Integer getPropertyInt(String property, Integer defaultValue) {
        Integer value = getPropertyInt(property);
        return GeneralUtils.isNotEmpty(value) ? value : defaultValue;
    }

    /**
     * <code>getPropertyBoolean</code>
     * <p>The get property boolean getter method.</p>
     * @param property {@link java.lang.String} <p>The property parameter is <code>String</code> type.</p>
     * @see  java.lang.String
     * @see  java.lang.Boolean
     * @return  {@link java.lang.Boolean} <p>The get property boolean return object is <code>Boolean</code> type.</p>
     */
    public Boolean getPropertyBoolean(String property) {
        String value = getProperty(property);
        return Boolean.parseBoolean(value);
    }

    /**
     * <code>getPropertyBoolean</code>
     * <p>The get property boolean getter method.</p>
     * @param property {@link java.lang.String} <p>The property parameter is <code>String</code> type.</p>
     * @param defaultValue {@link java.lang.Boolean} <p>The default value parameter is <code>Boolean</code> type.</p>
     * @see  java.lang.String
     * @see  java.lang.Boolean
     * @return  {@link java.lang.Boolean} <p>The get property boolean return object is <code>Boolean</code> type.</p>
     */
    public Boolean getPropertyBoolean(String property, Boolean defaultValue) {
        String value = getProperty(property);
        return GeneralUtils.isNotEmpty(value) ? Boolean.parseBoolean(value) : defaultValue;
    }

    /**
     * <code>setProperty</code>
     * <p>The set property setter method.</p>
     * @param property {@link java.lang.String} <p>The property parameter is <code>String</code> type.</p>
     * @param value {@link java.lang.String} <p>The value parameter is <code>String</code> type.</p>
     * @see  java.lang.String
     */
    public void setProperty(String property, String value) {
        this.properties.remove(property);
        this.properties.put(property, value);
    }

    /**
     * <code>setProperty</code>
     * <p>The set property setter method.</p>
     * @param property {@link io.github.nichetoolkit.rice.table.RestProperty} <p>The property parameter is <code>RestProperty</code> type.</p>
     * @see  io.github.nichetoolkit.rice.table.RestProperty
     */
    public void setProperty(RestProperty property) {
        setProperty(property.name(), property.value());
    }

    /**
     * <code>setProperties</code>
     * <p>The set properties setter method.</p>
     * @param properties {@link java.util.Map} <p>The properties parameter is <code>Map</code> type.</p>
     * @see  java.util.Map
     * @see  java.lang.SuppressWarnings
     * @return P <p>The set properties return object is <code>P</code> type.</p>
     */
    @SuppressWarnings(value = "unchecked")
    public P setProperties(Map<String, String> properties) {
        if (GeneralUtils.isNotEmpty(properties)) {
            this.properties.putAll(properties);
        }
        return (P) this;
    }

    /**
     * <code>setProperties</code>
     * <p>The set properties setter method.</p>
     * @param properties {@link io.github.nichetoolkit.rice.table.RestProperty} <p>The properties parameter is <code>RestProperty</code> type.</p>
     * @see  io.github.nichetoolkit.rice.table.RestProperty
     * @see  java.lang.SuppressWarnings
     * @return P <p>The set properties return object is <code>P</code> type.</p>
     */
    @SuppressWarnings(value = "unchecked")
    public P setProperties(RestProperty... properties) {
        if (GeneralUtils.isNotEmpty(properties)) {
            Map<String, String> propertiesMap = Arrays.stream(properties).collect(Collectors.toMap(RestProperty::name, RestProperty::value));
            this.properties.putAll(propertiesMap);
        }
        return (P) this;
    }

    /**
     * <code>removeProperty</code>
     * <p>The remove property method.</p>
     * @param property {@link java.lang.String} <p>The property parameter is <code>String</code> type.</p>
     * @see  java.lang.String
     * @return  {@link java.lang.String} <p>The remove property return object is <code>String</code> type.</p>
     */
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
