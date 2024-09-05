package io.github.nichetoolkit.mybatis.configure;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.zaxxer.hikari.HikariDataSource;
import io.github.nichetoolkit.mybatis.datasource.DruidRoutingDatasource;
import io.github.nichetoolkit.mybatis.datasource.DruidRoutingType;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * <code>MybatisDatasourceAutoConfigure</code>
 * <p>The type mybatis datasource auto configure class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see lombok.extern.slf4j.Slf4j
 * @see org.springframework.context.annotation.Configuration
 * @see org.springframework.context.annotation.ComponentScan
 * @see org.springframework.boot.context.properties.EnableConfigurationProperties
 * @see org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
 * @since Jdk1.8
 */
@Slf4j
@Configuration
@ComponentScan(basePackages = {"io.github.nichetoolkit.mybatis"})
@EnableConfigurationProperties({MybatisDatasourceProperties.class, MybatisDruidPoolProperties.class})
@ConditionalOnProperty(prefix = "nichetoolkit.mybatis.datasource", name="enabled",  havingValue = "true", matchIfMissing = true)
public class MybatisDatasourceAutoConfigure {

    /**
     * <code>MybatisDatasourceAutoConfigure</code>
     * Instantiates a new mybatis datasource auto configure.
     */
    public MybatisDatasourceAutoConfigure() {
        log.debug("================= mybatis-datasource-auto-configure initiated ！ ===================");
    }

    /**
     * <code>HikariDatasourceAutoConfigure</code>
     * <p>The type hikari datasource auto configure class.</p>
     * @author Cyan (snow22314@outlook.com)
     * @see org.springframework.context.annotation.Configuration
     * @see org.springframework.transaction.annotation.EnableTransactionManagement
     * @see org.springframework.boot.autoconfigure.condition.ConditionalOnClass
     * @see org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
     * @since Jdk1.8
     */
    @Configuration
    @EnableTransactionManagement
    @ConditionalOnClass(HikariDataSource.class)
    @ConditionalOnProperty(prefix = "nichetoolkit.mybatis.datasource", name="type", havingValue = "hikari", matchIfMissing = true)
    public static class HikariDatasourceAutoConfigure {

        /**
         * <code>HikariDatasourceAutoConfigure</code>
         * Instantiates a new hikari datasource auto configure.
         */
        public HikariDatasourceAutoConfigure() {
            log.debug("================= hikari-datasource-auto-configure initiated ！ ===================");
        }

        /**
         * <code>hikariDatasource</code>
         * <p>the datasource method.</p>
         * @return {@link com.zaxxer.hikari.HikariDataSource} <p>the datasource return object is <code>HikariDataSource</code> type.</p>
         * @see com.zaxxer.hikari.HikariDataSource
         * @see org.springframework.context.annotation.Primary
         * @see org.springframework.context.annotation.Bean
         * @see org.springframework.boot.context.properties.ConfigurationProperties
         * @see org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
         */
        @Primary
        @Bean(name = "hikariDatasource")
        @ConfigurationProperties(prefix = "spring.datasource.hikari")
        @ConditionalOnMissingBean(HikariDataSource.class)
        public HikariDataSource hikariDatasource() {
            return DataSourceBuilder.create().type(HikariDataSource.class).build();
        }

        /**
         * <code>sqlSessionFactory</code>
         * <p>the session factory method.</p>
         * @param dataSource {@link javax.sql.DataSource} <p>the data source parameter is <code>DataSource</code> type.</p>
         * @return {@link org.apache.ibatis.session.SqlSessionFactory} <p>the session factory return object is <code>SqlSessionFactory</code> type.</p>
         * @throws Exception {@link java.lang.Exception} <p>the exception is <code>Exception</code> type.</p>
         * @see javax.sql.DataSource
         * @see org.springframework.beans.factory.annotation.Qualifier
         * @see org.apache.ibatis.session.SqlSessionFactory
         * @see org.springframework.context.annotation.Bean
         * @see java.lang.Exception
         */
        @Bean(name = "sqlSessionFactory")
        public SqlSessionFactory sqlSessionFactory(@Qualifier("hikariDatasource") DataSource dataSource) throws Exception {
            SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
            bean.setDataSource(dataSource);
            bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/*/*.xml"));
            return bean.getObject();
        }

        /**
         * <code>transactionManager</code>
         * <p>the manager method.</p>
         * @param dataSource {@link javax.sql.DataSource} <p>the data source parameter is <code>DataSource</code> type.</p>
         * @return {@link org.springframework.jdbc.datasource.DataSourceTransactionManager} <p>the manager return object is <code>DataSourceTransactionManager</code> type.</p>
         * @see javax.sql.DataSource
         * @see org.springframework.beans.factory.annotation.Qualifier
         * @see org.springframework.jdbc.datasource.DataSourceTransactionManager
         * @see org.springframework.context.annotation.Bean
         */
        @Bean(name = "transactionManager")
        public DataSourceTransactionManager transactionManager(@Qualifier("hikariDatasource") DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }

    }


