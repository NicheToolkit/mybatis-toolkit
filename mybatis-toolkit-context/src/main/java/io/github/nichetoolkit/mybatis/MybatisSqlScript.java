package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.rice.consts.EntityConstants;
import io.github.nichetoolkit.rice.consts.SQLConstants;
import io.github.nichetoolkit.rice.consts.ScriptConstants;
import io.github.nichetoolkit.mybatis.error.MybatisAssertErrorException;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.actuator.SupplierActuator;
import io.github.nichetoolkit.rest.util.OptionalUtils;
import org.apache.ibatis.builder.annotation.ProviderContext;

/**
 * <code>MybatisSqlScript</code>
 * <p>The mybatis sql script interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisOrder
 * @since Jdk1.8
 */
public interface MybatisSqlScript extends MybatisOrder {

    /**
     * <code>caching</code>
     * <p>The caching method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param sqlScript       {@link io.github.nichetoolkit.mybatis.MybatisSqlScript} <p>The sql script parameter is <code>MybatisSqlScript</code> type.</p>
     * @return {@link java.lang.String} <p>The caching return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    static String caching(ProviderContext providerContext, MybatisSqlScript sqlScript) throws RestException {
        MybatisTable table = MybatisFactory.createTable(providerContext.getMapperType(), providerContext.getMapperMethod());
        return MybatisSqlSourceCaching.cache(providerContext, table, () -> {
            MybatisSqlScript restSqlScript = MybatisSqlScriptResolver.ofResolve(providerContext, table, sqlScript);
            String sql = restSqlScript.sql(table);
            return String.format(ScriptConstants.SCRIPT_LABEL, sql);
        });
    }

    /**
     * <code>caching</code>
     * <p>The caching method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param sqlScript       {@link io.github.nichetoolkit.mybatis.MybatisSqlScript.SimpleSqlScript} <p>The sql script parameter is <code>SimpleSqlScript</code> type.</p>
     * @return {@link java.lang.String} <p>The caching return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see io.github.nichetoolkit.mybatis.MybatisSqlScript.SimpleSqlScript
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    static String caching(ProviderContext providerContext, MybatisSqlScript.SimpleSqlScript sqlScript) throws RestException {
        return caching(providerContext, (MybatisSqlScript) sqlScript);
    }

    /**
     * <code>where</code>
     * <p>The where method.</p>
     * @param content {@link io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier} <p>The content parameter is <code>LinefeedSupplier</code> type.</p>
     * @return {@link java.lang.String} <p>The where return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    default String where(LinefeedSupplier content) throws RestException {
        return String.format(ScriptConstants.WHERE_LABEL, content.withLinefeed());
    }

    /**
     * <code>sql</code>
     * <p>The sql method.</p>
     * @param table {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The table parameter is <code>MybatisTable</code> type.</p>
     * @return {@link java.lang.String} <p>The sql return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    String sql(MybatisTable table) throws RestException;

    /**
     * <code>choose</code>
     * <p>The choose method.</p>
     * @param content {@link io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier} <p>The content parameter is <code>LinefeedSupplier</code> type.</p>
     * @return {@link java.lang.String} <p>The choose return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    default String choose(LinefeedSupplier content) throws RestException {
        return String.format(ScriptConstants.CHOOSE_LABEL, content.withLinefeed());
    }

    /**
     * <code>otherwise</code>
     * <p>The otherwise method.</p>
     * @param content {@link io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier} <p>The content parameter is <code>LinefeedSupplier</code> type.</p>
     * @return {@link java.lang.String} <p>The otherwise return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    default String otherwise(LinefeedSupplier content) throws RestException {
        return String.format(ScriptConstants.OTHERWISE_LABEL, content.withLinefeed());
    }

    /**
     * <code>set</code>
     * <p>The set method.</p>
     * @param content {@link io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier} <p>The content parameter is <code>LinefeedSupplier</code> type.</p>
     * @return {@link java.lang.String} <p>The set return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    default String set(LinefeedSupplier content) throws RestException {
        return String.format(ScriptConstants.SET_LABEL, content.withLinefeed());
    }

    /**
     * <code>ifTest</code>
     * <p>The if test method.</p>
     * @param test    {@link java.lang.String} <p>The test parameter is <code>String</code> type.</p>
     * @param content {@link io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier} <p>The content parameter is <code>LinefeedSupplier</code> type.</p>
     * @return {@link java.lang.String} <p>The if test return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier
     * @see io.github.nichetoolkit.rest.RestException
     */
    default String ifTest(String test, LinefeedSupplier content) throws RestException {
        return String.format(ScriptConstants.IF_TEST_LABEL, test, content.withLinefeed());
    }

