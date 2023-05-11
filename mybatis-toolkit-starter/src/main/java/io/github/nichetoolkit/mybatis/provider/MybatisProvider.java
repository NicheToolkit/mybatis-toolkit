package io.github.nichetoolkit.mybatis.provider;

import io.github.nichetoolkit.mybatis.MybatisColumn;
import io.github.nichetoolkit.mybatis.MybatisSqlScript;
import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.configure.MybatisTableProperties;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.error.natives.ParamErrorException;
import io.github.nichetoolkit.rest.helper.OptionalHelper;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rice.enums.DatabaseType;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>MybatisProvider</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public class MybatisProvider implements InitializingBean {

    private MybatisTableProperties tableProperties;

    @Autowired
    public MybatisProvider(MybatisTableProperties tableProperties) {
        this.tableProperties = tableProperties;
    }

    private static MybatisProvider INSTANCE = null;

    public static MybatisProvider instance() {
        return INSTANCE;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        INSTANCE = this;
    }

    public static DatabaseType databaseType() {
        DatabaseType databaseType = DatabaseType.POSTGRESQL;
        if (GeneralUtils.isNotEmpty(INSTANCE) && GeneralUtils.isNotEmpty(INSTANCE.tableProperties)) {
            databaseType = INSTANCE.tableProperties.getDatabaseType();
        }
        return databaseType;
    }

    public static String upset(MybatisTable table) {
        DatabaseType databaseType = databaseType();
        String upsetSql;
        switch (databaseType) {
            case OPENGAUSS:
            case MYSQL:
                upsetSql = " ON DUPLICATE KEY UPDATE ";
                break;
            case POSTGRESQL:
            default:
                upsetSql = " ON CONFLICT (" + table.identityColumnList() + ") DO UPDATE SET ";
                break;
        }
        return upsetSql;
    }

    public static String save(ProviderContext providerContext, @Param("entity") Object entity) throws RestException {
        return saveDynamic(providerContext, null, entity);
    }

    public static String saveDynamic(ProviderContext providerContext, @Param("tablename") String tablename, @Param("entity") Object entity) throws RestException {
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(entity), "entity cannot be empty!", ParamErrorException::new);
        return MybatisSqlScript.caching(providerContext, table -> "INSERT INTO " + Optional.ofNullable(tablename).orElse(table.tableName())
                + " (" + table.insertColumnList() + ")"
                + " VALUES (" + table.insertColumns().stream()
                .map(column -> column.variable("entity.")).collect(Collectors.joining(", "))
                + ")" + upset(table) + table.updateColumns().stream()
                .map(MybatisColumn::excluded).collect(Collectors.joining(", ")));
    }

    public static String saveAll(ProviderContext providerContext, @Param("entityList") Collection<?> entityList) throws RestException {
        return saveDynamicAll(providerContext, null, entityList);
    }

    public static String saveDynamicAll(ProviderContext providerContext, @Param("tablename") String tablename, @Param("entityList") Collection<?> entityList) throws RestException {
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(entityList), "entity list cannot be empty!", ParamErrorException::new);
        return MybatisSqlScript.caching(providerContext, new MybatisSqlScript() {
            @Override
            public String sql(MybatisTable table) {
                return "INSERT INTO " + Optional.ofNullable(tablename).orElse(table.tableName())
                        + " (" + table.insertColumnList() + ") VALUES "
                        + foreach("entityList", "entity", ", ", () ->
                        " (" +  table.insertColumns().stream().map(column -> column.variable("entity.")).collect(Collectors.joining(", "))) + " )"
                        + upset(table) + table.updateColumns().stream().map(MybatisColumn::excluded).collect(Collectors.joining(", "));
            }
        });
    }

    public static String deleteById(ProviderContext providerContext, @Param("id") Object id) throws RestException {
        return deleteDynamicById(providerContext, null, id);
    }

    public static String deleteDynamicById(ProviderContext providerContext, @Param("tablename") String tablename, @Param("id") Object id) throws RestException {
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(id), "id cannot be empty!", ParamErrorException::new);
        return MybatisSqlScript.caching(providerContext, table -> "DELETE FROM " + Optional.ofNullable(tablename).orElse(table.tableName())
                + " WHERE " + table.getIdentity().columnEqualsProperty());
    }

    public static String deleteByAll(ProviderContext providerContext, @Param("idList") Collection<?> idList) throws RestException {
        return deleteDynamicByAll(providerContext, null, idList);
    }

    public static String deleteDynamicByAll(ProviderContext providerContext, @Param("tablename") String tablename, @Param("idList") Collection<?> idList) throws RestException {
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(idList), "id list cannot be empty!", ParamErrorException::new);
        return MybatisSqlScript.caching(providerContext, new MybatisSqlScript() {
            @Override
            public String sql(MybatisTable table) {
                return "DELETE FROM " + Optional.ofNullable(tablename).orElse(table.tableName())
                        + " WHERE " + table.getIdentity().getColumnName() + " IN " + foreach("idList", "id", ", ", "(", ")", () -> table.getIdentity().variable());
            }
        });
    }

    public static String findById(ProviderContext providerContext, @Param("id") Object id) throws RestException {
        return findDynamicById(providerContext, null, id);
    }

    public static String findDynamicById(ProviderContext providerContext, @Param("tablename") String tablename, @Param("id") Object id) throws RestException {
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(id), "id cannot be empty!", ParamErrorException::new);
        return MybatisSqlScript.caching(providerContext, table -> "SELECT " + table.selectColumnList()
                + " FROM " + Optional.ofNullable(tablename).orElse(table.tableName())
                + " WHERE " + table.getIdentity().columnEqualsProperty());
    }

    public static String findByAll(ProviderContext providerContext, @Param("idList") Collection<?> idList) throws RestException {
        return findDynamicByAll(providerContext, null, idList);
    }

    public static String findDynamicByAll(ProviderContext providerContext, @Param("tablename") String tablename, @Param("idList") Collection<?> idList) throws RestException {
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(idList), "id list cannot be empty!", ParamErrorException::new);
        return MybatisSqlScript.caching(providerContext, new MybatisSqlScript() {
            @Override
            public String sql(MybatisTable table) {
                return "SELECT " + table.selectColumnList()
                        + " FROM " + Optional.ofNullable(tablename).orElse(table.tableName())
                        + " WHERE " + table.getIdentity().getColumnName() + " IN " + foreach("idList", "id", ", ","(", ")", () -> table.getIdentity().variable());

            }
        });
    }

    public static String findAllByWhere(ProviderContext providerContext, @Param("whereSql") String whereSql) throws RestException {
        return findDynamicAllByWhere(providerContext, null, whereSql);
    }

    public static String findDynamicAllByWhere(ProviderContext providerContext, @Param("tablename") String tablename, @Param("whereSql") String whereSql) throws RestException {
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(whereSql), "where sql cannot be empty!", ParamErrorException::new);
        return MybatisSqlScript.caching(providerContext, new MybatisSqlScript() {
            @Override
            public String sql(MybatisTable table) {
                return "SELECT " + table.selectColumnList()
                        + " FROM " + Optional.ofNullable(tablename).orElse(table.tableName())
                        + ifTest("whereSql!=null", () -> "${whereSql}");
            }
        });
    }

    public static String deleteAllByWhere(ProviderContext providerContext, @Param("whereSql") String whereSql) throws RestException {
        return deleteDynamicAllByWhere(providerContext, null, whereSql);
    }

    public static String deleteDynamicAllByWhere(ProviderContext providerContext,@Param("tablename") String tablename, @Param("whereSql") String whereSql) throws RestException {
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(whereSql), "where sql cannot be empty!", ParamErrorException::new);
        return MybatisSqlScript.caching(providerContext, new MybatisSqlScript() {
            @Override
            public String sql(MybatisTable table) {
                return "DELETE FROM" + Optional.ofNullable(tablename).orElse(table.tableName())
                        + ifTest("whereSql!=null", () -> "${whereSql}");
            }
        });
    }


}
