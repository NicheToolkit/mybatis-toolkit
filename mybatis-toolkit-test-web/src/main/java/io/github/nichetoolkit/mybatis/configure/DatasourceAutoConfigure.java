package io.github.nichetoolkit.mybatis.configure;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * <code>DatasourceAutoConfigure</code>
 * <p>The type datasource auto configure class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see Slf4j
 * @see Configuration
 * @see EnableTransactionManagement
 * @since Jdk1.8
 */
@Slf4j
@Configuration
@EnableTransactionManagement
public class DatasourceAutoConfigure {
    /**
     * <code>DatasourceAutoConfigure</code>
     * Instantiates a new datasource auto configure.
     */
    public DatasourceAutoConfigure() {
        log.debug("================= datasource-auto-configure initiated ！ ===================");
    }

    /**
     * <code>dataSource</code>
     * <p>the source method.</p>
     * @return {@link HikariDataSource} <p>the source return object is <code>HikariDataSource</code> type.</p>
     * @see HikariDataSource
     * @see Primary
     * @see Bean
     * @see ConfigurationProperties
     * @see ConditionalOnMissingBean
     */
    @Primary
    @Bean(name = "hikariDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    @ConditionalOnMissingBean(HikariDataSource.class)
    public HikariDataSource dataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    /**
     * <code>sqlSessionFactory</code>
     * <p>the session factory method.</p>
     * @param dataSource {@link DataSource} <p>the data source parameter is <code>DataSource</code> type.</p>
     * @return {@link SqlSessionFactory} <p>the session factory return object is <code>SqlSessionFactory</code> type.</p>
     * @throws Exception {@link Exception} <p>the exception is <code>Exception</code> type.</p>
     * @see DataSource
     * @see Qualifier
     * @see SqlSessionFactory
     * @see Bean
     * @see Exception
     */
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("hikariDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/*/*.xml"));
        return bean.getObject();
    }

    /**
     * <code>transactionManager</code>
     * <p>the manager method.</p>
     * @param dataSource {@link DataSource} <p>the data source parameter is <code>DataSource</code> type.</p>
     * @return {@link DataSourceTransactionManager} <p>the manager return object is <code>DataSourceTransactionManager</code> type.</p>
     * @see DataSource
     * @see Qualifier
     * @see DataSourceTransactionManager
     * @see Bean
     */
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("hikariDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * <code>sqlSessionTemplate</code>
     * <p>the session template method.</p>
     * @param sqlSessionFactory {@link SqlSessionFactory} <p>the sql session factory parameter is <code>SqlSessionFactory</code> type.</p>
     * @return {@link SqlSessionTemplate} <p>the session template return object is <code>SqlSessionTemplate</code> type.</p>
     * @see SqlSessionFactory
     * @see Qualifier
     * @see SqlSessionTemplate
     * @see Bean
     */
    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
