package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.configure.MybatisTableProperties;
import io.github.nichetoolkit.mybatis.enums.DatabaseType;
import io.github.nichetoolkit.mybatis.error.MybatisIdentityLackError;
import io.github.nichetoolkit.mybatis.error.MybatisParamErrorException;
import io.github.nichetoolkit.mybatis.error.MybatisProviderLackError;
import io.github.nichetoolkit.mybatis.helper.ServiceLoaderHelper;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.RestOptional;
import io.github.nichetoolkit.rest.actuator.ConsumerActuator;
import io.github.nichetoolkit.rest.actuator.PredicateActuator;
import io.github.nichetoolkit.rest.stream.RestCollectors;
import io.github.nichetoolkit.rest.stream.RestStream;
import io.github.nichetoolkit.rest.util.ContextUtils;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rest.util.OptionalUtils;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public interface MybatisSqlProvider extends ProviderMethodResolver, MybatisOrder {

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

    static <I> String providing(ProviderContext providerContext, @Nullable String tablename, I id, MybatisSqlProvider sqlProvider) throws RestException {
        return providing(providerContext, tablename, id, table -> {
        }, sqlProvider);
    }

    static <I> String providing(ProviderContext providerContext, @Nullable String tablename, I id, ConsumerActuator<MybatisTable> actuator, MybatisSqlProvider sqlProvider) throws RestException {
        Object identity = resolveParameter(id);
        return MybatisSqlScript.caching(providerContext, (table, sqlScript) -> {
            actuator.actuate(table);
            if (table.isIdentity()) {
                valueOfIdentity(table, identity);
            }
            return sqlProvider.provide(tablename, table, Collections.emptyMap(), sqlScript);
        });
    }

    static <I> String providing(ProviderContext providerContext, @Nullable String tablename, Collection<I> idList, MybatisSqlProvider sqlProvider) throws RestException {
        return providing(providerContext, tablename, idList, table -> {
        }, sqlProvider);
    }

    static <I> String providing(ProviderContext providerContext, @Nullable String tablename, Collection<I> idList, ConsumerActuator<MybatisTable> actuator, MybatisSqlProvider sqlProvider) throws RestException {
        return MybatisSqlScript.caching(providerContext, (table, sqlScript) -> {
            actuator.actuate(table);
            Map<Integer, List<I>> identities = Collections.emptyMap();
            if (table.isIdentity()) {
                RestStream.stream(idList).forEach(id -> valueOfIdentity(table, id));
                identities = sliceOfIdentity(table, idList);
            }
            return sqlProvider.provide(tablename, table, identities, sqlScript);
        });
    }

    static void valueOfIdentity(MybatisTable table, Object id) throws RestException {
        Boolean logicalAnd = RestStream.stream(table.identityColumns()).map(MybatisColumn::getField)
                .map(mybatisField -> GeneralUtils.isNotEmpty(mybatisField.get(id)))
                .collect(RestCollectors.logicalOr());
        String message = "the field values of identity can not all be empty, " + table.getIdentity().getName();
        OptionalUtils.ofFalseError(logicalAnd, message, MybatisIdentityLackError::new);
    }

    static <I> Map<Integer, List<I>> sliceOfIdentity(MybatisTable table, Collection<I> idList) throws RestException {
        List<MybatisField> mybatisFields = RestStream.stream(table.identityColumns()).map(MybatisColumn::getField).collect(RestCollectors.toList());
        /*
         * mybatisFields: {a,b,c}, index: 0,1,2 indexValue: 1,2,4
         * 0: {}, 1: {a}, 2: {b}, 3: {a,b}, 4: {c} 5: {a,c}, 6: {b,c}, 7: {a,b,c}
         */
        return RestStream.stream(idList).collect(RestCollectors.groupingBy(id -> {
            int indexValue = 0;
            for (int index = 0; index < mybatisFields.size(); index++) {
                MybatisField mybatisField = mybatisFields.get(index);
                if (GeneralUtils.isValid(mybatisField.get(id))) {
                    indexValue = indexValue | -(-1 << index);
                }
            }
            return indexValue;
        }));
    }

    @Override
    default Method resolveMethod(ProviderContext context) {
        Class<? extends MybatisSqlProvider> providerClass = this.getClass();
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

    default DatabaseType databaseType() {
        MybatisTableProperties tableProperties = ContextUtils.getBean(MybatisTableProperties.class);
        return Optional.ofNullable(tableProperties).map(MybatisTableProperties::getDatabaseType).orElse(DatabaseType.POSTGRESQL);
    }

    <I> String provide(@Nullable String tablename, MybatisTable table, Map<Integer, List<I>> identities, MybatisSqlScript sqlScript) throws RestException;


    interface SimpleSqlProvider extends MybatisSqlProvider {

        @Override
        default <I> String provide(@Nullable String tablename, MybatisTable mybatisTable, Map<Integer, List<I>> identitySliceMap, MybatisSqlScript sqlScript) throws RestException {
            return provide(tablename, mybatisTable, identitySliceMap, sqlScript, this);
        }

        <I> String provide(@Nullable String tablename, MybatisTable mybatisTable, Map<Integer, List<I>> identitySliceMap, MybatisSqlScript sqlScript, MybatisSqlProvider sqlProvider) throws RestException;

    }

    class Instance {

        private static volatile Map<DatabaseType, List<MybatisSqlProvider>> SQL_PROVIDERS;

        public static List<MybatisSqlProvider> sqlProviderChain(DatabaseType databaseType) throws RestException {
            String messageOfSqlProviders = "the sql providers can not load from service loader";
            String messageOfDatabaseType = "the sql providers can not found, maybe it is unsupported with '" + databaseType.name() + "' type";
            if (SQL_PROVIDERS == null) {
                synchronized (MybatisSqlProvider.class) {
                    if (SQL_PROVIDERS == null) {
                        List<MybatisSqlProvider> sqlProviders = ServiceLoaderHelper.instances(MybatisSqlProvider.class);
                        RestOptional<List<MybatisSqlProvider>> sqlProvidersOptional = RestOptional.ofEmptyable(sqlProviders);
                        OptionalUtils.ofEmptyError(sqlProvidersOptional, messageOfSqlProviders, MybatisProviderLackError::new);
                        SQL_PROVIDERS = sqlProviders.stream().collect(Collectors.groupingBy(MybatisSqlProvider::databaseType));
                    }
                }
            }
            OptionalUtils.ofFalseError(SQL_PROVIDERS.containsKey(databaseType), messageOfDatabaseType, MybatisProviderLackError::new);
            return SQL_PROVIDERS.get(databaseType);
        }
    }


}
