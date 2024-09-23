package io.github.nichetoolkit.mybatis.provider;

import io.github.nichetoolkit.mybatis.MybatisColumn;
import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.configure.MybatisTableProperties;
import io.github.nichetoolkit.mybatis.enums.DatabaseType;
import io.github.nichetoolkit.mybatis.error.MybatisUnrealizedLackError;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <code>DefaultMapperProvider</code>
 * <p>The type default mapper provider class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see org.springframework.beans.factory.InitializingBean
 * @since Jdk1.8
 */
public abstract class DefaultMapperProvider implements InitializingBean {

    /**
     * <code>tableProperties</code>
     * {@link io.github.nichetoolkit.mybatis.configure.MybatisTableProperties} <p>the <code>tableProperties</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.configure.MybatisTableProperties
     */
    private final MybatisTableProperties tableProperties;

    /**
     * <code>DefaultMapperProvider</code>
     * Instantiates a new default mapper provider.
     * @param tableProperties {@link io.github.nichetoolkit.mybatis.configure.MybatisTableProperties} <p>the table properties parameter is <code>MybatisTableProperties</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.configure.MybatisTableProperties
     * @see org.springframework.beans.factory.annotation.Autowired
     */
    @Autowired
    public DefaultMapperProvider(MybatisTableProperties tableProperties) {
        this.tableProperties = tableProperties;
    }

    /**
     * <code>INSTANCE</code>
     * {@link io.github.nichetoolkit.mybatis.provider.DefaultMapperProvider} <p>the constant <code>INSTANCE</code> field.</p>
     */
    private static DefaultMapperProvider INSTANCE = null;

    /**
     * <code>instance</code>
     * <p>the method.</p>
     * @return {@link io.github.nichetoolkit.mybatis.provider.DefaultMapperProvider} <p>the return object is <code>DefaultMapperProvider</code> type.</p>
     */
    public static DefaultMapperProvider instance() {
        return INSTANCE;
    }

    @Override
    public void afterPropertiesSet() {
        INSTANCE = this;
    }

    /**
     * <code>databaseType</code>
     * <p>the type method.</p>
     * @return {@link io.github.nichetoolkit.mybatis.enums.DatabaseType} <p>the type return object is <code>DatabaseType</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.enums.DatabaseType
     */
    public static DatabaseType databaseType() {
        DatabaseType databaseType = DatabaseType.POSTGRESQL;
        if (GeneralUtils.isNotEmpty(INSTANCE) && GeneralUtils.isNotEmpty(INSTANCE.tableProperties)) {
            databaseType = INSTANCE.tableProperties.getDatabaseType();
        }
        return databaseType;
    }

    /**
     * <code>saveUpsetSql</code>
     * <p>the upset sql method.</p>
     * @param tablename {@link java.lang.String} <p>the tablename parameter is <code>String</code> type.</p>
     * @param table     {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>the table parameter is <code>MybatisTable</code> type.</p>
     * @return {@link java.lang.String} <p>the upset sql return object is <code>String</code> type.</p>
     * @see java.lang.String
     * @see org.springframework.lang.Nullable
     * @see org.apache.ibatis.annotations.Param
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     */
    public static String saveUpsetSql(@Nullable @Param("tablename") String tablename, MybatisTable table) {
        DatabaseType databaseType = databaseType();
        List<MybatisColumn> updateColumns = table.updateColumns();
        boolean doNothing = false;
        String doUpdateSql = "";
        if (GeneralUtils.isEmpty(updateColumns)) {
            doNothing = true;
        } else {
            String dynamicTablename = table.tablename(tablename);
            doUpdateSql = table.updateColumns().stream().map(column -> column.excluded(dynamicTablename)).collect(Collectors.joining(", "));
        }
        String upsetSql;
        switch (databaseType) {
            case MARIADB:
            case SQLSEVER:
            case ORACLE:
            case SQLITE:
            case H2:
            case HSQLDB:
            case REDSHIFT:
            case CASSANDRA:
                throw new MybatisUnrealizedLackError("the function is unrealized of this database type: " + databaseType.getKey());
            case GAUSSDB:
            case MYSQL:
                if (doNothing) {
                    upsetSql = " ON DUPLICATE KEY DO NOTHING ";
                } else {
                    upsetSql = " ON DUPLICATE KEY UPDATE ";
                }
                break;
            case POSTGRESQL:
            default:
                if (doNothing) {
                    upsetSql = " ON CONFLICT (" + table.identityColumnList() + ") DO NOTHING ";
                } else {
                    upsetSql = " ON CONFLICT (" + table.identityColumnList() + ") DO UPDATE SET ";
                }
                break;
        }
        return upsetSql + doUpdateSql;
    }
}
