package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.enums.DatabaseType;
import io.github.nichetoolkit.mybatis.error.MybatisProviderLackError;
import io.github.nichetoolkit.rest.stream.RestStream;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rest.util.OptionalUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class MybatisSqlProviderResolver implements ProviderMethodResolver {

    private static final Map<String,Method> SQL_PROVIDER_METHOD_CACHES = new ConcurrentHashMap<>();

    private Method resolveMethod(String providerName,String message) {
        Method findMethod = null;
        if (GeneralUtils.isNotEmpty(SQL_PROVIDER_METHOD_CACHES)) {
            findMethod = SQL_PROVIDER_METHOD_CACHES.get(providerName);
            OptionalUtils.ofNullError(findMethod,message,MybatisProviderLackError::new);
        }
        return findMethod;
    }

    @Override
    public Method resolveMethod(ProviderContext context) {
        String providerName = context.getMapperMethod().getName();
        String message = "Cannot find the provider method with name as " + providerName + ", maybe it is unsupported currently.";
        Method findMethod = resolveMethod(providerName,message);
        if (GeneralUtils.isNotNull(findMethod)) {
            return findMethod;
        }
        DatabaseType databaseType = MybatisSqlProviderHolder.defaultDatabaseType();
        try {
            List<MybatisSqlProvider> sqlProviders = MybatisSqlProviderHolder.defaultSqlProviders(databaseType);
            OptionalUtils.ofEmptyError(sqlProviders,message,MybatisProviderLackError::new);
            RestStream.stream(sqlProviders).forEach(provider -> {
                Method[] methods = provider.getClass().getMethods();
                RestStream.stream(methods).forEach(method -> {
                    String methodName = method.getName();
                    if (SQL_PROVIDER_METHOD_CACHES.containsKey(methodName)) {
                        SQL_PROVIDER_METHOD_CACHES.replace(methodName,method);
                    } else {
                        SQL_PROVIDER_METHOD_CACHES.put(methodName,method);
                    }
                });
            });
        } catch (Exception ignored) {
        }
        return resolveMethod(providerName,message);
    }

}
