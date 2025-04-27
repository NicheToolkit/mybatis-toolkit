package io.github.nichetoolkit.mybatis.provider;

import io.github.nichetoolkit.mybatis.MybatisSqlProvider;
import io.github.nichetoolkit.mybatis.enums.DatabaseType;
import io.github.nichetoolkit.mybatis.error.MybatisParamErrorException;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.RestField;
import io.github.nichetoolkit.rest.util.OptionalUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class MybatisTableProvider implements MybatisSqlProvider {

    @Override
    public List<DatabaseType> databaseTypes() {
        return Arrays.asList(DatabaseType.POSTGRESQL, DatabaseType.MYSQL, DatabaseType.GAUSSDB, DatabaseType.SQLITE);
    }

    public static String findColumns(ProviderContext providerContext, String tablename) throws RestException {
        return findTableColumns(providerContext, null);
    }

    public static String findTableColumns(ProviderContext providerContext, String tablename) throws RestException {
        return MybatisSqlProvider.providingOfTablename(providerContext, tablename);
    }

    public static String createIndex(ProviderContext providerContext, RestField<?> field) throws RestException {
        return createTableIndex(providerContext, null, field);
    }

    public static String createTableIndex(ProviderContext providerContext, String tablename, RestField<?> field) throws RestException {
        OptionalUtils.ofEmpty(field, "The field param of 'createIndex' method cannot be empty!", message -> new MybatisParamErrorException("createIndex", "field", message));
        OptionalUtils.ofEmpty(field.getKey(), "The field key param of 'createIndex' method cannot be empty!", message -> new MybatisParamErrorException("createIndex", "field key", message));
        return MybatisSqlProvider.providingOfCreateIndex(providerContext, tablename, field);
    }

    public static String dropIndex(ProviderContext providerContext, RestField<?> field) throws RestException {
        return dropTableIndex(providerContext, null, field);
    }

    public static String dropTableIndex(ProviderContext providerContext, String tablename, RestField<?> field) throws RestException {
        OptionalUtils.ofEmpty(field, "The field param of 'dropIndex' method cannot be empty!", message -> new MybatisParamErrorException("dropIndex", "field", message));
        OptionalUtils.ofEmpty(field.getKey(), "The field key param of 'dropIndex' method cannot be empty!", message -> new MybatisParamErrorException("dropIndex", "field key", message));
        return MybatisSqlProvider.providingOfDropIndex(providerContext, tablename, field);
    }

    public static String modifyColumn(ProviderContext providerContext, RestField<?> field) throws RestException {
        return modifyTableColumn(providerContext, null, field);
    }

    public static String modifyTableColumn(ProviderContext providerContext, String tablename, RestField<?> field) throws RestException {
        OptionalUtils.ofEmpty(field, "The field param of 'modifyColumn' method cannot be empty!", message -> new MybatisParamErrorException("modifyColumn", "field", message));
        OptionalUtils.ofEmpty(field.getKey(), "The field key param of 'modifyColumn' method cannot be empty!", message -> new MybatisParamErrorException("modifyColumn", "field key", message));
        OptionalUtils.ofEmpty(field.getType(), "The field type param of 'modifyColumn' method cannot be empty!", message -> new MybatisParamErrorException("modifyColumn", "field type", message));
        return MybatisSqlProvider.providingOfModifyColumn(providerContext, tablename, field);
    }

    public static String addColumn(ProviderContext providerContext, RestField<?> field) throws RestException {
        return addTableColumn(providerContext, null, field);
    }

    public static String addTableColumn(ProviderContext providerContext, String tablename, RestField<?> field) throws RestException {
        OptionalUtils.ofEmpty(field, "The field param of 'addColumn' method cannot be empty!", message -> new MybatisParamErrorException("addColumn", "field", message));
        OptionalUtils.ofEmpty(field.getKey(), "The field key param of 'addColumn' method cannot be empty!", message -> new MybatisParamErrorException("addColumn", "field key", message));
        OptionalUtils.ofEmpty(field.getType(), "The field type param of 'addColumn' method cannot be empty!", message -> new MybatisParamErrorException("addColumn", "field type", message));
        return MybatisSqlProvider.providingOfAddColumn(providerContext, tablename, field);
    }

    public static String dropColumn(ProviderContext providerContext, RestField<?> field) throws RestException {
        return dropTableColumn(providerContext, null, field);
    }

    public static String dropTableColumn(ProviderContext providerContext, String tablename, RestField<?> field) throws RestException {
        OptionalUtils.ofEmpty(field, "The field param of 'dropColumn' method cannot be empty!", message -> new MybatisParamErrorException("dropColumn", "field", message));
        OptionalUtils.ofEmpty(field.getKey(), "The field key param of 'dropColumn' method cannot be empty!", message -> new MybatisParamErrorException("dropColumn", "field key", message));
        return MybatisSqlProvider.providingOfDropColumn(providerContext, tablename, field);
    }

}
