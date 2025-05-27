package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.error.MybatisProviderLackError;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rice.IdEntity;
import io.github.nichetoolkit.rice.mapper.SuperMapper;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class MybatisMapperFactory<M extends SuperMapper<E, I>, E extends IdEntity<I>, I> implements ApplicationContextAware, ApplicationListener<ContextRefreshedEvent> {

    protected final Map<Class<?>, SuperMapper<E, I>> SUPER_MAPPER_CACHE = new ConcurrentHashMap<>();

    protected static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        MybatisMapperFactory.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
        registryMappers();
    }

    public static <M extends SuperMapper<E, I>, E extends IdEntity<I>, I> MybatisMapperFactory<M, E, I> defaultInstance() {
        return MybatisMapperFactoryInstance.instance();
    }

    @SuppressWarnings(value = "unchecked")
    public static <M extends SuperMapper<?, ?>> MybatisMapperFactory<M, ?, ?> instanceOfBean() {
        return (MybatisMapperFactory<M, ?, ?>) applicationContext.getBean(MybatisMapperFactory.class);
    }

    @SuppressWarnings(value = "unchecked")
    public static <M extends SuperMapper<E, I>, E extends IdEntity<I>, I> MybatisMapperFactory<M, E, I> getInstance(String instanceName) {
        return (MybatisMapperFactory<M, E, I>) applicationContext.getBean(instanceName);
    }

    abstract protected void registryMappers();

    @SuppressWarnings(value = "unchecked")
    protected void cacheMapper(Class<?> mapperClass, Object mapper) {
        if (GeneralUtils.isEmpty(mapperClass) || GeneralUtils.isEmpty(mapper)) {
            return;
        }
        if (MybatisMapper.class.isAssignableFrom(mapperClass)) {
            MybatisMapper<?> mybatisMapper = (MybatisMapper<?>) mapper;
            mybatisMapper.mapperType();
            mybatisMapper.entityType();
        }
        if (SuperMapper.class.isAssignableFrom(mapperClass)) {
            Class<?> entityClass = MybatisClassFinder.findEntityClass(mapperClass, null);
            if (GeneralUtils.isNotNull(entityClass) && !SUPER_MAPPER_CACHE.containsKey(entityClass)) {
                SUPER_MAPPER_CACHE.put(entityClass, (SuperMapper<E, I>) mapper);
            }
        }
    }


    @SuppressWarnings(value = "unchecked")
    public M superMapper(Class<?> entityClazz) {
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
        MybatisMapperFactoryInstance.instance(this);
    }

    private static class MybatisMapperFactoryInstance {
        public static MybatisMapperFactory<? extends SuperMapper<?, ?> , ? extends IdEntity<?>,?> INSTANCE;

        @SuppressWarnings(value = "unchecked")
        private static <M extends SuperMapper<E, I>, E extends IdEntity<I>, I> MybatisMapperFactory<M, E, I> instance() {
            if (INSTANCE == null) {
                throw new MybatisProviderLackError("MybatisMapperProvider default instance not found");
            }
            return (MybatisMapperFactory<M, E, I>) INSTANCE;
        }

        private static <M extends SuperMapper<E, I>, E extends IdEntity<I>, I> void instance(MybatisMapperFactory<M, E, I> instance) {
            MybatisMapperFactoryInstance.INSTANCE = instance;
        }
    }
}
