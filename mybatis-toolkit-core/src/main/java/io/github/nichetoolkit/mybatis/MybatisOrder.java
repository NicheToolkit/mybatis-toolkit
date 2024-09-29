package io.github.nichetoolkit.mybatis;

public interface MybatisOrder {

    default int getOrder() {
        return 0;
    }
}
