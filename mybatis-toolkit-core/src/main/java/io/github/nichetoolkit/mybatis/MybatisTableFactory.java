package io.github.nichetoolkit.mybatis;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public interface MybatisTableFactory extends MybatisOrder {

    boolean supports(@NonNull Class<?> entityType);

    MybatisTable createTable(@NonNull Class<?> entityType, @Nullable Class<?> identityType, @Nullable Class<?> linkageType, @Nullable Class<?> alertnessType, Chain chain);

    interface Chain {
        MybatisTable createTable(@NonNull Class<?> entityType, @Nullable Class<?> identityType, @Nullable Class<?> linkageType, @Nullable Class<?> alertnessType);
    }
}
