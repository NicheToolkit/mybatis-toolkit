package io.github.nichetoolkit.mybatis.error;

import io.github.nichetoolkit.rest.RestError;
import io.github.nichetoolkit.rest.RestErrorException;
import io.github.nichetoolkit.rest.RestStatus;

/**
 * <code>MybatisErrorException</code>
 * <p>The type mybatis error exception class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rest.RestErrorException
 * @since Jdk1.8
 */
public class MybatisErrorException extends RestErrorException {
    /**
     * <code>MybatisErrorException</code>
     * Instantiates a new mybatis error exception.
     */
    public MybatisErrorException() {
        super(MybatisErrorStatus.MYBATIS_ERROR);
    }

    /**
     * <code>MybatisErrorException</code>
     * Instantiates a new mybatis error exception.
     * @param status {@link io.github.nichetoolkit.mybatis.error.MybatisErrorStatus} <p>the status parameter is <code>MybatisErrorStatus</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.error.MybatisErrorStatus
     */
    public MybatisErrorException(MybatisErrorStatus status) {
        super(status);
    }

    /**
     * <code>MybatisErrorException</code>
     * Instantiates a new mybatis error exception.
     * @param error {@link java.lang.String} <p>the error parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public MybatisErrorException(String error) {
        super(MybatisErrorStatus.MYBATIS_ERROR, RestError.error(MybatisErrorStatus.MYBATIS_ERROR, error));
    }

    /**
     * <code>MybatisErrorException</code>
     * Instantiates a new mybatis error exception.
     * @param error {@link java.lang.String} <p>the error parameter is <code>String</code> type.</p>
     * @param cause {@link java.lang.Throwable} <p>the cause parameter is <code>Throwable</code> type.</p>
     * @see java.lang.String
     * @see java.lang.Throwable
     */
    public MybatisErrorException(String error, Throwable cause) {
        super(MybatisErrorStatus.MYBATIS_ERROR, RestError.error(MybatisErrorStatus.MYBATIS_ERROR, error, cause));
    }


    /**
     * <code>MybatisErrorException</code>
     * Instantiates a new mybatis error exception.
     * @param status {@link io.github.nichetoolkit.rest.RestStatus} <p>the status parameter is <code>RestStatus</code> type.</p>
     * @see io.github.nichetoolkit.rest.RestStatus
     */
    public MybatisErrorException(RestStatus status) {
        super(status, RestError.error(status));
    }

    /**
     * <code>MybatisErrorException</code>
     * Instantiates a new mybatis error exception.
     * @param status {@link io.github.nichetoolkit.rest.RestStatus} <p>the status parameter is <code>RestStatus</code> type.</p>
     * @param error  {@link io.github.nichetoolkit.rest.RestError} <p>the error parameter is <code>RestError</code> type.</p>
     * @see io.github.nichetoolkit.rest.RestStatus
     * @see io.github.nichetoolkit.rest.RestError
     */
    public MybatisErrorException(RestStatus status, RestError error) {
        super(status, error);
    }

    /**
     * <code>MybatisErrorException</code>
     * Instantiates a new mybatis error exception.
     * @param status {@link io.github.nichetoolkit.rest.RestStatus} <p>the status parameter is <code>RestStatus</code> type.</p>
     * @param error  {@link java.lang.String} <p>the error parameter is <code>String</code> type.</p>
     * @see io.github.nichetoolkit.rest.RestStatus
     * @see java.lang.String
     */
    public MybatisErrorException(RestStatus status, String error) {
        super(status, RestError.error(status, error));
    }

    /**
     * <code>MybatisErrorException</code>
     * Instantiates a new mybatis error exception.
     * @param service {@link java.lang.String} <p>the service parameter is <code>String</code> type.</p>
     * @param error   {@link java.lang.String} <p>the error parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public MybatisErrorException(String service, String error) {
        super(MybatisErrorStatus.MYBATIS_ERROR, RestError.error(service, MybatisErrorStatus.MYBATIS_ERROR, error));
    }


    /**
     * <code>MybatisErrorException</code>
     * Instantiates a new mybatis error exception.
     * @param service {@link java.lang.String} <p>the service parameter is <code>String</code> type.</p>
     * @param error   {@link java.lang.String} <p>the error parameter is <code>String</code> type.</p>
     * @param cause   {@link java.lang.Throwable} <p>the cause parameter is <code>Throwable</code> type.</p>
     * @see java.lang.String
     * @see java.lang.Throwable
     */
    public MybatisErrorException(String service, String error, Throwable cause) {
        super(MybatisErrorStatus.MYBATIS_ERROR, RestError.error(service, MybatisErrorStatus.MYBATIS_ERROR, error, cause));
    }

    /**
     * <code>MybatisErrorException</code>
     * Instantiates a new mybatis error exception.
     * @param service {@link java.lang.String} <p>the service parameter is <code>String</code> type.</p>
     * @param status  {@link io.github.nichetoolkit.rest.RestStatus} <p>the status parameter is <code>RestStatus</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestStatus
     */
    public MybatisErrorException(String service, RestStatus status) {
        super(status, RestError.error(service, status));
    }

    public MybatisErrorException get() {
        return new MybatisErrorException();
    }
}

