package io.github.nichetoolkit.mybatis.error;

import io.github.nichetoolkit.rest.RestError;

/**
 * <code>MybatisParamErrorException</code>
 * <p>The mybatis param error exception class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.error.MybatisErrorException
 * @since Jdk1.8
 */
public class MybatisParamErrorException extends MybatisErrorException {
    /**
     * <code>MybatisParamErrorException</code>
     * <p>Instantiates a new mybatis param error exception.</p>
     */
    public MybatisParamErrorException() {
        super(MybatisErrorStatus.MYBATIS_PARAM_ERROR);
    }

    /**
     * <code>MybatisParamErrorException</code>
     * <p>Instantiates a new mybatis param error exception.</p>
     * @param status {@link io.github.nichetoolkit.mybatis.error.MybatisErrorStatus} <p>The status parameter is <code>MybatisErrorStatus</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.error.MybatisErrorStatus
     */
    public MybatisParamErrorException(MybatisErrorStatus status) {
        super(status);
    }

    /**
     * <code>MybatisParamErrorException</code>
     * <p>Instantiates a new mybatis param error exception.</p>
     * @param error {@link java.lang.String} <p>The error parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public MybatisParamErrorException(String error) {
        super(MybatisErrorStatus.MYBATIS_PARAM_ERROR, RestError.error(MybatisErrorStatus.MYBATIS_PARAM_ERROR, error));
    }

    /**
     * <code>MybatisParamErrorException</code>
     * <p>Instantiates a new mybatis param error exception.</p>
     * @param param {@link java.lang.String} <p>The param parameter is <code>String</code> type.</p>
     * @param error {@link java.lang.String} <p>The error parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public MybatisParamErrorException(String param, String error) {
        super(MybatisErrorStatus.MYBATIS_PARAM_ERROR, RestError.error(param, MybatisErrorStatus.MYBATIS_PARAM_ERROR, error));
    }

    /**
     * <code>MybatisParamErrorException</code>
     * <p>Instantiates a new mybatis param error exception.</p>
     * @param service {@link java.lang.String} <p>The service parameter is <code>String</code> type.</p>
     * @param param   {@link java.lang.String} <p>The param parameter is <code>String</code> type.</p>
     * @param error   {@link java.lang.String} <p>The error parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public MybatisParamErrorException(String service, String param, String error) {
        super(MybatisErrorStatus.MYBATIS_PARAM_ERROR, RestError.error(service, param, MybatisErrorStatus.MYBATIS_PARAM_ERROR, error));
    }

    /**
     * <code>MybatisParamErrorException</code>
     * <p>Instantiates a new mybatis param error exception.</p>
     * @param service {@link java.lang.String} <p>The service parameter is <code>String</code> type.</p>
     * @param param   {@link java.lang.String} <p>The param parameter is <code>String</code> type.</p>
     * @param value   {@link java.lang.Object} <p>The value parameter is <code>Object</code> type.</p>
     * @param error   {@link java.lang.String} <p>The error parameter is <code>String</code> type.</p>
     * @see java.lang.String
     * @see java.lang.Object
     */
    public MybatisParamErrorException(String service, String param, Object value, String error) {
        super(MybatisErrorStatus.MYBATIS_PARAM_ERROR, RestError.error(service, param, value, MybatisErrorStatus.MYBATIS_PARAM_ERROR, error));
    }

    public MybatisParamErrorException get() {
        return new MybatisParamErrorException();
    }
}