    /**
     * <code>ifParameterNotNull</code>
     * <p>The if parameter not null method.</p>
     * @param content {@link io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier} <p>The content parameter is <code>LinefeedSupplier</code> type.</p>
     * @return {@link java.lang.String} <p>The if parameter not null return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    default String ifParameterNotNull(LinefeedSupplier content) throws RestException {
        return String.format(ScriptConstants.IF_TEST_PARAM_LABEL, content.withLinefeed());
    }

    /**
     * <code>parameterNotNull</code>
     * <p>The parameter not null method.</p>
     * @param message {@link java.lang.String} <p>The message parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The parameter not null return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    default String parameterNotNull(String message) throws RestException {
        return variableNotNull(ScriptConstants.PARAM, message);
    }

    /**
     * <code>variableIsTrue</code>
     * <p>The variable is true method.</p>
     * @param variable {@link java.lang.String} <p>The variable parameter is <code>String</code> type.</p>
     * @param message  {@link java.lang.String} <p>The message parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The variable is true return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    default String variableIsTrue(String variable, String message) throws RestException {
        OptionalUtils.ofFalse(Boolean.parseBoolean(variable), message, error -> new MybatisAssertErrorException("isTrue", "variable", error));
        return variable;
    }

    /**
     * <code>variableIsFalse</code>
     * <p>The variable is false method.</p>
     * @param variable {@link java.lang.String} <p>The variable parameter is <code>String</code> type.</p>
     * @param message  {@link java.lang.String} <p>The message parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The variable is false return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    default String variableIsFalse(String variable, String message) throws RestException {
        OptionalUtils.ofTrue(Boolean.parseBoolean(variable), message, error -> new MybatisAssertErrorException("isFalse", "variable", error));
        return variable;
    }

    /**
     * <code>variableNotNull</code>
     * <p>The variable not null method.</p>
     * @param variable {@link java.lang.String} <p>The variable parameter is <code>String</code> type.</p>
     * @param message  {@link java.lang.String} <p>The message parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The variable not null return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    default String variableNotNull(String variable, String message) throws RestException {
        OptionalUtils.ofNull(variable, message, error -> new MybatisAssertErrorException("notNull", "variable", error));
        return variable;
    }

    /**
     * <code>variableNotEmpty</code>
     * <p>The variable not empty method.</p>
     * @param variable {@link java.lang.String} <p>The variable parameter is <code>String</code> type.</p>
     * @param message  {@link java.lang.String} <p>The message parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The variable not empty return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    default String variableNotEmpty(String variable, String message) throws RestException {
        OptionalUtils.ofEmpty(variable, message, error -> new MybatisAssertErrorException("notEmpty", "variable", error));
        return variable;
    }

    /**
     * <code>whenTest</code>
     * <p>The when test method.</p>
     * @param test    {@link java.lang.String} <p>The test parameter is <code>String</code> type.</p>
     * @param content {@link io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier} <p>The content parameter is <code>LinefeedSupplier</code> type.</p>
     * @return {@link java.lang.String} <p>The when test return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier
     * @see io.github.nichetoolkit.rest.RestException
     */
    default String whenTest(String test, LinefeedSupplier content) throws RestException {
        return String.format(ScriptConstants.WHEN_TEST_LABEL, test, content.withLinefeed());
    }

    /**
     * <code>trim</code>
     * <p>The trim method.</p>
     * @param prefix          {@link java.lang.String} <p>The prefix parameter is <code>String</code> type.</p>
     * @param suffix          {@link java.lang.String} <p>The suffix parameter is <code>String</code> type.</p>
     * @param prefixOverrides {@link java.lang.String} <p>The prefix overrides parameter is <code>String</code> type.</p>
     * @param suffixOverrides {@link java.lang.String} <p>The suffix overrides parameter is <code>String</code> type.</p>
     * @param content         {@link io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier} <p>The content parameter is <code>LinefeedSupplier</code> type.</p>
     * @return {@link java.lang.String} <p>The trim return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier
     * @see io.github.nichetoolkit.rest.RestException
     */
    default String trim(String prefix, String suffix, String prefixOverrides, String suffixOverrides, LinefeedSupplier content) throws RestException {
        return String.format(ScriptConstants.TRIM_LABEL, prefix, prefixOverrides, suffixOverrides, suffix, content.withLinefeed());
    }

