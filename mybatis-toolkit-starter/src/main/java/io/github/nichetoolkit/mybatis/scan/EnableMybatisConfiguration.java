package io.github.nichetoolkit.mybatis.scan;

import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.boot.autoconfigure.AutoConfigurationExcludeFilter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * <code>EnableMybatisConfiguration</code>
 * <p>The enable mybatis configuration interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see java.lang.annotation.Annotation
 * @see java.lang.annotation.Target
 * @see java.lang.annotation.Retention
 * @see java.lang.annotation.Documented
 * @see java.lang.annotation.Inherited
 * @see org.springframework.boot.autoconfigure.EnableAutoConfiguration
 * @see org.springframework.context.annotation.ComponentScan
 * @see org.springframework.context.annotation.Import
 * @see io.github.nichetoolkit.mybatis.scan.MybatisMapperScan
 * @since Jdk17
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableAutoConfiguration
@ComponentScan(
        excludeFilters = {@ComponentScan.Filter(
                type = FilterType.CUSTOM,
                classes = {TypeExcludeFilter.class}
        ), @ComponentScan.Filter(
                type = FilterType.CUSTOM,
                classes = {AutoConfigurationExcludeFilter.class}
        )}
)
@Import({MybatisMapperAutoRegister.class})
@MybatisMapperScan
public @interface EnableMybatisConfiguration {
    /**
     * <code>exclude</code>
     * <p>The exclude method.</p>
     * @return {@link java.lang.Class} <p>The exclude return object is <code>Class</code> type.</p>
     * @see java.lang.Class
     * @see org.springframework.core.annotation.AliasFor
     */
    @AliasFor(
            annotation = EnableAutoConfiguration.class
    )
    Class<?>[] exclude() default {};

    /**
     * <code>excludeName</code>
     * <p>The exclude name method.</p>
     * @return {@link java.lang.String} <p>The exclude name return object is <code>String</code> type.</p>
     * @see java.lang.String
     * @see org.springframework.core.annotation.AliasFor
     */
    @AliasFor(
            annotation = EnableAutoConfiguration.class
    )
    String[] excludeName() default {};

    /**
     * <code>scanBasePackages</code>
     * <p>The scan base packages method.</p>
     * @return {@link java.lang.String} <p>The scan base packages return object is <code>String</code> type.</p>
     * @see java.lang.String
     * @see org.springframework.core.annotation.AliasFor
     */
    @AliasFor(
            annotation = MybatisMapperScan.class,
            attribute = "basePackages"
    )
    String[] scanBasePackages() default {};

    /**
     * <code>scanBasePackageClasses</code>
     * <p>The scan base package classes method.</p>
     * @return {@link java.lang.Class} <p>The scan base package classes return object is <code>Class</code> type.</p>
     * @see java.lang.Class
     * @see org.springframework.core.annotation.AliasFor
     */
    @AliasFor(
            annotation = MybatisMapperScan.class,
            attribute = "basePackageClasses"
    )
    Class<?>[] scanBasePackageClasses() default {};

    /**
     * <code>nameGenerator</code>
     * <p>The name generator method.</p>
     * @return {@link java.lang.Class} <p>The name generator return object is <code>Class</code> type.</p>
     * @see java.lang.Class
     * @see org.springframework.core.annotation.AliasFor
     */
    @AliasFor(
            annotation = MybatisMapperScan.class,
            attribute = "nameGenerator"
    )
    Class<? extends BeanNameGenerator> nameGenerator() default BeanNameGenerator.class;
}
