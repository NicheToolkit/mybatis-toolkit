package io.github.nichetoolkit.mybatis.error;

import io.github.nichetoolkit.rest.RestError;
import io.github.nichetoolkit.rest.RestStatus;

import java.util.function.Supplier;

/**
 * <code>MybatisIdentityLackError</code>
 * <p>The mybatis identity lack error class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rest.RestError
 * @since Jdk1.8
 */
public class MybatisIdentityLackError extends RestError {
    /**
     * <code>MybatisIdentityLackError</code>
     * <p>Instantiates a new mybatis identity lack error.</p>
     */
    public MybatisIdentityLackError() {
        super(MybatisErrorStatus.MYBATIS_IDENTITY_LACK_ERROR);
    }

    /**
     * <code>MybatisIdentityLackError</code>
     * <p>Instantiates a new mybatis identity lack error.</p>
     * @param cause {@link java.lang.Throwable} <p>The cause parameter is <code>Throwable</code> type.</p>
     * @see java.lang.Throwable
     */
    public MybatisIdentityLackError(Throwable cause) {
        super(MybatisErrorStatus.MYBATIS_IDENTITY_LACK_ERROR, cause);
    }

    /**
     * <code>MybatisIdentityLackError</code>
     * <p>Instantiates a new mybatis identity lack error.</p>
     * @param supplier {@link java.util.function.Supplier} <p>The supplier parameter is <code>Supplier</code> type.</p>
     * @see java.util.function.Supplier
     */
    public MybatisIdentityLackError(Supplier<RestStatus> supplier) {
        super(supplier);
    }

    /**
     * <code>MybatisIdentityLackError</code>
     * <p>Instantiates a new mybatis identity lack error.</p>
     * @param error {@link java.lang.String} <p>The error parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public MybatisIdentityLackError(String error) {
        super(error, MybatisErrorStatus.MYBATIS_IDENTITY_LACK_ERROR);
    }

    /**
     * <code>MybatisIdentityLackError</code>
     * <p>Instantiates a new mybatis identity lack error.</p>
     * @param error {@link java.lang.String} <p>The error parameter is <code>String</code> type.</p>
     * @param cause {@link java.lang.Throwable} <p>The cause parameter is <code>Throwable</code> type.</p>
     * @see java.lang.String
     * @see java.lang.Throwable
     */
    public MybatisIdentityLackError(String error, Throwable cause) {
        super(MybatisErrorStatus.MYBATIS_IDENTITY_LACK_ERROR, error, cause);
    }

    /**
     * <code>MybatisIdentityLackError</code>
     * <p>Instantiates a new mybatis identity lack error.</p>
     * @param status {@link io.github.nichetoolkit.rest.RestStatus} <p>The status parameter is <code>RestStatus</code> type.</p>
     * @see io.github.nichetoolkit.rest.RestStatus
     */
    public MybatisIdentityLackError(RestStatus status) {
        super(status);
    }

    /**
     * <code>MybatisIdentityLackError</code>
     * <p>Instantiates a new mybatis identity lack error.</p>
     * @param status {@link io.github.nichetoolkit.rest.RestStatus} <p>The status parameter is <code>RestStatus</code> type.</p>
     * @param cause  {@link java.lang.Throwable} <p>The cause parameter is <code>Throwable</code> type.</p>
     * @see io.github.nichetoolkit.rest.RestStatus
     * @see java.lang.Throwable
     */
    public MybatisIdentityLackError(RestStatus status, Throwable cause) {
        super(status, cause);
    }

    public MybatisIdentityLackError get() {
        return new MybatisIdentityLackError();
    }
}
