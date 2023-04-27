package io.github.nichetoolkit.mybatis.error;

import io.github.nichetoolkit.rest.RestStatus;

/**
 * <p>MybatisErrorStatus</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public enum MybatisErrorStatus implements RestStatus {
    MYBATIS_ASSERT_ERROR(21101,"mybatis assert error"),
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
