package io.github.nichetoolkit.mybatis.error;

import io.github.nichetoolkit.rest.RestStatus;

/**
 * <p>MybatisErrorStatus</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public enum MybatisErrorStatus implements RestStatus {
    MYBATIS_ERROR(21100, "mybatis error"),
    MYBATIS_PARAM_ERROR(21101, "mybatis param error"),
    MYBATIS_COLUMN_ERROR(21102, "mybatis column error"),
    MYBATIS_TABLE_ERROR(21103, "mybatis column error"),
    MYBATIS_UNSUPPORTED_ERROR(21104, "mybatis unsupported error"),
    MYBATIS_ASSERT_ERROR(21199,"mybatis assert error"),

    MYBATIS_UNREALIZED_LACK_ERROR(21200, "mybatis unrealized lack error"),
    MYBATIS_PROVIDER_LACK_ERROR(21201, "mybatis provider lack error"),
    MYBATIS_SQL_SCRIPT_LACK_ERROR(21202, "mybatis sql script lack error"),
    ;

    private final Integer status;
    private final String message;

    MybatisErrorStatus(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getName() {
        return this.name().toLowerCase().replace("_", " ");
    }

    @Override
    public Integer getStatus() {
        return this.status;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
