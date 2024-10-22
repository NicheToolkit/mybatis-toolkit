package io.github.nichetoolkit.mybatis.error;

import io.github.nichetoolkit.rest.RestStatus;

public enum MybatisErrorStatus implements RestStatus {
    MYBATIS_ERROR(21100, "mybatis error"),
    MYBATIS_PARAM_ERROR(21101, "mybatis param error"),
    MYBATIS_COLUMN_ERROR(21102, "mybatis column error"),
    MYBATIS_TABLE_ERROR(21103, "mybatis table error"),
    MYBATIS_UNSUPPORTED_ERROR(21104, "mybatis unsupported error"),
    MYBATIS_ASSERT_ERROR(21199,"mybatis assert error"),

    MYBATIS_IDENTITY_LACK_ERROR(21200, "mybatis identity lack error"),
    MYBATIS_LINKAGE_LACK_ERROR(21201, "mybatis linkage lack error"),
    MYBATIS_TABLE_LACK_ERROR(21202, "mybatis table lack error"),
    MYBATIS_UNREALIZED_LACK_ERROR(21203, "mybatis unrealized lack error"),
    MYBATIS_PROVIDER_LACK_ERROR(21204, "mybatis provider lack error"),
    MYBATIS_SQL_SCRIPT_LACK_ERROR(21205, "mybatis sql script lack error"),
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
