package io.github.nichetoolkit.mybatis.stereotype;

import io.github.nichetoolkit.mybatis.enums.SortType;
import io.github.nichetoolkit.mybatis.stereotype.column.*;
import io.github.nichetoolkit.mybatis.stereotype.table.RestProperties;
import io.github.nichetoolkit.mybatis.stereotype.table.RestProperty;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.UnknownTypeHandler;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
@Indexed

@RestColname
@RestOrder
@RestSelect
@RestInsert
@RestUpdate
@RestSortType
@RestJdbcType
@RestForceInsert
@RestForceUpdate
@RestProperties
public @interface RestColumn {

    @AliasFor(annotation = RestColname.class, attribute = "name")
    String value() default "";

    @AliasFor(annotation = RestColname.class, attribute = "comment")
    String comment() default "";

    @AliasFor(annotation = RestOrder.class, attribute = "value")
    int order() default 0;

    @AliasFor(annotation = RestSortType.class, attribute = "type")
    SortType sortType() default SortType.NONE;

    @AliasFor(annotation = RestSortType.class, attribute = "priority")
    int priority() default 0;

    @AliasFor(annotation = RestForceInsert.class, attribute = "value")
    String forceInsert() default "";

    @AliasFor(annotation = RestForceUpdate.class, attribute = "value")
    String forceUpdate() default "";

    @AliasFor(annotation = RestJdbcType.class, attribute = "jdbcType")
    JdbcType jdbcType() default JdbcType.UNDEFINED;

    @AliasFor(annotation = RestJdbcType.class, attribute = "typeHandler")
    Class<? extends TypeHandler> typeHandler() default UnknownTypeHandler.class;

    @AliasFor(annotation = RestJdbcType.class, attribute = "numericScale")
    String numericScale() default "";

    @AliasFor(annotation = RestProperties.class, attribute = "properties")
    RestProperty[] properties() default {};

    @AliasFor(annotation = RestSelect.class, attribute = "value")
    boolean select() default true;

    @AliasFor(annotation = RestInsert.class, attribute = "value")
    boolean insert() default true;

    @AliasFor(annotation = RestUpdate.class, attribute = "value")
    boolean update() default true;

    boolean exclude() default false;

    boolean identityKey() default false;

    boolean primaryKey() default false;

    boolean linkKey() default false;

    boolean alertKey() default false;

    boolean operateKey() default false;

    boolean uniqueKey() default false;

    boolean unionKey() default false;

    boolean logicKey() default false;
}
