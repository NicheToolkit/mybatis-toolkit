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
 * <p>MybatisDatasourceAutoConfigure</p>
 * @author Cyan (snow22314 @ outlook.com)
 * @version v1.0.0
 */
@Slf4j
@Configuration
@ComponentScan(basePackages = {"io.github.nichetoolkit.mybatis"})
@EnableConfigurationProperties({MybatisDatasourceProperties.class, MybatisDruidPoolProperties.class})
@ConditionalOnProperty(prefix = "nichetoolkit.mybatis.datasource", name="enabled",  havingValue = "true", matchIfMissing = true)
public class MybatisDatasourceAutoConfigure {

    public MybatisDatasourceAutoConfigure() {
        log.debug("================= mybatis-datasource-auto-configure initiated ！ ===================");
    }

    @Configuration
    @EnableTransactionManagement
    @ConditionalOnClass(HikariDataSource.class)
    @ConditionalOnProperty(prefix = "nichetoolkit.mybatis.datasource", name="type", havingValue = "hikari", matchIfMissing = true)
    public static class HikariDatasourceAutoConfigure {

        public HikariDatasourceAutoConfigure() {
            log.debug("================= hikari-datasource-auto-configure initiated ！ ===================");
        }

        @Primary
        @Bean(name = "hikariDatasource")
        @ConfigurationProperties(prefix = "spring.datasource.hikari")
        @ConditionalOnMissingBean(HikariDataSource.class)
        public HikariDataSource hikariDatasource() {
            return DataSourceBuilder.create().type(HikariDataSource.class).build();
        }

        @Bean(name = "sqlSessionFactory")
        public SqlSessionFactory sqlSessionFactory(@Qualifier("hikariDatasource") DataSource dataSource) throws Exception {
            SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
            bean.setDataSource(dataSource);
            bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/*/*.xml"));
            return bean.getObject();
        }

        @Bean(name = "transactionManager")
        public DataSourceTransactionManager transactionManager(@Qualifier("hikariDatasource") DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }

    }


    @Configuration
    @EnableTransactionManagement
    @ConditionalOnClass(DruidDataSource.class)
    @ConditionalOnProperty(prefix = "nichetoolkit.mybatis.datasource", name = "type", havingValue = "druid", matchIfMissing = true)
    public static class DruidDatasourceAutoConfigure {

        public DruidDatasourceAutoConfigure() {
            log.debug("================= druid-datasource-auto-configure initiated ！ ===================");
        }

        @Bean(name = "masterDatasource")
        @ConfigurationProperties("nichetoolkit.mybatis.datasource.druid.master")
        @ConditionalOnProperty(prefix = "nichetoolkit.mybatis.datasource.druid.master", name = "enabled", havingValue = "true", matchIfMissing = true)
        public DataSource masterDatasource(MybatisDruidPoolProperties druidPoolProperties) {
            DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
            druidDatasourceConfig(dataSource,druidPoolProperties);
            return dataSource;
        }

        @Bean(name = "slaveDatasource")
        @ConfigurationProperties("nichetoolkit.mybatis.datasource.druid.slave")
        @ConditionalOnProperty(prefix = "nichetoolkit.mybatis.datasource.druid.slave", name = "enabled", havingValue = "true", matchIfMissing = true)
        public DataSource slaveDatasource(MybatisDruidPoolProperties druidPoolProperties) {
            DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
            druidDatasourceConfig(dataSource,druidPoolProperties);
            return dataSource;
        }

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

        @Primary
        @Autowired(required = false)
        @Bean(name = "druidDatasource")
        @ConditionalOnMissingBean(DruidRoutingDatasource.class)
        public DruidRoutingDatasource masterDatasource(@Qualifier("masterDatasource") DataSource masterDatasource) {
            Map<Object, Object> datasourceMap = new HashMap<>();
            datasourceMap.put(DruidRoutingType.MASTER, masterDatasource);
            return new DruidRoutingDatasource(masterDatasource, datasourceMap);
        }

        @Primary
        @Autowired(required = false)
        @Bean(name = "druidDatasource")
        @ConditionalOnMissingBean(DruidRoutingDatasource.class)
        public DruidRoutingDatasource slaveDatasource(@Qualifier("slaveDatasource") DataSource slaveDatasource) {
            Map<Object, Object> datasourceMap = new HashMap<>();
            datasourceMap.put(DruidRoutingType.SLAVE, slaveDatasource);
            return new DruidRoutingDatasource(slaveDatasource, datasourceMap);
        }

        @Bean(name = "sqlSessionFactory")
        public SqlSessionFactory sqlSessionFactory(@Qualifier("druidDatasource") DataSource druidDatasource) throws Exception {
            SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
            bean.setDataSource(druidDatasource);
            bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/*/*.xml"));
            return bean.getObject();
        }

        @Bean(name = "transactionManager")
        public DataSourceTransactionManager transactionManager(@Qualifier("druidDatasource") DataSource druidDatasource) {
            return new DataSourceTransactionManager(druidDatasource);
        }

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

    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
