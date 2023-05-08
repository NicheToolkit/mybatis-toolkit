package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.rest.error.lack.ConfigureLackError;
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
import java.util.function.Supplier;

/**
 * <p>MybatisCache</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
@Slf4j
public class MybatisCaching extends XMLLanguageDriver {

    /**
     * 根据接口和方法生成缓存 key
     * @param providerContext 执行方法上下文
     * @return 缓存key，经过 String.intern 处理，可以作为锁对象
     */
    private static String cacheKey(ProviderContext providerContext) {
        return (providerContext.getMapperType().getName() + "." + providerContext.getMapperMethod().getName()).intern();
    }

    /**
     * 判断方法是否提供了 @Lang(Caching.class) 注解
     * @param providerContext 执行方法上下文
     */
    private static void isPresentLang(ProviderContext providerContext) {
        Method mapperMethod = providerContext.getMapperMethod();
        if (mapperMethod.isAnnotationPresent(Lang.class)) {
            Lang lang = mapperMethod.getAnnotation(Lang.class);
            if (lang.value() == MybatisCaching.class) {
                return;
            }
        }
        throw new ConfigureLackError(mapperMethod + " need to configure @Lang(MybatisCaching.class) to use the MybatisCaching.cache method for caching");
    }


    /**
     * 缓存 sqlScript 对应的 SQL 和配置
     * @param providerContext   执行方法上下文
     * @param entity            实体类信息
     * @param sqlScriptSupplier sql脚本提供者
     * @return 缓存的 key
     */
    public static String cache(ProviderContext providerContext, MybatisTable entity, Supplier<String> sqlScriptSupplier) {
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
                            throw new RuntimeException(script + " => CACHE_SQL is NULL, you need to configure nichetoolkit.mybatis.table.cache-sql.use-once=false");
                        }
                        /** 初始化 MybatisTable，每个方法执行一次，可以利用 configuration 进行一些特殊操作 */
                        sqlCache.table().initContext(configuration, sqlCache.context(), cacheKey);
                        Map<String, SqlSource> cacheKeyMap = MybatisCaches.CONFIGURATION_CACHE_KEY_MAP.computeIfAbsent(configuration, k -> new HashMap<>());
                        /** 定制化处理 mappedStatement */
                        MappedStatement mappedStatement = configuration.getMappedStatement(cacheKey);
                        MybatisCustomize.DEFAULT_CUSTOMIZE.customize(sqlCache.table(), mappedStatement, sqlCache.context());
                        /** 下面的方法才会真正生成最终的 XML SQL，生成的时候可以用到上面的 configuration 和 ProviderContext 参数 */
                        String sqlScript = sqlCache.sqlScript();
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
