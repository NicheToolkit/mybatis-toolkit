package io.github.nichetoolkit.mybatis.stereotype;

import io.github.nichetoolkit.mybatis.enums.SortType;
import io.github.nichetoolkit.mybatis.stereotype.column.*;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.UnknownTypeHandler;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * <code>RestColumn</code>
 * <p>The type rest column interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see java.lang.annotation.Annotation
 * @see java.lang.annotation.Retention
 * @see java.lang.annotation.Target
 * @see java.lang.annotation.Documented
 * @see org.springframework.stereotype.Indexed
 * @see io.github.nichetoolkit.mybatis.stereotype.column.RestName
 * @see io.github.nichetoolkit.mybatis.stereotype.column.RestExclude
 * @see io.github.nichetoolkit.mybatis.stereotype.column.RestInsert
 * @see io.github.nichetoolkit.mybatis.stereotype.column.RestUpdate
 * @see io.github.nichetoolkit.mybatis.stereotype.column.RestSelect
 * @see io.github.nichetoolkit.mybatis.stereotype.column.RestPrimaryKey
 * @see io.github.nichetoolkit.mybatis.stereotype.column.RestUnionKey
 * @see io.github.nichetoolkit.mybatis.stereotype.column.RestLinkKey
 * @see io.github.nichetoolkit.mybatis.stereotype.column.RestAlertKey
 * @see io.github.nichetoolkit.mybatis.stereotype.column.RestUniqueKey
 * @see io.github.nichetoolkit.mybatis.stereotype.column.RestSortType
 * @see io.github.nichetoolkit.mybatis.stereotype.column.RestJdbcType
 * @see io.github.nichetoolkit.mybatis.stereotype.RestProperties
 * @since Jdk1.8
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
@Indexed
@RestName
@RestExclude
@RestInsert
@RestUpdate
@RestSelect
@RestPrimaryKey
@RestUnionKey
@RestLinkKey
@RestAlertKey
@RestUniqueKey
@RestSortType
@RestJdbcType
@RestProperties
public @interface RestColumn {
    /**
     * <code>value</code>
     * <p>the method.</p>
     * @return {@link java.lang.String} <p>the return object is <code>String</code> type.</p>
     * @see java.lang.String
     * @see org.springframework.core.annotation.AliasFor
     */
    @AliasFor(annotation = RestName.class, attribute = "name")
    String value() default "";

    /**
     * <code>remark</code>
     * <p>the method.</p>
     * @return {@link java.lang.String} <p>the return object is <code>String</code> type.</p>
     * @see java.lang.String
     * @see org.springframework.core.annotation.AliasFor
     */
    @AliasFor(annotation = RestName.class, attribute = "comment")
    String comment() default "";

    /**
     * <code>primaryKey</code>
     * <p>the key method.</p>
     * @return boolean <p>the key return object is <code>boolean</code> type.</p>
     * @see org.springframework.core.annotation.AliasFor
     */
    @AliasFor(annotation = RestPrimaryKey.class, attribute = "value")
    boolean primaryKey() default false;

    /**
     * <code>linkKey</code>
     * <p>the key method.</p>
     * @return boolean <p>the key return object is <code>boolean</code> type.</p>
     * @see org.springframework.core.annotation.AliasFor
     */
    @AliasFor(annotation = RestLinkKey.class, attribute = "value")
    boolean linkKey() default false;

    /**
     * <code>alertKey</code>
     * <p>the key method.</p>
     * @return boolean <p>the key return object is <code>boolean</code> type.</p>
     * @see org.springframework.core.annotation.AliasFor
     */
    @AliasFor(annotation = RestAlertKey.class, attribute = "value")
    boolean alertKey() default false;

    /**
     * <code>unionKey</code>
     * <p>the key method.</p>
     * @return boolean <p>the key return object is <code>boolean</code> type.</p>
     * @see org.springframework.core.annotation.AliasFor
     */
    @AliasFor(annotation = RestUnionKey.class, attribute = "value")
    boolean unionKey() default false;

