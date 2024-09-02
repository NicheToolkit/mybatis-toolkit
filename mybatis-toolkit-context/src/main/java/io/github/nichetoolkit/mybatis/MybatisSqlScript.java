package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.error.MybatisAssertErrorException;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.actuator.SupplierActuator;
import io.github.nichetoolkit.rest.helper.OptionalHelper;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import org.apache.ibatis.builder.annotation.ProviderContext;

/**
 * <code>MybatisSqlScript</code>
 * <p>The type mybatis sql script interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
public interface MybatisSqlScript {

    /**
     * <code>LINEFEED</code>
     * {@link java.lang.String} <p>the constant <code>LINEFEED</code> field.</p>
     * @see java.lang.String
     */
    String LINEFEED = "\n";

    /**
     * <code>caching</code>
     * <p>the method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param sqlScript       {@link io.github.nichetoolkit.mybatis.MybatisSqlScript} <p>the sql script parameter is <code>MybatisSqlScript</code> type.</p>
     * @return {@link java.lang.String} <p>the return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    static String caching(ProviderContext providerContext, MybatisSqlScript sqlScript) throws RestException {
        MybatisTable table = MybatisFactory.createTable(providerContext.getMapperType(), providerContext.getMapperMethod());
        return MybatisCaching.cache(providerContext, table, () -> {
            MybatisSqlScript mybatisSqlScript = MybatisSqlScriptWrapper.wrapSqlScript(providerContext, table, sqlScript);
            String sql = mybatisSqlScript.sql(table);
            return String.format("<script>\n%s\n</script>", sql);
        });
    }

    /**
     * <code>cachingSimple</code>
     * <p>the simple method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param sqlScript       {@link io.github.nichetoolkit.mybatis.MybatisSqlScript.SimpleSqlScript} <p>the sql script parameter is <code>SimpleSqlScript</code> type.</p>
     * @return {@link java.lang.String} <p>the simple return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see io.github.nichetoolkit.mybatis.MybatisSqlScript.SimpleSqlScript
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    static String cachingSimple(ProviderContext providerContext, SimpleSqlScript sqlScript) throws RestException {
        return caching(providerContext, sqlScript);
    }

    /**
     * <code>where</code>
     * <p>the method.</p>
     * @param content {@link io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier} <p>the content parameter is <code>LinefeedSupplier</code> type.</p>
     * @return {@link java.lang.String} <p>the return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    default String where(LinefeedSupplier content) throws RestException {
        return String.format("\n<where>%s\n</where> ", content.withLinefeed());
    }

    /**
     * <code>sql</code>
     * <p>the method.</p>
     * @param table {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>the table parameter is <code>MybatisTable</code> type.</p>
     * @return {@link java.lang.String} <p>the return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    String sql(MybatisTable table) throws RestException;

    /**
     * <code>choose</code>
     * <p>the method.</p>
     * @param content {@link io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier} <p>the content parameter is <code>LinefeedSupplier</code> type.</p>
     * @return {@link java.lang.String} <p>the return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    default String choose(LinefeedSupplier content) throws RestException {
        return String.format("\n<choose>%s\n</choose> ", content.withLinefeed());
    }

    /**
     * <code>otherwise</code>
     * <p>the method.</p>
     * @param content {@link io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier} <p>the content parameter is <code>LinefeedSupplier</code> type.</p>
     * @return {@link java.lang.String} <p>the return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    default String otherwise(LinefeedSupplier content) throws RestException {
        return String.format("\n<otherwise>%s\n</otherwise> ", content.withLinefeed());
    }

    /**
     * <code>set</code>
     * <p>the method.</p>
     * @param content {@link io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier} <p>the content parameter is <code>LinefeedSupplier</code> type.</p>
     * @return {@link java.lang.String} <p>the return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    default String set(LinefeedSupplier content) throws RestException {
        return String.format("\n<set>%s\n</set> ", content.withLinefeed());
    }

    /**
     * <code>ifTest</code>
     * <p>the test method.</p>
     * @param test    {@link java.lang.String} <p>the test parameter is <code>String</code> type.</p>
     * @param content {@link io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier} <p>the content parameter is <code>LinefeedSupplier</code> type.</p>
     * @return {@link java.lang.String} <p>the test return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier
     * @see io.github.nichetoolkit.rest.RestException
     */
    default String ifTest(String test, LinefeedSupplier content) throws RestException {
        return String.format("<if test=\"%s\">%s\n</if> ", test, content.withLinefeed());
    }

    /**
     * <code>ifParameterNotNull</code>
     * <p>the parameter not null method.</p>
     * @param content {@link io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier} <p>the content parameter is <code>LinefeedSupplier</code> type.</p>
     * @return {@link java.lang.String} <p>the parameter not null return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    default String ifParameterNotNull(LinefeedSupplier content) throws RestException {
        return String.format("<if test=\"_parameter != null\">%s\n</if> ", content.withLinefeed());
    }

    /**
     * <code>parameterNotNull</code>
     * <p>the not null method.</p>
     * @param message {@link java.lang.String} <p>the message parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>the not null return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    default String parameterNotNull(String message) throws RestException {
        return variableNotNull("_parameter", message);
    }

    /**
     * <code>variableIsTrue</code>
     * <p>the is true method.</p>
     * @param variable {@link java.lang.String} <p>the variable parameter is <code>String</code> type.</p>
     * @param message  {@link java.lang.String} <p>the message parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>the is true return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    default String variableIsTrue(String variable, String message) throws RestException {
        OptionalHelper.falseable(Boolean.parseBoolean(variable), message, error -> new MybatisAssertErrorException("isTrue","variable",error));
        return variable;
    }

    /**
     * <code>variableIsFalse</code>
     * <p>the is false method.</p>
     * @param variable {@link java.lang.String} <p>the variable parameter is <code>String</code> type.</p>
     * @param message  {@link java.lang.String} <p>the message parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>the is false return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    default String variableIsFalse(String variable, String message) throws RestException {
        OptionalHelper.trueable(Boolean.parseBoolean(variable), message, error -> new MybatisAssertErrorException("isFalse","variable",error));
        return variable;
    }

    /**
     * <code>variableNotNull</code>
     * <p>the not null method.</p>
     * @param variable {@link java.lang.String} <p>the variable parameter is <code>String</code> type.</p>
     * @param message  {@link java.lang.String} <p>the message parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>the not null return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    default String variableNotNull(String variable, String message) throws RestException {
        OptionalHelper.falseable(variable != null, message, error -> new MybatisAssertErrorException("notNull","variable",error));
        return variable;
    }

    /**
     * <code>variableNotEmpty</code>
     * <p>the not empty method.</p>
     * @param variable {@link java.lang.String} <p>the variable parameter is <code>String</code> type.</p>
     * @param message  {@link java.lang.String} <p>the message parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>the not empty return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    default String variableNotEmpty(String variable, String message) throws RestException {
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(variable), message, error -> new MybatisAssertErrorException("notEmpty","variable",error));
        return variable;
    }

    /**
     * <code>whenTest</code>
     * <p>the test method.</p>
     * @param test    {@link java.lang.String} <p>the test parameter is <code>String</code> type.</p>
     * @param content {@link io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier} <p>the content parameter is <code>LinefeedSupplier</code> type.</p>
     * @return {@link java.lang.String} <p>the test return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier
     * @see io.github.nichetoolkit.rest.RestException
     */
    default String whenTest(String test, LinefeedSupplier content) throws RestException {
        return String.format("\n<when test=\"%s\">%s\n</when> ", test, content.withLinefeed());
    }

    /**
     * <code>trim</code>
     * <p>the method.</p>
     * @param prefix          {@link java.lang.String} <p>the prefix parameter is <code>String</code> type.</p>
     * @param suffix          {@link java.lang.String} <p>the suffix parameter is <code>String</code> type.</p>
     * @param prefixOverrides {@link java.lang.String} <p>the prefix overrides parameter is <code>String</code> type.</p>
     * @param suffixOverrides {@link java.lang.String} <p>the suffix overrides parameter is <code>String</code> type.</p>
     * @param content         {@link io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier} <p>the content parameter is <code>LinefeedSupplier</code> type.</p>
     * @return {@link java.lang.String} <p>the return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier
     * @see io.github.nichetoolkit.rest.RestException
     */
    default String trim(String prefix, String suffix, String prefixOverrides, String suffixOverrides, LinefeedSupplier content) throws RestException {
        return String.format("\n<trim prefix=\"%s\" prefixOverrides=\"%s\" suffixOverrides=\"%s\" suffix=\"%s\">%s\n</trim> "
                , prefix, prefixOverrides, suffixOverrides, suffix, content.withLinefeed());
    }

    /**
     * <code>trimPrefixOverrides</code>
     * <p>the prefix overrides method.</p>
     * @param prefix          {@link java.lang.String} <p>the prefix parameter is <code>String</code> type.</p>
     * @param suffix          {@link java.lang.String} <p>the suffix parameter is <code>String</code> type.</p>
     * @param prefixOverrides {@link java.lang.String} <p>the prefix overrides parameter is <code>String</code> type.</p>
     * @param content         {@link io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier} <p>the content parameter is <code>LinefeedSupplier</code> type.</p>
     * @return {@link java.lang.String} <p>the prefix overrides return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier
     * @see io.github.nichetoolkit.rest.RestException
     */
    default String trimPrefixOverrides(String prefix, String suffix, String prefixOverrides, LinefeedSupplier content) throws RestException {
        return String.format("\n<trim prefix=\"%s\" prefixOverrides=\"%s\" suffix=\"%s\">%s\n</trim> ", prefix, prefixOverrides, suffix, content.withLinefeed());
    }

    /**
     * <code>trimSuffixOverrides</code>
     * <p>the suffix overrides method.</p>
     * @param prefix          {@link java.lang.String} <p>the prefix parameter is <code>String</code> type.</p>
     * @param suffix          {@link java.lang.String} <p>the suffix parameter is <code>String</code> type.</p>
     * @param suffixOverrides {@link java.lang.String} <p>the suffix overrides parameter is <code>String</code> type.</p>
     * @param content         {@link io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier} <p>the content parameter is <code>LinefeedSupplier</code> type.</p>
     * @return {@link java.lang.String} <p>the suffix overrides return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier
     * @see io.github.nichetoolkit.rest.RestException
     */
    default String trimSuffixOverrides(String prefix, String suffix, String suffixOverrides, LinefeedSupplier content) throws RestException {
        return String.format("\n<trim prefix=\"%s\" suffixOverrides=\"%s\" suffix=\"%s\">%s\n</trim> ", prefix, suffixOverrides, suffix, content.withLinefeed());
    }

    /**
     * <code>foreach</code>
     * <p>the method.</p>
     * @param collection {@link java.lang.String} <p>the collection parameter is <code>String</code> type.</p>
     * @param item       {@link java.lang.String} <p>the item parameter is <code>String</code> type.</p>
     * @param content    {@link io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier} <p>the content parameter is <code>LinefeedSupplier</code> type.</p>
     * @return {@link java.lang.String} <p>the return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier
     * @see io.github.nichetoolkit.rest.RestException
     */
    default String foreach(String collection, String item, LinefeedSupplier content) throws RestException {
        return String.format("\n<foreach collection=\"%s\" item=\"%s\">%s\n</foreach> ", collection, item, content.withLinefeed());
    }

    /**
     * <code>foreach</code>
     * <p>the method.</p>
     * @param collection {@link java.lang.String} <p>the collection parameter is <code>String</code> type.</p>
     * @param item       {@link java.lang.String} <p>the item parameter is <code>String</code> type.</p>
     * @param separator  {@link java.lang.String} <p>the separator parameter is <code>String</code> type.</p>
     * @param content    {@link io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier} <p>the content parameter is <code>LinefeedSupplier</code> type.</p>
     * @return {@link java.lang.String} <p>the return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier
     * @see io.github.nichetoolkit.rest.RestException
     */
    default String foreach(String collection, String item, String separator, LinefeedSupplier content) throws RestException {
        return String.format("\n<foreach collection=\"%s\" item=\"%s\" separator=\"%s\">%s\n</foreach> "
                , collection, item, separator, content.withLinefeed());
    }

    /**
     * <code>foreach</code>
     * <p>the method.</p>
     * @param collection {@link java.lang.String} <p>the collection parameter is <code>String</code> type.</p>
     * @param item       {@link java.lang.String} <p>the item parameter is <code>String</code> type.</p>
     * @param separator  {@link java.lang.String} <p>the separator parameter is <code>String</code> type.</p>
     * @param open       {@link java.lang.String} <p>the open parameter is <code>String</code> type.</p>
     * @param close      {@link java.lang.String} <p>the close parameter is <code>String</code> type.</p>
     * @param content    {@link io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier} <p>the content parameter is <code>LinefeedSupplier</code> type.</p>
     * @return {@link java.lang.String} <p>the return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier
     * @see io.github.nichetoolkit.rest.RestException
     */
    default String foreach(String collection, String item, String separator, String open, String close, LinefeedSupplier content) throws RestException {
        return String.format("\n<foreach collection=\"%s\" item=\"%s\" open=\"%s\" close=\"%s\" separator=\"%s\">%s\n</foreach> "
                , collection, item, open, close, separator, content.withLinefeed());
    }

    /**
     * <code>foreach</code>
     * <p>the method.</p>
     * @param collection {@link java.lang.String} <p>the collection parameter is <code>String</code> type.</p>
     * @param item       {@link java.lang.String} <p>the item parameter is <code>String</code> type.</p>
     * @param separator  {@link java.lang.String} <p>the separator parameter is <code>String</code> type.</p>
     * @param open       {@link java.lang.String} <p>the open parameter is <code>String</code> type.</p>
     * @param close      {@link java.lang.String} <p>the close parameter is <code>String</code> type.</p>
     * @param index      {@link java.lang.String} <p>the index parameter is <code>String</code> type.</p>
     * @param content    {@link io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier} <p>the content parameter is <code>LinefeedSupplier</code> type.</p>
     * @return {@link java.lang.String} <p>the return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.MybatisSqlScript.LinefeedSupplier
     * @see io.github.nichetoolkit.rest.RestException
     */
    default String foreach(String collection, String item, String separator, String open, String close, String index, LinefeedSupplier content) throws RestException {
        return String.format("\n<foreach collection=\"%s\" item=\"%s\" index=\"%s\" open=\"%s\" close=\"%s\" separator=\"%s\">%s\n</foreach> "
                , collection, item, index, open, close, separator, content.withLinefeed());
    }

    /**
     * <code>bind</code>
     * <p>the method.</p>
     * @param name  {@link java.lang.String} <p>the name parameter is <code>String</code> type.</p>
     * @param value {@link java.lang.String} <p>the value parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>the return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    default String bind(String name, String value) {
        return String.format("\n<bind name=\"%s\" value=\"%s\"/>", name, value);
    }

    /**
     * <code>LinefeedSupplier</code>
     * <p>The type linefeed supplier interface.</p>
     * @author Cyan (snow22314@outlook.com)
     * @see io.github.nichetoolkit.rest.actuator.SupplierActuator
     * @since Jdk1.8
     */
    interface LinefeedSupplier extends SupplierActuator<String> {

        /**
         * <code>withLinefeed</code>
         * <p>the linefeed method.</p>
         * @return {@link java.lang.String} <p>the linefeed return object is <code>String</code> type.</p>
         * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
         * @see java.lang.String
         * @see io.github.nichetoolkit.rest.RestException
         */
        default String withLinefeed() throws RestException {
            String linefeed = get();
            if (!linefeed.isEmpty() && linefeed.charAt(0) == LINEFEED.charAt(0)) {
                return linefeed;
            }
            return LINEFEED + linefeed;
        }

    }

    /**
     * <code>SimpleSqlScript</code>
     * <p>The type simple sql script interface.</p>
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
         * <p>the method.</p>
         * @param table     {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>the table parameter is <code>MybatisTable</code> type.</p>
         * @param sqlScript {@link io.github.nichetoolkit.mybatis.MybatisSqlScript} <p>the sql script parameter is <code>MybatisSqlScript</code> type.</p>
         * @return {@link java.lang.String} <p>the return object is <code>String</code> type.</p>
         * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
         * @see io.github.nichetoolkit.mybatis.MybatisTable
         * @see java.lang.String
         * @see io.github.nichetoolkit.rest.RestException
         */
        String sql(MybatisTable table, MybatisSqlScript sqlScript) throws RestException;

    }
}
