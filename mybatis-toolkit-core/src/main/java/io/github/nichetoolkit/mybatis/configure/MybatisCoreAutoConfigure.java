package io.github.nichetoolkit.mybatis.configure;

import io.github.nichetoolkit.rice.configure.RiceContextAutoConfigure;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;

/**
 * <code>MybatisCoreAutoConfigure</code>
 * <p>The mybatis core auto configure class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see lombok.extern.slf4j.Slf4j
 * @see org.springframework.boot.autoconfigure.AutoConfiguration
 * @see org.springframework.boot.autoconfigure.AutoConfigureAfter
 * @since Jdk17
 */
@Slf4j
@AutoConfiguration
@AutoConfigureAfter(RiceContextAutoConfigure.class)
public class MybatisCoreAutoConfigure {
    /**
     * <code>MybatisCoreAutoConfigure</code>
     * <p>Instantiates a new mybatis core auto configure.</p>
     */
    public MybatisCoreAutoConfigure() {
        log.debug("The auto configuration for [mybatis-core] initiated");
    }

}
