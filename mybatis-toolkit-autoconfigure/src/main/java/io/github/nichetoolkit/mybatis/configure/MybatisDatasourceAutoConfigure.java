package io.github.nichetoolkit.mybatis.configure;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.zaxxer.hikari.HikariDataSource;
import io.github.nichetoolkit.mybatis.datasource.DynamicDatasource;
import io.github.nichetoolkit.mybatis.datasource.DynamicType;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
 * <p>DatasourceAutoConfigure</p>
 * @author Cyan (snow22314 @ outlook.com)
 * @version v1.0.0
 */
@Slf4j
@Configuration
@ComponentScan(basePackages = {"io.github.nichetoolkit.mybatis"})
@ConditionalOnProperty(prefix = "nichetoolkit.mybatis.datasource",  havingValue = "true", matchIfMissing = true)
public class MybatisDatasourceAutoConfigure {

    public MybatisDatasourceAutoConfigure() {
        log.debug("================= mybatis-datasource-auto-configure initiated ！ ===================");
    }

    @Configuration
    @EnableTransactionManagement
    @ConditionalOnProperty(value = "nichetoolkit.mybatis.datasource.type", havingValue = "default", matchIfMissing = true)
    public class DefaultDatasourceAutoConfigure {

        public DefaultDatasourceAutoConfigure() {
            log.debug("================= default-hikari-datasource-auto-configure initiated ！ ===================");
        }

        @Primary
        @Bean(name = "hikariDatasource")
        @ConfigurationProperties(prefix = "spring.datasource.hikari")
        @ConditionalOnMissingBean(HikariDataSource.class)
        public HikariDataSource dataSource() {
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
    @ConditionalOnProperty(value = "nichetoolkit.mybatis.datasource.type", havingValue = "dynamic", matchIfMissing = true)
    public class DynamicDatasourceAutoConfigure {

        public DynamicDatasourceAutoConfigure() {
            log.debug("================= dynamic-druid-datasource-auto-configure initiated ！ ===================");
        }

        @Bean(name = "masterDatasource")
        @ConfigurationProperties("nichetoolkit.mybatis.datasource.druid.master")
        @ConditionalOnProperty(prefix = "nichetoolkit.mybatis.datasource.type", havingValue = "dynamic", matchIfMissing = true)
        public DataSource masterDatasource(MybatisDatasourceProperties datasourceProperties) {
            DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
            druidDatasourceConfig(dataSource,datasourceProperties.getDruid());
            return dataSource;
        }

        @Bean(name = "slaveDatasource")
        @ConfigurationProperties("nichetoolkit.mybatis.datasource.druid.slave")
        @ConditionalOnProperty(prefix = "nichetoolkit.mybatis.datasource.druid.slave", name = "enabled", havingValue = "true", matchIfMissing = true)
        public DataSource slaveDatasource(MybatisDatasourceProperties datasourceProperties) {
            DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
            druidDatasourceConfig(dataSource,datasourceProperties.getDruid());
            return dataSource;
        }

        @Primary
        @Autowired(required = false)
        @Bean(name = "dynamicDatasource")
        @ConditionalOnMissingBean(DynamicDatasource.class)
        public DynamicDatasource dynamicDatasource(@Qualifier("masterDatasource") DataSource masterDatasource, @Qualifier("slaveDatasource") DataSource slaveDatasource) {
            Map<Object, Object> datasourceMap = new HashMap<>();
            datasourceMap.put(DynamicType.MASTER, masterDatasource);
            datasourceMap.put(DynamicType.SLAVE, slaveDatasource);
            return new DynamicDatasource(masterDatasource, datasourceMap);
        }

        @Primary
        @Bean(name = "dynamicDatasource")
        @ConditionalOnMissingBean(DynamicDatasource.class)
        public DynamicDatasource dynamicDatasource(@Qualifier("masterDatasource") DataSource masterDatasource) {
            Map<Object, Object> datasourceMap = new HashMap<>();
            datasourceMap.put(DynamicType.MASTER, masterDatasource);
            return new DynamicDatasource(masterDatasource, datasourceMap);
        }

        @Bean(name = "sqlSessionFactory")
        public SqlSessionFactory sqlSessionFactory(@Qualifier("dynamicDatasource") DataSource dataSource) throws Exception {
            SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
            bean.setDataSource(dataSource);
            bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/*/*.xml"));
            return bean.getObject();
        }

        @Bean(name = "transactionManager")
        public DataSourceTransactionManager transactionManager(@Qualifier("dynamicDatasource") DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }

        private void druidDatasourceConfig(DruidDataSource datasource, MybatisDruidPoolProperties druidPoolProperties) {
            /** 配置线程初始化核心数量、最小空闲数量、最大工作数量 */
            datasource.setInitialSize(druidPoolProperties.getCoreSize());
            datasource.setMaxActive(druidPoolProperties.getMaxWorkSize());
            datasource.setMinIdle(druidPoolProperties.getMinIdleSize());
            /** 配置获取连接等待超时的时间 */
            datasource.setMaxWait(druidPoolProperties.getMaxWaitTime());
            /** 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒 */
            datasource.setTimeBetweenEvictionRunsMillis(druidPoolProperties.getCheckBeatTime());
            /** 配置一个连接在池中最小、最大生存的时间，单位是毫秒 */
            datasource.setMinEvictableIdleTimeMillis(druidPoolProperties.getMinLiveTime());
            datasource.setMaxEvictableIdleTimeMillis(druidPoolProperties.getMaxLiveTime());
            /** 用来检测连接是否有效的sql，要求是一个查询语句 */
            datasource.setValidationQuery(druidPoolProperties.getTestSql());
            /** 空闲时间使用测试SQL测试链接 */
            datasource.setTestWhileIdle(druidPoolProperties.getIdleTest());
            /** 申请连接时使用测试SQL测试链接 */
            datasource.setTestOnBorrow(druidPoolProperties.getApplyTest());
            /** 归还连接时使用测试SQL测试链接 */
            datasource.setTestOnReturn(druidPoolProperties.getRevertTest());
        }

    }

    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
