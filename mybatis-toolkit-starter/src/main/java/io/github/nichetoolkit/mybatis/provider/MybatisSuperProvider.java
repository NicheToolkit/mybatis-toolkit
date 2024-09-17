package io.github.nichetoolkit.mybatis.provider;

import io.github.nichetoolkit.mybatis.MybatisColumn;
import io.github.nichetoolkit.mybatis.MybatisSqlScript;
import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.configure.MybatisTableProperties;
import io.github.nichetoolkit.mybatis.enums.DatabaseType;
import io.github.nichetoolkit.mybatis.error.MybatisParamErrorException;
import io.github.nichetoolkit.mybatis.error.MybatisTableErrorException;
import io.github.nichetoolkit.mybatis.error.MybatisUnrealizedLackError;
import io.github.nichetoolkit.mybatis.error.MybatisUnsupportedErrorException;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rest.util.OptionalUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <code>MybatisSuperProvider</code>
 * <p>The type mybatis super provider class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see org.springframework.beans.factory.InitializingBean
 * @since Jdk1.8
 */
public class MybatisSuperProvider implements InitializingBean {

    /**
     * <code>tableProperties</code>
     * {@link io.github.nichetoolkit.mybatis.configure.MybatisTableProperties} <p>the <code>tableProperties</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.configure.MybatisTableProperties
     */
    private final MybatisTableProperties tableProperties;

    /**
     * <code>MybatisSuperProvider</code>
     * Instantiates a new mybatis super provider.
     * @param tableProperties {@link io.github.nichetoolkit.mybatis.configure.MybatisTableProperties} <p>the table properties parameter is <code>MybatisTableProperties</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.configure.MybatisTableProperties
     * @see org.springframework.beans.factory.annotation.Autowired
     */
    @Autowired
    public MybatisSuperProvider(MybatisTableProperties tableProperties) {
        this.tableProperties = tableProperties;
    }

    /**
     * <code>INSTANCE</code>
     * {@link io.github.nichetoolkit.mybatis.provider.MybatisSuperProvider} <p>the constant <code>INSTANCE</code> field.</p>
     */
    private static MybatisSuperProvider INSTANCE = null;

    /**
     * <code>instance</code>
     * <p>the method.</p>
     * @return {@link io.github.nichetoolkit.mybatis.provider.MybatisSuperProvider} <p>the return object is <code>MybatisSuperProvider</code> type.</p>
     */
    public static MybatisSuperProvider instance() {
        return INSTANCE;
    }

    @Override
    public void afterPropertiesSet() {
        INSTANCE = this;
    }

    /**
     * <code>databaseType</code>
     * <p>the type method.</p>
     * @return {@link io.github.nichetoolkit.mybatis.enums.DatabaseType} <p>the type return object is <code>DatabaseType</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.enums.DatabaseType
     */
    public static DatabaseType databaseType() {
        DatabaseType databaseType = DatabaseType.POSTGRESQL;
        if (GeneralUtils.isNotEmpty(INSTANCE) && GeneralUtils.isNotEmpty(INSTANCE.tableProperties)) {
            databaseType = INSTANCE.tableProperties.getDatabaseType();
        }
        return databaseType;
    }

