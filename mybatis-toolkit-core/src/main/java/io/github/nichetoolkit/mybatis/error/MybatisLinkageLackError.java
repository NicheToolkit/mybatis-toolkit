package io.github.nichetoolkit.mybatis.error;

import io.github.nichetoolkit.rest.RestError;
import io.github.nichetoolkit.rest.RestStatus;

import java.util.function.Supplier;

public class MybatisLinkageLackError extends RestError {
    public MybatisLinkageLackError() {
        super(MybatisErrorStatus.MYBATIS_LINKAGE_LACK_ERROR);
    }

    public MybatisLinkageLackError(Throwable cause) {
        super(MybatisErrorStatus.MYBATIS_LINKAGE_LACK_ERROR, cause);
    }

    public MybatisLinkageLackError(Supplier<RestStatus> supplier) {
        super(supplier);
    }

    public MybatisLinkageLackError(String error) {
        super(error, MybatisErrorStatus.MYBATIS_LINKAGE_LACK_ERROR);
    }

    public MybatisLinkageLackError(String error, Throwable cause) {
        super(MybatisErrorStatus.MYBATIS_LINKAGE_LACK_ERROR, error, cause);
    }

    public MybatisLinkageLackError(RestStatus status) {
        super(status);
    }

    public MybatisLinkageLackError(RestStatus status, Throwable cause) {
        super(status, cause);
    }

    public MybatisLinkageLackError get() {
        return new MybatisLinkageLackError();
    }
}
