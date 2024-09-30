package io.github.nichetoolkit.mybatis.record;

import io.github.nichetoolkit.mybatis.MybatisClassFinder;
import io.github.nichetoolkit.mybatis.error.MybatisProviderLackError;
import io.github.nichetoolkit.mybatis.mapper.MybatisSuperMapper;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rice.IdEntity;
import org.apache.ibatis.binding.MapperRegistry;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MybatisRecordProvider<M extends MybatisSuperMapper<E, I>, E extends IdEntity<I>, I> implements ApplicationContextAware, ApplicationListener<ContextRefreshedEvent> {

    protected final Map<Class<?>, MybatisSuperMapper<E, I>> SUPER_MAPPER_CACHE = new ConcurrentHashMap<>();

    protected static ApplicationContext applicationContext;

    protected SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    public MybatisRecordProvider(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        MybatisRecordProvider.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
        registryMappers();
    }

    public static <M extends MybatisSuperMapper<E, I>, E extends IdEntity<I>, I> MybatisRecordProvider<M, E, I> defaultInstance() {
        return MybatisMapperProviderInstance.instance();
    }

    @SuppressWarnings(value = "unchecked")
    public static <M extends MybatisSuperMapper<E, I>, E extends IdEntity<I>, I> MybatisRecordProvider<M, E, I> getInstance(String instanceName) {
        return (MybatisRecordProvider<M, E, I>) applicationContext.getBean(instanceName);
    }

    protected void registryMappers() {
        MapperRegistry mapperRegistry = this.sqlSessionTemplate.getConfiguration().getMapperRegistry();
        mapperRegistry.getMappers().forEach(mapper -> cacheMapper(mapper, this.sqlSessionTemplate.getMapper(mapper)));
    }

    @SuppressWarnings(value = "unchecked")
    public void cacheMapper(Class<?> mapperClass, Object mapper) {
        if (GeneralUtils.isEmpty(mapperClass) && GeneralUtils.isEmpty(mapper)) {
            return;
        }
        if (MybatisSuperMapper.class.isAssignableFrom(mapperClass)) {
            Class<?> entityClass = MybatisClassFinder.findEntityClass(mapperClass, null);
            if (GeneralUtils.isNotNull(entityClass) && !SUPER_MAPPER_CACHE.containsKey(entityClass)) {
                SUPER_MAPPER_CACHE.put(entityClass, (MybatisSuperMapper<E, I>) mapper);
            }
        }
    }

    @SuppressWarnings(value = "unchecked")
    public M superMapper(Class<E> entityClazz) {
        if (!SUPER_MAPPER_CACHE.containsKey(entityClazz)) {
            synchronized (this) {
                if (!SUPER_MAPPER_CACHE.containsKey(entityClazz)) {
                    this.registryMappers();
                }
            }
        }
        if (SUPER_MAPPER_CACHE.containsKey(entityClazz)) {
            return (M) SUPER_MAPPER_CACHE.get(entityClazz);
        }
        throw new MybatisProviderLackError(entityClazz.getName() + " Mapper interface not found");
    }

    public void registerAsDefault() {
        MybatisMapperProviderInstance.instance(this);
    }

    private static class MybatisMapperProviderInstance {
        public static MybatisRecordProvider<? extends MybatisSuperMapper<?, ?> , ? extends IdEntity<?>,?> INSTANCE;

        @SuppressWarnings(value = "unchecked")
        private static <M extends MybatisSuperMapper<E, I>, E extends IdEntity<I>, I> MybatisRecordProvider<M, E, I> instance() {
            if (INSTANCE == null) {
                throw new MybatisProviderLackError("MybatisMapperProvider default instance not found");
            }
            return (MybatisRecordProvider<M, E, I>) INSTANCE;
        }

        private static <M extends MybatisSuperMapper<E, I>, E extends IdEntity<I>, I> void instance(MybatisRecordProvider<M, E, I> instance) {
            MybatisMapperProviderInstance.INSTANCE = instance;
        }
    }
}