    /**
     * <code>saveUpsetSql</code>
     * <p>the upset sql method.</p>
     * @param tablename {@link java.lang.String} <p>the tablename parameter is <code>String</code> type.</p>
     * @param table     {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>the table parameter is <code>MybatisTable</code> type.</p>
     * @return {@link java.lang.String} <p>the upset sql return object is <code>String</code> type.</p>
     * @see java.lang.String
     * @see org.springframework.lang.Nullable
     * @see org.apache.ibatis.annotations.Param
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     */
    public static String saveUpsetSql(@Nullable  @Param("tablename") String tablename, MybatisTable table) {
        DatabaseType databaseType = databaseType();
        List<MybatisColumn> updateColumns = table.updateColumns();
        boolean doNothing = false;
        String doUpdateSql = "";
        if (GeneralUtils.isEmpty(updateColumns)) {
            doNothing = true;
        } else {
            String dynamicTablename = Optional.ofNullable(tablename).orElse(table.tablename());
            doUpdateSql = table.updateColumns().stream().map(column -> column.excluded(dynamicTablename)).collect(Collectors.joining(", "));
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

    /**
     * <code>save</code>
     * <p>the method.</p>
     * @param <E>             {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param entity          E <p>the entity parameter is <code>E</code> type.</p>
     * @return {@link java.lang.String} <p>the return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see org.apache.ibatis.annotations.Param
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <E> String save(ProviderContext providerContext, @Param("entity") E entity) throws RestException {
        return saveDynamic(providerContext, null, entity);
    }

    /**
     * <code>saveDynamic</code>
     * <p>the dynamic method.</p>
     * @param <E>             {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>the tablename parameter is <code>String</code> type.</p>
     * @param entity          E <p>the entity parameter is <code>E</code> type.</p>
     * @return {@link java.lang.String} <p>the dynamic return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.apache.ibatis.annotations.Param
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <E> String saveDynamic(ProviderContext providerContext, @Param("tablename") String tablename, @Param("entity") E entity) throws RestException {
        OptionalUtils.falseable(GeneralUtils.isNotEmpty(entity), "the entity param of 'save' method cannot be empty!", message -> new MybatisParamErrorException("save", "entity", message));
        return MybatisSqlScript.caching(providerContext, table -> {
            OptionalUtils.falseable(GeneralUtils.isNotEmpty(table.insertColumns()), "the insert columns of table with 'save' method cannot be empty!", message -> new MybatisTableErrorException("save", "insertColumns", message));
            OptionalUtils.falseable(GeneralUtils.isNotEmpty(table.identityColumns()), "the identity columns of table with 'save' method cannot be empty!", message -> new MybatisTableErrorException("save", "identityColumns", message));
            return "INSERT INTO " + Optional.ofNullable(tablename).orElse(table.tablename())
                    + " (" + table.insertColumnList() + ")"
                    + " VALUES (" + table.insertColumns().stream()
                    .map(column -> column.variable("entity.")).collect(Collectors.joining(", "))
                    + ")" + saveUpsetSql(tablename,table);
        });
    }

    /**
     * <code>saveAll</code>
     * <p>the all method.</p>
     * @param <E>             {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param entityList      {@link java.util.Collection} <p>the entity list parameter is <code>Collection</code> type.</p>
     * @return {@link java.lang.String} <p>the all return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.util.Collection
     * @see org.apache.ibatis.annotations.Param
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <E> String saveAll(ProviderContext providerContext, @Param("entityList") Collection<E> entityList) throws RestException {
        return saveDynamicAll(providerContext, null, entityList);
    }

    /**
     * <code>saveDynamicAll</code>
     * <p>the dynamic all method.</p>
     * @param <E>             {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>the tablename parameter is <code>String</code> type.</p>
     * @param entityList      {@link java.util.Collection} <p>the entity list parameter is <code>Collection</code> type.</p>
     * @return {@link java.lang.String} <p>the dynamic all return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.apache.ibatis.annotations.Param
     * @see java.util.Collection
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <E> String saveDynamicAll(ProviderContext providerContext, @Param("tablename") String tablename, @Param("entityList") Collection<E> entityList) throws RestException {
        OptionalUtils.falseable(GeneralUtils.isNotEmpty(entityList), "the entity list param of 'saveAll' method cannot be empty!", message -> new MybatisParamErrorException("saveAll", "entityList", message));
        return MybatisSqlScript.caching(providerContext, new MybatisSqlScript() {
            @Override
            public String sql(MybatisTable table) throws RestException {
                OptionalUtils.falseable(GeneralUtils.isNotEmpty(table.insertColumns()), "the insert columns of table with 'saveAll' method cannot be empty!", message -> new MybatisTableErrorException("saveAll", "insertColumns", message));
                OptionalUtils.falseable(GeneralUtils.isNotEmpty(table.identityColumns()), "the identity columns of table with 'saveAll' method cannot be empty!", message -> new MybatisTableErrorException("saveAll", "identityColumns", message));
                return "INSERT INTO " + Optional.ofNullable(tablename).orElse(table.tablename())
                        + " (" + table.insertColumnList() + ") VALUES "
                        + foreach("entityList", "entity", ", ", () ->
                        " (" + table.insertColumns().stream().map(column -> column.variable("entity.")).collect(Collectors.joining(", ")) + " )")
                        + saveUpsetSql(tablename,table);
            }
        });
    }

    /**
     * <code>deleteById</code>
     * <p>the by id method.</p>
     * @param <I>             {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param id              I <p>the id parameter is <code>I</code> type.</p>
     * @return {@link java.lang.String} <p>the by id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see org.apache.ibatis.annotations.Param
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String deleteById(ProviderContext providerContext, @Param("id") I id) throws RestException {
        return deleteDynamicById(providerContext, null, id);
    }

    /**
     * <code>deleteDynamicById</code>
     * <p>the dynamic by id method.</p>
     * @param <I>             {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>the tablename parameter is <code>String</code> type.</p>
     * @param id              I <p>the id parameter is <code>I</code> type.</p>
     * @return {@link java.lang.String} <p>the dynamic by id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.apache.ibatis.annotations.Param
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String deleteDynamicById(ProviderContext providerContext, @Param("tablename") String tablename, @Param("id") I id) throws RestException {
        OptionalUtils.falseable(GeneralUtils.isNotEmpty(id), "the id param of 'deleteById' method cannot be empty!", message -> new MybatisParamErrorException("deleteById", "id", message));
        return MybatisSqlScript.caching(providerContext, table -> {
            OptionalUtils.trueable(table.isUseUnionKey(), "the union keys of table with 'deleteById' method is unsupported!", message -> new MybatisUnsupportedErrorException("deleteById", "unionKeys", message));
            return "DELETE FROM " + Optional.ofNullable(tablename).orElse(table.tablename())
                    + " WHERE " + table.getIdentityColumn().columnEqualsProperty();
        });
    }

    /**
     * <code>deleteByAll</code>
     * <p>the by all method.</p>
     * @param <I>             {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param idList          {@link java.util.Collection} <p>the id list parameter is <code>Collection</code> type.</p>
     * @return {@link java.lang.String} <p>the by all return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.util.Collection
     * @see org.apache.ibatis.annotations.Param
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String deleteByAll(ProviderContext providerContext, @Param("idList") Collection<I> idList) throws RestException {
        return deleteDynamicByAll(providerContext, null, idList);
    }

    /**
     * <code>deleteDynamicByAll</code>
     * <p>the dynamic by all method.</p>
     * @param <I>             {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>the tablename parameter is <code>String</code> type.</p>
     * @param idList          {@link java.util.Collection} <p>the id list parameter is <code>Collection</code> type.</p>
     * @return {@link java.lang.String} <p>the dynamic by all return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.apache.ibatis.annotations.Param
     * @see java.util.Collection
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String deleteDynamicByAll(ProviderContext providerContext, @Param("tablename") String tablename, @Param("idList") Collection<I> idList) throws RestException {
        OptionalUtils.falseable(GeneralUtils.isNotEmpty(idList), "the id list param of 'deleteByAll' method cannot be empty!", message -> new MybatisParamErrorException("deleteByAll", "idList", message));
        return MybatisSqlScript.caching(providerContext, new MybatisSqlScript() {
            @Override
            public String sql(MybatisTable table) throws RestException {
                OptionalUtils.trueable(table.isUseUnionKey(), "the union keys of table with 'deleteByAll' method is unsupported!", message -> new MybatisUnsupportedErrorException("deleteByAll", "unionKeys", message));
                return "DELETE FROM " + Optional.ofNullable(tablename).orElse(table.tablename())
                        + " WHERE " + table.getIdentityColumn().getColumnName() + " IN " + foreach("idList", "id", ", ", "(", ")", () -> table.getIdentityColumn().variable());
            }
        });
    }

    /**
     * <code>findById</code>
     * <p>the by id method.</p>
     * @param <I>             {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param id              I <p>the id parameter is <code>I</code> type.</p>
     * @return {@link java.lang.String} <p>the by id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see org.apache.ibatis.annotations.Param
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String findById(ProviderContext providerContext, @Param("id") I id) throws RestException {
        return findDynamicById(providerContext, null, id);
    }

    /**
     * <code>findDynamicById</code>
     * <p>the dynamic by id method.</p>
     * @param <I>             {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>the tablename parameter is <code>String</code> type.</p>
     * @param id              I <p>the id parameter is <code>I</code> type.</p>
     * @return {@link java.lang.String} <p>the dynamic by id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.apache.ibatis.annotations.Param
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String findDynamicById(ProviderContext providerContext, @Param("tablename") String tablename, @Param("id") I id) throws RestException {
        OptionalUtils.falseable(GeneralUtils.isNotEmpty(id), "the id param of 'findById' method cannot be empty!", message -> new MybatisParamErrorException("findById", "id", message));
        return MybatisSqlScript.caching(providerContext, table -> {
            OptionalUtils.falseable(GeneralUtils.isNotEmpty(table.selectColumns()), "the select columns of table with 'findById' method cannot be empty!", message -> new MybatisTableErrorException("findById", "selectColumns", message));
            OptionalUtils.trueable(table.isUseUnionKey(), "the union keys of table with 'findById' method is unsupported!", message -> new MybatisUnsupportedErrorException("findById", "unionKeys", message));
            return "SELECT " + table.selectColumnList()
                    + " FROM " + Optional.ofNullable(tablename).orElse(table.tablename())
                    + " WHERE " + table.getIdentityColumn().columnEqualsProperty();
        });

    }

    /**
     * <code>findByAll</code>
     * <p>the by all method.</p>
     * @param <I>             {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param idList          {@link java.util.Collection} <p>the id list parameter is <code>Collection</code> type.</p>
     * @return {@link java.lang.String} <p>the by all return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.util.Collection
     * @see org.apache.ibatis.annotations.Param
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String findByAll(ProviderContext providerContext, @Param("idList") Collection<I> idList) throws RestException {
        return findDynamicByAll(providerContext, null, idList);
    }

    /**
     * <code>findDynamicByAll</code>
     * <p>the dynamic by all method.</p>
     * @param <I>             {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>the tablename parameter is <code>String</code> type.</p>
     * @param idList          {@link java.util.Collection} <p>the id list parameter is <code>Collection</code> type.</p>
     * @return {@link java.lang.String} <p>the dynamic by all return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.apache.ibatis.annotations.Param
     * @see java.util.Collection
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String findDynamicByAll(ProviderContext providerContext, @Param("tablename") String tablename, @Param("idList") Collection<I> idList) throws RestException {
        OptionalUtils.falseable(GeneralUtils.isNotEmpty(idList), "the id list param of 'findByAll' method cannot be empty!", message -> new MybatisParamErrorException("findByAll", "idList", message));
        return MybatisSqlScript.caching(providerContext, new MybatisSqlScript() {
            @Override
            public String sql(MybatisTable table) throws RestException {
                OptionalUtils.falseable(GeneralUtils.isNotEmpty(table.selectColumns()), "the select columns of table with 'findByAll' method cannot be empty!", message -> new MybatisTableErrorException("findByAll", "selectColumns", message));
                OptionalUtils.trueable(table.isUseUnionKey(), "the union keys of table with 'findByAll' method is unsupported!", message -> new MybatisUnsupportedErrorException("findByAll", "unionKeys", message));
                return "SELECT " + table.selectColumnList()
                        + " FROM " + Optional.ofNullable(tablename).orElse(table.tablename())
                        + " WHERE " + table.getIdentityColumn().getColumnName() + " IN " + foreach("idList", "id", ", ", "(", ")", () -> table.getIdentityColumn().variable());

            }
        });
    }

    /**
     * <code>findAllByWhere</code>
     * <p>the all by where method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param whereSql        {@link java.lang.String} <p>the where sql parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>the all by where return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.apache.ibatis.annotations.Param
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static String findAllByWhere(ProviderContext providerContext, @Param("whereSql") String whereSql) throws RestException {
        return findDynamicAllByWhere(providerContext, null, whereSql);
    }

    /**
     * <code>findDynamicAllByWhere</code>
     * <p>the dynamic all by where method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>the tablename parameter is <code>String</code> type.</p>
     * @param whereSql        {@link java.lang.String} <p>the where sql parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>the dynamic all by where return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.apache.ibatis.annotations.Param
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static String findDynamicAllByWhere(ProviderContext providerContext, @Param("tablename") String tablename, @Param("whereSql") String whereSql) throws RestException {
        OptionalUtils.falseable(GeneralUtils.isNotEmpty(whereSql), "the where sql param of 'findAllByWhere' method cannot be empty!", message -> new MybatisParamErrorException("findAllByWhere", "whereSql", message));
        return MybatisSqlScript.caching(providerContext, new MybatisSqlScript() {
            @Override
            public String sql(MybatisTable table) throws RestException {
                OptionalUtils.falseable(GeneralUtils.isNotEmpty(table.selectColumns()), "the select columns of table with 'findAllByWhere' method cannot be empty!", message -> new MybatisTableErrorException("findAllByWhere", "selectColumns", message));
                return "SELECT " + table.selectColumnList()
                        + " FROM " + Optional.ofNullable(tablename).orElse(table.tablename())
                        + " WHERE 1=1 "
                        + ifTest("whereSql!=null", () -> "${whereSql}");
            }
        });
    }

    /**
     * <code>deleteAllByWhere</code>
     * <p>the all by where method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param whereSql        {@link java.lang.String} <p>the where sql parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>the all by where return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.apache.ibatis.annotations.Param
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static String deleteAllByWhere(ProviderContext providerContext, @Param("whereSql") String whereSql) throws RestException {
        return deleteDynamicAllByWhere(providerContext, null, whereSql);
    }

    /**
     * <code>deleteDynamicAllByWhere</code>
     * <p>the dynamic all by where method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>the tablename parameter is <code>String</code> type.</p>
     * @param whereSql        {@link java.lang.String} <p>the where sql parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>the dynamic all by where return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.apache.ibatis.annotations.Param
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static String deleteDynamicAllByWhere(ProviderContext providerContext, @Param("tablename") String tablename, @Param("whereSql") String whereSql) throws RestException {
        OptionalUtils.falseable(GeneralUtils.isNotEmpty(whereSql), "the where sql param of 'deleteAllByWhere' method cannot be empty!", message -> new MybatisParamErrorException("deleteAllByWhere", "whereSql", message));
        return MybatisSqlScript.caching(providerContext, new MybatisSqlScript() {
            @Override
            public String sql(MybatisTable table) throws RestException {
                return "DELETE FROM " + Optional.ofNullable(tablename).orElse(table.tablename())
                        + " WHERE 1=1 "
                        + ifTest("whereSql!=null", () -> "${whereSql}");
            }
        });
    }


}
