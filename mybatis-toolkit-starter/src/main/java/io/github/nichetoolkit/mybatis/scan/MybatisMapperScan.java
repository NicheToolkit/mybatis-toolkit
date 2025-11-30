package io.github.nichetoolkit.mybatis.scan;

import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * <code>MybatisMapperScan</code>
 * <p>The mybatis mapper scan interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see java.lang.annotation.Annotation
 * @see java.lang.annotation.Retention
 * @see java.lang.annotation.Target
 * @see java.lang.annotation.Documented
 * @see org.springframework.context.annotation.Import
 * @since Jdk17
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({MybatisMapperAutoRegister.class})
@interface MybatisMapperScan {
    /**
     * <code>value</code>
     * <p>The value method.</p>
     * @return {@link java.lang.String} <p>The value return object is <code>String</code> type.</p>
     * @see java.lang.String
     * @see org.springframework.core.annotation.AliasFor
     */
    @AliasFor("basePackages")
    String[] value() default {};

    /**
     * <code>basePackages</code>
     * <p>The base packages method.</p>
     * @return {@link java.lang.String} <p>The base packages return object is <code>String</code> type.</p>
     * @see java.lang.String
     * @see org.springframework.core.annotation.AliasFor
     */
    @AliasFor("value")
    String[] basePackages() default {};

    /**
     * <code>basePackageClasses</code>
     * <p>The base package classes method.</p>
     * @return {@link java.lang.Class} <p>The base package classes return object is <code>Class</code> type.</p>
     * @see java.lang.Class
     */
    Class<?>[] basePackageClasses() default {};

    /**
     * <code>nameGenerator</code>
     * <p>The name generator method.</p>
     * @return {@link java.lang.Class} <p>The name generator return object is <code>Class</code> type.</p>
     * @see java.lang.Class
     */
    Class<? extends BeanNameGenerator> nameGenerator() default BeanNameGenerator.class;

    /**
     * <code>annotationClass</code>
     * <p>The annotation class method.</p>
     * @return {@link java.lang.Class} <p>The annotation class return object is <code>Class</code> type.</p>
     * @see java.lang.Class
     */
    Class<? extends Annotation> annotationClass() default Annotation.class;

    /**
     * <code>markerInterface</code>
     * <p>The marker interface method.</p>
     * @return {@link java.lang.Class} <p>The marker interface return object is <code>Class</code> type.</p>
     * @see java.lang.Class
     */
    Class<?> markerInterface() default Class.class;

    /**
     * <code>sqlSessionTemplateRef</code>
     * <p>The sql session template ref method.</p>
     * @return {@link java.lang.String} <p>The sql session template ref return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    String sqlSessionTemplateRef() default "";

    /**
     * <code>sqlSessionFactoryRef</code>
     * <p>The sql session factory ref method.</p>
     * @return {@link java.lang.String} <p>The sql session factory ref return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    String sqlSessionFactoryRef() default "";

    /**
     * <code>lazyInitialization</code>
     * <p>The lazy initialization method.</p>
     * @return {@link java.lang.String} <p>The lazy initialization return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    String lazyInitialization() default "";

    /**
     * <code>defaultScope</code>
     * <p>The default scope method.</p>
     * @return {@link java.lang.String} <p>The default scope return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    String defaultScope() default "";

    /**
     * <code>processPropertyPlaceHolders</code>
     * <p>The process property place holders method.</p>
     * @return boolean <p>The process property place holders return object is <code>boolean</code> type.</p>
     */
    boolean processPropertyPlaceHolders() default true;
}