    /**
     * <code>DruidDatasourceAutoConfigure</code>
     * <p>The type druid datasource auto configure class.</p>
     * @author Cyan (snow22314@outlook.com)
     * @see org.springframework.context.annotation.Configuration
     * @see org.springframework.transaction.annotation.EnableTransactionManagement
     * @see org.springframework.boot.autoconfigure.condition.ConditionalOnClass
     * @see org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
     * @since Jdk1.8
     */
    @Configuration
    @EnableTransactionManagement
    @ConditionalOnClass(DruidDataSource.class)
    @ConditionalOnProperty(prefix = "nichetoolkit.mybatis.datasource", name = "type", havingValue = "druid", matchIfMissing = true)
    public static class DruidDatasourceAutoConfigure {

        /**
         * <code>DruidDatasourceAutoConfigure</code>
         * Instantiates a new druid datasource auto configure.
         */
        public DruidDatasourceAutoConfigure() {
            log.debug("================= druid-datasource-auto-configure initiated ！ ===================");
        }

        /**
         * <code>masterDatasource</code>
         * <p>the datasource method.</p>
         * @param druidPoolProperties {@link io.github.nichetoolkit.mybatis.configure.MybatisDruidPoolProperties} <p>the druid pool properties parameter is <code>MybatisDruidPoolProperties</code> type.</p>
         * @return {@link javax.sql.DataSource} <p>the datasource return object is <code>DataSource</code> type.</p>
         * @see io.github.nichetoolkit.mybatis.configure.MybatisDruidPoolProperties
         * @see javax.sql.DataSource
         * @see org.springframework.context.annotation.Bean
         * @see org.springframework.boot.context.properties.ConfigurationProperties
         * @see org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
         */
        @Bean(name = "masterDatasource")
        @ConfigurationProperties("nichetoolkit.mybatis.datasource.druid.master")
        @ConditionalOnProperty(prefix = "nichetoolkit.mybatis.datasource.druid.master", name = "enabled", havingValue = "true", matchIfMissing = true)
        public DataSource masterDatasource(MybatisDruidPoolProperties druidPoolProperties) {
            DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
            druidDatasourceConfig(dataSource,druidPoolProperties);
            return dataSource;
        }

        /**
         * <code>slaveDatasource</code>
         * <p>the datasource method.</p>
         * @param druidPoolProperties {@link io.github.nichetoolkit.mybatis.configure.MybatisDruidPoolProperties} <p>the druid pool properties parameter is <code>MybatisDruidPoolProperties</code> type.</p>
         * @return {@link javax.sql.DataSource} <p>the datasource return object is <code>DataSource</code> type.</p>
         * @see io.github.nichetoolkit.mybatis.configure.MybatisDruidPoolProperties
         * @see javax.sql.DataSource
         * @see org.springframework.context.annotation.Bean
         * @see org.springframework.boot.context.properties.ConfigurationProperties
         * @see org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
         */
        @Bean(name = "slaveDatasource")
        @ConfigurationProperties("nichetoolkit.mybatis.datasource.druid.slave")
        @ConditionalOnProperty(prefix = "nichetoolkit.mybatis.datasource.druid.slave", name = "enabled", havingValue = "true", matchIfMissing = true)
        public DataSource slaveDatasource(MybatisDruidPoolProperties druidPoolProperties) {
            DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
            druidDatasourceConfig(dataSource,druidPoolProperties);
            return dataSource;
        }

