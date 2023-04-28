package io.github.nichetoolkit.mybatis.provider;

import io.github.nichetoolkit.mybatis.MybatisColumn;
import io.github.nichetoolkit.mybatis.MybatisSqlScript;
import io.github.nichetoolkit.mybatis.MybatisTable;
import org.apache.ibatis.builder.annotation.ProviderContext;

import java.util.stream.Collectors;

/**
 * <p>MybatisProvider</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public class MybatisProvider {

    public static String save(ProviderContext providerContext) {
        return MybatisSqlScript.caching(providerContext, entity -> "INSERT INTO " + entity.tableName()
                + "(" + entity.insertColumnList() + ")"
                + " VALUES (" + entity.insertColumns().stream()
                .map(MybatisColumn::variables).collect(Collectors.joining(",")) + ")");
    }

    public static String saveAll(ProviderContext providerContext) {
        return MybatisSqlScript.caching(providerContext, entity -> "INSERT INTO " + entity.tableName()
                + "(" + entity.insertColumnList() + ")"
                + " VALUES (" + entity.insertColumns().stream()
                .map(MybatisColumn::variables).collect(Collectors.joining(",")) + ")");
    }

    public static String deleteById(ProviderContext providerContext) {
        return MybatisSqlScript.caching(providerContext, entity -> "DELETE FROM " + entity.tableName()
                + " WHERE " + entity.identityColumns().stream().map(MybatisColumn::columnEqualsProperty).collect(Collectors.joining(" AND ")));
    }

    public static String deleteByAll(ProviderContext providerContext) {
        return MybatisSqlScript.caching(providerContext, entity -> "DELETE FROM " + entity.tableName()
                + " WHERE " + entity.identityColumns().stream().map(MybatisColumn::columnEqualsProperty).collect(Collectors.joining(" AND ")));
    }

    public static String findById(ProviderContext providerContext) {
        return MybatisSqlScript.caching(providerContext, new MybatisSqlScript() {
            @Override
            public String sql(MybatisTable entity) {
                return "SELECT " + entity.baseColumnAsPropertyList()
                        + " FROM " + entity.tableName()
                        + where(() -> entity.identityColumns().stream().map(MybatisColumn::columnEqualsProperty).collect(Collectors.joining(" AND ")));
            }
        });
    }

    public static String findByAll(ProviderContext providerContext) {
        return MybatisSqlScript.caching(providerContext, new MybatisSqlScript() {
            @Override
            public String sql(MybatisTable entity) {
                return "SELECT " + entity.baseColumnAsPropertyList()
                        + " FROM " + entity.tableName()
                        + where(() -> entity.identityColumns().stream().map(MybatisColumn::columnEqualsProperty).collect(Collectors.joining(" AND ")));
            }
        });
    }

    public static String findAllByWhere(ProviderContext providerContext) {
        return MybatisSqlScript.caching(providerContext, new MybatisSqlScript() {
            @Override
            public String sql(MybatisTable entity) {
                return "SELECT " + entity.baseColumnAsPropertyList()
                        + " FROM " + entity.tableName()
                        + where(() -> entity.identityColumns().stream().map(MybatisColumn::columnEqualsProperty).collect(Collectors.joining(" AND ")));
            }
        });
    }

    public static String deleteAllByWhere(ProviderContext providerContext) {
        return MybatisSqlScript.caching(providerContext, new MybatisSqlScript() {
            @Override
            public String sql(MybatisTable entity) {
                return "SELECT " + entity.baseColumnAsPropertyList()
                        + " FROM " + entity.tableName()
                        + where(() -> entity.identityColumns().stream().map(MybatisColumn::columnEqualsProperty).collect(Collectors.joining(" AND ")));
            }
        });
    }

}
