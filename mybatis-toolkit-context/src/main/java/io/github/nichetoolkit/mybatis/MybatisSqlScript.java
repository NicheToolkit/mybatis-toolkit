package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.error.MybatisAssertErrorException;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.actuator.SupplierActuator;
import io.github.nichetoolkit.rest.helper.OptionalHelper;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import org.apache.ibatis.builder.annotation.ProviderContext;

/**
 * <p>MybatisSqlScript</p>
 * 对 xml 形式 sql 简单封装
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public interface MybatisSqlScript {

    /**
     * 换行符
     */
    String LINEFEED = "\n";

    /**
     * 创建SQL并缓存
     * @param providerContext 执行方法上下文
     * @param sqlScript       xml sql 脚本实现
     * @return 缓存key
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
     * 创建SQL并缓存
     * @param providerContext 执行方法上下文
     * @param sqlScript       xml sql 脚本实现
     * @return 缓存key
     */
    static String cachingSimple(ProviderContext providerContext, SimpleSqlScript sqlScript) throws RestException {
        return caching(providerContext, sqlScript);
    }

    /**
     * 生成 where 标签包装的 xml 结构
     * @param content 标签中的内容
     * @return where 标签包装的 xml 结构
     */
    default String where(LinefeedSupplier content) throws RestException {
        return String.format("\n<where>%s\n</where> ", content.withLinefeed());
    }

    /**
     * 生成对应的 SQL，支持动态标签
     * @param table 实体类信息
     * @return xml sql 脚本
     */
    String sql(MybatisTable table) throws RestException;

    /**
     * 生成 choose 标签包装的 xml 结构
     * @param content 标签中的内容
     * @return choose 标签包装的 xml 结构
     */
    default String choose(LinefeedSupplier content) throws RestException {
        return String.format("\n<choose>%s\n</choose> ", content.withLinefeed());
    }

    /**
     * 生成 otherwise 标签包装的 xml 结构
     * @param content 标签中的内容
     * @return otherwise 标签包装的 xml 结构
     */
    default String otherwise(LinefeedSupplier content) throws RestException {
        return String.format("\n<otherwise>%s\n</otherwise> ", content.withLinefeed());
    }

    /**
     * 生成 set 标签包装的 xml 结构
     * @param content 标签中的内容
     * @return set 标签包装的 xml 结构
     */
    default String set(LinefeedSupplier content) throws RestException {
        return String.format("\n<set>%s\n</set> ", content.withLinefeed());
    }

    /**
     * 生成 if 标签包装的 xml 结构
     * @param test    if 的判断条件
     * @param content 标签中的内容
     * @return if 标签包装的 xml 结构
     */
    default String ifTest(String test, LinefeedSupplier content) throws RestException {
        return String.format("<if test=\"%s\">%s\n</if> ", test, content.withLinefeed());
    }

    /**
     * 生成 &lt;if test="_parameter != null"&gt; 标签包装的 xml 结构，允许参数为空时使用，
     * 当参数必填时，可以使用 {@link #parameterNotNull(String)} 方法
     * @param content 标签中的内容
     * @return &lt;if test="_parameter != null"&gt; 标签包装的 xml 结构
     */
    default String ifParameterNotNull(LinefeedSupplier content) throws RestException {
        return String.format("<if test=\"_parameter != null\">%s\n</if> ", content.withLinefeed());
    }

    /**
     * 增加对参数的校验，参数不能为空
     * @param message 提示信息
     * @return 在代码基础上增加一段校验
     */
    default String parameterNotNull(String message) throws RestException {
        return variableNotNull("_parameter", message);
    }

    /**
     * 增加对参数的校验，参数必须为 true
     * @param variable 参数, 值为 boolean
     * @param message  提示信息
     * @return 在代码基础上增加一段校验
     */
    default String variableIsTrue(String variable, String message) throws RestException {
        OptionalHelper.falseable(Boolean.parseBoolean(variable), message, error -> new MybatisAssertErrorException("isTrue","variable",error));
        return variable;
    }

    /**
     * 增加对参数的校验，参数必须为 false
     * @param variable 参数, 值为 boolean
     * @param message  提示信息
     * @return 在代码基础上增加一段校验
     */
    default String variableIsFalse(String variable, String message) throws RestException {
        OptionalHelper.trueable(Boolean.parseBoolean(variable), message, error -> new MybatisAssertErrorException("isFalse","variable",error));
        return variable;
    }

    /**
     * 增加对参数的校验，参数不能为 null
     * @param variable 参数
     * @param message  提示信息
     * @return 在代码基础上增加一段校验
     */
    default String variableNotNull(String variable, String message) throws RestException {
        OptionalHelper.falseable(variable != null, message, error -> new MybatisAssertErrorException("notNull","variable",error));
        return variable;
    }

    /**
     * 增加对参数的校验，参数不能为空
     * @param message 提示信息
     * @return 在代码基础上增加一段校验
     */
    default String variableNotEmpty(String variable, String message) throws RestException {
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(variable), message, error -> new MybatisAssertErrorException("notEmpty","variable",error));
        return variable;
    }

    /**
     * 生成 when 标签包装的 xml 结构
     * @param test    when 的判断条件
     * @param content 标签中的内容
     * @return when 标签包装的 xml 结构
     */
    default String whenTest(String test, LinefeedSupplier content) throws RestException {
        return String.format("\n<when test=\"%s\">%s\n</when> ", test, content.withLinefeed());
    }

    /**
     * 生成 trim 标签包装的 xml 结构
     * @param prefix          前缀
     * @param suffix          后缀
     * @param prefixOverrides 前缀替换内容
     * @param suffixOverrides 后缀替换内容
     * @param content         标签中的内容
     * @return trim 标签包装的 xml 结构
     */
    default String trim(String prefix, String suffix, String prefixOverrides, String suffixOverrides, LinefeedSupplier content) throws RestException {
        return String.format("\n<trim prefix=\"%s\" prefixOverrides=\"%s\" suffixOverrides=\"%s\" suffix=\"%s\">%s\n</trim> "
                , prefix, prefixOverrides, suffixOverrides, suffix, content.withLinefeed());
    }

    /**
     * 生成 trim 标签包装的 xml 结构
     * @param prefix          前缀
     * @param suffix          后缀
     * @param prefixOverrides 前缀替换内容
     * @param content         标签中的内容
     * @return trim 标签包装的 xml 结构
     */
    default String trimPrefixOverrides(String prefix, String suffix, String prefixOverrides, LinefeedSupplier content) throws RestException {
        return String.format("\n<trim prefix=\"%s\" prefixOverrides=\"%s\" suffix=\"%s\">%s\n</trim> ", prefix, prefixOverrides, suffix, content.withLinefeed());
    }

    /**
     * 生成 trim 标签包装的 xml 结构
     * @param prefix          前缀
     * @param suffix          后缀
     * @param suffixOverrides 后缀替换内容
     * @param content         标签中的内容
     * @return trim 标签包装的 xml 结构
     */
    default String trimSuffixOverrides(String prefix, String suffix, String suffixOverrides, LinefeedSupplier content) throws RestException {
        return String.format("\n<trim prefix=\"%s\" suffixOverrides=\"%s\" suffix=\"%s\">%s\n</trim> ", prefix, suffixOverrides, suffix, content.withLinefeed());
    }

    /**
     * 生成 foreach 标签包装的 xml 结构
     * @param collection 遍历的对象
     * @param item       对象名
     * @param content    标签中的内容
     * @return foreach 标签包装的 xml 结构
     */
    default String foreach(String collection, String item, LinefeedSupplier content) throws RestException {
        return String.format("\n<foreach collection=\"%s\" item=\"%s\">%s\n</foreach> ", collection, item, content.withLinefeed());
    }

    /**
     * 生成 foreach 标签包装的 xml 结构
     * @param collection 遍历的对象
     * @param item       对象名
     * @param separator  连接符
     * @param content    标签中的内容
     * @return foreach 标签包装的 xml 结构
     */
    default String foreach(String collection, String item, String separator, LinefeedSupplier content) throws RestException {
        return String.format("\n<foreach collection=\"%s\" item=\"%s\" separator=\"%s\">%s\n</foreach> "
                , collection, item, separator, content.withLinefeed());
    }

    /**
     * 生成 foreach 标签包装的 xml 结构
     * @param collection 遍历的对象
     * @param item       对象名
     * @param separator  连接符
     * @param open       开始符号
     * @param close      结束符号
     * @param content    标签中的内容
     * @return foreach 标签包装的 xml 结构
     */
    default String foreach(String collection, String item, String separator, String open, String close, LinefeedSupplier content) throws RestException {
        return String.format("\n<foreach collection=\"%s\" item=\"%s\" open=\"%s\" close=\"%s\" separator=\"%s\">%s\n</foreach> "
                , collection, item, open, close, separator, content.withLinefeed());
    }

    /**
     * 生成 foreach 标签包装的 xml 结构
     * @param collection 遍历的对象
     * @param item       对象名
     * @param separator  连接符
     * @param open       开始符号
     * @param close      结束符号
     * @param index      索引名（list为索引，map为key）
     * @param content    标签中的内容
     * @return foreach 标签包装的 xml 结构
     */
    default String foreach(String collection, String item, String separator, String open, String close, String index, LinefeedSupplier content) throws RestException {
        return String.format("\n<foreach collection=\"%s\" item=\"%s\" index=\"%s\" open=\"%s\" close=\"%s\" separator=\"%s\">%s\n</foreach> "
                , collection, item, index, open, close, separator, content.withLinefeed());
    }

    /**
     * 生成 bind 标签包装的 xml 结构
     * @param name  变量名
     * @param value 变量值
     * @return bind 标签包装的 xml 结构
     */
    default String bind(String name, String value) {
        return String.format("\n<bind name=\"%s\" value=\"%s\"/>", name, value);
    }

    /**
     * 保证所有字符串前面都有换行符
     */
    interface LinefeedSupplier extends SupplierActuator<String> {

        default String withLinefeed() throws RestException {
            String linefeed = get();
            if (!linefeed.isEmpty() && linefeed.charAt(0) == LINEFEED.charAt(0)) {
                return linefeed;
            }
            return LINEFEED + linefeed;
        }

    }

    /**
     * 支持简单写法
     */
    interface SimpleSqlScript extends MybatisSqlScript {

        @Override
        default String sql(MybatisTable table) throws RestException {
            return sql(table, this);
        }

        /**
         * 生成对应的 SQL，支持动态标签
         * @param table     实体类信息
         * @param sqlScript 当前对象的引用，可以在 lambda 中使用当前对象的方法
         * @return 对应的 SQL，支持动态标签
         */
        String sql(MybatisTable table, MybatisSqlScript sqlScript) throws RestException;

    }
}