    /**
     * <code>unionIndex</code>
     * <p>the index method.</p>
     * @return int <p>the index return object is <code>int</code> type.</p>
     * @see org.springframework.core.annotation.AliasFor
     */
    @AliasFor(annotation = RestUnionKey.class, attribute = "index")
    int unionIndex() default 0;

    /**
     * <code>sortType</code>
     * <p>the type method.</p>
     * @return {@link io.github.nichetoolkit.mybatis.enums.SortType} <p>the type return object is <code>SortType</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.enums.SortType
     * @see org.springframework.core.annotation.AliasFor
     */
    @AliasFor(annotation = RestSortType.class, attribute = "type")
    SortType sortType() default SortType.NONE;

    /**
     * <code>priority</code>
     * <p>the method.</p>
     * @return int <p>the return object is <code>int</code> type.</p>
     * @see org.springframework.core.annotation.AliasFor
     */
    @AliasFor(annotation = RestSortType.class, attribute = "priority")
    int priority() default 0;

    /**
     * <code>exclude</code>
     * <p>the method.</p>
     * @return boolean <p>the return object is <code>boolean</code> type.</p>
     * @see org.springframework.core.annotation.AliasFor
     */
    @AliasFor(annotation = RestExclude.class, attribute = "exclude")
    boolean exclude() default false;

    /**
     * <code>select</code>
     * <p>the method.</p>
     * @return boolean <p>the return object is <code>boolean</code> type.</p>
     * @see org.springframework.core.annotation.AliasFor
     */
    @AliasFor(annotation = RestSelect.class, attribute = "select")
    boolean select() default true;

    /**
     * <code>insert</code>
     * <p>the method.</p>
     * @return boolean <p>the return object is <code>boolean</code> type.</p>
     * @see org.springframework.core.annotation.AliasFor
     */
    @AliasFor(annotation = RestInsert.class, attribute = "insert")
    boolean insert() default true;

    /**
     * <code>update</code>
     * <p>the method.</p>
     * @return boolean <p>the return object is <code>boolean</code> type.</p>
     * @see org.springframework.core.annotation.AliasFor
     */
    @AliasFor(annotation = RestUpdate.class, attribute = "update")
    boolean update() default true;

    /**
     * <code>jdbcType</code>
     * <p>the type method.</p>
     * @return {@link org.apache.ibatis.type.JdbcType} <p>the type return object is <code>JdbcType</code> type.</p>
     * @see org.apache.ibatis.type.JdbcType
     * @see org.springframework.core.annotation.AliasFor
     */
    @AliasFor(annotation = RestJdbcType.class, attribute = "jdbcType")
    JdbcType jdbcType() default JdbcType.UNDEFINED;

    /**
     * <code>typeHandler</code>
     * <p>the handler method.</p>
     * @return {@link java.lang.Class} <p>the handler return object is <code>Class</code> type.</p>
     * @see java.lang.Class
     * @see org.springframework.core.annotation.AliasFor
     */
    @AliasFor(annotation = RestJdbcType.class, attribute = "typeHandler")
    Class<? extends TypeHandler> typeHandler() default UnknownTypeHandler.class;

    /**
     * <code>numericScale</code>
     * <p>the scale method.</p>
     * @return {@link java.lang.String} <p>the scale return object is <code>String</code> type.</p>
     * @see java.lang.String
     * @see org.springframework.core.annotation.AliasFor
     */
    @AliasFor(annotation = RestJdbcType.class, attribute = "numericScale")
    String numericScale() default "";

    /**
     * <code>properties</code>
     * <p>the method.</p>
     * @return {@link io.github.nichetoolkit.mybatis.stereotype.RestProperty} <p>the return object is <code>RestProperty</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.stereotype.RestProperty
     * @see org.springframework.core.annotation.AliasFor
     */
    @AliasFor(annotation = RestProperties.class, attribute = "properties")
    RestProperty[] properties() default {};
}
