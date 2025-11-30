package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.rice.IdEntity;
import org.apache.ibatis.binding.MapperRegistry;
import org.mybatis.spring.SqlSessionTemplate;

/**
 * <code>DefaultMapperFactory</code>
 * <p>The default mapper factory class.</p>
 * @param <M> {@link io.github.nichetoolkit.mybatis.MybatisSuperMapper} <p>The generic parameter is <code>MybatisSuperMapper</code> type.</p>
 * @param <E> {@link io.github.nichetoolkit.rice.IdEntity} <p>The generic parameter is <code>IdEntity</code> type.</p>
 * @param <I> {@link java.lang.Object} <p>The parameter can be of any type.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisSuperMapper
 * @see io.github.nichetoolkit.rice.IdEntity
 * @see io.github.nichetoolkit.mybatis.MybatisMapperFactory
 * @since Jdk17
 */
public class DefaultMapperFactory<M extends MybatisSuperMapper<E, I>, E extends IdEntity<I>, I> extends MybatisMapperFactory<M,E,I> {

    /**
     * <code>sqlSessionTemplate</code>
     * {@link org.mybatis.spring.SqlSessionTemplate} <p>The <code>sqlSessionTemplate</code> field.</p>
     * @see org.mybatis.spring.SqlSessionTemplate
     */
    protected SqlSessionTemplate sqlSessionTemplate;

    /**
     * <code>DefaultMapperFactory</code>
     * <p>Instantiates a new default mapper factory.</p>
     * @param sqlSessionTemplate {@link org.mybatis.spring.SqlSessionTemplate} <p>The sql session template parameter is <code>SqlSessionTemplate</code> type.</p>
     * @see org.mybatis.spring.SqlSessionTemplate
     */
    public DefaultMapperFactory(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    @Override
    public void registryMappers() {
        MapperRegistry mapperRegistry = this.sqlSessionTemplate.getConfiguration().getMapperRegistry();
        mapperRegistry.getMappers().forEach(mapper -> cacheMapper(mapper, this.sqlSessionTemplate.getMapper(mapper)));
    }

}
