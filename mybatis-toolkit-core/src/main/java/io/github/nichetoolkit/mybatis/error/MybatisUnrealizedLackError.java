package io.github.nichetoolkit.mybatis.error;

import io.github.nichetoolkit.rest.RestError;
import io.github.nichetoolkit.rest.RestStatus;

import java.util.function.Supplier;

/**
 * <p>MybatisUnrealizedLackError</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public class MybatisUnrealizedLackError extends RestError {
    public MybatisUnrealizedLackError() {
        super(MybatisErrorStatus.MYBATIS_UNREALIZED_LACK_ERROR);
    }

    public MybatisUnrealizedLackError(Throwable cause) {
        super(MybatisErrorStatus.MYBATIS_UNREALIZED_LACK_ERROR, cause);
    }

    public MybatisUnrealizedLackError(Supplier<RestStatus> supplier) {
        super(supplier);
    }

    public MybatisUnrealizedLackError(String error) {
        super(error, MybatisErrorStatus.MYBATIS_UNREALIZED_LACK_ERROR);
    }

    public MybatisUnrealizedLackError(String error, Throwable cause) {
        super(MybatisErrorStatus.MYBATIS_UNREALIZED_LACK_ERROR, error, cause);
    }

    public MybatisUnrealizedLackError(RestStatus status) {
        super(status);
    }

    public MybatisUnrealizedLackError(RestStatus status, Throwable cause) {
        super(status, cause);
    }

    public MybatisUnrealizedLackError get() {
        return new MybatisUnrealizedLackError();
    }
}
