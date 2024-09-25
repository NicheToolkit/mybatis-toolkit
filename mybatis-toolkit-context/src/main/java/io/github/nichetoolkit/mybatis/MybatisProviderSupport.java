package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.configure.MybatisTableProperties;
import io.github.nichetoolkit.mybatis.enums.DatabaseType;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <code>MybatisProviderSupport</code>
 * <p>The type mybatis provider support class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see org.springframework.beans.factory.InitializingBean
 * @since Jdk1.8
 */
public abstract class MybatisProviderSupport implements InitializingBean {

    /**
     * <code>DATABASE_TYPE</code>
     * {@link io.github.nichetoolkit.mybatis.enums.DatabaseType} <p>the <code>DATABASE_TYPE</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.enums.DatabaseType
     */
    static DatabaseType DATABASE_TYPE;

    /**
     * <code>DATABASE_ID</code>
     * {@link java.lang.String} <p>the <code>DATABASE_ID</code> field.</p>
     * @see java.lang.String
     */
    static String DATABASE_ID;

    /**
     * <code>tableProperties</code>
     * {@link io.github.nichetoolkit.mybatis.configure.MybatisTableProperties} <p>the <code>tableProperties</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.configure.MybatisTableProperties
     */
    private final MybatisTableProperties tableProperties;

    /**
     * <code>INSTANCE</code>
     * {@link io.github.nichetoolkit.mybatis.MybatisProviderSupport} <p>the constant <code>INSTANCE</code> field.</p>
     */
    private static MybatisProviderSupport INSTANCE = null;

    /**
     * <code>MybatisProviderSupport</code>
     * Instantiates a new mybatis provider support.
     * @param tableProperties {@link io.github.nichetoolkit.mybatis.configure.MybatisTableProperties} <p>the table properties parameter is <code>MybatisTableProperties</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.configure.MybatisTableProperties
     * @see org.springframework.beans.factory.annotation.Autowired
     */
    @Autowired
    public MybatisProviderSupport(MybatisTableProperties tableProperties) {
        this.tableProperties = tableProperties;
    }


    /**
     * <code>instance</code>
     * <p>the method.</p>
     * @return {@link io.github.nichetoolkit.mybatis.MybatisProviderSupport} <p>the return object is <code>MybatisProviderSupport</code> type.</p>
     */
    public static MybatisProviderSupport instance() {
        return INSTANCE;
    }

    @Override
    public void afterPropertiesSet() {
        INSTANCE = this;
        DATABASE_TYPE = databaseType();
        DATABASE_ID = DATABASE_TYPE.getKey();
    }

    /**
     * <code>databaseType</code>
     * <p>the type method.</p>
     * @return {@link io.github.nichetoolkit.mybatis.enums.DatabaseType} <p>the type return object is <code>DatabaseType</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.enums.DatabaseType
     */
    private static DatabaseType databaseType() {
        DatabaseType databaseType = DatabaseType.POSTGRESQL;
        if (GeneralUtils.isNotEmpty(INSTANCE) && GeneralUtils.isNotEmpty(INSTANCE.tableProperties)) {
            databaseType = INSTANCE.tableProperties.getDatabaseType();
        }
        return databaseType;
    }

}
