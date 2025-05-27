package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.rice.IdEntity;
import org.apache.ibatis.binding.MapperRegistry;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class DefaultMapperFactory<M extends MybatisSuperMapper<E, I>, E extends IdEntity<I>, I> extends MybatisMapperFactory<M,E,I> {

    protected SqlSessionTemplate sqlSessionTemplate;

    public DefaultMapperFactory(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    @Override
    protected void registryMappers() {
        MapperRegistry mapperRegistry = this.sqlSessionTemplate.getConfiguration().getMapperRegistry();
        mapperRegistry.getMappers().forEach(mapper -> cacheMapper(mapper, this.sqlSessionTemplate.getMapper(mapper)));
    }

}
