package io.github.nichetoolkit.mybatis;

/**
 * <code>MybatisOrder</code>
 * <p>The mybatis order interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see java.lang.Comparable
 * @since Jdk1.8
 */
public interface MybatisOrder extends Comparable<MybatisOrder> {

    /**
     * <code>getOrder</code>
     * <p>The get order getter method.</p>
     * @return int <p>The get order return object is <code>int</code> type.</p>
     */
    default int getOrder() {
        return 0;
    }

    @Override
    default int compareTo(MybatisOrder mybatisOrder) {
        return Integer.compare(this.getOrder(), mybatisOrder.getOrder());
    }

}
