package io.github.nichetoolkit.mybatis.stereotype;

import io.github.nichetoolkit.mybatis.MybatisStyle;
import io.github.nichetoolkit.mybatis.enums.StyleType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>RestTable</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RestTable {
    /**
     * 表名，默认空时使用对象名
     */
    String value() default "";

    /**
     * 备注，仅用于在注解上展示，不用于任何其他处理
     */
    String remark() default "";

    /**
     * catalog 名称，配置后，会在表名前面加上 catalog名称
     * 默认使用 全局配置
     */
    String catalog() default "";

    /**
     * schema 名称，配置后，会在表名前面加上 schema 名称
     * 默认使用 全局配置
     */
    String schema() default "";

    /**
     * 名称规则样式 默认小写加下划线 支持自定义
     * 默认使用 全局配置 LOWER_UNDERSCORE
     */
    String styleName() default MybatisStyle.LOWER_UNDERLINE;

    /**
     * 名称规则样式 默认小写加下划线
     * 默认使用 全局配置 LOWER_UNDERSCORE
     */
    StyleType styleType() default StyleType.LOWER_UNDERLINE;

    /**
     * 是否将主键注解添加到联合主键
     */
    boolean unionPrimaryKey() default false;

    /**
     * 使用指定的 <resultMap>
     */
    String resultMap() default "";

    /**
     * 自动根据字段生成 <resultMap>
     */
    boolean autoResultMap() default true;

    /**
     * 属性配置
     */
    RestProperty[] properties() default {};

    /**
     * 排除指定父类的所有字段
     */
    Class<?>[] excludeSuperClasses() default {};

    /**
     * 排除指定类型的字段
     */
    Class<?>[] excludeFieldTypes() default {};

    /**
     * 排除指定字段名的字段
     */
    String[] excludeFields() default {};
}
