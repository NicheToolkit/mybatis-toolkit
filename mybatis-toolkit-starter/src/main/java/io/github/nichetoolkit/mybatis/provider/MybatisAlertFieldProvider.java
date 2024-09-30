package io.github.nichetoolkit.mybatis.provider;

import io.github.nichetoolkit.mybatis.MybatisSqlProviders;
import io.github.nichetoolkit.mybatis.error.MybatisParamErrorException;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rest.util.OptionalUtils;
import org.apache.ibatis.builder.annotation.ProviderContext;

import java.util.Collection;

public class MybatisAlertFieldProvider implements MybatisProviderResolver {

    public static <I> String alertFieldById(ProviderContext providerContext, I id, String field, Integer key) throws RestException {
        return alertDynamicFieldById(providerContext, null, id, field, key);
    }

    public static <I> String alertDynamicFieldById(ProviderContext providerContext, String tablename, I id, String field, Integer key) throws RestException {
        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(id), "the id param of 'alertFieldById' method cannot be empty!", message -> new MybatisParamErrorException("alertFieldById", "id", message));
        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(key), "the field param of 'alertFieldById' method cannot be empty!", message -> new MybatisParamErrorException("alertFieldById", "field", message));
        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(key), "the key param of 'alertFieldById' method cannot be empty!", message -> new MybatisParamErrorException("alertFieldById", "key", message));
        return MybatisSqlProviders.providing(providerContext, tablename, id, MybatisSqlProviders.ALERT_FIELD_BY_ID);
    }

    public static <I> String alertFieldAll(ProviderContext providerContext, Collection<I> idList, String field, Integer key) throws RestException {
        return alertDynamicFieldAll(providerContext, null, idList, field, key);
    }

    public static <I> String alertDynamicFieldAll(ProviderContext providerContext, String tablename, Collection<I> idList, String field, Integer key) throws RestException {
        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(idList), "the id list param of 'alertFieldAll' method cannot be empty!", message -> new MybatisParamErrorException("alertFieldAll", "idList", message));
        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(field), "the field param of 'alertFieldAll' method cannot be empty!", message -> new MybatisParamErrorException("alertFieldAll", "field", message));
        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(key), "the key param of 'alertFieldAll' method cannot be empty!", message -> new MybatisParamErrorException("alertFieldAll", "key", message));
        return MybatisSqlProviders.providing(providerContext, tablename, idList, MybatisSqlProviders.ALERT_FIELD_ALL);
    }

}