        /**
         * <code>druidDatasource</code>
         * <p>the datasource method.</p>
         * @param masterDatasource {@link javax.sql.DataSource} <p>the master datasource parameter is <code>DataSource</code> type.</p>
         * @param slaveDatasource  {@link javax.sql.DataSource} <p>the slave datasource parameter is <code>DataSource</code> type.</p>
         * @return {@link io.github.nichetoolkit.mybatis.datasource.DruidRoutingDatasource} <p>the datasource return object is <code>DruidRoutingDatasource</code> type.</p>
         * @see javax.sql.DataSource
         * @see org.springframework.beans.factory.annotation.Qualifier
         * @see io.github.nichetoolkit.mybatis.datasource.DruidRoutingDatasource
         * @see org.springframework.context.annotation.Primary
         * @see org.springframework.beans.factory.annotation.Autowired
         * @see org.springframework.context.annotation.Bean
         * @see org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
         */
        @Primary
        @Autowired(required = false)
        @Bean(name = "druidDatasource")
        @ConditionalOnMissingBean(DruidRoutingDatasource.class)
        public DruidRoutingDatasource druidDatasource(@Qualifier("masterDatasource") DataSource masterDatasource, @Qualifier("slaveDatasource") DataSource slaveDatasource) {
            Map<Object, Object> datasourceMap = new HashMap<>();
            datasourceMap.put(DruidRoutingType.MASTER, masterDatasource);
            datasourceMap.put(DruidRoutingType.SLAVE, slaveDatasource);
            return new DruidRoutingDatasource(masterDatasource, datasourceMap);
        }

        /**
         * <code>masterDatasource</code>
         * <p>the datasource method.</p>
         * @param masterDatasource {@link javax.sql.DataSource} <p>the master datasource parameter is <code>DataSource</code> type.</p>
         * @return {@link io.github.nichetoolkit.mybatis.datasource.DruidRoutingDatasource} <p>the datasource return object is <code>DruidRoutingDatasource</code> type.</p>
         * @see javax.sql.DataSource
         * @see org.springframework.beans.factory.annotation.Qualifier
         * @see io.github.nichetoolkit.mybatis.datasource.DruidRoutingDatasource
         * @see org.springframework.context.annotation.Primary
         * @see org.springframework.beans.factory.annotation.Autowired
         * @see org.springframework.context.annotation.Bean
         * @see org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
         */
        @Primary
        @Autowired(required = false)
        @Bean(name = "druidDatasource")
        @ConditionalOnMissingBean(DruidRoutingDatasource.class)
        public DruidRoutingDatasource masterDatasource(@Qualifier("masterDatasource") DataSource masterDatasource) {
            Map<Object, Object> datasourceMap = new HashMap<>();
            datasourceMap.put(DruidRoutingType.MASTER, masterDatasource);
            return new DruidRoutingDatasource(masterDatasource, datasourceMap);
        }

        /**
         * <code>slaveDatasource</code>
         * <p>the datasource method.</p>
         * @param slaveDatasource {@link javax.sql.DataSource} <p>the slave datasource parameter is <code>DataSource</code> type.</p>
         * @return {@link io.github.nichetoolkit.mybatis.datasource.DruidRoutingDatasource} <p>the datasource return object is <code>DruidRoutingDatasource</code> type.</p>
         * @see javax.sql.DataSource
         * @see org.springframework.beans.factory.annotation.Qualifier
         * @see io.github.nichetoolkit.mybatis.datasource.DruidRoutingDatasource
         * @see org.springframework.context.annotation.Primary
         * @see org.springframework.beans.factory.annotation.Autowired
         * @see org.springframework.context.annotation.Bean
         * @see org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
         */
        @Primary
        @Autowired(required = false)
        @Bean(name = "druidDatasource")
        @ConditionalOnMissingBean(DruidRoutingDatasource.class)
        public DruidRoutingDatasource slaveDatasource(@Qualifier("slaveDatasource") DataSource slaveDatasource) {
            Map<Object, Object> datasourceMap = new HashMap<>();
            datasourceMap.put(DruidRoutingType.SLAVE, slaveDatasource);
            return new DruidRoutingDatasource(slaveDatasource, datasourceMap);
        }

