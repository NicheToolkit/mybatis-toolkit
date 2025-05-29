package io.github.nichetoolkit.mybatis.error;

import io.github.nichetoolkit.rest.RestError;

/**
 * <code>MybatisTableErrorException</code>
 * <p>The mybatis table error exception class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.error.MybatisErrorException
 * @since Jdk1.8
 */
public class MybatisTableErrorException extends MybatisErrorException {
    /**
     * <code>MybatisTableErrorException</code>
     * <p>Instantiates a new mybatis table error exception.</p>
     */
    public MybatisTableErrorException() {
        super(MybatisErrorStatus.MYBATIS_TABLE_ERROR);
    }

    /**
     * <code>MybatisTableErrorException</code>
     * <p>Instantiates a new mybatis table error exception.</p>
     * @param status {@link io.github.nichetoolkit.mybatis.error.MybatisErrorStatus} <p>The status parameter is <code>MybatisErrorStatus</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.error.MybatisErrorStatus
     */
    public MybatisTableErrorException(MybatisErrorStatus status) {
        super(status);
    }

    /**
     * <code>MybatisTableErrorException</code>
     * <p>Instantiates a new mybatis table error exception.</p>
     * @param error {@link java.lang.String} <p>The error parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public MybatisTableErrorException(String error) {
        super(MybatisErrorStatus.MYBATIS_TABLE_ERROR, RestError.error(MybatisErrorStatus.MYBATIS_TABLE_ERROR, error));
    }

    /**
     * <code>MybatisTableErrorException</code>
     * <p>Instantiates a new mybatis table error exception.</p>
     * @param table {@link java.lang.String} <p>The table parameter is <code>String</code> type.</p>
     * @param error {@link java.lang.String} <p>The error parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public MybatisTableErrorException(String table, String error) {
        super(MybatisErrorStatus.MYBATIS_TABLE_ERROR, RestError.error(table, MybatisErrorStatus.MYBATIS_TABLE_ERROR, error));
    }

    /**
     * <code>MybatisTableErrorException</code>
     * <p>Instantiates a new mybatis table error exception.</p>
     * @param service {@link java.lang.String} <p>The service parameter is <code>String</code> type.</p>
     * @param table   {@link java.lang.String} <p>The table parameter is <code>String</code> type.</p>
     * @param error   {@link java.lang.String} <p>The error parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public MybatisTableErrorException(String service, String table, String error) {
        super(MybatisErrorStatus.MYBATIS_TABLE_ERROR, RestError.error(service, table, MybatisErrorStatus.MYBATIS_TABLE_ERROR, error));
    }

    /**
     * <code>MybatisTableErrorException</code>
     * <p>Instantiates a new mybatis table error exception.</p>
     * @param service {@link java.lang.String} <p>The service parameter is <code>String</code> type.</p>
     * @param table   {@link java.lang.String} <p>The table parameter is <code>String</code> type.</p>
     * @param value   {@link java.lang.Object} <p>The value parameter is <code>Object</code> type.</p>
     * @param error   {@link java.lang.String} <p>The error parameter is <code>String</code> type.</p>
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

