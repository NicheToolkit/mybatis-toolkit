package io.github.nichetoolkit.mybatis.error;

import io.github.nichetoolkit.rest.error.natives.ServiceErrorException;

/**
 * <p>MybatisAssertException</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public class MybatisAssertException extends ServiceErrorException {
    public MybatisAssertException() {
        super(MybatisErrorStatus.MYBATIS_ASSERT_ERROR);
    }

    public MybatisAssertException(String error) {
        super(MybatisErrorStatus.MYBATIS_ASSERT_ERROR, error);
    }

    public MybatisAssertException(String service, String error) {
        super(service, MybatisErrorStatus.MYBATIS_ASSERT_ERROR, error);
    }

    @Override
    public MybatisAssertException get() {
        return new MybatisAssertException();
    }
}
