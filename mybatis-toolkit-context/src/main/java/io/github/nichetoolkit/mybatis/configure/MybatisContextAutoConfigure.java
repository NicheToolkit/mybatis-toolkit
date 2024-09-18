package io.github.nichetoolkit.mybatis.configure;

import io.github.nichetoolkit.mybatis.MybatisCaches;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.lang.Nullable;

/**
 * <code>MybatisContextAutoConfigure</code>
 * <p>The type mybatis context auto configure class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see lombok.extern.slf4j.Slf4j
 * @see org.springframework.context.annotation.Configuration
 * @see org.springframework.context.annotation.ComponentScan
 * @since Jdk1.8
 */
@Slf4j
@Configuration
@ComponentScan(basePackages = {"io.github.nichetoolkit.mybatis"})
public class MybatisContextAutoConfigure {

    /**
     * <code>MybatisContextAutoConfigure</code>
     * Instantiates a new mybatis context auto configure.
     */
    public MybatisContextAutoConfigure() {
        log.debug("the auto configuration for [mybatis-context] initiated");
    }

    /**
     * <code>mybatisCaches</code>
     * <p>the caches method.</p>
     * @param cacheProperties {@link io.github.nichetoolkit.mybatis.configure.MybatisCacheProperties} <p>the cache properties parameter is <code>MybatisCacheProperties</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.MybatisCaches} <p>the caches return object is <code>MybatisCaches</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.configure.MybatisCacheProperties
     * @see io.github.nichetoolkit.mybatis.MybatisCaches
     * @see org.springframework.context.annotation.Bean
     * @see org.springframework.context.annotation.Primary
     * @see org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
     */
    @Bean
    @Primary
    @ConditionalOnMissingBean(MybatisCaches.class)
    public MybatisCaches mybatisCaches(MybatisCacheProperties cacheProperties) {
        return new MybatisCaches(cacheProperties);
    }

}
