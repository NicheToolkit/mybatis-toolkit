package io.github.nichetoolkit.mybatis.provider;

import io.github.nichetoolkit.mybatis.MybatisSqlProvider;
import io.github.nichetoolkit.mybatis.MybatisSqlScript;
import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.enums.DatabaseType;
import io.github.nichetoolkit.mybatis.error.MybatisParamErrorException;
import io.github.nichetoolkit.mybatis.error.MybatisTableErrorException;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.actuator.ConsumerActuator;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rest.util.OptionalUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class MybatisAlertProvider  {






    MybatisSqlProvider ALERT_BY_ID = new MybatisSqlProvider() {
        @Override
        public DatabaseType databaseType() {
            return DatabaseType.POSTGRESQL;
        }

        @Override
        public <I> String provide(@Nullable String tablename, MybatisTable table, Map<Integer, List<I>> identitySliceMap, MybatisSqlScript sqlScript) throws RestException {
            return updateOfAlertSql(tablename, table, sqlScript) + identityWhereSql(table, sqlScript);
        }

        public <I> String alertById(ProviderContext providerContext, @Param("id") I id, @Param("key") Integer key) throws RestException {
            return alertDynamicById(providerContext, null, id, key);
        }

        public <I> String alertDynamicById(ProviderContext providerContext, @Param("tablename") String tablename, @Param("id") I id, @Param("key") Integer key) throws RestException {
            OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(id), "the id param of 'alertById' method cannot be empty!", message -> new MybatisParamErrorException("alertById", "id", message));
            OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(key), "the key param of 'alertById' method cannot be empty!", message -> new MybatisParamErrorException("alertById", "key", message));
            ConsumerActuator<MybatisTable> tableOptional = table -> {
                OptionalUtils.ofEmpty(table.getAlertColumn(), "the alert column of table with 'alertById' method cannot be empty!", message -> new MybatisTableErrorException("alertById", "alertColumn", message));
            };
            return MybatisSqlProvider.providing(providerContext, tablename, id, tableOptional, ALERT_BY_ID);
        }

    };

    MybatisSqlProvider ALERT_ALL = new MybatisSqlProvider() {

        @Override
        public DatabaseType databaseType() {
            return DatabaseType.POSTGRESQL;
        }

        @Override
        public <I> String provide(@Nullable String tablename, MybatisTable table, Map<Integer, List<I>> identitySliceMap, MybatisSqlScript sqlScript) throws RestException {
            return updateOfAlertSql(tablename, table, sqlScript) + identitiesWhereSql(identitySliceMap, table, sqlScript);
        }

        public  <I> String alertAll(ProviderContext providerContext, @Param("idList") Collection<I> idList, @Param("key") Integer key) throws RestException {
            return alertDynamicAll(providerContext, null, idList, key);
        }

        public <I> String alertDynamicAll(ProviderContext providerContext, @Param("tablename") String tablename, @Param("idList") Collection<I> idList, @Param("key") Integer key) throws RestException {
            OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(idList), "the id list param of 'alertAll' method cannot be empty!", message -> new MybatisParamErrorException("alertAll", "idList", message));
            OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(key), "the key param of 'alertAll' method cannot be empty!", message -> new MybatisParamErrorException("alertAll", "key", message));
            ConsumerActuator<MybatisTable> tableOptional = table -> {
                OptionalUtils.ofEmpty(table.getAlertColumn(), "the alert column of table with 'alertAll' method cannot be empty!", message -> new MybatisTableErrorException("alertAll", "alertColumn", message));
            };
            return MybatisSqlProvider.providing(providerContext, tablename, idList, tableOptional, ALERT_ALL);
        }

    };

}
