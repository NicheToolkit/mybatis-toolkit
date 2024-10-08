package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.enums.DatabaseType;
import io.github.nichetoolkit.mybatis.error.MybatisProviderLackError;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.RestOptional;
import io.github.nichetoolkit.rest.actuator.PredicateActuator;
import io.github.nichetoolkit.rest.stream.RestStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class MybatisSqlProviderResolver implements ProviderMethodResolver {

    /* ProviderSqlSource 将会通过反射创建此对象 此时MybatisSqlProviderHolder并未注入 */
    public MybatisSqlProviderResolver() {
    }

    @Override
    public Method resolveMethod(ProviderContext context) {
        DatabaseType databaseType = MybatisSqlProviderHolder.defaultDatabaseType();
        log.debug("the sql provider of {} will be loaded.", databaseType.getKey());
        String providerName = context.getMapperMethod().getName();
        String message = "cannot find the provider method with name as " + providerName + ", maybe it is unsupported currently.";
        Method findMethod = null;
        try {
            List<RestSqlProvider> sqlProviders = MybatisSqlProviderHolder.defaultSqlProviders(databaseType);
            for (RestSqlProvider provider : sqlProviders) {
                Class<? extends RestSqlProvider> providerClass = provider.getClass();
                Method[] providerMethods = providerClass.getMethods();
                PredicateActuator<Method> actuator = method -> method.getName().equals(providerName)
                        && CharSequence.class.isAssignableFrom(method.getReturnType());
                RestOptional<Method> methodOptional = RestStream.stream(providerMethods).findAny(actuator);
                if (methodOptional.isPresent()) {
                    findMethod = methodOptional.get();
                    break;
                }
            }
        } catch (RestException ignored) {
        }
        return RestOptional.ofNullable(findMethod).orElseThrow(() -> new MybatisProviderLackError(message));
    }


    static class Instance {

        private static volatile Map<DatabaseType, List<RestSqlProvider>> SQL_PROVIDERS;

        static List<RestSqlProvider> sqlProviders(DatabaseType databaseType) {
            String messageOfSqlProviders = "the sql providers can not load from service loader";
            String messageOfDatabaseType = "the sql providers can not found, maybe it is unsupported with '" + databaseType.name() + "' type";
            if (SQL_PROVIDERS == null) {
                List<RestSqlProvider> sqlProviders = SpringFactoriesLoader.loadFactories(RestSqlProvider.class, null);
                RestOptional<List<RestSqlProvider>> sqlProvidersOptional = RestOptional.ofEmptyable(sqlProviders);

                SQL_PROVIDERS = sqlProviders.stream().collect(Collectors.groupingBy(RestSqlProvider::databaseType));
            }
            return SQL_PROVIDERS.get(databaseType);
        }
    }


}
