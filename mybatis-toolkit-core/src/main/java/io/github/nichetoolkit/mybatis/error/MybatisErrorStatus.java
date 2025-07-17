package io.github.nichetoolkit.mybatis.error;

import io.github.nichetoolkit.rest.RestStatus;
import io.github.nichetoolkit.rest.util.I18nUtils;
import lombok.Getter;

/**
 * <code>MybatisErrorStatus</code>
 * <p>The mybatis error status enumeration.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rest.RestStatus
 * @see lombok.Getter
 * @since Jdk1.8
 */
@Getter
public enum MybatisErrorStatus implements RestStatus {
    /**
     * <code>MYBATIS_ERROR</code>
     * {@link io.github.nichetoolkit.mybatis.error.MybatisErrorStatus} <p>The <code>MYBATIS_ERROR</code> field.</p>
     */
    MYBATIS_ERROR(21100, "It has encountered a mybatis related error"),
    /**
     * <code>MYBATIS_PARAM_ERROR</code>
     * {@link io.github.nichetoolkit.mybatis.error.MybatisErrorStatus} <p>The <code>MYBATIS_PARAM_ERROR</code> field.</p>
     */
    MYBATIS_PARAM_ERROR(21101, "It has encountered a mybatis param related error"),
    /**
     * <code>MYBATIS_COLUMN_ERROR</code>
     * {@link io.github.nichetoolkit.mybatis.error.MybatisErrorStatus} <p>The <code>MYBATIS_COLUMN_ERROR</code> field.</p>
     */
    MYBATIS_COLUMN_ERROR(21102, "It has encountered a mybatis colum related error"),
    /**
     * <code>MYBATIS_TABLE_ERROR</code>
     * {@link io.github.nichetoolkit.mybatis.error.MybatisErrorStatus} <p>The <code>MYBATIS_TABLE_ERROR</code> field.</p>
     */
    MYBATIS_TABLE_ERROR(21103, "It has encountered a mybatis table related error"),
    /**
     * <code>MYBATIS_UNSUPPORTED_ERROR</code>
     * {@link io.github.nichetoolkit.mybatis.error.MybatisErrorStatus} <p>The <code>MYBATIS_UNSUPPORTED_ERROR</code> field.</p>
     */
    MYBATIS_UNSUPPORTED_ERROR(21104, "It has encountered a mybatis unsupported related error"),
    /**
     * <code>MYBATIS_ASSERT_ERROR</code>
     * {@link io.github.nichetoolkit.mybatis.error.MybatisErrorStatus} <p>The <code>MYBATIS_ASSERT_ERROR</code> field.</p>
     */
    MYBATIS_ASSERT_ERROR(21199,"It has encountered a mybatis assert related error"),

    /**
     * <code>MYBATIS_IDENTITY_LACK_ERROR</code>
     * {@link io.github.nichetoolkit.mybatis.error.MybatisErrorStatus} <p>The <code>MYBATIS_IDENTITY_LACK_ERROR</code> field.</p>
     */
    MYBATIS_IDENTITY_LACK_ERROR(21200, "It has encountered a mybatis identity lack error"),
    /**
     * <code>MYBATIS_LINKAGE_LACK_ERROR</code>
     * {@link io.github.nichetoolkit.mybatis.error.MybatisErrorStatus} <p>The <code>MYBATIS_LINKAGE_LACK_ERROR</code> field.</p>
     */
    MYBATIS_LINKAGE_LACK_ERROR(21201, "It has encountered a mybatis linkage lack error"),
    /**
     * <code>MYBATIS_TABLE_LACK_ERROR</code>
     * {@link io.github.nichetoolkit.mybatis.error.MybatisErrorStatus} <p>The <code>MYBATIS_TABLE_LACK_ERROR</code> field.</p>
     */
    MYBATIS_TABLE_LACK_ERROR(21202, "It has encountered a mybatis table lack error"),
    /**
     * <code>MYBATIS_UNREALIZED_LACK_ERROR</code>
     * {@link io.github.nichetoolkit.mybatis.error.MybatisErrorStatus} <p>The <code>MYBATIS_UNREALIZED_LACK_ERROR</code> field.</p>
     */
    MYBATIS_UNREALIZED_LACK_ERROR(21203, "It has encountered a mybatis unrealized lack error"),
    /**
     * <code>MYBATIS_PROVIDER_LACK_ERROR</code>
     * {@link io.github.nichetoolkit.mybatis.error.MybatisErrorStatus} <p>The <code>MYBATIS_PROVIDER_LACK_ERROR</code> field.</p>
     */
    MYBATIS_PROVIDER_LACK_ERROR(21204, "It has encountered a mybatis provider lack error"),
    /**
     * <code>MYBATIS_SQL_SCRIPT_LACK_ERROR</code>
     * {@link io.github.nichetoolkit.mybatis.error.MybatisErrorStatus} <p>The <code>MYBATIS_SQL_SCRIPT_LACK_ERROR</code> field.</p>
     */
    MYBATIS_SQL_SCRIPT_LACK_ERROR(21205, "It has encountered a mybatis sql script lack error"),
    ;

    /**
     * <code>status</code>
     * {@link java.lang.Integer} <p>The <code>status</code> field.</p>
     * @see java.lang.Integer
     */
    private final Integer status;
    /**
     * <code>message</code>
     * {@link java.lang.String} <p>The <code>message</code> field.</p>
     * @see java.lang.String
     */
    private final String message;

    /**
     * <code>MybatisErrorStatus</code>
     * <p>Instantiates a new mybatis error status.</p>
     * @param status  {@link java.lang.Integer} <p>The status parameter is <code>Integer</code> type.</p>
     * @param message {@link java.lang.String} <p>The message parameter is <code>String</code> type.</p>
     * @see java.lang.Integer
     * @see java.lang.String
     */
    MybatisErrorStatus(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return I18nUtils.message(name(), this.message);
    }

}
