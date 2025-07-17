package io.github.nichetoolkit.mybatis.configure;

import io.github.nichetoolkit.mybatis.MybatisAlertnessHandler;
import io.github.nichetoolkit.mybatis.MybatisColumnResolver;
import io.github.nichetoolkit.mybatis.MybatisIdentityHandler;
import io.github.nichetoolkit.rest.RestI18n;
import io.github.nichetoolkit.rice.DefaultAlertnessHandler;
import io.github.nichetoolkit.rice.DefaultColumnResolver;
import io.github.nichetoolkit.rice.DefaultIdentityHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.Collections;

/**
 * <code>MybatisStarterAutoConfigure</code>
 * <p>The mybatis starter auto configure class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see lombok.extern.slf4j.Slf4j
 * @see org.springframework.boot.autoconfigure.AutoConfiguration
 * @see org.springframework.context.annotation.ComponentScan
 * @see org.springframework.boot.autoconfigure.ImportAutoConfiguration
 * @since Jdk1.8
 */
@Slf4j
@AutoConfiguration
@ComponentScan(basePackages = {"io.github.nichetoolkit.mybatis"})
@ImportAutoConfiguration({MybatisMapperAutoConfigure.class})
public class MybatisStarterAutoConfigure {
    /**
     * <code>MYBATIS_I18N</code>
     * {@link java.lang.String} <p>The constant <code>MYBATIS_I18N</code> field.</p>
     * @see java.lang.String
     */
    private static final String MYBATIS_I18N = "mybatis-i18n";

    /**
     * <code>MybatisStarterAutoConfigure</code>
     * <p>Instantiates a new mybatis starter auto configure.</p>
     */
    public MybatisStarterAutoConfigure() {
        log.debug("The auto configuration for [mybatis-starter] initiated");
    }

    /**
     * <code>mybatisI18nBasename</code>
     * <p>The mybatis i 18 n basename method.</p>
     * @return {@link io.github.nichetoolkit.rest.RestI18n} <p>The mybatis i 18 n basename return object is <code>RestI18n</code> type.</p>
     * @see io.github.nichetoolkit.rest.RestI18n
     * @see org.springframework.context.annotation.Bean
     */
    @Bean
    public RestI18n mybatisI18nBasename() {
        return () -> Collections.singleton(MYBATIS_I18N);
    }


    /**
     * <code>defaultIdentityHandler</code>
     * <p>The default identity handler method.</p>
     * @return {@link io.github.nichetoolkit.rice.DefaultIdentityHandler} <p>The default identity handler return object is <code>DefaultIdentityHandler</code> type.</p>
     * @see io.github.nichetoolkit.rice.DefaultIdentityHandler
     * @see org.springframework.context.annotation.Bean
     * @see org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
     */
    @Bean
    @ConditionalOnMissingBean(DefaultIdentityHandler.class)
    public DefaultIdentityHandler<?> defaultIdentityHandler() {
        return MybatisIdentityHandler.DEFAULT_HANDLER;
    }


    /**
     * <code>defaultColumnResolver</code>
     * <p>The default column resolver method.</p>
     * @return {@link io.github.nichetoolkit.mybatis.MybatisColumnResolver} <p>The default column resolver return object is <code>MybatisColumnResolver</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisColumnResolver
     * @see org.springframework.context.annotation.Bean
     * @see org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
     */
    @Bean
    @ConditionalOnMissingBean(DefaultColumnResolver.class)
    public MybatisColumnResolver defaultColumnResolver() {
        return MybatisColumnResolver.DEFAULT_RESOLVER;
    }

    /**
     * <code>defaultAlertnessHandler</code>
     * <p>The default alertness handler method.</p>
     * @return {@link io.github.nichetoolkit.rice.DefaultAlertnessHandler} <p>The default alertness handler return object is <code>DefaultAlertnessHandler</code> type.</p>
     * @see io.github.nichetoolkit.rice.DefaultAlertnessHandler
     * @see org.springframework.context.annotation.Bean
     * @see org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
     */
    @Bean
    @ConditionalOnMissingBean(DefaultAlertnessHandler.class)
    public DefaultAlertnessHandler<?> defaultAlertnessHandler() {
        return MybatisAlertnessHandler.DEFAULT_HANDLER;
    }

}
