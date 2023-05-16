package io.github.nichetoolkit.mybatis.error;

import io.github.nichetoolkit.rest.RestError;

/**
 * <p>MybatisAssertException</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public class MybatisAssertErrorException extends MybatisErrorException {
    public MybatisAssertErrorException() {
        super(MybatisErrorStatus.MYBATIS_ASSERT_ERROR);
    }

    public MybatisAssertErrorException(MybatisErrorStatus status) {
        super(status);
    }

    public MybatisAssertErrorException(String error) {
        super(MybatisErrorStatus.MYBATIS_ASSERT_ERROR, RestError.error(MybatisErrorStatus.MYBATIS_ASSERT_ERROR, error));
    }

    public MybatisAssertErrorException(String variable, String error) {
        super(MybatisErrorStatus.MYBATIS_ASSERT_ERROR, RestError.error(variable, MybatisErrorStatus.MYBATIS_ASSERT_ERROR, error));
    }

    public MybatisAssertErrorException(String service, String variable, String error) {
        super(MybatisErrorStatus.MYBATIS_ASSERT_ERROR, RestError.error(service, variable, MybatisErrorStatus.MYBATIS_ASSERT_ERROR, error));
    }

    public MybatisAssertErrorException(String service, String variable, Object value, String error) {
        super(MybatisErrorStatus.MYBATIS_ASSERT_ERROR, RestError.error(service, variable, value, MybatisErrorStatus.MYBATIS_ASSERT_ERROR, error));
    }

    public MybatisAssertErrorException get() {
        return new MybatisAssertErrorException();
    }
}
