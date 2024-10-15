package io.github.nichetoolkit.mybatis.error;

import io.github.nichetoolkit.rest.RestError;
import io.github.nichetoolkit.rest.RestStatus;

import java.util.function.Supplier;

public class MybatisTableLackError extends RestError {
    public MybatisTableLackError() {
        super(MybatisErrorStatus.MYBATIS_TABLE_LACK_ERROR);
    }

    public MybatisTableLackError(Throwable cause) {
        super(MybatisErrorStatus.MYBATIS_TABLE_LACK_ERROR, cause);
    }

    public MybatisTableLackError(Supplier<RestStatus> supplier) {
        super(supplier);
    }

    public MybatisTableLackError(String error) {
        super(error, MybatisErrorStatus.MYBATIS_TABLE_LACK_ERROR);
    }

    public MybatisTableLackError(String error, Throwable cause) {
        super(MybatisErrorStatus.MYBATIS_TABLE_LACK_ERROR, error, cause);
    }

    public MybatisTableLackError(RestStatus status) {
        super(status);
    }

    public MybatisTableLackError(RestStatus status, Throwable cause) {
        super(status, cause);
    }

    public MybatisTableLackError get() {
        return new MybatisTableLackError();
    }
}
