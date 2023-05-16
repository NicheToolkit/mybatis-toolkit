package io.github.nichetoolkit.mybatis.error;

import io.github.nichetoolkit.rest.RestError;

/**
 * <p>MybatisParamInvalidException</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public class MybatisColumnErrorException extends MybatisErrorException {
    public MybatisColumnErrorException() {
        super(MybatisErrorStatus.MYBATIS_COLUMN_ERROR);
    }

    public MybatisColumnErrorException(MybatisErrorStatus status) {
        super(status);
    }

    public MybatisColumnErrorException(String error) {
        super(MybatisErrorStatus.MYBATIS_COLUMN_ERROR, RestError.error(MybatisErrorStatus.MYBATIS_COLUMN_ERROR, error));
    }

    public MybatisColumnErrorException(String column, String error) {
        super(MybatisErrorStatus.MYBATIS_COLUMN_ERROR, RestError.error(column, MybatisErrorStatus.MYBATIS_COLUMN_ERROR, error));
    }

    public MybatisColumnErrorException(String service, String column, String error) {
        super(MybatisErrorStatus.MYBATIS_COLUMN_ERROR, RestError.error(service, column, MybatisErrorStatus.MYBATIS_COLUMN_ERROR, error));
    }

    public MybatisColumnErrorException(String service, String column, Object value, String error) {
        super(MybatisErrorStatus.MYBATIS_COLUMN_ERROR, RestError.error(service, column, value, MybatisErrorStatus.MYBATIS_COLUMN_ERROR, error));
    }

    public MybatisColumnErrorException get() {
        return new MybatisColumnErrorException();
    }
}

