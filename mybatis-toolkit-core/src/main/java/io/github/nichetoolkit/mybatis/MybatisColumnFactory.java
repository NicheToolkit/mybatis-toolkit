package io.github.nichetoolkit.mybatis;

import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface MybatisColumnFactory extends MybatisOrder {

    boolean supports(@NonNull MybatisTable table, @NonNull MybatisField field);

    Optional<List<MybatisColumn>> createColumn(@NonNull MybatisTable table, @NonNull MybatisField field, Chain chain);

    interface Chain {
        Optional<List<MybatisColumn>> createColumn(@NonNull MybatisTable table, @NonNull MybatisField field);
    }
}
