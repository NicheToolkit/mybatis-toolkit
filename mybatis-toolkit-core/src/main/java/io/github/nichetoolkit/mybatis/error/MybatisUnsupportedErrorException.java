package io.github.nichetoolkit.mybatis.error;

import io.github.nichetoolkit.rest.RestError;

/**
 * <code>MybatisUnsupportedErrorException</code>
 * <p>The type mybatis unsupported error exception class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.error.MybatisErrorException
 * @since Jdk1.8
 */
public class MybatisUnsupportedErrorException extends MybatisErrorException {
    /**
     * <code>MybatisUnsupportedErrorException</code>
     * Instantiates a new mybatis unsupported error exception.
     */
    public MybatisUnsupportedErrorException() {
        super(MybatisErrorStatus.MYBATIS_UNSUPPORTED_ERROR);
    }

    /**
     * <code>MybatisUnsupportedErrorException</code>
     * Instantiates a new mybatis unsupported error exception.
     * @param status {@link io.github.nichetoolkit.mybatis.error.MybatisErrorStatus} <p>the status parameter is <code>MybatisErrorStatus</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.error.MybatisErrorStatus
     */
    public MybatisUnsupportedErrorException(MybatisErrorStatus status) {
        super(status);
    }

    /**
     * <code>MybatisUnsupportedErrorException</code>
     * Instantiates a new mybatis unsupported error exception.
     * @param error {@link java.lang.String} <p>the error parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public MybatisUnsupportedErrorException(String error) {
        super(MybatisErrorStatus.MYBATIS_UNSUPPORTED_ERROR, RestError.error(MybatisErrorStatus.MYBATIS_UNSUPPORTED_ERROR, error));
    }

    /**
     * <code>MybatisUnsupportedErrorException</code>
     * Instantiates a new mybatis unsupported error exception.
     * @param table {@link java.lang.String} <p>the table parameter is <code>String</code> type.</p>
     * @param error {@link java.lang.String} <p>the error parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public MybatisUnsupportedErrorException(String table, String error) {
        super(MybatisErrorStatus.MYBATIS_UNSUPPORTED_ERROR, RestError.error(table, MybatisErrorStatus.MYBATIS_UNSUPPORTED_ERROR, error));
    }

    /**
     * <code>MybatisUnsupportedErrorException</code>
     * Instantiates a new mybatis unsupported error exception.
     * @param service {@link java.lang.String} <p>the service parameter is <code>String</code> type.</p>
     * @param table   {@link java.lang.String} <p>the table parameter is <code>String</code> type.</p>
     * @param error   {@link java.lang.String} <p>the error parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public MybatisUnsupportedErrorException(String service, String table, String error) {
        super(MybatisErrorStatus.MYBATIS_UNSUPPORTED_ERROR, RestError.error(service, table, MybatisErrorStatus.MYBATIS_UNSUPPORTED_ERROR, error));
    }

    /**
     * <code>MybatisUnsupportedErrorException</code>
     * Instantiates a new mybatis unsupported error exception.
     * @param service {@link java.lang.String} <p>the service parameter is <code>String</code> type.</p>
     * @param table   {@link java.lang.String} <p>the table parameter is <code>String</code> type.</p>
     * @param value   {@link java.lang.Object} <p>the value parameter is <code>Object</code> type.</p>
     * @param error   {@link java.lang.String} <p>the error parameter is <code>String</code> type.</p>
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

