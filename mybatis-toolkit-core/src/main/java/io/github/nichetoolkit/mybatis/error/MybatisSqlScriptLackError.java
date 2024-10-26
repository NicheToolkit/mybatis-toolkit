package io.github.nichetoolkit.mybatis.error;

import io.github.nichetoolkit.rest.RestError;
import io.github.nichetoolkit.rest.RestStatus;

import java.util.function.Supplier;

/**
 * <code>MybatisSqlScriptLackError</code>
 * <p>The mybatis sql script lack error class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rest.RestError
 * @since Jdk1.8
 */
public class MybatisSqlScriptLackError extends RestError {
    /**
     * <code>MybatisSqlScriptLackError</code>
     * <p>Instantiates a new mybatis sql script lack error.</p>
     */
    public MybatisSqlScriptLackError() {
        super(MybatisErrorStatus.MYBATIS_SQL_SCRIPT_LACK_ERROR);
    }

    /**
     * <code>MybatisSqlScriptLackError</code>
     * <p>Instantiates a new mybatis sql script lack error.</p>
     * @param cause {@link java.lang.Throwable} <p>The cause parameter is <code>Throwable</code> type.</p>
     * @see java.lang.Throwable
     */
    public MybatisSqlScriptLackError(Throwable cause) {
        super(MybatisErrorStatus.MYBATIS_SQL_SCRIPT_LACK_ERROR, cause);
    }

    /**
     * <code>MybatisSqlScriptLackError</code>
     * <p>Instantiates a new mybatis sql script lack error.</p>
     * @param supplier {@link java.util.function.Supplier} <p>The supplier parameter is <code>Supplier</code> type.</p>
     * @see java.util.function.Supplier
     */
    public MybatisSqlScriptLackError(Supplier<RestStatus> supplier) {
        super(supplier);
    }

    /**
     * <code>MybatisSqlScriptLackError</code>
     * <p>Instantiates a new mybatis sql script lack error.</p>
     * @param error {@link java.lang.String} <p>The error parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public MybatisSqlScriptLackError(String error) {
        super(error, MybatisErrorStatus.MYBATIS_SQL_SCRIPT_LACK_ERROR);
    }

    /**
     * <code>MybatisSqlScriptLackError</code>
     * <p>Instantiates a new mybatis sql script lack error.</p>
     * @param error {@link java.lang.String} <p>The error parameter is <code>String</code> type.</p>
     * @param cause {@link java.lang.Throwable} <p>The cause parameter is <code>Throwable</code> type.</p>
     * @see java.lang.String
     * @see java.lang.Throwable
     */
    public MybatisSqlScriptLackError(String error, Throwable cause) {
        super(MybatisErrorStatus.MYBATIS_SQL_SCRIPT_LACK_ERROR, error, cause);
    }

    /**
     * <code>MybatisSqlScriptLackError</code>
     * <p>Instantiates a new mybatis sql script lack error.</p>
     * @param status {@link io.github.nichetoolkit.rest.RestStatus} <p>The status parameter is <code>RestStatus</code> type.</p>
     * @see io.github.nichetoolkit.rest.RestStatus
     */
    public MybatisSqlScriptLackError(RestStatus status) {
        super(status);
    }

    /**
     * <code>MybatisSqlScriptLackError</code>
     * <p>Instantiates a new mybatis sql script lack error.</p>
     * @param status {@link io.github.nichetoolkit.rest.RestStatus} <p>The status parameter is <code>RestStatus</code> type.</p>
     * @param cause  {@link java.lang.Throwable} <p>The cause parameter is <code>Throwable</code> type.</p>
     * @see io.github.nichetoolkit.rest.RestStatus
     * @see java.lang.Throwable
     */
    public MybatisSqlScriptLackError(RestStatus status, Throwable cause) {
        super(status, cause);
    }

    public MybatisSqlScriptLackError get() {
        return new MybatisSqlScriptLackError();
    }
}