        /**
         * <code>sqlSessionFactory</code>
         * <p>the session factory method.</p>
         * @param druidDatasource {@link javax.sql.DataSource} <p>the druid datasource parameter is <code>DataSource</code> type.</p>
         * @return {@link org.apache.ibatis.session.SqlSessionFactory} <p>the session factory return object is <code>SqlSessionFactory</code> type.</p>
         * @throws Exception {@link java.lang.Exception} <p>the exception is <code>Exception</code> type.</p>
         * @see javax.sql.DataSource
         * @see org.springframework.beans.factory.annotation.Qualifier
         * @see org.apache.ibatis.session.SqlSessionFactory
         * @see org.springframework.context.annotation.Bean
         * @see java.lang.Exception
         */
        @Bean(name = "sqlSessionFactory")
        public SqlSessionFactory sqlSessionFactory(@Qualifier("druidDatasource") DataSource druidDatasource) throws Exception {
            SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
            bean.setDataSource(druidDatasource);
            bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/*/*.xml"));
            return bean.getObject();
        }

        /**
         * <code>transactionManager</code>
         * <p>the manager method.</p>
         * @param druidDatasource {@link javax.sql.DataSource} <p>the druid datasource parameter is <code>DataSource</code> type.</p>
         * @return {@link org.springframework.jdbc.datasource.DataSourceTransactionManager} <p>the manager return object is <code>DataSourceTransactionManager</code> type.</p>
         * @see javax.sql.DataSource
         * @see org.springframework.beans.factory.annotation.Qualifier
         * @see org.springframework.jdbc.datasource.DataSourceTransactionManager
         * @see org.springframework.context.annotation.Bean
         */
        @Bean(name = "transactionManager")
        public DataSourceTransactionManager transactionManager(@Qualifier("druidDatasource") DataSource druidDatasource) {
            return new DataSourceTransactionManager(druidDatasource);
        }

        /**
         * <code>druidDatasourceConfig</code>
         * <p>the datasource config method.</p>
         * @param datasource          {@link com.alibaba.druid.pool.DruidDataSource} <p>the datasource parameter is <code>DruidDataSource</code> type.</p>
         * @param druidPoolProperties {@link io.github.nichetoolkit.mybatis.configure.MybatisDruidPoolProperties} <p>the druid pool properties parameter is <code>MybatisDruidPoolProperties</code> type.</p>
         * @see com.alibaba.druid.pool.DruidDataSource
         * @see io.github.nichetoolkit.mybatis.configure.MybatisDruidPoolProperties
         */
        private void druidDatasourceConfig(DruidDataSource datasource, MybatisDruidPoolProperties druidPoolProperties) {
            /** 配置线程初始化核心数量、最小空闲数量、最大工作数量 */
            datasource.setInitialSize(druidPoolProperties.getInitialSize());
            datasource.setMaxActive(druidPoolProperties.getMaxActive());
            datasource.setMinIdle(druidPoolProperties.getMinIdle());
            /** 配置获取连接等待超时的时间 */
            datasource.setMaxWait(druidPoolProperties.getMaxWait());
            /** 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒 */
            datasource.setTimeBetweenEvictionRunsMillis(druidPoolProperties.getTimeBetweenEvictionRunsMillis());
            /** 配置一个连接在池中最小、最大生存的时间，单位是毫秒 */
            datasource.setMinEvictableIdleTimeMillis(druidPoolProperties.getMinEvictableIdleTimeMillis());
            datasource.setMaxEvictableIdleTimeMillis(druidPoolProperties.getMaxEvictableIdleTimeMillis());
            /** 用来检测连接是否有效的sql，要求是一个查询语句 */
            datasource.setValidationQuery(druidPoolProperties.getValidationQuery());
            /** 空闲时间使用测试SQL测试链接 */
            datasource.setTestWhileIdle(druidPoolProperties.getTestWhileIdle());
            /** 申请连接时使用测试SQL测试链接 */
            datasource.setTestOnBorrow(druidPoolProperties.getTestOnBorrow());
            /** 归还连接时使用测试SQL测试链接 */
            datasource.setTestOnReturn(druidPoolProperties.getTestOnReturn());
        }

    }

    /**
     * <code>sqlSessionTemplate</code>
     * <p>the session template method.</p>
     * @param sqlSessionFactory {@link org.apache.ibatis.session.SqlSessionFactory} <p>the sql session factory parameter is <code>SqlSessionFactory</code> type.</p>
     * @return {@link org.mybatis.spring.SqlSessionTemplate} <p>the session template return object is <code>SqlSessionTemplate</code> type.</p>
     * @see org.apache.ibatis.session.SqlSessionFactory
     * @see org.springframework.beans.factory.annotation.Qualifier
     * @see org.mybatis.spring.SqlSessionTemplate
     * @see org.springframework.context.annotation.Bean
     */
    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
