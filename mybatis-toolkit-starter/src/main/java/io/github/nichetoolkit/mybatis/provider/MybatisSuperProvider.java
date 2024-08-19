package io.github.nichetoolkit.mybatis.provider;

import io.github.nichetoolkit.mybatis.MybatisColumn;
import io.github.nichetoolkit.mybatis.MybatisSqlScript;
import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.configure.MybatisTableProperties;
import io.github.nichetoolkit.mybatis.driver.DatabaseType;
import io.github.nichetoolkit.mybatis.error.MybatisParamErrorException;
import io.github.nichetoolkit.mybatis.error.MybatisTableErrorException;
import io.github.nichetoolkit.mybatis.error.MybatisUnrealizedLackError;
import io.github.nichetoolkit.mybatis.error.MybatisUnsupportedErrorException;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.helper.OptionalHelper;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>MybatisProvider</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public class MybatisSuperProvider implements InitializingBean {

    private final MybatisTableProperties tableProperties;

    @Autowired
    public MybatisSuperProvider(MybatisTableProperties tableProperties) {
        this.tableProperties = tableProperties;
    }

    private static MybatisSuperProvider INSTANCE = null;

    public static MybatisSuperProvider instance() {
        return INSTANCE;
    }

    @Override
    public void afterPropertiesSet() {
        INSTANCE = this;
    }

    public static DatabaseType databaseType() {
        DatabaseType databaseType = DatabaseType.POSTGRESQL;
        if (GeneralUtils.isNotEmpty(INSTANCE) && GeneralUtils.isNotEmpty(INSTANCE.tableProperties)) {
            databaseType = INSTANCE.tableProperties.getDatabaseType();
        }
        return databaseType;
    }

    public static String saveUpsetSql(MybatisTable table) {
        DatabaseType databaseType = databaseType();
        List<MybatisColumn> updateColumns = table.updateColumns();
        boolean doNothing = false;
        String doUpdateSql = "";
        if (GeneralUtils.isEmpty(updateColumns)) {
            doNothing = true;
        } else {
            doUpdateSql = table.updateColumns().stream().map(MybatisColumn::excluded).collect(Collectors.joining(", "));
        }
        String upsetSql;
        switch (databaseType) {
            case MARIADB:
            case SQLSEVER:
            case ORACLE:
            case SQLITE:
            case H2:
            case HSQLDB:
            case REDSHIFT:
            case CASSANDRA:
                throw new MybatisUnrealizedLackError("the function is unrealized of this database type: " + databaseType.getKey());
            case GAUSSDB:
            case MYSQL:
                if (doNothing) {
                    upsetSql = " ON DUPLICATE KEY DO NOTHING ";
                } else {
                    upsetSql = " ON DUPLICATE KEY UPDATE ";
                }
                break;
            case POSTGRESQL:
            default:
                if (doNothing) {
                    upsetSql = " ON CONFLICT (" + table.identityColumnList() + ") DO NOTHING ";
                } else {
                    upsetSql = " ON CONFLICT (" + table.identityColumnList() + ") DO UPDATE SET ";
                }
                break;
        }
        return upsetSql + doUpdateSql;
    }

    public static <E> String save(ProviderContext providerContext, @Param("entity") E entity) throws RestException {
        return saveDynamic(providerContext, null, entity);
    }

    public static <E> String saveDynamic(ProviderContext providerContext, @Param("tablename") String tablename, @Param("entity") E entity) throws RestException {
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(entity), "the entity param of 'save' method cannot be empty!", message -> new MybatisParamErrorException("save", "entity", message));
        return MybatisSqlScript.caching(providerContext, table -> {
            OptionalHelper.falseable(GeneralUtils.isNotEmpty(table.insertColumns()), "the insert columns of table with 'save' method cannot be empty!", message -> new MybatisTableErrorException("save", "insertColumns", message));
            OptionalHelper.falseable(GeneralUtils.isNotEmpty(table.identityColumns()), "the identity columns of table with 'save' method cannot be empty!", message -> new MybatisTableErrorException("save", "identityColumns", message));
            return "INSERT INTO " + Optional.ofNullable(tablename).orElse(table.tableName())
                    + " (" + table.insertColumnList() + ")"
                    + " VALUES (" + table.insertColumns().stream()
                    .map(column -> column.variable("entity.")).collect(Collectors.joining(", "))
                    + ")" + saveUpsetSql(table);
        });
    }

    public static <E> String saveAll(ProviderContext providerContext, @Param("entityList") Collection<E> entityList) throws RestException {
        return saveDynamicAll(providerContext, null, entityList);
    }

    public static <E> String saveDynamicAll(ProviderContext providerContext, @Param("tablename") String tablename, @Param("entityList") Collection<E> entityList) throws RestException {
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(entityList), "the entity list param of 'saveAll' method cannot be empty!", message -> new MybatisParamErrorException("saveAll", "entityList", message));
        return MybatisSqlScript.caching(providerContext, new MybatisSqlScript() {
            @Override
            public String sql(MybatisTable table) throws RestException {
                OptionalHelper.falseable(GeneralUtils.isNotEmpty(table.insertColumns()), "the insert columns of table with 'saveAll' method cannot be empty!", message -> new MybatisTableErrorException("saveAll", "insertColumns", message));
                OptionalHelper.falseable(GeneralUtils.isNotEmpty(table.identityColumns()), "the identity columns of table with 'saveAll' method cannot be empty!", message -> new MybatisTableErrorException("saveAll", "identityColumns", message));
                return "INSERT INTO " + Optional.ofNullable(tablename).orElse(table.tableName())
                        + " (" + table.insertColumnList() + ") VALUES "
                        + foreach("entityList", "entity", ", ", () ->
                        " (" + table.insertColumns().stream().map(column -> column.variable("entity.")).collect(Collectors.joining(", ")) + " )")
                        + saveUpsetSql(table);
            }
        });
    }

    public static <I> String deleteById(ProviderContext providerContext, @Param("id") I id) throws RestException {
        return deleteDynamicById(providerContext, null, id);
    }

    public static <I> String deleteDynamicById(ProviderContext providerContext, @Param("tablename") String tablename, @Param("id") I id) throws RestException {
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(id), "the id param of 'deleteById' method cannot be empty!", message -> new MybatisParamErrorException("deleteById", "id", message));
        return MybatisSqlScript.caching(providerContext, table -> {
            OptionalHelper.trueable(table.isUseUnionKey(), "the union keys of table with 'deleteById' method is unsupported!", message -> new MybatisUnsupportedErrorException("deleteById", "unionKeys", message));
            return "DELETE FROM " + Optional.ofNullable(tablename).orElse(table.tableName())
                    + " WHERE " + table.getIdentityColumn().columnEqualsProperty();
        });
    }

    public static <I> String deleteByAll(ProviderContext providerContext, @Param("idList") Collection<I> idList) throws RestException {
        return deleteDynamicByAll(providerContext, null, idList);
    }

    public static <I> String deleteDynamicByAll(ProviderContext providerContext, @Param("tablename") String tablename, @Param("idList") Collection<I> idList) throws RestException {
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(idList), "the id list param of 'deleteByAll' method cannot be empty!", message -> new MybatisParamErrorException("deleteByAll", "idList", message));
        return MybatisSqlScript.caching(providerContext, new MybatisSqlScript() {
            @Override
            public String sql(MybatisTable table) throws RestException {
                OptionalHelper.trueable(table.isUseUnionKey(), "the union keys of table with 'deleteByAll' method is unsupported!", message -> new MybatisUnsupportedErrorException("deleteByAll", "unionKeys", message));
                return "DELETE FROM " + Optional.ofNullable(tablename).orElse(table.tableName())
                        + " WHERE " + table.getIdentityColumn().getColumnName() + " IN " + foreach("idList", "id", ", ", "(", ")", () -> table.getIdentityColumn().variable());
            }
        });
    }

    public static <I> String findById(ProviderContext providerContext, @Param("id") I id) throws RestException {
        return findDynamicById(providerContext, null, id);
    }

    public static <I> String findDynamicById(ProviderContext providerContext, @Param("tablename") String tablename, @Param("id") I id) throws RestException {
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(id), "the id param of 'findById' method cannot be empty!", message -> new MybatisParamErrorException("findById", "id", message));
        return MybatisSqlScript.caching(providerContext, table -> {
            OptionalHelper.falseable(GeneralUtils.isNotEmpty(table.selectColumns()), "the select columns of table with 'findById' method cannot be empty!", message -> new MybatisTableErrorException("findById", "selectColumns", message));
            OptionalHelper.trueable(table.isUseUnionKey(), "the union keys of table with 'findById' method is unsupported!", message -> new MybatisUnsupportedErrorException("findById", "unionKeys", message));
            return "SELECT " + table.selectColumnList()
                    + " FROM " + Optional.ofNullable(tablename).orElse(table.tableName())
                    + " WHERE " + table.getIdentityColumn().columnEqualsProperty();
        });

    }

    public static <I> String findByAll(ProviderContext providerContext, @Param("idList") Collection<I> idList) throws RestException {
        return findDynamicByAll(providerContext, null, idList);
    }

    public static <I> String findDynamicByAll(ProviderContext providerContext, @Param("tablename") String tablename, @Param("idList") Collection<I> idList) throws RestException {
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(idList), "the id list param of 'findByAll' method cannot be empty!", message -> new MybatisParamErrorException("findByAll", "idList", message));
        return MybatisSqlScript.caching(providerContext, new MybatisSqlScript() {
            @Override
            public String sql(MybatisTable table) throws RestException {
                OptionalHelper.falseable(GeneralUtils.isNotEmpty(table.selectColumns()), "the select columns of table with 'findByAll' method cannot be empty!", message -> new MybatisTableErrorException("findByAll", "selectColumns", message));
                OptionalHelper.trueable(table.isUseUnionKey(), "the union keys of table with 'findByAll' method is unsupported!", message -> new MybatisUnsupportedErrorException("findByAll", "unionKeys", message));
                return "SELECT " + table.selectColumnList()
                        + " FROM " + Optional.ofNullable(tablename).orElse(table.tableName())
                        + " WHERE " + table.getIdentityColumn().getColumnName() + " IN " + foreach("idList", "id", ", ", "(", ")", () -> table.getIdentityColumn().variable());

            }
        });
    }

    public static String findAllByWhere(ProviderContext providerContext, @Param("whereSql") String whereSql) throws RestException {
        return findDynamicAllByWhere(providerContext, null, whereSql);
    }

    public static String findDynamicAllByWhere(ProviderContext providerContext, @Param("tablename") String tablename, @Param("whereSql") String whereSql) throws RestException {
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(whereSql), "the where sql param of 'findAllByWhere' method cannot be empty!", message -> new MybatisParamErrorException("findAllByWhere", "whereSql", message));
        return MybatisSqlScript.caching(providerContext, new MybatisSqlScript() {
            @Override
            public String sql(MybatisTable table) throws RestException {
                OptionalHelper.falseable(GeneralUtils.isNotEmpty(table.selectColumns()), "the select columns of table with 'findAllByWhere' method cannot be empty!", message -> new MybatisTableErrorException("findAllByWhere", "selectColumns", message));
                return "SELECT " + table.selectColumnList()
                        + " FROM " + Optional.ofNullable(tablename).orElse(table.tableName())
                        + " WHERE 1=1 "
                        + ifTest("whereSql!=null", () -> "${whereSql}");
            }
        });
    }

    public static String deleteAllByWhere(ProviderContext providerContext, @Param("whereSql") String whereSql) throws RestException {
        return deleteDynamicAllByWhere(providerContext, null, whereSql);
    }

    public static String deleteDynamicAllByWhere(ProviderContext providerContext, @Param("tablename") String tablename, @Param("whereSql") String whereSql) throws RestException {
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(whereSql), "the where sql param of 'deleteAllByWhere' method cannot be empty!", message -> new MybatisParamErrorException("deleteAllByWhere", "whereSql", message));
        return MybatisSqlScript.caching(providerContext, new MybatisSqlScript() {
            @Override
            public String sql(MybatisTable table) throws RestException {
                return "DELETE FROM " + Optional.ofNullable(tablename).orElse(table.tableName())
                        + " WHERE 1=1 "
                        + ifTest("whereSql!=null", () -> "${whereSql}");
            }
        });
    }


}
