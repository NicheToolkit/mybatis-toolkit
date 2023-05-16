package io.github.nichetoolkit.mybatis.error;

import io.github.nichetoolkit.rest.RestError;

/**
 * <p>MybatisParamInvalidException</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public class MybatisParamErrorException extends MybatisErrorException {
    public MybatisParamErrorException() {
        super(MybatisErrorStatus.MYBATIS_PARAM_ERROR);
    }

    public MybatisParamErrorException(MybatisErrorStatus status) {
        super(status);
    }

    public MybatisParamErrorException(String error) {
        super(MybatisErrorStatus.MYBATIS_PARAM_ERROR, RestError.error(MybatisErrorStatus.MYBATIS_PARAM_ERROR, error));
    }

    public MybatisParamErrorException(String param, String error) {
        super(MybatisErrorStatus.MYBATIS_PARAM_ERROR, RestError.error(param, MybatisErrorStatus.MYBATIS_PARAM_ERROR, error));
    }

    public MybatisParamErrorException(String service, String param, String error) {
        super(MybatisErrorStatus.MYBATIS_PARAM_ERROR, RestError.error(service, param, MybatisErrorStatus.MYBATIS_PARAM_ERROR, error));
    }

    public MybatisParamErrorException(String service, String param, Object value, String error) {
        super(MybatisErrorStatus.MYBATIS_PARAM_ERROR, RestError.error(service, param, value, MybatisErrorStatus.MYBATIS_PARAM_ERROR, error));
    }

    public MybatisParamErrorException get() {
        return new MybatisParamErrorException();
    }
}

