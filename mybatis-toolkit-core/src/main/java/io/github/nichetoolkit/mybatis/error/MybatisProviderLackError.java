package io.github.nichetoolkit.mybatis.error;

import io.github.nichetoolkit.rest.RestError;
import io.github.nichetoolkit.rest.RestStatus;

import java.util.function.Supplier;

/**
 * <p>MybatisProviderLackError</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public class MybatisProviderLackError extends RestError {
    public MybatisProviderLackError() {
        super(MybatisErrorStatus.MYBATIS_PROVIDER_LACK_ERROR);
    }

    public MybatisProviderLackError(Throwable cause) {
        super(MybatisErrorStatus.MYBATIS_PROVIDER_LACK_ERROR, cause);
    }

    public MybatisProviderLackError(Supplier<RestStatus> supplier) {
        super(supplier);
    }

    public MybatisProviderLackError(String error) {
        super(error, MybatisErrorStatus.MYBATIS_PROVIDER_LACK_ERROR);
    }

    public MybatisProviderLackError(String error, Throwable cause) {
        super(MybatisErrorStatus.MYBATIS_PROVIDER_LACK_ERROR, error, cause);
    }

    public MybatisProviderLackError(RestStatus status) {
        super(status);
    }

    public MybatisProviderLackError(RestStatus status, Throwable cause) {
        super(status, cause);
    }

    public MybatisProviderLackError get() {
        return new MybatisProviderLackError();
    }
}
