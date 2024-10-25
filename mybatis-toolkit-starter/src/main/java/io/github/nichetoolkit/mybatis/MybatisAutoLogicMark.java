package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.identity.IdentityUtils;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rice.DefaultLogicMark;
import io.github.nichetoolkit.rice.configure.RiceServiceProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * <code>DefaultAutoLogicMark</code>
 * <p>The default auto logic mark class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see DefaultLogicMark
 * @since Jdk1.8
 */
@Component
public class MybatisAutoLogicMark extends DefaultLogicMark {

    /**
     * <code>DefaultAutoLogicMark</code>
     * <p>Instantiates a new default auto logic mark.</p>
     * @param serviceProperties {@link RiceServiceProperties} <p>The service properties parameter is <code>RiceServiceProperties</code> type.</p>
     * @see RiceServiceProperties
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
