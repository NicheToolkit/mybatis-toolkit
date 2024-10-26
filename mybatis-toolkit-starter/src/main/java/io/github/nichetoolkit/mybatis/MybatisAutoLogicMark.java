package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.identity.IdentityUtils;
import io.github.nichetoolkit.rice.DefaultLogicMark;
import io.github.nichetoolkit.rice.configure.RiceServiceProperties;
import org.springframework.stereotype.Component;

/**
 * <code>MybatisAutoLogicMark</code>
 * <p>The mybatis auto logic mark class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.DefaultLogicMark
 * @see org.springframework.stereotype.Component
 * @since Jdk1.8
 */
@Component
public class MybatisAutoLogicMark extends DefaultLogicMark {

    /**
     * <code>MybatisAutoLogicMark</code>
     * <p>Instantiates a new mybatis auto logic mark.</p>
     * @param serviceProperties {@link io.github.nichetoolkit.rice.configure.RiceServiceProperties} <p>The service properties parameter is <code>RiceServiceProperties</code> type.</p>
     * @see io.github.nichetoolkit.rice.configure.RiceServiceProperties
     */
    public MybatisAutoLogicMark(RiceServiceProperties serviceProperties) {
        super(serviceProperties);
    }

    @Override
    public Object getAutoMark() throws RestException {
        Object autoMark;
        switch (this.autoMark) {
            case DATETIME:
                autoMark = "now()";
                break;
            case VERSION:
                autoMark = System.currentTimeMillis();
                break;
            case IDENTITY:
            default:
                autoMark = IdentityUtils.valueOfString();
                break;
        }
        return autoMark;
    }

    @Override
    public Object getAutoUnmark() throws RestException {
        return null;
    }
}
