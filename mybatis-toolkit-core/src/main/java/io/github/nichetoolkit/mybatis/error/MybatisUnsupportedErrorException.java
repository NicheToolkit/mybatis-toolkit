package io.github.nichetoolkit.mybatis.error;

import io.github.nichetoolkit.rest.RestError;

/**
 * <code>MybatisUnsupportedErrorException</code>
 * <p>The mybatis unsupported error exception class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.error.MybatisErrorException
 * @since Jdk1.8
 */
public class MybatisUnsupportedErrorException extends MybatisErrorException {
    /**
     * <code>MybatisUnsupportedErrorException</code>
     * <p>Instantiates a new mybatis unsupported error exception.</p>
     */
    public MybatisUnsupportedErrorException() {
        super(MybatisErrorStatus.MYBATIS_UNSUPPORTED_ERROR);
    }

    /**
     * <code>MybatisUnsupportedErrorException</code>
     * <p>Instantiates a new mybatis unsupported error exception.</p>
     * @param status {@link io.github.nichetoolkit.mybatis.error.MybatisErrorStatus} <p>The status parameter is <code>MybatisErrorStatus</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.error.MybatisErrorStatus
     */
    public MybatisUnsupportedErrorException(MybatisErrorStatus status) {
        super(status);
    }

    /**
     * <code>MybatisUnsupportedErrorException</code>
     * <p>Instantiates a new mybatis unsupported error exception.</p>
     * @param error {@link java.lang.String} <p>The error parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public MybatisUnsupportedErrorException(String error) {
        super(MybatisErrorStatus.MYBATIS_UNSUPPORTED_ERROR, RestError.error(MybatisErrorStatus.MYBATIS_UNSUPPORTED_ERROR, error));
    }

    /**
     * <code>MybatisUnsupportedErrorException</code>
     * <p>Instantiates a new mybatis unsupported error exception.</p>
     * @param table {@link java.lang.String} <p>The table parameter is <code>String</code> type.</p>
     * @param error {@link java.lang.String} <p>The error parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public MybatisUnsupportedErrorException(String table, String error) {
        super(MybatisErrorStatus.MYBATIS_UNSUPPORTED_ERROR, RestError.error(table, MybatisErrorStatus.MYBATIS_UNSUPPORTED_ERROR, error));
    }

    /**
     * <code>MybatisUnsupportedErrorException</code>
     * <p>Instantiates a new mybatis unsupported error exception.</p>
     * @param service {@link java.lang.String} <p>The service parameter is <code>String</code> type.</p>
     * @param table   {@link java.lang.String} <p>The table parameter is <code>String</code> type.</p>
     * @param error   {@link java.lang.String} <p>The error parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public MybatisUnsupportedErrorException(String service, String table, String error) {
        super(MybatisErrorStatus.MYBATIS_UNSUPPORTED_ERROR, RestError.error(service, table, MybatisErrorStatus.MYBATIS_UNSUPPORTED_ERROR, error));
    }

    /**
     * <code>MybatisUnsupportedErrorException</code>
     * <p>Instantiates a new mybatis unsupported error exception.</p>
     * @param service {@link java.lang.String} <p>The service parameter is <code>String</code> type.</p>
     * @param table   {@link java.lang.String} <p>The table parameter is <code>String</code> type.</p>
     * @param value   {@link java.lang.Object} <p>The value parameter is <code>Object</code> type.</p>
     * @param error   {@link java.lang.String} <p>The error parameter is <code>String</code> type.</p>
     * @see java.lang.String
     * @see java.lang.Object
     */
    public MybatisUnsupportedErrorException(String service, String table, Object value, String error) {
        super(MybatisErrorStatus.MYBATIS_UNSUPPORTED_ERROR, RestError.error(service, table, value, MybatisErrorStatus.MYBATIS_UNSUPPORTED_ERROR, error));
    }

    public MybatisUnsupportedErrorException get() {
        return new MybatisUnsupportedErrorException();
    }
}

