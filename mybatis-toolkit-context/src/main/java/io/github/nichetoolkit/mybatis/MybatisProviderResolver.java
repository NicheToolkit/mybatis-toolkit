package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.error.MybatisParamErrorException;
import io.github.nichetoolkit.mybatis.error.MybatisProviderLackError;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.RestOptional;
import io.github.nichetoolkit.rest.actuator.PredicateActuator;
import io.github.nichetoolkit.rest.stream.RestStream;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;


public interface MybatisProviderResolver extends ProviderMethodResolver {

    @SuppressWarnings("unchecked")
    static <I> Object resolveParameter(I parameter) throws RestException {
        Class<?> parameterClass = parameter.getClass();
        if (Map.class.isAssignableFrom(parameterClass)) {
            Map<String, ?> param = (Map<String, ?>) parameter;
            Optional<?> firstParam = param.values().stream().findFirst();
            return firstParam.orElseThrow(MybatisParamErrorException::new);
        } else {
            return parameter;
        }
    }

    @Override
    default Method resolveMethod(ProviderContext context) {
        Class<? extends MybatisProviderResolver> providerClass = this.getClass();
        Method[] providerMethods = providerClass.getMethods();
        String providerName = context.getMapperMethod().getName();
        String message = "cannot resolve the provider method because '" + providerName
                + "' does not return the CharSequence or its subclass in SqlProvider '" + providerClass.getName() + "'.";
        try {
            PredicateActuator<Method> actuator = method -> method.getName().equals(providerName)
                            && CharSequence.class.isAssignableFrom(method.getReturnType());
            RestOptional<Method> methodOptional = RestStream.stream(providerMethods).findAny(actuator);
            return methodOptional.nullElseThrow(() -> new MybatisProviderLackError(message));
        } catch (RestException ignored) {
            throw new MybatisProviderLackError(message);
        }
    }
}
