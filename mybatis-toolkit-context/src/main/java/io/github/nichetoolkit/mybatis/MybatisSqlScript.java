package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.consts.EntityConstants;
import io.github.nichetoolkit.mybatis.consts.SQLConstants;
import io.github.nichetoolkit.mybatis.consts.ScriptConstants;
import io.github.nichetoolkit.mybatis.error.MybatisAssertErrorException;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.actuator.SupplierActuator;
import io.github.nichetoolkit.rest.util.OptionalUtils;
import org.apache.ibatis.builder.annotation.ProviderContext;

public interface MybatisSqlScript extends MybatisOrder {

    static String caching(ProviderContext providerContext, MybatisSqlScript sqlScript) throws RestException {
        MybatisTable table = MybatisFactory.createTable(providerContext.getMapperType(), providerContext.getMapperMethod());
        return MybatisSqlSourceCaching.cache(providerContext, table, () -> {
            MybatisSqlScript restSqlScript = MybatisSqlScriptResolver.ofResolve(providerContext, table, sqlScript);
            String sql = restSqlScript.sql(table);
            return String.format(ScriptConstants.SCRIPT_LABEL, sql);
        });
    }

    static String caching(ProviderContext providerContext, MybatisSqlScript.SimpleSqlScript sqlScript) throws RestException {
        return caching(providerContext, (MybatisSqlScript) sqlScript);
    }

    default String where(LinefeedSupplier content) throws RestException {
        return String.format(ScriptConstants.WHERE_LABEL, content.withLinefeed());
    }

    String sql(MybatisTable table) throws RestException;

    default String choose(LinefeedSupplier content) throws RestException {
        return String.format(ScriptConstants.CHOOSE_LABEL, content.withLinefeed());
    }

    default String otherwise(LinefeedSupplier content) throws RestException {
        return String.format(ScriptConstants.OTHERWISE_LABEL, content.withLinefeed());
    }

    default String set(LinefeedSupplier content) throws RestException {
        return String.format(ScriptConstants.SET_LABEL, content.withLinefeed());
    }

    default String ifTest(String test, LinefeedSupplier content) throws RestException {
        return String.format(ScriptConstants.IF_TEST_LABEL, test, content.withLinefeed());
    }

    default String ifParameterNotNull(LinefeedSupplier content) throws RestException {
        return String.format(ScriptConstants.IF_TEST_PARAM_LABEL, content.withLinefeed());
    }

    default String parameterNotNull(String message) throws RestException {
        return variableNotNull(ScriptConstants.PARAM, message);
    }

    default String variableIsTrue(String variable, String message) throws RestException {
        OptionalUtils.ofFalse(Boolean.parseBoolean(variable), message, error -> new MybatisAssertErrorException("isTrue", "variable", error));
        return variable;
    }

    default String variableIsFalse(String variable, String message) throws RestException {
        OptionalUtils.ofTrue(Boolean.parseBoolean(variable), message, error -> new MybatisAssertErrorException("isFalse", "variable", error));
        return variable;
    }

    default String variableNotNull(String variable, String message) throws RestException {
        OptionalUtils.ofNull(variable, message, error -> new MybatisAssertErrorException("notNull", "variable", error));
        return variable;
    }

    default String variableNotEmpty(String variable, String message) throws RestException {
        OptionalUtils.ofEmpty(variable, message, error -> new MybatisAssertErrorException("notEmpty", "variable", error));
        return variable;
    }

    default String whenTest(String test, LinefeedSupplier content) throws RestException {
        return String.format(ScriptConstants.WHEN_TEST_LABEL, test, content.withLinefeed());
    }

    default String trim(String prefix, String suffix, String prefixOverrides, String suffixOverrides, LinefeedSupplier content) throws RestException {
        return String.format(ScriptConstants.TRIM_LABEL, prefix, prefixOverrides, suffixOverrides, suffix, content.withLinefeed());
    }

    default String trimPrefixOverrides(String prefix, String suffix, String prefixOverrides, LinefeedSupplier content) throws RestException {
        return String.format(ScriptConstants.TRIM_PREFIX_OVERRIDE_LABEL, prefix, prefixOverrides, suffix, content.withLinefeed());
    }

    default String trimSuffixOverrides(String prefix, String suffix, String suffixOverrides, LinefeedSupplier content) throws RestException {
        return String.format(ScriptConstants.TRIM_SUFFIX_OVERRIDE_LABEL, prefix, suffixOverrides, suffix, content.withLinefeed());
    }

    default String foreach(String collection, String item, LinefeedSupplier content) throws RestException {
        return String.format(ScriptConstants.FOREACH_LABEL, collection, item, content.withLinefeed());
    }

    default String foreach(String collection, String item, String separator, LinefeedSupplier content) throws RestException {
        return String.format(ScriptConstants.FOREACH_SEPARATOR_LABEL, collection, item, separator, content.withLinefeed());
    }

    default String foreach(String collection, String item, String separator, String open, String close, LinefeedSupplier content) throws RestException {
        return String.format(ScriptConstants.FOREACH_BRACE_LABEL, collection, item, open, close, separator, content.withLinefeed());
    }

    default String foreach(String collection, String item, String separator, String open, String close, String index, LinefeedSupplier content) throws RestException {
        return String.format(ScriptConstants.FOREACH_INDEX_LABEL, collection, item, index, open, close, separator, content.withLinefeed());
    }

    default String foreachOfIdList(LinefeedSupplier content) throws RestException {
        return String.format(ScriptConstants.FOREACH_INDEX_LABEL, EntityConstants.IDENTITY_LIST, EntityConstants.IDENTITY, EntityConstants.INDEX,  SQLConstants.BRACE_LT,  SQLConstants.BRACE_GT, SQLConstants.COMMA + SQLConstants.BLANK, content.withLinefeed());
    }

    default String foreachOfLinkIdList(LinefeedSupplier content) throws RestException {
        return String.format(ScriptConstants.FOREACH_INDEX_LABEL, EntityConstants.LINK_ID_LIST, EntityConstants.LINK_ID, EntityConstants.INDEX,  SQLConstants.BRACE_LT,  SQLConstants.BRACE_GT, SQLConstants.COMMA + SQLConstants.BLANK, content.withLinefeed());
    }

    default String bind(String name, String value) {
        return String.format(ScriptConstants.BIND_LABEL, name, value);
    }


    interface LinefeedSupplier extends SupplierActuator<String> {

        default String withLinefeed() throws RestException {
            String linefeed = get();
            if (!linefeed.isEmpty() && linefeed.charAt(0) == SQLConstants.LINEFEED.charAt(0)) {
                return linefeed;
            }
            return SQLConstants.LINEFEED + linefeed;
        }

    }

    interface SimpleSqlScript extends MybatisSqlScript {

        @Override
        default String sql(MybatisTable table) throws RestException {
            return sql(table, this);
        }

        String sql(MybatisTable table, MybatisSqlScript sqlScript) throws RestException;

    }
}
