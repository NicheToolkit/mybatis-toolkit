package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.configure.MybatisTableProperties;
import io.github.nichetoolkit.mybatis.enums.DatabaseType;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class MybatisProviderSupport implements InitializingBean {

    static DatabaseType DATABASE_TYPE;

    static String DATABASE_ID;

    private final MybatisTableProperties tableProperties;

    private static MybatisProviderSupport INSTANCE = null;

    @Autowired
    public MybatisProviderSupport(MybatisTableProperties tableProperties) {
        this.tableProperties = tableProperties;
    }


    public static MybatisProviderSupport instance() {
        return INSTANCE;
    }

    @Override
    public void afterPropertiesSet() {
        INSTANCE = this;
        DATABASE_TYPE = databaseType();
        DATABASE_ID = DATABASE_TYPE.getKey();
    }

    private static DatabaseType databaseType() {
        DatabaseType databaseType = DatabaseType.POSTGRESQL;
        if (GeneralUtils.isNotEmpty(INSTANCE) && GeneralUtils.isNotEmpty(INSTANCE.tableProperties)) {
            databaseType = INSTANCE.tableProperties.getDatabaseType();
        }
        return databaseType;
    }

}
