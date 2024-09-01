package io.github.nichetoolkit.mybatis.error;

import io.github.nichetoolkit.rest.RestError;

/**
 * <code>MybatisAssertErrorException</code>
 * <p>The type mybatis assert error exception class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.error.MybatisErrorException
 * @since Jdk1.8
 */
public class MybatisAssertErrorException extends MybatisErrorException {
    /**
     * <code>MybatisAssertErrorException</code>
     * Instantiates a new mybatis assert error exception.
     */
    public MybatisAssertErrorException() {
        super(MybatisErrorStatus.MYBATIS_ASSERT_ERROR);
    }

    /**
     * <code>MybatisAssertErrorException</code>
     * Instantiates a new mybatis assert error exception.
     * @param status {@link io.github.nichetoolkit.mybatis.error.MybatisErrorStatus} <p>the status parameter is <code>MybatisErrorStatus</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.error.MybatisErrorStatus
     */
    public MybatisAssertErrorException(MybatisErrorStatus status) {
        super(status);
    }

    /**
     * <code>MybatisAssertErrorException</code>
     * Instantiates a new mybatis assert error exception.
     * @param error {@link java.lang.String} <p>the error parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public MybatisAssertErrorException(String error) {
        super(MybatisErrorStatus.MYBATIS_ASSERT_ERROR, RestError.error(MybatisErrorStatus.MYBATIS_ASSERT_ERROR, error));
    }

    /**
     * <code>MybatisAssertErrorException</code>
     * Instantiates a new mybatis assert error exception.
     * @param variable {@link java.lang.String} <p>the variable parameter is <code>String</code> type.</p>
     * @param error    {@link java.lang.String} <p>the error parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public MybatisAssertErrorException(String variable, String error) {
        super(MybatisErrorStatus.MYBATIS_ASSERT_ERROR, RestError.error(variable, MybatisErrorStatus.MYBATIS_ASSERT_ERROR, error));
    }

    /**
     * <code>MybatisAssertErrorException</code>
     * Instantiates a new mybatis assert error exception.
     * @param service  {@link java.lang.String} <p>the service parameter is <code>String</code> type.</p>
     * @param variable {@link java.lang.String} <p>the variable parameter is <code>String</code> type.</p>
     * @param error    {@link java.lang.String} <p>the error parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public MybatisAssertErrorException(String service, String variable, String error) {
        super(MybatisErrorStatus.MYBATIS_ASSERT_ERROR, RestError.error(service, variable, MybatisErrorStatus.MYBATIS_ASSERT_ERROR, error));
    }

    /**
     * <code>MybatisAssertErrorException</code>
     * Instantiates a new mybatis assert error exception.
     * @param service  {@link java.lang.String} <p>the service parameter is <code>String</code> type.</p>
     * @param variable {@link java.lang.String} <p>the variable parameter is <code>String</code> type.</p>
     * @param value    {@link java.lang.Object} <p>the value parameter is <code>Object</code> type.</p>
     * @param error    {@link java.lang.String} <p>the error parameter is <code>String</code> type.</p>
     * @see java.lang.String
     * @see java.lang.Object
     */
    public MybatisAssertErrorException(String service, String variable, Object value, String error) {
        super(MybatisErrorStatus.MYBATIS_ASSERT_ERROR, RestError.error(service, variable, value, MybatisErrorStatus.MYBATIS_ASSERT_ERROR, error));
    }

    public MybatisAssertErrorException get() {
        return new MybatisAssertErrorException();
    }
}
