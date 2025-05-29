package io.github.nichetoolkit.mybatis.error;

import io.github.nichetoolkit.rest.RestError;
import io.github.nichetoolkit.rest.RestStatus;

import java.util.function.Supplier;

/**
 * <code>MybatisProviderLackError</code>
 * <p>The mybatis provider lack error class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rest.RestError
 * @since Jdk1.8
 */
public class MybatisProviderLackError extends RestError {
    /**
     * <code>MybatisProviderLackError</code>
     * <p>Instantiates a new mybatis provider lack error.</p>
     */
    public MybatisProviderLackError() {
        super(MybatisErrorStatus.MYBATIS_PROVIDER_LACK_ERROR);
    }

    /**
     * <code>MybatisProviderLackError</code>
     * <p>Instantiates a new mybatis provider lack error.</p>
     * @param cause {@link java.lang.Throwable} <p>The cause parameter is <code>Throwable</code> type.</p>
     * @see java.lang.Throwable
     */
    public MybatisProviderLackError(Throwable cause) {
        super(MybatisErrorStatus.MYBATIS_PROVIDER_LACK_ERROR, cause);
    }

    /**
     * <code>MybatisProviderLackError</code>
     * <p>Instantiates a new mybatis provider lack error.</p>
     * @param supplier {@link java.util.function.Supplier} <p>The supplier parameter is <code>Supplier</code> type.</p>
     * @see java.util.function.Supplier
     */
    public MybatisProviderLackError(Supplier<RestStatus> supplier) {
        super(supplier);
    }

    /**
     * <code>MybatisProviderLackError</code>
     * <p>Instantiates a new mybatis provider lack error.</p>
     * @param error {@link java.lang.String} <p>The error parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public MybatisProviderLackError(String error) {
        super(error, MybatisErrorStatus.MYBATIS_PROVIDER_LACK_ERROR);
    }

    /**
     * <code>MybatisProviderLackError</code>
     * <p>Instantiates a new mybatis provider lack error.</p>
     * @param error {@link java.lang.String} <p>The error parameter is <code>String</code> type.</p>
     * @param cause {@link java.lang.Throwable} <p>The cause parameter is <code>Throwable</code> type.</p>
     * @see java.lang.String
     * @see java.lang.Throwable
     */
    public MybatisProviderLackError(String error, Throwable cause) {
        super(MybatisErrorStatus.MYBATIS_PROVIDER_LACK_ERROR, error, cause);
    }

    /**
     * <code>MybatisProviderLackError</code>
     * <p>Instantiates a new mybatis provider lack error.</p>
     * @param status {@link io.github.nichetoolkit.rest.RestStatus} <p>The status parameter is <code>RestStatus</code> type.</p>
     * @see io.github.nichetoolkit.rest.RestStatus
     */
    public MybatisProviderLackError(RestStatus status) {
        super(status);
    }

    /**
     * <code>MybatisProviderLackError</code>
     * <p>Instantiates a new mybatis provider lack error.</p>
     * @param status {@link io.github.nichetoolkit.rest.RestStatus} <p>The status parameter is <code>RestStatus</code> type.</p>
     * @param cause  {@link java.lang.Throwable} <p>The cause parameter is <code>Throwable</code> type.</p>
     * @see io.github.nichetoolkit.rest.RestStatus
     * @see java.lang.Throwable
     */
    public MybatisProviderLackError(RestStatus status, Throwable cause) {
        super(status, cause);
    }

    public MybatisProviderLackError get() {
        return new MybatisProviderLackError();
    }
}
