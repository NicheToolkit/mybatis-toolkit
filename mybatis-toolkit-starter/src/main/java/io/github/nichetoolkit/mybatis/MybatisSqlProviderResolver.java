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

import java.lang.reflect.Method;
import java.util.List;

@Slf4j
public class MybatisSqlProviderResolver implements ProviderMethodResolver {

    @Override
    public Method resolveMethod(ProviderContext context) {
        DatabaseType databaseType = MybatisSqlProviderHolder.defaultDatabaseType();
        log.debug("the sql provider of {} will be loaded.", databaseType.getKey());
        String providerName = context.getMapperMethod().getName();
        String message = "cannot find the provider method with name as " + providerName + ", maybe it is unsupported currently.";
        Method findMethod = null;
        try {
            List<MybatisSqlProvider> sqlProviders = MybatisSqlProviderHolder.defaultSqlProviders(databaseType);
            for (MybatisSqlProvider provider : sqlProviders) {
                Class<? extends MybatisSqlProvider> providerClass = provider.getClass();
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

}
