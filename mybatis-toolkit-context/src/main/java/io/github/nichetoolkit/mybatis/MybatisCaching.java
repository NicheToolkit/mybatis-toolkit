package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.actuator.SupplierActuator;
import io.github.nichetoolkit.rest.error.lack.ConfigureLackError;
import io.github.nichetoolkit.rest.error.lack.MethodLackError;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <code>MybatisCaching</code>
 * <p>The type mybatis caching class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see org.apache.ibatis.scripting.xmltags.XMLLanguageDriver
 * @see lombok.extern.slf4j.Slf4j
 * @since Jdk1.8
 */
@Slf4j
public class MybatisCaching extends XMLLanguageDriver {

    /**
     * <code>cacheKey</code>
     * <p>the key method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @return {@link java.lang.String} <p>the key return object is <code>String</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     */
    private static String cacheKey(ProviderContext providerContext) {
        return (providerContext.getMapperType().getName() + "." + providerContext.getMapperMethod().getName()).intern();
    }

    /**
     * <code>isPresentLang</code>
     * <p>the present lang method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     */
    private static void isPresentLang(ProviderContext providerContext) {
        Method mapperMethod = providerContext.getMapperMethod();
        Lang lang = mapperMethod.getAnnotation(Lang.class);
        if (GeneralUtils.isEmpty(lang) || !lang.value().isAssignableFrom(MybatisCaching.class)) {
            throw new ConfigureLackError(mapperMethod + " need to configure @Lang(MybatisCaching.class) to use the MybatisCaching.cache method for caching");
        }
    }


    /**
     * <code>cache</code>
     * <p>the method.</p>
     * @param providerContext   {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param entity            {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>the entity parameter is <code>MybatisTable</code> type.</p>
     * @param sqlScriptSupplier {@link io.github.nichetoolkit.rest.actuator.SupplierActuator} <p>the sql script supplier parameter is <code>SupplierActuator</code> type.</p>
     * @return {@link java.lang.String} <p>the return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     * @see io.github.nichetoolkit.rest.actuator.SupplierActuator
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static String cache(ProviderContext providerContext, MybatisTable entity, SupplierActuator<String> sqlScriptSupplier) throws RestException {
        String cacheKey = cacheKey(providerContext);
        if (!MybatisCaches.CACHE_SQL.containsKey(cacheKey)) {
            isPresentLang(providerContext);
            synchronized (cacheKey) {
                if (!MybatisCaches.CACHE_SQL.containsKey(cacheKey)) {
                    MybatisCaches.CACHE_SQL.put(cacheKey, new MybatisSqlCache(
                            Objects.requireNonNull(providerContext),
                            Objects.requireNonNull(entity),
                            Objects.requireNonNull(sqlScriptSupplier)));
                }
            }
        }
        return cacheKey;
    }

    @Override
    public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {
        /**
         * 调用过 MybatisCaching.cache 方法的，这里的 script 就是 String.intern 后的 cacheKey，可以用来加锁
         * 没有调用过 MybatisCaching.cache 方法的，属于默认方式，可能是误加 @Lang(MybatisCaching.class) 注解，这里执行 else 中默认的方式
         * 先判断 CACHE_SQL 中是否有此 script，有就是调用过 MybatisCaching.cache 方法后的 cacheKey
         */
        if (MybatisCaches.CACHE_SQL.containsKey(script)) {
            /** 为了容易理解，使用 cacheKey 变量代替 script */
            String cacheKey = script;
            /** 判断是否已经解析过 */
            if (!(MybatisCaches.CONFIGURATION_CACHE_KEY_MAP.containsKey(configuration) && MybatisCaches.CONFIGURATION_CACHE_KEY_MAP.get(configuration).containsKey(cacheKey))) {
                synchronized (cacheKey) {
                    if (!(MybatisCaches.CONFIGURATION_CACHE_KEY_MAP.containsKey(configuration) && MybatisCaches.CONFIGURATION_CACHE_KEY_MAP.get(configuration).containsKey(cacheKey))) {
                        /** 取出缓存的信息 */
                        MybatisSqlCache sqlCache = MybatisCaches.CACHE_SQL.get(cacheKey);
                        if (sqlCache == MybatisSqlCache.NULL_SQL_CACHE) {
                            throw new ConfigureLackError(script + " => CACHE_SQL is NULL, you need to configure nichetoolkit.mybatis.table.cache-sql.use-once=false");
                        }
                        /** 初始化 MybatisTable，每个方法执行一次，可以利用 configuration 进行一些特殊操作 */
                        sqlCache.table().initContext(configuration, sqlCache.context(), cacheKey);
                        Map<String, SqlSource> cacheKeyMap = MybatisCaches.CONFIGURATION_CACHE_KEY_MAP.computeIfAbsent(configuration, k -> new HashMap<>());
                        /** 定制化处理 mappedStatement */
                        MappedStatement mappedStatement = configuration.getMappedStatement(cacheKey);
                        MybatisCustomize.DEFAULT_CUSTOMIZE.customize(sqlCache.table(), mappedStatement, sqlCache.context());
                        /** 下面的方法才会真正生成最终的 XML SQL，生成的时候可以用到上面的 configuration 和 ProviderContext 参数 */
                        String sqlScript;
                        try {
                            sqlScript = sqlCache.sqlScript();
                        } catch (RestException exception) {
                            throw new MethodLackError("the sql script has error, you need to configure entity with annotation, " + exception.getMessage(),exception);
                        }
                        if (log.isTraceEnabled()) {
                            log.trace("cacheKey - " + cacheKey + " :\n" + sqlScript + "\n");
                        }
                        /** 缓存 sqlSource */
                        SqlSource sqlSource = super.createSqlSource(configuration, sqlScript, parameterType);
                        sqlSource = MybatisSqlSourceCustomize.DEFAULT_SQL_SOURCE_CUSTOMIZE.customize(sqlSource, sqlCache.table(), mappedStatement, sqlCache.context());
                        cacheKeyMap.put(cacheKey, sqlSource);
                        /** 取消cache对象的引用，减少内存占用 */
                        if (MybatisCaches.USE_ONCE) {
                            MybatisCaches.CACHE_SQL.put(cacheKey, MybatisSqlCache.NULL_SQL_CACHE);
                        }
                    }
                }
            }
            return MybatisCaches.CONFIGURATION_CACHE_KEY_MAP.get(configuration).get(cacheKey);
        } else {
            return super.createSqlSource(configuration, script, parameterType);
        }
    }



}