    /**
     * <code>trimPrefixOverrides</code>
     * <p>The trim prefix overrides method.</p>
     * @param prefix          {@link java.lang.String} <p>The prefix parameter is <code>String</code> type.</p>
     * @param suffix          {@link java.lang.String} <p>The suffix parameter is <code>String</code> type.</p>
     * @param prefixOverrides {@link java.lang.String} <p>The prefix overrides parameter is <code>String</code> type.</p>
     * @param content         {@link io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier} <p>The content parameter is <code>LinefeedSupplier</code> type.</p>
     * @return {@link java.lang.String} <p>The trim prefix overrides return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier
     * @see io.github.nichetoolkit.rest.RestException
     */
    default String trimPrefixOverrides(String prefix, String suffix, String prefixOverrides, LinefeedSupplier content) throws RestException {
        return String.format(ScriptConstants.TRIM_PREFIX_OVERRIDE_LABEL, prefix, prefixOverrides, suffix, content.withLinefeed());
    }

    /**
     * <code>trimSuffixOverrides</code>
     * <p>The trim suffix overrides method.</p>
     * @param prefix          {@link java.lang.String} <p>The prefix parameter is <code>String</code> type.</p>
     * @param suffix          {@link java.lang.String} <p>The suffix parameter is <code>String</code> type.</p>
     * @param suffixOverrides {@link java.lang.String} <p>The suffix overrides parameter is <code>String</code> type.</p>
     * @param content         {@link io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier} <p>The content parameter is <code>LinefeedSupplier</code> type.</p>
     * @return {@link java.lang.String} <p>The trim suffix overrides return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier
     * @see io.github.nichetoolkit.rest.RestException
     */
    default String trimSuffixOverrides(String prefix, String suffix, String suffixOverrides, LinefeedSupplier content) throws RestException {
        return String.format(ScriptConstants.TRIM_SUFFIX_OVERRIDE_LABEL, prefix, suffixOverrides, suffix, content.withLinefeed());
    }

    /**
     * <code>foreach</code>
     * <p>The foreach method.</p>
     * @param collection {@link java.lang.String} <p>The collection parameter is <code>String</code> type.</p>
     * @param item       {@link java.lang.String} <p>The item parameter is <code>String</code> type.</p>
     * @param content    {@link io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier} <p>The content parameter is <code>LinefeedSupplier</code> type.</p>
     * @return {@link java.lang.String} <p>The foreach return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier
     * @see io.github.nichetoolkit.rest.RestException
     */
    default String foreach(String collection, String item, LinefeedSupplier content) throws RestException {
        return String.format(ScriptConstants.FOREACH_LABEL, collection, item, content.withLinefeed());
    }

    /**
     * <code>foreach</code>
     * <p>The foreach method.</p>
     * @param collection {@link java.lang.String} <p>The collection parameter is <code>String</code> type.</p>
     * @param item       {@link java.lang.String} <p>The item parameter is <code>String</code> type.</p>
     * @param separator  {@link java.lang.String} <p>The separator parameter is <code>String</code> type.</p>
     * @param content    {@link io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier} <p>The content parameter is <code>LinefeedSupplier</code> type.</p>
     * @return {@link java.lang.String} <p>The foreach return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier
     * @see io.github.nichetoolkit.rest.RestException
     */
    default String foreach(String collection, String item, String separator, LinefeedSupplier content) throws RestException {
        return String.format(ScriptConstants.FOREACH_SEPARATOR_LABEL, collection, item, separator, content.withLinefeed());
    }

    /**
     * <code>foreach</code>
     * <p>The foreach method.</p>
     * @param collection {@link java.lang.String} <p>The collection parameter is <code>String</code> type.</p>
     * @param item       {@link java.lang.String} <p>The item parameter is <code>String</code> type.</p>
     * @param separator  {@link java.lang.String} <p>The separator parameter is <code>String</code> type.</p>
     * @param open       {@link java.lang.String} <p>The open parameter is <code>String</code> type.</p>
     * @param close      {@link java.lang.String} <p>The close parameter is <code>String</code> type.</p>
     * @param content    {@link io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier} <p>The content parameter is <code>LinefeedSupplier</code> type.</p>
     * @return {@link java.lang.String} <p>The foreach return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier
     * @see io.github.nichetoolkit.rest.RestException
     */
    default String foreach(String collection, String item, String separator, String open, String close, LinefeedSupplier content) throws RestException {
        return String.format(ScriptConstants.FOREACH_BRACE_LABEL, collection, item, open, close, separator, content.withLinefeed());
    }

