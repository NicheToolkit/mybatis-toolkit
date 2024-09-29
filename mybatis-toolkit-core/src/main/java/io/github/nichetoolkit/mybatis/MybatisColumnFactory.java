package io.github.nichetoolkit.mybatis;

import java.util.List;
import java.util.Optional;

public interface MybatisColumnFactory extends MybatisOrder {

    boolean supports(MybatisTable table, MybatisField field);

    Optional<List<MybatisColumn>> createColumn(MybatisTable table, MybatisField field, Chain chain);

    interface Chain {
        Optional<List<MybatisColumn>> createColumn(MybatisTable table, MybatisField field);
    }
}
