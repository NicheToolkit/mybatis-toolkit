package io.github.nichetoolkit.mybatis.error;

import io.github.nichetoolkit.rest.RestError;
import io.github.nichetoolkit.rest.RestStatus;

import java.util.function.Supplier;

/**
 * <code>MybatisUnrealizedLackError</code>
 * <p>The mybatis unrealized lack error class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rest.RestError
 * @since Jdk1.8
 */
public class MybatisUnrealizedLackError extends RestError {
    /**
     * <code>MybatisUnrealizedLackError</code>
     * <p>Instantiates a new mybatis unrealized lack error.</p>
     */
    public MybatisUnrealizedLackError() {
        super(MybatisErrorStatus.MYBATIS_UNREALIZED_LACK_ERROR);
    }

    /**
     * <code>MybatisUnrealizedLackError</code>
     * <p>Instantiates a new mybatis unrealized lack error.</p>
     * @param cause {@link java.lang.Throwable} <p>The cause parameter is <code>Throwable</code> type.</p>
     * @see java.lang.Throwable
     */
    public MybatisUnrealizedLackError(Throwable cause) {
        super(MybatisErrorStatus.MYBATIS_UNREALIZED_LACK_ERROR, cause);
    }

    /**
     * <code>MybatisUnrealizedLackError</code>
     * <p>Instantiates a new mybatis unrealized lack error.</p>
     * @param supplier {@link java.util.function.Supplier} <p>The supplier parameter is <code>Supplier</code> type.</p>
     * @see java.util.function.Supplier
     */
    public MybatisUnrealizedLackError(Supplier<RestStatus> supplier) {
        super(supplier);
    }

    /**
     * <code>MybatisUnrealizedLackError</code>
     * <p>Instantiates a new mybatis unrealized lack error.</p>
     * @param error {@link java.lang.String} <p>The error parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public MybatisUnrealizedLackError(String error) {
        super(error, MybatisErrorStatus.MYBATIS_UNREALIZED_LACK_ERROR);
    }

    /**
     * <code>MybatisUnrealizedLackError</code>
     * <p>Instantiates a new mybatis unrealized lack error.</p>
     * @param error {@link java.lang.String} <p>The error parameter is <code>String</code> type.</p>
     * @param cause {@link java.lang.Throwable} <p>The cause parameter is <code>Throwable</code> type.</p>
     * @see java.lang.String
     * @see java.lang.Throwable
     */
    public MybatisUnrealizedLackError(String error, Throwable cause) {
        super(MybatisErrorStatus.MYBATIS_UNREALIZED_LACK_ERROR, error, cause);
    }

    /**
     * <code>MybatisUnrealizedLackError</code>
     * <p>Instantiates a new mybatis unrealized lack error.</p>
     * @param status {@link io.github.nichetoolkit.rest.RestStatus} <p>The status parameter is <code>RestStatus</code> type.</p>
     * @see io.github.nichetoolkit.rest.RestStatus
     */
    public MybatisUnrealizedLackError(RestStatus status) {
        super(status);
    }

    /**
     * <code>MybatisUnrealizedLackError</code>
     * <p>Instantiates a new mybatis unrealized lack error.</p>
     * @param status {@link io.github.nichetoolkit.rest.RestStatus} <p>The status parameter is <code>RestStatus</code> type.</p>
     * @param cause  {@link java.lang.Throwable} <p>The cause parameter is <code>Throwable</code> type.</p>
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
