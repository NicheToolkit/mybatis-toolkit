package io.github.nichetoolkit.mybatis.provider;

import io.github.nichetoolkit.mybatis.MybatisSqlProviders;
import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.error.MybatisParamErrorException;
import io.github.nichetoolkit.mybatis.error.MybatisTableErrorException;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.actuator.ConsumerActuator;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rest.util.OptionalUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderContext;

import java.util.Collection;

public class MybatisAlertProvider implements MybatisProviderResolver {

    public static <I> String alertById(ProviderContext providerContext, @Param("id") I id, @Param("key") Integer key) throws RestException {
        return alertDynamicById(providerContext, null, id, key);
    }

    public static <I> String alertDynamicById(ProviderContext providerContext, @Param("tablename") String tablename, @Param("id") I id, @Param("key") Integer key) throws RestException {
        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(id), "the id param of 'alertById' method cannot be empty!", message -> new MybatisParamErrorException("alertById", "id", message));
        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(key), "the key param of 'alertById' method cannot be empty!", message -> new MybatisParamErrorException("alertById", "key", message));
        ConsumerActuator<MybatisTable> tableOptional = table -> {
            OptionalUtils.ofEmpty(table.getAlertColumn(), "the alert column of table with 'alertById' method cannot be empty!", message -> new MybatisTableErrorException("alertById", "alertColumn", message));
        };
        return MybatisSqlProviders.providing(providerContext, tablename, id, tableOptional, MybatisSqlProviders.ALERT_BY_ID);
    }

    public static <I> String alertAll(ProviderContext providerContext, @Param("idList") Collection<I> idList, @Param("key") Integer key) throws RestException {
        return alertDynamicAll(providerContext, null, idList, key);
    }

    public static <I> String alertDynamicAll(ProviderContext providerContext, @Param("tablename") String tablename, @Param("idList") Collection<I> idList, @Param("key") Integer key) throws RestException {
        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(idList), "the id list param of 'alertAll' method cannot be empty!", message -> new MybatisParamErrorException("alertAll", "idList", message));
        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(key), "the key param of 'alertAll' method cannot be empty!", message -> new MybatisParamErrorException("alertAll", "key", message));
        ConsumerActuator<MybatisTable> tableOptional = table -> {
            OptionalUtils.ofEmpty(table.getAlertColumn(), "the alert column of table with 'alertAll' method cannot be empty!", message -> new MybatisTableErrorException("alertAll", "alertColumn", message));
        };
        return MybatisSqlProviders.providing(providerContext, tablename, idList, tableOptional, MybatisSqlProviders.ALERT_ALL);
    }

}
