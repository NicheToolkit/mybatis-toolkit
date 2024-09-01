package io.github.nichetoolkit.mybatis;

/**
 * <code>MybatisOrder</code>
 * <p>The type mybatis order interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
public interface MybatisOrder {

    /**
     * <code>getOrder</code>
     * <p>the order getter method.</p>
     * @return int <p>the order return object is <code>int</code> type.</p>
     */
    default int getOrder() {
        return 0;
    }
}
