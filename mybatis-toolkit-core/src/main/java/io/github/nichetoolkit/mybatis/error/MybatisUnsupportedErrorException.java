package io.github.nichetoolkit.mybatis.error;

import io.github.nichetoolkit.rest.RestError;

public class MybatisUnsupportedErrorException extends MybatisErrorException {
    public MybatisUnsupportedErrorException() {
        super(MybatisErrorStatus.MYBATIS_UNSUPPORTED_ERROR);
    }

    public MybatisUnsupportedErrorException(MybatisErrorStatus status) {
        super(status);
    }

    public MybatisUnsupportedErrorException(String error) {
        super(MybatisErrorStatus.MYBATIS_UNSUPPORTED_ERROR, RestError.error(MybatisErrorStatus.MYBATIS_UNSUPPORTED_ERROR, error));
    }

    public MybatisUnsupportedErrorException(String table, String error) {
        super(MybatisErrorStatus.MYBATIS_UNSUPPORTED_ERROR, RestError.error(table, MybatisErrorStatus.MYBATIS_UNSUPPORTED_ERROR, error));
    }

    public MybatisUnsupportedErrorException(String service, String table, String error) {
        super(MybatisErrorStatus.MYBATIS_UNSUPPORTED_ERROR, RestError.error(service, table, MybatisErrorStatus.MYBATIS_UNSUPPORTED_ERROR, error));
    }

    public MybatisUnsupportedErrorException(String service, String table, Object value, String error) {
        super(MybatisErrorStatus.MYBATIS_UNSUPPORTED_ERROR, RestError.error(service, table, value, MybatisErrorStatus.MYBATIS_UNSUPPORTED_ERROR, error));
    }

    public MybatisUnsupportedErrorException get() {
        return new MybatisUnsupportedErrorException();
    }
}

