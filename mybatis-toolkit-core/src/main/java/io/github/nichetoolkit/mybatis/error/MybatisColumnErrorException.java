package io.github.nichetoolkit.mybatis.error;

import io.github.nichetoolkit.rest.RestError;

/**
 * <code>MybatisColumnErrorException</code>
 * <p>The type mybatis column error exception class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.error.MybatisErrorException
 * @since Jdk1.8
 */
public class MybatisColumnErrorException extends MybatisErrorException {
    /**
     * <code>MybatisColumnErrorException</code>
     * Instantiates a new mybatis column error exception.
     */
    public MybatisColumnErrorException() {
        super(MybatisErrorStatus.MYBATIS_COLUMN_ERROR);
    }

    /**
     * <code>MybatisColumnErrorException</code>
     * Instantiates a new mybatis column error exception.
     * @param status {@link io.github.nichetoolkit.mybatis.error.MybatisErrorStatus} <p>the status parameter is <code>MybatisErrorStatus</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.error.MybatisErrorStatus
     */
    public MybatisColumnErrorException(MybatisErrorStatus status) {
        super(status);
    }

    /**
     * <code>MybatisColumnErrorException</code>
     * Instantiates a new mybatis column error exception.
     * @param error {@link java.lang.String} <p>the error parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public MybatisColumnErrorException(String error) {
        super(MybatisErrorStatus.MYBATIS_COLUMN_ERROR, RestError.error(MybatisErrorStatus.MYBATIS_COLUMN_ERROR, error));
    }

    /**
     * <code>MybatisColumnErrorException</code>
     * Instantiates a new mybatis column error exception.
     * @param column {@link java.lang.String} <p>the column parameter is <code>String</code> type.</p>
     * @param error  {@link java.lang.String} <p>the error parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public MybatisColumnErrorException(String column, String error) {
        super(MybatisErrorStatus.MYBATIS_COLUMN_ERROR, RestError.error(column, MybatisErrorStatus.MYBATIS_COLUMN_ERROR, error));
    }

    /**
     * <code>MybatisColumnErrorException</code>
     * Instantiates a new mybatis column error exception.
     * @param service {@link java.lang.String} <p>the service parameter is <code>String</code> type.</p>
     * @param column  {@link java.lang.String} <p>the column parameter is <code>String</code> type.</p>
     * @param error   {@link java.lang.String} <p>the error parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public MybatisColumnErrorException(String service, String column, String error) {
        super(MybatisErrorStatus.MYBATIS_COLUMN_ERROR, RestError.error(service, column, MybatisErrorStatus.MYBATIS_COLUMN_ERROR, error));
    }

    /**
     * <code>MybatisColumnErrorException</code>
     * Instantiates a new mybatis column error exception.
     * @param service {@link java.lang.String} <p>the service parameter is <code>String</code> type.</p>
     * @param column  {@link java.lang.String} <p>the column parameter is <code>String</code> type.</p>
     * @param value   {@link java.lang.Object} <p>the value parameter is <code>Object</code> type.</p>
     * @param error   {@link java.lang.String} <p>the error parameter is <code>String</code> type.</p>
     * @see java.lang.String
     * @see java.lang.Object
     */
    public MybatisColumnErrorException(String service, String column, Object value, String error) {
        super(MybatisErrorStatus.MYBATIS_COLUMN_ERROR, RestError.error(service, column, value, MybatisErrorStatus.MYBATIS_COLUMN_ERROR, error));
    }

    public MybatisColumnErrorException get() {
        return new MybatisColumnErrorException();
    }
}

