package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.enums.StyleType;
import io.github.nichetoolkit.mybatis.helper.MybatisHelper;
import io.github.nichetoolkit.mybatis.helper.ServiceLoaderHelper;
import io.github.nichetoolkit.rest.error.lack.ConfigureLackError;
import io.github.nichetoolkit.rest.error.lack.InterfaceLackError;
import io.github.nichetoolkit.rest.error.natives.UnsupportedErrorException;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import org.springframework.lang.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>MybatisStyle</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public interface MybatisStyle {
    /** 默认驼峰 */
    String NORMAL = "normal";
    /** 小写加下划线 */
    String LOWER_UNDERLINE = "lowerUnderline";
    /** 小写 */
    String LOWER = "lower";
    /** 大写 */
    String UPPER = "upper";
    /** 大写加下划线 */
    String UPPER_UNDERLINE = "upperUnderline";


    Map<String, MybatisStyle> STYLE_MAP = new HashMap<String, MybatisStyle>() {
        {
            List<MybatisStyle> instances = ServiceLoaderHelper.instances(MybatisStyle.class);
            for (MybatisStyle instance : instances) {
                put(instance.getStyleName(), instance);
            }
        }
    };

    /**
     * 获取样式名, 默认提供: normal, underline, lower, upper, upperUnderline
     * @return String
     */
    default String getStyleName() {
        return getStyleType().getKey();
    }

    /**
     * 获取样式类型，{@link StyleType}
     */
    StyleType getStyleType();


    static MybatisStyle defaultStyle() {
        return style(((String) null));
    }

    static MybatisStyle style(@NonNull StyleType styleType) {
        return style(styleType.getKey());
    }

    static MybatisStyle style(String styleName) {
        if (GeneralUtils.isEmpty(styleName)) {
            styleName = MybatisHelper.getTableProperties().getStyleType().getKey();
        }
        if (STYLE_MAP.containsKey(styleName)) {
            return STYLE_MAP.get(styleName);
        } else {
            throw new InterfaceLackError("the mybatis style is unsupported, style name: " + styleName);
        }
    }

    /**
     * 转换表名
     * @param entityClass 实体类
     * @return 对应的表名 String
     */
    String tableName(Class<?> entityClass);

    /**
     * 转换列名
     * @param mybatisTable 表
     * @param mybatisField 属性
     * @return String
     */
    String columnName(MybatisTable mybatisTable, MybatisField mybatisField);
}
