package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.consts.StyleConstants;
import io.github.nichetoolkit.mybatis.enums.StyleType;
import io.github.nichetoolkit.rest.error.lack.InterfaceLackError;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.lang.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface MybatisTableStyle {
    String NORMAL = StyleConstants.NORMAL;
    String LOWER_UNDERLINE = StyleConstants.LOWER_UNDERLINE;
    String LOWER = StyleConstants.LOWER;
    String UPPER = StyleConstants.UPPER;
    String UPPER_UNDERLINE = StyleConstants.UPPER_UNDERLINE;


    Map<String, MybatisTableStyle> STYLE_MAP = new HashMap<String, MybatisTableStyle>() {
        {
            List<MybatisTableStyle> instances = SpringFactoriesLoader.loadFactories(MybatisTableStyle.class, null);
            for (MybatisTableStyle instance : instances) {
                put(instance.getStyleName(), instance);
            }
        }
    };

    default String getStyleName() {
        return getStyleType().getKey();
    }

    StyleType getStyleType();

    static MybatisTableStyle defaultStyle() {
        return style(NORMAL);
    }

    static MybatisTableStyle style(@NonNull StyleType styleType) {
        return style(styleType.getKey());
    }

    static MybatisTableStyle style(@NonNull String styleName) {
        if (STYLE_MAP.containsKey(styleName)) {
            return STYLE_MAP.get(styleName);
        } else {
            throw new InterfaceLackError("the mybatis style is unsupported, style name: " + styleName);
        }
    }

    String tableName(Class<?> entityType);

    String tableAlias(Class<?> entityType);

    String columnName(MybatisTable table, MybatisField field);
}
