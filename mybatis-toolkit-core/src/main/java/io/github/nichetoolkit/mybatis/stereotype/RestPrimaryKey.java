package io.github.nichetoolkit.mybatis.stereotype;

import io.github.nichetoolkit.rice.enums.SortType;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.UnknownTypeHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>RestPrimaryKey</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface RestPrimaryKey {
    /**
     * 列名，默认空时使用字段名
     */
    String value() default "";

    /**
     * 备注，仅用于在注解上展示，不用于任何其他处理
     */
    String remark() default "";

    /**
     * 标记字段是否 用于主键字段
     */
    boolean primaryKey() default true;

    /**
     * 标记字段是否 用于联合主键字段
     */
    boolean unionKey() default false;

    /**
     * 排序方式,{@link SortType}
     */
    String orderBy() default "";

    /**
     * 排序的优先级，数值越小优先级越高
     */
    int priority() default 0;

    /** 可查询 */
    boolean selectable() default true;

    /** 可插入 */
    boolean insertable() default true;

    /** 可更新 */
    boolean updatable() default true;
    /**
     * 数据库类型 {, jdbcType=VARCHAR}
     */
    JdbcType jdbcType() default JdbcType.UNDEFINED;

    /**
     * 类型处理器 {, typeHandler=XXTypeHandler}
     */
    Class<? extends TypeHandler> typeHandler() default UnknownTypeHandler.class;

    /**
     * 小数位数 {, numericScale=2}
     */
    String numericScale() default "";

    /**
     * 属性配置
     */
    RestProperty[] properties() default {};
}
