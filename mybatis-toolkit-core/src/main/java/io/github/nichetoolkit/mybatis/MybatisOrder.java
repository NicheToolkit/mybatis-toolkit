package io.github.nichetoolkit.mybatis;

/**
 * <p>MybatisOrder</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public interface MybatisOrder {

    /**
     * 执行顺序，数字越大优先级越高，越早执行
     * @return int
     */
    default int getOrder() {
        return 0;
    }
}
