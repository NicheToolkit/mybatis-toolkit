package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.helper.ServiceLoaderHelper;
import io.github.nichetoolkit.rest.error.lack.InterfaceLackError;
import io.github.nichetoolkit.rice.consts.StyleConst;
import io.github.nichetoolkit.rice.enums.StyleType;
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
    String NORMAL = StyleConst.NORMAL;
    /** 小写加下划线 */
    String LOWER_UNDERLINE = StyleConst.LOWER_UNDERLINE;
    /** 小写 */
    String LOWER = StyleConst.LOWER;
    /** 大写 */
    String UPPER = StyleConst.UPPER;
    /** 大写加下划线 */
    String UPPER_UNDERLINE = StyleConst.UPPER_UNDERLINE;


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
        return style(NORMAL);
    }

    static MybatisStyle style(@NonNull StyleType styleType) {
        return style(styleType.getKey());
    }

    static MybatisStyle style(@NonNull String styleName) {
        if (STYLE_MAP.containsKey(styleName)) {
            return STYLE_MAP.get(styleName);
        } else {
            throw new InterfaceLackError("the mybatis style is unsupported, style name: " + styleName);
        }
    }

    /**
     * 转换表名
     * @param clazz 实体类
     * @return 对应的表名 String
     */
    String tableName(Class<?> clazz);

    /**
     * 转换列名
     * @param table 表
     * @param field 属性
     * @return String
     */
    String columnName(MybatisTable table, MybatisField field);
}
