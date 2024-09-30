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
import java.util.Map;
import java.util.Objects;

@Slf4j
public class MybatisSqlSourceCaching extends XMLLanguageDriver {

    private static String cacheKey(ProviderContext providerContext) {
        return (providerContext.getMapperType().getName() + "." + providerContext.getMapperMethod().getName()).intern();
    }

    private static void isPresentLang(ProviderContext providerContext) {
        Method mapperMethod = providerContext.getMapperMethod();
        Lang lang = mapperMethod.getAnnotation(Lang.class);
        if (GeneralUtils.isEmpty(lang) || !lang.value().isAssignableFrom(MybatisSqlSourceCaching.class)) {
            throw new ConfigureLackError(mapperMethod + " need to configure @Lang(MybatisCaching.class) to use the MybatisCaching.cache method for caching");
        }
    }


    public static String cache(ProviderContext providerContext, MybatisTable entity, SupplierActuator<String> sqlScriptSupplier) throws RestException {
        String cacheKey = cacheKey(providerContext);
        if (!MybatisSqlSourceHolder.containsKey(cacheKey)) {
            isPresentLang(providerContext);
            synchronized (cacheKey) {
                if (!MybatisSqlSourceHolder.containsKey(cacheKey)) {
                    MybatisSqlSourceHolder.putSqlCache(cacheKey, new MybatisSqlCache(
                            Objects.requireNonNull(providerContext),
                            Objects.requireNonNull(entity),
                            Objects.requireNonNull(sqlScriptSupplier)));
                }
            }
        }
        return cacheKey;
    }

    @Override
    @SuppressWarnings("all")
    public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {
        /*
         * 调用过 MybatisCaching.cache 方法的，这里的 script 就是 String.intern 后的 cacheKey，可以用来加锁
         * 没有调用过 MybatisCaching.cache 方法的，属于默认方式，可能是误加 @Lang(MybatisCaching.class) 注解，这里执行 else 中默认的方式
         * 先判断 CACHE_SQL 中是否有此 script，有就是调用过 MybatisCaching.cache 方法后的 cacheKey
         */
        if (MybatisSqlSourceHolder.containsKey(script)) {
            /* 为了容易理解，使用 cacheKey 变量代替 script */
            String cacheKey = script;
            /* 判断是否已经解析过 */
            if (!(MybatisSqlSourceHolder.containsKey(configuration) && MybatisSqlSourceHolder.containsKey(configuration, cacheKey))) {
                synchronized (cacheKey) {
                    if (!(MybatisSqlSourceHolder.containsKey(configuration) && MybatisSqlSourceHolder.containsKey(configuration, cacheKey))) {
                        /* 取出缓存的信息 */
                        MybatisSqlCache sqlCache = MybatisSqlSourceHolder.getSqlCache(cacheKey);
                        if (sqlCache == MybatisSqlCache.NULL_SQL_CACHE) {
                            throw new ConfigureLackError(script + " => CACHE_SQL is NULL, you need to configure nichetoolkit.mybatis.table.cache-sql.use-once=false");
                        }
                        /* 初始化 MybatisTable，每个方法执行一次，可以利用 configuration 进行一些特殊操作 */
                        sqlCache.table().initContext(configuration, sqlCache.context(), cacheKey);
                        Map<String, SqlSource> sqlSources = MybatisSqlSourceHolder.computeIfAbsent(configuration);
                        /* 定制化处理 mappedStatement */
                        MappedStatement mappedStatement = configuration.getMappedStatement(cacheKey);
                        MybatisHandler.ofHandle(sqlCache.table(), mappedStatement, sqlCache.context());
                        /* 下面的方法才会真正生成最终的 XML SQL，生成的时候可以用到上面的 configuration 和 ProviderContext 参数 */
                        String sqlScript;
                        try {
                            sqlScript = sqlCache.sqlScript();
                        } catch (RestException exception) {
                            throw new MethodLackError("the sql script has error, you need to configure entity with annotation, " + exception.getMessage(), exception);
                        }
                        if (log.isDebugEnabled()) {
                            log.debug("cacheKey - {} :\n{}\n", cacheKey, sqlScript);
                        }
                        /* 缓存 sqlSource */
                        SqlSource sqlSource = super.createSqlSource(configuration, sqlScript, parameterType);
                        sqlSource = MybatisSqlSourceProvider.ofProvide(sqlSource, sqlCache.table(), mappedStatement, sqlCache.context());
                        sqlSources.put(cacheKey, sqlSource);
                        /* 取消cache对象的引用，减少内存占用 */
                        if (MybatisSqlSourceHolder.isUseOnce()) {
                            MybatisSqlSourceHolder.putSqlCache(cacheKey, MybatisSqlCache.NULL_SQL_CACHE);
                        }
                    }
                }
            }
            return MybatisSqlSourceHolder.getSqlSource(configuration, cacheKey);
        } else {
            return super.createSqlSource(configuration, script, parameterType);
        }
    }


}
