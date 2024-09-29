package io.github.nichetoolkit.mybatis.error;

import io.github.nichetoolkit.rest.RestError;
import io.github.nichetoolkit.rest.RestStatus;

import java.util.function.Supplier;

public class MybatisIdentityLackError extends RestError {
    public MybatisIdentityLackError() {
        super(MybatisErrorStatus.MYBATIS_IDENTITY_LACK_ERROR);
    }

    public MybatisIdentityLackError(Throwable cause) {
        super(MybatisErrorStatus.MYBATIS_IDENTITY_LACK_ERROR, cause);
    }

    public MybatisIdentityLackError(Supplier<RestStatus> supplier) {
        super(supplier);
    }

    public MybatisIdentityLackError(String error) {
        super(error, MybatisErrorStatus.MYBATIS_IDENTITY_LACK_ERROR);
    }

    public MybatisIdentityLackError(String error, Throwable cause) {
        super(MybatisErrorStatus.MYBATIS_IDENTITY_LACK_ERROR, error, cause);
    }

    public MybatisIdentityLackError(RestStatus status) {
        super(status);
    }

    public MybatisIdentityLackError(RestStatus status, Throwable cause) {
        super(status, cause);
    }

    public MybatisIdentityLackError get() {
        return new MybatisIdentityLackError();
    }
}
