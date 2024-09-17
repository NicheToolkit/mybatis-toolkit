package io.github.nichetoolkit.mybatis.provider;

import io.github.nichetoolkit.mybatis.MybatisColumn;
import io.github.nichetoolkit.mybatis.MybatisSqlScript;
import io.github.nichetoolkit.mybatis.error.MybatisParamErrorException;
import io.github.nichetoolkit.mybatis.error.MybatisTableErrorException;
import io.github.nichetoolkit.mybatis.error.MybatisUnsupportedErrorException;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rest.util.OptionalUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderContext;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <code>MybatisInfoProvider</code>
 * <p>The type mybatis info provider class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
public class MybatisInfoProvider {

    /**
     * <code>findByName</code>
     * <p>the by name method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param name            {@link java.lang.String} <p>the name parameter is <code>String</code> type.</p>
     * @param sign            {@link java.lang.String} <p>the sign parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>the by name return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.apache.ibatis.annotations.Param
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static String findByName(ProviderContext providerContext, @Param("name") String name, @Param("sign") String sign) throws RestException {
        return findDynamicByName(providerContext, null, name, sign);
    }

    /**
     * <code>findDynamicByName</code>
     * <p>the dynamic by name method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>the tablename parameter is <code>String</code> type.</p>
     * @param name            {@link java.lang.String} <p>the name parameter is <code>String</code> type.</p>
     * @param sign            {@link java.lang.String} <p>the sign parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>the dynamic by name return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.apache.ibatis.annotations.Param
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static String findDynamicByName(ProviderContext providerContext, @Param("tablename") String tablename, @Param("name") String name, @Param("sign") String sign) throws RestException {
        OptionalUtils.falseable(GeneralUtils.isNotEmpty(name), "the name param of 'findByName' method cannot be empty!", message -> new MybatisParamErrorException("findByName", "name", message));
        return MybatisSqlScript.caching(providerContext, table -> {
            OptionalUtils.falseable(GeneralUtils.isNotEmpty(table.selectColumns()), "the select columns of table with 'findAllByWhere' method cannot be empty!", message -> new MybatisTableErrorException("findByName", "selectColumns", message));
            return "SELECT " + table.selectColumnList()
                    + " FROM " + Optional.ofNullable(tablename).orElse(table.tablename())
                    + " WHERE " + Optional.ofNullable(table.fieldColumn("name"))
                    .map(MybatisColumn::columnEqualsProperty).orElse("name = #{name}")
                    + Optional.ofNullable(table.getLogicColumn()).map(logic -> "AND " + logic.columnEqualsSign()).orElse("");
        });

    }

    /**
     * <code>findByNameAndNotId</code>
     * <p>the by name and not id method.</p>
     * @param <I>             {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param name            {@link java.lang.String} <p>the name parameter is <code>String</code> type.</p>
     * @param id              I <p>the id parameter is <code>I</code> type.</p>
     * @param sign            {@link java.lang.String} <p>the sign parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>the by name and not id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.apache.ibatis.annotations.Param
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String findByNameAndNotId(ProviderContext providerContext, @Param("name") String name, @Param("id") I id, @Param("sign") String sign) throws RestException {
        return findDynamicByNameAndNotId(providerContext, null, name, id, sign);
    }

    /**
     * <code>findDynamicByNameAndNotId</code>
     * <p>the dynamic by name and not id method.</p>
     * @param <I>             {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>the tablename parameter is <code>String</code> type.</p>
     * @param name            {@link java.lang.String} <p>the name parameter is <code>String</code> type.</p>
     * @param id              I <p>the id parameter is <code>I</code> type.</p>
     * @param sign            {@link java.lang.String} <p>the sign parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>the dynamic by name and not id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.apache.ibatis.annotations.Param
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String findDynamicByNameAndNotId(ProviderContext providerContext, @Param("tablename") String tablename, @Param("name") String name, @Param("id") I id, @Param("sign") String sign) throws RestException {
        OptionalUtils.falseable(GeneralUtils.isNotEmpty(id) && GeneralUtils.isNotEmpty(name), "the id and name params of 'findByNameAndNotId' method cannot be empty!", message -> new MybatisParamErrorException("findByNameAndNotId", "id and name", message));
        return MybatisSqlScript.caching(providerContext, table -> {
            OptionalUtils.falseable(GeneralUtils.isNotEmpty(table.selectColumns()), "the select columns of table with 'findByNameAndNotId' method cannot be empty!", message -> new MybatisTableErrorException("findByNameAndNotId", "selectColumns", message));
            OptionalUtils.trueable(table.isUseUnionKey(), "the union keys of table with 'findByNameAndNotId' method is unsupported!", message -> new MybatisUnsupportedErrorException("findByNameAndNotId", "unionKeys", message));
            return "SELECT " + table.selectColumnList()
                    + " FROM " + Optional.ofNullable(tablename).orElse(table.tablename())
                    + " WHERE " + Optional.ofNullable(table.fieldColumn("name"))
                    .map(MybatisColumn::columnEqualsProperty).orElse("name = #{name}")
                    + " AND " + table.getIdentityColumn().columnNotEqualsProperty()
                    + Optional.ofNullable(table.getLogicColumn()).map(logic -> "AND " + logic.columnEqualsSign()).orElse("");
        });
    }


    /**
     * <code>findByEntity</code>
     * <p>the by entity method.</p>
     * @param <E>             {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param entity          E <p>the entity parameter is <code>E</code> type.</p>
     * @param sign            {@link java.lang.String} <p>the sign parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>the by entity return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see org.apache.ibatis.annotations.Param
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <E> String findByEntity(ProviderContext providerContext, @Param("entity") E entity, @Param("sign") String sign) throws RestException {
        return findDynamicByEntity(providerContext, null, entity, sign);
    }

    /**
     * <code>findDynamicByEntity</code>
     * <p>the dynamic by entity method.</p>
     * @param <E>             {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>the tablename parameter is <code>String</code> type.</p>
     * @param entity          E <p>the entity parameter is <code>E</code> type.</p>
     * @param sign            {@link java.lang.String} <p>the sign parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>the dynamic by entity return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.apache.ibatis.annotations.Param
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <E> String findDynamicByEntity(ProviderContext providerContext, @Param("tablename") String tablename, @Param("entity") E entity, @Param("sign") String sign) throws RestException {
        OptionalUtils.falseable(GeneralUtils.isNotEmpty(entity), "the entity param of 'findByEntity' method cannot be empty!", message -> new MybatisParamErrorException("findByEntity", "entity", message));
        return MybatisSqlScript.caching(providerContext, table -> {
            OptionalUtils.falseable(GeneralUtils.isNotEmpty(table.uniqueColumns()), "the unique columns of table with 'findByEntity' method cannot be empty!", message -> new MybatisTableErrorException("findByEntity", "uniqueColumns", message));
            OptionalUtils.falseable(GeneralUtils.isNotEmpty(table.selectColumns()), "the select columns of table with 'findByEntity' method cannot be empty!", message -> new MybatisTableErrorException("findByEntity", "selectColumns", message));
            return "SELECT " + table.selectColumnList()
                  + " FROM " + Optional.ofNullable(tablename).orElse(table.tablename())
                  + " WHERE (" + table.uniqueColumns().stream()
                  .map(column -> column.columnEqualsProperty("entity."))
                  .collect(Collectors.joining(" OR ")) + ") "
                  + Optional.ofNullable(table.getLogicColumn()).map(logic -> "AND " + logic.columnEqualsSign()).orElse("");
        });

    }

    /**
     * <code>findByEntityAndNotId</code>
     * <p>the by entity and not id method.</p>
     * @param <I>             {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param <E>             {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param entity          E <p>the entity parameter is <code>E</code> type.</p>
     * @param id              I <p>the id parameter is <code>I</code> type.</p>
     * @param sign            {@link java.lang.String} <p>the sign parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>the by entity and not id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see org.apache.ibatis.annotations.Param
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I, E> String findByEntityAndNotId(ProviderContext providerContext, @Param("entity") E entity, @Param("id") I id, @Param("sign") String sign) throws RestException {
        return findDynamicByEntityAndNotId(providerContext, null, entity, id, sign);
    }

    /**
     * <code>findDynamicByEntityAndNotId</code>
     * <p>the dynamic by entity and not id method.</p>
     * @param <I>             {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param <E>             {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>the tablename parameter is <code>String</code> type.</p>
     * @param entity          E <p>the entity parameter is <code>E</code> type.</p>
     * @param id              I <p>the id parameter is <code>I</code> type.</p>
     * @param sign            {@link java.lang.String} <p>the sign parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>the dynamic by entity and not id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.apache.ibatis.annotations.Param
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I, E> String findDynamicByEntityAndNotId(ProviderContext providerContext, @Param("tablename") String tablename, @Param("entity") E entity, @Param("id") I id, @Param("sign") String sign) throws RestException {
        OptionalUtils.falseable(GeneralUtils.isNotEmpty(id) && GeneralUtils.isNotEmpty(entity), "the id and entity params of 'findByEntityAndNotId' method cannot be empty!", message -> new MybatisParamErrorException("findByEntityAndNotId", "id and entity", message));
        return MybatisSqlScript.caching(providerContext, table -> {
            OptionalUtils.falseable(GeneralUtils.isNotEmpty(table.uniqueColumns()), "the unique columns of table with 'findByEntityAndNotId' method cannot be empty!", message -> new MybatisTableErrorException("findByEntityAndNotId", "uniqueColumns", message));
            OptionalUtils.falseable(GeneralUtils.isNotEmpty(table.selectColumns()), "the select columns of table with 'findByEntityAndNotId' method cannot be empty!", message -> new MybatisTableErrorException("findByEntityAndNotId", "selectColumns", message));
            OptionalUtils.trueable(table.isUseUnionKey(), "the union keys of table with 'findByEntityAndNotId' method is unsupported!", message -> new MybatisUnsupportedErrorException("findByEntityAndNotId", "unionKeys", message));
            return "SELECT " + table.selectColumnList()
                   + " FROM " + Optional.ofNullable(tablename).orElse(table.tablename())
                   + " WHERE (" + table.uniqueColumns().stream()
                   .map(column -> column.columnEqualsProperty("entity."))
                   .collect(Collectors.joining(" OR ")) + ") "
                   + " AND " + table.getIdentityColumn().columnNotEqualsProperty()
                   + Optional.ofNullable(table.getLogicColumn()).map(logic -> "AND " + logic.columnEqualsSign()).orElse("");
        });
    }

}
