package io.github.nichetoolkit.mybatis.error;

import io.github.nichetoolkit.rest.RestError;

/**
 * <code>MybatisTableErrorException</code>
 * <p>The type mybatis table error exception class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.error.MybatisErrorException
 * @since Jdk1.8
 */
public class MybatisTableErrorException extends MybatisErrorException {
    /**
     * <code>MybatisTableErrorException</code>
     * Instantiates a new mybatis table error exception.
     */
    public MybatisTableErrorException() {
        super(MybatisErrorStatus.MYBATIS_TABLE_ERROR);
    }

    /**
     * <code>MybatisTableErrorException</code>
     * Instantiates a new mybatis table error exception.
     * @param status {@link io.github.nichetoolkit.mybatis.error.MybatisErrorStatus} <p>the status parameter is <code>MybatisErrorStatus</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.error.MybatisErrorStatus
     */
    public MybatisTableErrorException(MybatisErrorStatus status) {
        super(status);
    }

    /**
     * <code>MybatisTableErrorException</code>
     * Instantiates a new mybatis table error exception.
     * @param error {@link java.lang.String} <p>the error parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public MybatisTableErrorException(String error) {
        super(MybatisErrorStatus.MYBATIS_TABLE_ERROR, RestError.error(MybatisErrorStatus.MYBATIS_TABLE_ERROR, error));
    }

    /**
     * <code>MybatisTableErrorException</code>
     * Instantiates a new mybatis table error exception.
     * @param table {@link java.lang.String} <p>the table parameter is <code>String</code> type.</p>
     * @param error {@link java.lang.String} <p>the error parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public MybatisTableErrorException(String table, String error) {
        super(MybatisErrorStatus.MYBATIS_TABLE_ERROR, RestError.error(table, MybatisErrorStatus.MYBATIS_TABLE_ERROR, error));
    }

    /**
     * <code>MybatisTableErrorException</code>
     * Instantiates a new mybatis table error exception.
     * @param service {@link java.lang.String} <p>the service parameter is <code>String</code> type.</p>
     * @param table   {@link java.lang.String} <p>the table parameter is <code>String</code> type.</p>
     * @param error   {@link java.lang.String} <p>the error parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public MybatisTableErrorException(String service, String table, String error) {
        super(MybatisErrorStatus.MYBATIS_TABLE_ERROR, RestError.error(service, table, MybatisErrorStatus.MYBATIS_TABLE_ERROR, error));
    }

    /**
     * <code>MybatisTableErrorException</code>
     * Instantiates a new mybatis table error exception.
     * @param service {@link java.lang.String} <p>the service parameter is <code>String</code> type.</p>
     * @param table   {@link java.lang.String} <p>the table parameter is <code>String</code> type.</p>
     * @param value   {@link java.lang.Object} <p>the value parameter is <code>Object</code> type.</p>
     * @param error   {@link java.lang.String} <p>the error parameter is <code>String</code> type.</p>
     * @see java.lang.String
     * @see java.lang.Object
     */
    public MybatisTableErrorException(String service, String table, Object value, String error) {
        super(MybatisErrorStatus.MYBATIS_TABLE_ERROR, RestError.error(service, table, value, MybatisErrorStatus.MYBATIS_TABLE_ERROR, error));
    }

    public MybatisTableErrorException get() {
        return new MybatisTableErrorException();
    }
}

