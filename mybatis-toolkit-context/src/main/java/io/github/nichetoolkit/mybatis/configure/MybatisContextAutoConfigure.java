package io.github.nichetoolkit.mybatis.configure;

import io.github.nichetoolkit.rice.configure.RiceContextAutoConfigure;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * <code>MybatisContextAutoConfigure</code>
 * <p>The mybatis context auto configure class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see lombok.extern.slf4j.Slf4j
 * @see org.springframework.boot.autoconfigure.AutoConfiguration
 * @see org.springframework.boot.autoconfigure.AutoConfigureAfter
 * @see org.springframework.boot.context.properties.EnableConfigurationProperties
 * @since Jdk1.8
 */
@Slf4j
@AutoConfiguration
@AutoConfigureAfter(MybatisCoreAutoConfigure.class)
@EnableConfigurationProperties({MybatisCacheProperties.class, MybatisTableProperties.class})
public class MybatisContextAutoConfigure {

    /**
     * <code>MybatisContextAutoConfigure</code>
     * <p>Instantiates a new mybatis context auto configure.</p>
     */
    public MybatisContextAutoConfigure() {
        log.debug("The auto configuration for [mybatis-context] initiated");
    }

}
