package io.github.nichetoolkit.mybatis.provider;

import io.github.nichetoolkit.mybatis.MybatisColumn;
import io.github.nichetoolkit.mybatis.MybatisSqlScript;
import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.configure.MybatisMapperProperties;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rice.enums.DatabaseType;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * <p>MybatisProvider</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
@Component
public class MybatisProvider implements InitializingBean {

    @Autowired
    private MybatisMapperProperties mapperProperties;

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
        if (GeneralUtils.isNotEmpty(INSTANCE) && GeneralUtils.isNotEmpty(INSTANCE.mapperProperties)) {
            databaseType = INSTANCE.mapperProperties.getDatabaseType();
        }
        return databaseType;
    }

    public static String upset(MybatisTable entity) {
        DatabaseType databaseType = databaseType();
        String upsetSql;
        switch (databaseType) {
            case OPENGAUSS:
            case MYSQL:
                upsetSql = "ON DUPLICATE KEY UPDATE";
                break;
            case POSTGRESQL:
            default:
                upsetSql = "ON CONFLICT (" + entity.identityColumnList() + ") DO UPDATE SET";
                break;
        }
        return upsetSql;
    }

    public static String save(ProviderContext providerContext) {

        return MybatisSqlScript.caching(providerContext, entity -> {
            return "INSERT INTO " + entity.tableName()
                    + "(" + entity.insertColumnList() + ")"
                    + " VALUES (" + entity.insertColumns().stream()
                    .map(MybatisColumn::variable).collect(Collectors.joining(","))
                    + ")" + upset(entity) + entity.updateColumns().stream()
                    .map(MybatisColumn::excluded).collect(Collectors.joining(","));
        });
    }

    public static String saveAll(ProviderContext providerContext) {
//        return MybatisSqlScript.caching(providerContext, entity -> "INSERT INTO " + entity.tableName()
//                + "(" + entity.insertColumnList() + ")"
//                + " VALUES (" + entity.insertColumns().stream()
//                .map(MybatisColumn::variable).collect(Collectors.joining(",")) + ")");
        return null;
    }

    public static String deleteById(ProviderContext providerContext) {
//        return MybatisSqlScript.caching(providerContext, entity -> "DELETE FROM " + entity.tableName()
//                + " WHERE " + entity.identityColumns().stream().map(MybatisColumn::columnEqualsProperty).collect(Collectors.joining(" AND ")));
        return null;
    }

    public static String deleteByAll(ProviderContext providerContext) {
//        return MybatisSqlScript.caching(providerContext, entity -> "DELETE FROM " + entity.tableName()
//                + " WHERE " + entity.identityColumns().stream().map(MybatisColumn::columnEqualsProperty).collect(Collectors.joining(" AND ")));
        return null;
    }

    public static String findById(ProviderContext providerContext) {
//        return MybatisSqlScript.caching(providerContext, new MybatisSqlScript() {
//            @Override
//            public String sql(MybatisTable entity) {
//                return "SELECT " + entity.baseColumnAsPropertyList()
//                        + " FROM " + entity.tableName()
//                        + where(() -> entity.identityColumns().stream().map(MybatisColumn::columnEqualsProperty).collect(Collectors.joining(" AND ")));
//            }
//        });
        return null;
    }

    public static String findByAll(ProviderContext providerContext) {
//        return MybatisSqlScript.caching(providerContext, new MybatisSqlScript() {
//            @Override
//            public String sql(MybatisTable entity) {
//                return "SELECT " + entity.baseColumnAsPropertyList()
//                        + " FROM " + entity.tableName()
//                        + where(() -> entity.identityColumns().stream().map(MybatisColumn::columnEqualsProperty).collect(Collectors.joining(" AND ")));
//            }
//        });
        return null;
    }

    public static String findAllByWhere(ProviderContext providerContext) {
//        return MybatisSqlScript.caching(providerContext, new MybatisSqlScript() {
//            @Override
//            public String sql(MybatisTable entity) {
//                return "SELECT " + entity.baseColumnAsPropertyList()
//                        + " FROM " + entity.tableName()
//                        + where(() -> entity.identityColumns().stream().map(MybatisColumn::columnEqualsProperty).collect(Collectors.joining(" AND ")));
//            }
//        });
        return null;
    }

    public static String deleteAllByWhere(ProviderContext providerContext) {
//        return MybatisSqlScript.caching(providerContext, new MybatisSqlScript() {
//            @Override
//            public String sql(MybatisTable entity) {
//                return "SELECT " + entity.baseColumnAsPropertyList()
//                        + " FROM " + entity.tableName()
//                        + where(() -> entity.identityColumns().stream().map(MybatisColumn::columnEqualsProperty).collect(Collectors.joining(" AND ")));
//            }
//        });
        return null;
    }

}
