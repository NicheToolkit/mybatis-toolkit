package io.github.nichetoolkit.mybatis.error;

import io.github.nichetoolkit.rest.RestError;
import io.github.nichetoolkit.rest.RestErrorException;
import io.github.nichetoolkit.rest.RestStatus;

/**
 * <p>MybatisErrorException</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public class MybatisErrorException extends RestErrorException {
    public MybatisErrorException() {
        super(MybatisErrorStatus.MYBATIS_ERROR);
    }

    public MybatisErrorException(MybatisErrorStatus status) {
        super(status);
    }

    public MybatisErrorException(String error) {
        super(MybatisErrorStatus.MYBATIS_ERROR, RestError.error(MybatisErrorStatus.MYBATIS_ERROR, error));
    }

    public MybatisErrorException(String error, Throwable cause) {
        super(MybatisErrorStatus.MYBATIS_ERROR, RestError.error(MybatisErrorStatus.MYBATIS_ERROR, error, cause));
    }


    public MybatisErrorException(RestStatus status) {
        super(status, RestError.error(status));
    }

    public MybatisErrorException(RestStatus status, RestError error) {
        super(status, error);
    }

    public MybatisErrorException(RestStatus status, String error) {
        super(status, RestError.error(status, error));
    }

    public MybatisErrorException(String service, String error) {
        super(MybatisErrorStatus.MYBATIS_ERROR, RestError.error(service, MybatisErrorStatus.MYBATIS_ERROR, error));
    }


    public MybatisErrorException(String service, String error, Throwable cause) {
        super(MybatisErrorStatus.MYBATIS_ERROR, RestError.error(service, MybatisErrorStatus.MYBATIS_ERROR, error, cause));
    }

    public MybatisErrorException(String service, RestStatus status) {
        super(status, RestError.error(service, status));
    }

    public MybatisErrorException get() {
        return new MybatisErrorException();
    }
}

