package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.consts.StyleConstants;
import io.github.nichetoolkit.mybatis.enums.StyleType;
import io.github.nichetoolkit.mybatis.helper.ServiceLoaderHelper;
import io.github.nichetoolkit.rest.error.lack.InterfaceLackError;
import org.springframework.lang.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <code>MybatisStyle</code>
 * <p>The type mybatis style interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
public interface MybatisStyle {
    /**
     * <code>NORMAL</code>
     * {@link java.lang.String} <p>the constant <code>NORMAL</code> field.</p>
     * @see java.lang.String
     */
    String NORMAL = StyleConstants.NORMAL;
    /**
     * <code>LOWER_UNDERLINE</code>
     * {@link java.lang.String} <p>the constant <code>LOWER_UNDERLINE</code> field.</p>
     * @see java.lang.String
     */
    String LOWER_UNDERLINE = StyleConstants.LOWER_UNDERLINE;
    /**
     * <code>LOWER</code>
     * {@link java.lang.String} <p>the constant <code>LOWER</code> field.</p>
     * @see java.lang.String
     */
    String LOWER = StyleConstants.LOWER;
    /**
     * <code>UPPER</code>
     * {@link java.lang.String} <p>the constant <code>UPPER</code> field.</p>
     * @see java.lang.String
     */
    String UPPER = StyleConstants.UPPER;
    /**
     * <code>UPPER_UNDERLINE</code>
     * {@link java.lang.String} <p>the constant <code>UPPER_UNDERLINE</code> field.</p>
     * @see java.lang.String
     */
    String UPPER_UNDERLINE = StyleConstants.UPPER_UNDERLINE;


    /**
     * <code>STYLE_MAP</code>
     * {@link java.util.Map} <p>the constant <code>STYLE_MAP</code> field.</p>
     * @see java.util.Map
     */
    Map<String, MybatisStyle> STYLE_MAP = new HashMap<String, MybatisStyle>() {
        {
            List<MybatisStyle> instances = ServiceLoaderHelper.instances(MybatisStyle.class);
            for (MybatisStyle instance : instances) {
                put(instance.getStyleName(), instance);
            }
        }
    };

    /**
     * <code>getStyleName</code>
     * <p>the style name getter method.</p>
     * @return {@link java.lang.String} <p>the style name return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    default String getStyleName() {
        return getStyleType().getKey();
    }

    /**
     * <code>getStyleType</code>
     * <p>the style type getter method.</p>
     * @return {@link io.github.nichetoolkit.mybatis.enums.StyleType} <p>the style type return object is <code>StyleType</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.enums.StyleType
     */
    StyleType getStyleType();


    /**
     * <code>defaultStyle</code>
     * <p>the style method.</p>
     * @return {@link io.github.nichetoolkit.mybatis.MybatisStyle} <p>the style return object is <code>MybatisStyle</code> type.</p>
     */
    static MybatisStyle defaultStyle() {
        return style(NORMAL);
    }

    /**
     * <code>style</code>
     * <p>the method.</p>
     * @param styleType {@link io.github.nichetoolkit.mybatis.enums.StyleType} <p>the style type parameter is <code>StyleType</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.MybatisStyle} <p>the return object is <code>MybatisStyle</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.enums.StyleType
     * @see org.springframework.lang.NonNull
     */
    static MybatisStyle style(@NonNull StyleType styleType) {
        return style(styleType.getKey());
    }

    /**
     * <code>style</code>
     * <p>the method.</p>
     * @param styleName {@link java.lang.String} <p>the style name parameter is <code>String</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.MybatisStyle} <p>the return object is <code>MybatisStyle</code> type.</p>
     * @see java.lang.String
     * @see org.springframework.lang.NonNull
     */
    static MybatisStyle style(@NonNull String styleName) {
        if (STYLE_MAP.containsKey(styleName)) {
            return STYLE_MAP.get(styleName);
        } else {
            throw new InterfaceLackError("the mybatis style is unsupported, style name: " + styleName);
        }
    }

    /**
     * <code>tableName</code>
     * <p>the name method.</p>
     * @param clazz {@link java.lang.Class} <p>the clazz parameter is <code>Class</code> type.</p>
     * @return {@link java.lang.String} <p>the name return object is <code>String</code> type.</p>
     * @see java.lang.Class
     * @see java.lang.String
     */
    String tableName(Class<?> clazz);

    /**
     * <code>columnName</code>
     * <p>the name method.</p>
     * @param table {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>the table parameter is <code>MybatisTable</code> type.</p>
     * @param field {@link io.github.nichetoolkit.mybatis.MybatisField} <p>the field parameter is <code>MybatisField</code> type.</p>
     * @return {@link java.lang.String} <p>the name return object is <code>String</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     * @see io.github.nichetoolkit.mybatis.MybatisField
     * @see java.lang.String
     */
    String columnName(MybatisTable table, MybatisField field);
}
