package io.github.nichetoolkit.mybatis.error;

import io.github.nichetoolkit.rest.RestError;
import io.github.nichetoolkit.rest.RestStatus;

import java.util.function.Supplier;

/**
 * <code>MybatisTableLackError</code>
 * <p>The mybatis table lack error class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rest.RestError
 * @since Jdk1.8
 */
public class MybatisTableLackError extends RestError {
    /**
     * <code>MybatisTableLackError</code>
     * <p>Instantiates a new mybatis table lack error.</p>
     */
    public MybatisTableLackError() {
        super(MybatisErrorStatus.MYBATIS_TABLE_LACK_ERROR);
    }

    /**
     * <code>MybatisTableLackError</code>
     * <p>Instantiates a new mybatis table lack error.</p>
     * @param cause {@link java.lang.Throwable} <p>The cause parameter is <code>Throwable</code> type.</p>
     * @see java.lang.Throwable
     */
    public MybatisTableLackError(Throwable cause) {
        super(MybatisErrorStatus.MYBATIS_TABLE_LACK_ERROR, cause);
    }

    /**
     * <code>MybatisTableLackError</code>
     * <p>Instantiates a new mybatis table lack error.</p>
     * @param supplier {@link java.util.function.Supplier} <p>The supplier parameter is <code>Supplier</code> type.</p>
     * @see java.util.function.Supplier
     */
    public MybatisTableLackError(Supplier<RestStatus> supplier) {
        super(supplier);
    }

    /**
     * <code>MybatisTableLackError</code>
     * <p>Instantiates a new mybatis table lack error.</p>
     * @param error {@link java.lang.String} <p>The error parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public MybatisTableLackError(String error) {
        super(error, MybatisErrorStatus.MYBATIS_TABLE_LACK_ERROR);
    }

    /**
     * <code>MybatisTableLackError</code>
     * <p>Instantiates a new mybatis table lack error.</p>
     * @param error {@link java.lang.String} <p>The error parameter is <code>String</code> type.</p>
     * @param cause {@link java.lang.Throwable} <p>The cause parameter is <code>Throwable</code> type.</p>
     * @see java.lang.String
     * @see java.lang.Throwable
     */
    public MybatisTableLackError(String error, Throwable cause) {
        super(MybatisErrorStatus.MYBATIS_TABLE_LACK_ERROR, error, cause);
    }

    /**
     * <code>MybatisTableLackError</code>
     * <p>Instantiates a new mybatis table lack error.</p>
     * @param status {@link io.github.nichetoolkit.rest.RestStatus} <p>The status parameter is <code>RestStatus</code> type.</p>
     * @see io.github.nichetoolkit.rest.RestStatus
     */
    public MybatisTableLackError(RestStatus status) {
        super(status);
    }

    /**
     * <code>MybatisTableLackError</code>
     * <p>Instantiates a new mybatis table lack error.</p>
     * @param status {@link io.github.nichetoolkit.rest.RestStatus} <p>The status parameter is <code>RestStatus</code> type.</p>
     * @param cause  {@link java.lang.Throwable} <p>The cause parameter is <code>Throwable</code> type.</p>
     * @see io.github.nichetoolkit.rest.RestStatus
     * @see java.lang.Throwable
     */
    public MybatisTableLackError(RestStatus status, Throwable cause) {
        super(status, cause);
    }

    public MybatisTableLackError get() {
        return new MybatisTableLackError();
    }
}
