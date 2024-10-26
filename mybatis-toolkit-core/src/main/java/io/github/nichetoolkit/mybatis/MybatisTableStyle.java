package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.consts.StyleConstants;
import io.github.nichetoolkit.mybatis.enums.StyleType;
import io.github.nichetoolkit.rest.error.lack.InterfaceLackError;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.lang.NonNull;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <code>MybatisTableStyle</code>
 * <p>The mybatis table style interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
public interface MybatisTableStyle {
    /**
     * <code>NORMAL</code>
     * {@link java.lang.String} <p>The constant <code>NORMAL</code> field.</p>
     * @see java.lang.String
     */
    String NORMAL = StyleConstants.NORMAL;
    /**
     * <code>LOWER_UNDERLINE</code>
     * {@link java.lang.String} <p>The constant <code>LOWER_UNDERLINE</code> field.</p>
     * @see java.lang.String
     */
    String LOWER_UNDERLINE = StyleConstants.LOWER_UNDERLINE;
    /**
     * <code>LOWER</code>
     * {@link java.lang.String} <p>The constant <code>LOWER</code> field.</p>
     * @see java.lang.String
     */
    String LOWER = StyleConstants.LOWER;
    /**
     * <code>UPPER</code>
     * {@link java.lang.String} <p>The constant <code>UPPER</code> field.</p>
     * @see java.lang.String
     */
    String UPPER = StyleConstants.UPPER;
    /**
     * <code>UPPER_UNDERLINE</code>
     * {@link java.lang.String} <p>The constant <code>UPPER_UNDERLINE</code> field.</p>
     * @see java.lang.String
     */
    String UPPER_UNDERLINE = StyleConstants.UPPER_UNDERLINE;


    /**
     * <code>STYLE_MAP</code>
     * {@link java.util.Map} <p>The constant <code>STYLE_MAP</code> field.</p>
     * @see java.util.Map
     */
    Map<String, MybatisTableStyle> STYLE_MAP = new HashMap<String, MybatisTableStyle>() {
        {
            List<MybatisTableStyle> instances = SpringFactoriesLoader.loadFactories(MybatisTableStyle.class, null);
            for (MybatisTableStyle instance : instances) {
                put(instance.getStyleName(), instance);
            }
        }
    };

    /**
     * <code>getStyleName</code>
     * <p>The get style name getter method.</p>
     * @return {@link java.lang.String} <p>The get style name return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    default String getStyleName() {
        return getStyleType().getKey();
    }

    /**
     * <code>getStyleType</code>
     * <p>The get style type getter method.</p>
     * @return {@link io.github.nichetoolkit.mybatis.enums.StyleType} <p>The get style type return object is <code>StyleType</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.enums.StyleType
     */
    StyleType getStyleType();

    /**
     * <code>defaultStyle</code>
     * <p>The default style method.</p>
     * @return {@link io.github.nichetoolkit.mybatis.MybatisTableStyle} <p>The default style return object is <code>MybatisTableStyle</code> type.</p>
     */
    static MybatisTableStyle defaultStyle() {
        return style(NORMAL);
    }

    /**
     * <code>style</code>
     * <p>The style method.</p>
     * @param styleType {@link io.github.nichetoolkit.mybatis.enums.StyleType} <p>The style type parameter is <code>StyleType</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.MybatisTableStyle} <p>The style return object is <code>MybatisTableStyle</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.enums.StyleType
     * @see org.springframework.lang.NonNull
     */
    static MybatisTableStyle style(@NonNull StyleType styleType) {
        return style(styleType.getKey());
    }

    /**
     * <code>style</code>
     * <p>The style method.</p>
     * @param styleName {@link java.lang.String} <p>The style name parameter is <code>String</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.MybatisTableStyle} <p>The style return object is <code>MybatisTableStyle</code> type.</p>
     * @see java.lang.String
     * @see org.springframework.lang.NonNull
     */
    static MybatisTableStyle style(@NonNull String styleName) {
        if (STYLE_MAP.containsKey(styleName)) {
            return STYLE_MAP.get(styleName);
        } else {
            throw new InterfaceLackError("the mybatis style is unsupported, style name: " + styleName);
        }
    }

    /**
     * <code>tableName</code>
     * <p>The table name method.</p>
     * @param entityType {@link java.lang.Class} <p>The entity type parameter is <code>Class</code> type.</p>
     * @return {@link java.lang.String} <p>The table name return object is <code>String</code> type.</p>
     * @see java.lang.Class
     * @see java.lang.String
     */
    String tableName(Class<?> entityType);

    /**
     * <code>tableAlias</code>
     * <p>The table alias method.</p>
     * @param entityType {@link java.lang.Class} <p>The entity type parameter is <code>Class</code> type.</p>
     * @return {@link java.lang.String} <p>The table alias return object is <code>String</code> type.</p>
     * @see java.lang.Class
     * @see java.lang.String
     */
    String tableAlias(Class<?> entityType);

    /**
     * <code>columnName</code>
     * <p>The column name method.</p>
     * @param field {@link java.lang.reflect.Field} <p>The field parameter is <code>Field</code> type.</p>
     * @return {@link java.lang.String} <p>The column name return object is <code>String</code> type.</p>
     * @see java.lang.reflect.Field
     * @see java.lang.String
     */
    String columnName(Field field);

    /**
     * <code>columnName</code>
     * <p>The column name method.</p>
     * @param field {@link io.github.nichetoolkit.mybatis.MybatisField} <p>The field parameter is <code>MybatisField</code> type.</p>
     * @return {@link java.lang.String} <p>The column name return object is <code>String</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisField
     * @see java.lang.String
     */
    String columnName(MybatisField field);

    /**
     * <code>columnName</code>
     * <p>The column name method.</p>
     * @param table {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The table parameter is <code>MybatisTable</code> type.</p>
     * @param field {@link io.github.nichetoolkit.mybatis.MybatisField} <p>The field parameter is <code>MybatisField</code> type.</p>
     * @return {@link java.lang.String} <p>The column name return object is <code>String</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisField
     * @see java.lang.String
     */
    String columnName(MybatisTable table, MybatisField field);
}
