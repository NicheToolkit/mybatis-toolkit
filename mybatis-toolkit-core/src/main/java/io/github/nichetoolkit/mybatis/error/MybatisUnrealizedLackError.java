package io.github.nichetoolkit.mybatis.error;

import io.github.nichetoolkit.rest.RestError;
import io.github.nichetoolkit.rest.RestStatus;

import java.util.function.Supplier;

/**
 * <code>MybatisUnrealizedLackError</code>
 * <p>The type mybatis unrealized lack error class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rest.RestError
 * @since Jdk1.8
 */
public class MybatisUnrealizedLackError extends RestError {
    /**
     * <code>MybatisUnrealizedLackError</code>
     * Instantiates a new mybatis unrealized lack error.
     */
    public MybatisUnrealizedLackError() {
        super(MybatisErrorStatus.MYBATIS_UNREALIZED_LACK_ERROR);
    }

    /**
     * <code>MybatisUnrealizedLackError</code>
     * Instantiates a new mybatis unrealized lack error.
     * @param cause {@link java.lang.Throwable} <p>the cause parameter is <code>Throwable</code> type.</p>
     * @see java.lang.Throwable
     */
    public MybatisUnrealizedLackError(Throwable cause) {
        super(MybatisErrorStatus.MYBATIS_UNREALIZED_LACK_ERROR, cause);
    }

    /**
     * <code>MybatisUnrealizedLackError</code>
     * Instantiates a new mybatis unrealized lack error.
     * @param supplier {@link java.util.function.Supplier} <p>the supplier parameter is <code>Supplier</code> type.</p>
     * @see java.util.function.Supplier
     */
    public MybatisUnrealizedLackError(Supplier<RestStatus> supplier) {
        super(supplier);
    }

    /**
     * <code>MybatisUnrealizedLackError</code>
     * Instantiates a new mybatis unrealized lack error.
     * @param error {@link java.lang.String} <p>the error parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public MybatisUnrealizedLackError(String error) {
        super(error, MybatisErrorStatus.MYBATIS_UNREALIZED_LACK_ERROR);
    }

    /**
     * <code>MybatisUnrealizedLackError</code>
     * Instantiates a new mybatis unrealized lack error.
     * @param error {@link java.lang.String} <p>the error parameter is <code>String</code> type.</p>
     * @param cause {@link java.lang.Throwable} <p>the cause parameter is <code>Throwable</code> type.</p>
     * @see java.lang.String
     * @see java.lang.Throwable
     */
    public MybatisUnrealizedLackError(String error, Throwable cause) {
        super(MybatisErrorStatus.MYBATIS_UNREALIZED_LACK_ERROR, error, cause);
    }

    /**
     * <code>MybatisUnrealizedLackError</code>
     * Instantiates a new mybatis unrealized lack error.
     * @param status {@link io.github.nichetoolkit.rest.RestStatus} <p>the status parameter is <code>RestStatus</code> type.</p>
     * @see io.github.nichetoolkit.rest.RestStatus
     */
    public MybatisUnrealizedLackError(RestStatus status) {
        super(status);
    }

    /**
     * <code>MybatisUnrealizedLackError</code>
     * Instantiates a new mybatis unrealized lack error.
     * @param status {@link io.github.nichetoolkit.rest.RestStatus} <p>the status parameter is <code>RestStatus</code> type.</p>
     * @param cause  {@link java.lang.Throwable} <p>the cause parameter is <code>Throwable</code> type.</p>
     * @see io.github.nichetoolkit.rest.RestStatus
     * @see java.lang.Throwable
     */
    public MybatisUnrealizedLackError(RestStatus status, Throwable cause) {
        super(status, cause);
    }

    public MybatisUnrealizedLackError get() {
        return new MybatisUnrealizedLackError();
    }
}