    /**
     * <code>foreach</code>
     * <p>The foreach method.</p>
     * @param collection {@link java.lang.String} <p>The collection parameter is <code>String</code> type.</p>
     * @param item       {@link java.lang.String} <p>The item parameter is <code>String</code> type.</p>
     * @param separator  {@link java.lang.String} <p>The separator parameter is <code>String</code> type.</p>
     * @param open       {@link java.lang.String} <p>The open parameter is <code>String</code> type.</p>
     * @param close      {@link java.lang.String} <p>The close parameter is <code>String</code> type.</p>
     * @param index      {@link java.lang.String} <p>The index parameter is <code>String</code> type.</p>
     * @param content    {@link io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier} <p>The content parameter is <code>LinefeedSupplier</code> type.</p>
     * @return {@link java.lang.String} <p>The foreach return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier
     * @see io.github.nichetoolkit.rest.RestException
     */
    default String foreach(String collection, String item, String separator, String open, String close, String index, LinefeedSupplier content) throws RestException {
        return String.format(ScriptConstants.FOREACH_INDEX_LABEL, collection, item, index, open, close, separator, content.withLinefeed());
    }

    /**
     * <code>foreachOfIdList</code>
     * <p>The foreach of id list method.</p>
     * @param content {@link io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier} <p>The content parameter is <code>LinefeedSupplier</code> type.</p>
     * @return {@link java.lang.String} <p>The foreach of id list return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    default String foreachOfIdList(LinefeedSupplier content) throws RestException {
        return String.format(ScriptConstants.FOREACH_INDEX_LABEL, EntityConstants.IDENTITY_LIST, EntityConstants.IDENTITY, EntityConstants.INDEX,  SQLConstants.BRACE_LT,  SQLConstants.BRACE_GT, SQLConstants.COMMA + SQLConstants.BLANK, content.withLinefeed());
    }

    /**
     * <code>foreachOfLinkIdList</code>
     * <p>The foreach of link id list method.</p>
     * @param content {@link io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier} <p>The content parameter is <code>LinefeedSupplier</code> type.</p>
     * @return {@link java.lang.String} <p>The foreach of link id list return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    default String foreachOfLinkIdList(LinefeedSupplier content) throws RestException {
        return String.format(ScriptConstants.FOREACH_INDEX_LABEL, EntityConstants.LINK_ID_LIST, EntityConstants.LINK_ID, EntityConstants.INDEX,  SQLConstants.BRACE_LT,  SQLConstants.BRACE_GT, SQLConstants.COMMA + SQLConstants.BLANK, content.withLinefeed());
    }

    /**
     * <code>bind</code>
     * <p>The bind method.</p>
     * @param name  {@link java.lang.String} <p>The name parameter is <code>String</code> type.</p>
     * @param value {@link java.lang.String} <p>The value parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The bind return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    default String bind(String name, String value) {
        return String.format(ScriptConstants.BIND_LABEL, name, value);
    }


    /**
     * <code>LinefeedSupplier</code>
     * <p>The linefeed supplier interface.</p>
     * @author Cyan (snow22314@outlook.com)
     * @see io.github.nichetoolkit.rest.actuator.SupplierActuator
     * @since Jdk1.8
     */
    interface LinefeedSupplier extends SupplierActuator<String> {

        /**
         * <code>withLinefeed</code>
         * <p>The with linefeed method.</p>
         * @return {@link java.lang.String} <p>The with linefeed return object is <code>String</code> type.</p>
         * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
         * @see java.lang.String
         * @see io.github.nichetoolkit.rest.RestException
         */
        default String withLinefeed() throws RestException {
            String linefeed = get();
            if (!linefeed.isEmpty() && linefeed.charAt(0) == SQLConstants.LINEFEED.charAt(0)) {
                return linefeed;
            }
            return SQLConstants.LINEFEED + linefeed;
        }

    }

    /**
     * <code>SimpleSqlScript</code>
     * <p>The simple sql script interface.</p>
     * @author Cyan (snow22314@outlook.com)
     * @since Jdk1.8
     */
    interface SimpleSqlScript extends MybatisSqlScript {

        @Override
        default String sql(MybatisTable table) throws RestException {
            return sql(table, this);
        }

        /**
         * <code>sql</code>
         * <p>The sql method.</p>
         * @param table     {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The table parameter is <code>MybatisTable</code> type.</p>
         * @param sqlScript {@link io.github.nichetoolkit.mybatis.MybatisSqlScript} <p>The sql script parameter is <code>MybatisSqlScript</code> type.</p>
         * @return {@link java.lang.String} <p>The sql return object is <code>String</code> type.</p>
         * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
         * @see io.github.nichetoolkit.mybatis.MybatisTable
         * @see java.lang.String
         * @see io.github.nichetoolkit.rest.RestException
         */
        String sql(MybatisTable table, MybatisSqlScript sqlScript) throws RestException;

    }
}
