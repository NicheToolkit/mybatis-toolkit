package io.github.nichetoolkit.mybatis.error;

import io.github.nichetoolkit.rest.RestError;
import io.github.nichetoolkit.rest.RestStatus;

import java.util.function.Supplier;

/**
 * <p>MybatisUnrealizedError</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public class MybatisUnrealizedError extends RestError {
    public MybatisUnrealizedError() {
        super(MybatisErrorStatus.MYBATIS_UNREALIZED_LACK_ERROR);
    }

    public MybatisUnrealizedError(Throwable cause) {
        super(MybatisErrorStatus.MYBATIS_UNREALIZED_LACK_ERROR, cause);
    }

    public MybatisUnrealizedError(Supplier<RestStatus> supplier) {
        super(supplier);
    }

    public MybatisUnrealizedError(String error) {
        super(error, MybatisErrorStatus.MYBATIS_UNREALIZED_LACK_ERROR);
    }

    public MybatisUnrealizedError(String error, Throwable cause) {
        super(MybatisErrorStatus.MYBATIS_UNREALIZED_LACK_ERROR, error, cause);
    }

    public MybatisUnrealizedError(RestStatus status) {
        super(status);
    }

    public MybatisUnrealizedError(RestStatus status, Throwable cause) {
        super(status, cause);
    }

    public MybatisUnrealizedError get() {
        return new MybatisUnrealizedError();
    }
}
