package io.github.nichetoolkit.mybatis.configure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <code>MybatisCacheProperties</code>
 * <p>The type mybatis cache properties class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see org.springframework.stereotype.Component
 * @see org.springframework.boot.context.properties.ConfigurationProperties
 * @since Jdk1.8
 */
@Component
@ConfigurationProperties(prefix = "nichetoolkit.mybatis.cache")
public class MybatisCacheProperties {
    /**
     * <code>initSize</code>
     * {@link java.lang.Integer} <p>the <code>initSize</code> field.</p>
     * @see java.lang.Integer
     */
    private Integer initSize = 1024;
    /**
     * <code>useOnce</code>
     * <p>the <code>useOnce</code> field.</p>
     */
    private boolean useOnce = false;

    /**
     * <code>MybatisCacheProperties</code>
     * Instantiates a new mybatis cache properties.
     */
    public MybatisCacheProperties() {
    }

    /**
     * <code>getInitSize</code>
     * <p>the init size getter method.</p>
     * @return {@link java.lang.Integer} <p>the init size return object is <code>Integer</code> type.</p>
     * @see java.lang.Integer
     */
    public Integer getInitSize() {
        return initSize;
    }

    /**
     * <code>setInitSize</code>
     * <p>the init size setter method.</p>
     * @param initSize {@link java.lang.Integer} <p>the init size parameter is <code>Integer</code> type.</p>
     * @see java.lang.Integer
     */
    public void setInitSize(Integer initSize) {
        this.initSize = initSize;
    }

    /**
     * <code>isUseOnce</code>
     * <p>the use once method.</p>
     * @return boolean <p>the use once return object is <code>boolean</code> type.</p>
     */
    public boolean isUseOnce() {
        return useOnce;
    }

    /**
     * <code>setUseOnce</code>
     * <p>the use once setter method.</p>
     * @param useOnce boolean <p>the use once parameter is <code>boolean</code> type.</p>
     */
    public void setUseOnce(boolean useOnce) {
        this.useOnce = useOnce;
    }
}
