package io.github.nichetoolkit.mybatis.error;

import io.github.nichetoolkit.rest.RestError;

/**
 * <p>MybatisParamInvalidException</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public class MybatisTableErrorException extends MybatisErrorException {
    public MybatisTableErrorException() {
        super(MybatisErrorStatus.MYBATIS_TABLE_ERROR);
    }

    public MybatisTableErrorException(MybatisErrorStatus status) {
        super(status);
    }

    public MybatisTableErrorException(String error) {
        super(MybatisErrorStatus.MYBATIS_TABLE_ERROR, RestError.error(MybatisErrorStatus.MYBATIS_TABLE_ERROR, error));
    }

    public MybatisTableErrorException(String table, String error) {
        super(MybatisErrorStatus.MYBATIS_TABLE_ERROR, RestError.error(table, MybatisErrorStatus.MYBATIS_TABLE_ERROR, error));
    }

    public MybatisTableErrorException(String service, String table, String error) {
        super(MybatisErrorStatus.MYBATIS_TABLE_ERROR, RestError.error(service, table, MybatisErrorStatus.MYBATIS_TABLE_ERROR, error));
    }

    public MybatisTableErrorException(String service, String table, Object value, String error) {
        super(MybatisErrorStatus.MYBATIS_TABLE_ERROR, RestError.error(service, table, value, MybatisErrorStatus.MYBATIS_TABLE_ERROR, error));
    }

    public MybatisTableErrorException get() {
        return new MybatisTableErrorException();
    }
}

