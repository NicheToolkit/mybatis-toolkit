package io.github.nichetoolkit.mybatis.error;

import io.github.nichetoolkit.rest.RestError;
import io.github.nichetoolkit.rest.RestStatus;

import java.util.function.Supplier;

/**
 * <code>MybatisLinkageLackError</code>
 * <p>The mybatis linkage lack error class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rest.RestError
 * @since Jdk1.8
 */
public class MybatisLinkageLackError extends RestError {
    /**
     * <code>MybatisLinkageLackError</code>
     * <p>Instantiates a new mybatis linkage lack error.</p>
     */
    public MybatisLinkageLackError() {
        super(MybatisErrorStatus.MYBATIS_LINKAGE_LACK_ERROR);
    }

    /**
     * <code>MybatisLinkageLackError</code>
     * <p>Instantiates a new mybatis linkage lack error.</p>
     * @param cause {@link java.lang.Throwable} <p>The cause parameter is <code>Throwable</code> type.</p>
     * @see java.lang.Throwable
     */
    public MybatisLinkageLackError(Throwable cause) {
        super(MybatisErrorStatus.MYBATIS_LINKAGE_LACK_ERROR, cause);
    }

    /**
     * <code>MybatisLinkageLackError</code>
     * <p>Instantiates a new mybatis linkage lack error.</p>
     * @param supplier {@link java.util.function.Supplier} <p>The supplier parameter is <code>Supplier</code> type.</p>
     * @see java.util.function.Supplier
     */
    public MybatisLinkageLackError(Supplier<RestStatus> supplier) {
        super(supplier);
    }

    /**
     * <code>MybatisLinkageLackError</code>
     * <p>Instantiates a new mybatis linkage lack error.</p>
     * @param error {@link java.lang.String} <p>The error parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public MybatisLinkageLackError(String error) {
        super(error, MybatisErrorStatus.MYBATIS_LINKAGE_LACK_ERROR);
    }

    /**
     * <code>MybatisLinkageLackError</code>
     * <p>Instantiates a new mybatis linkage lack error.</p>
     * @param error {@link java.lang.String} <p>The error parameter is <code>String</code> type.</p>
     * @param cause {@link java.lang.Throwable} <p>The cause parameter is <code>Throwable</code> type.</p>
     * @see java.lang.String
     * @see java.lang.Throwable
     */
    public MybatisLinkageLackError(String error, Throwable cause) {
        super(MybatisErrorStatus.MYBATIS_LINKAGE_LACK_ERROR, error, cause);
    }

    /**
     * <code>MybatisLinkageLackError</code>
     * <p>Instantiates a new mybatis linkage lack error.</p>
     * @param status {@link io.github.nichetoolkit.rest.RestStatus} <p>The status parameter is <code>RestStatus</code> type.</p>
     * @see io.github.nichetoolkit.rest.RestStatus
     */
    public MybatisLinkageLackError(RestStatus status) {
        super(status);
    }

    /**
     * <code>MybatisLinkageLackError</code>
     * <p>Instantiates a new mybatis linkage lack error.</p>
     * @param status {@link io.github.nichetoolkit.rest.RestStatus} <p>The status parameter is <code>RestStatus</code> type.</p>
     * @param cause  {@link java.lang.Throwable} <p>The cause parameter is <code>Throwable</code> type.</p>
     * @see io.github.nichetoolkit.rest.RestStatus
     * @see java.lang.Throwable
     */
    public MybatisLinkageLackError(RestStatus status, Throwable cause) {
        super(status, cause);
    }

    public MybatisLinkageLackError get() {
        return new MybatisLinkageLackError();
    }
}
