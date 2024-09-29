package io.github.nichetoolkit.mybatis.error;

import io.github.nichetoolkit.rest.RestError;
import io.github.nichetoolkit.rest.RestStatus;

import java.util.function.Supplier;

public class MybatisSqlScriptLackError extends RestError {
    public MybatisSqlScriptLackError() {
        super(MybatisErrorStatus.MYBATIS_SQL_SCRIPT_LACK_ERROR);
    }

    public MybatisSqlScriptLackError(Throwable cause) {
        super(MybatisErrorStatus.MYBATIS_SQL_SCRIPT_LACK_ERROR, cause);
    }

    public MybatisSqlScriptLackError(Supplier<RestStatus> supplier) {
        super(supplier);
    }

    public MybatisSqlScriptLackError(String error) {
        super(error, MybatisErrorStatus.MYBATIS_SQL_SCRIPT_LACK_ERROR);
    }

    public MybatisSqlScriptLackError(String error, Throwable cause) {
        super(MybatisErrorStatus.MYBATIS_SQL_SCRIPT_LACK_ERROR, error, cause);
    }

    public MybatisSqlScriptLackError(RestStatus status) {
        super(status);
    }

    public MybatisSqlScriptLackError(RestStatus status, Throwable cause) {
        super(status, cause);
    }

    public MybatisSqlScriptLackError get() {
        return new MybatisSqlScriptLackError();
    }
}
