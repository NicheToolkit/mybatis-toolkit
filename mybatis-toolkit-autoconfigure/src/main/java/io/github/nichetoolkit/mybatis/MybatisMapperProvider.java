package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.mapper.MybatisSuperMapper;
import io.github.nichetoolkit.rest.error.lack.BeanLackError;
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
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>MybatisMapperProvider</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public class MybatisMapperProvider<M extends MybatisSuperMapper<E, I>, E extends IdEntity<I>, I> implements ApplicationContextAware, ApplicationListener<ContextRefreshedEvent> {


    protected final Map<Class<?>, MybatisSuperMapper<E, I>> SUPER_MAPPER_CACHE = new ConcurrentHashMap<>();

    protected static ApplicationContext applicationContext;

    protected SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    public MybatisMapperProvider(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        MybatisMapperProvider.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
        registryMappers();
    }

    public static <M extends MybatisSuperMapper<E, I>, E extends IdEntity<I>, I> MybatisMapperProvider<M, E, I> defaultInstance() {
        return MybatisMapperProviderInstance.instance();
    }

    @SuppressWarnings(value = "unchecked")
    public static <M extends MybatisSuperMapper<E, I>, E extends IdEntity<I>, I> MybatisMapperProvider<M, E, I> getInstance(String instanceName) {
        return (MybatisMapperProvider<M, E, I>) applicationContext.getBean(instanceName);
    }

    protected void registryMappers() {
        MapperRegistry mapperRegistry = this.sqlSessionTemplate.getConfiguration().getMapperRegistry();
        mapperRegistry.getMappers().forEach(mapper -> cacheMapper(mapper, this.sqlSessionTemplate.getMapper(mapper)));
    }

    @SuppressWarnings(value = "unchecked")
    public void cacheMapper(Class<?> entityClazz, Object mapper) {
        if (GeneralUtils.isEmpty(entityClazz) && GeneralUtils.isEmpty(mapper)) {
            return;
        }
        if (MybatisSuperMapper.class.isAssignableFrom(entityClazz)) {
            Optional<Class<?>> optionalClass = MybatisClassFinder.findClass(entityClazz, null);
            if (optionalClass.isPresent() && !SUPER_MAPPER_CACHE.containsKey(optionalClass.get())) {
                SUPER_MAPPER_CACHE.put(optionalClass.get(), (MybatisSuperMapper<E, I>) mapper);
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
        throw new BeanLackError(entityClazz.getName() + " Mapper interface not found");
    }

    public void registerAsDefault() {
        MybatisMapperProviderInstance.instance(this);
    }

    private static class MybatisMapperProviderInstance {
        public static MybatisMapperProvider INSTANCE;

        @SuppressWarnings(value = "unchecked")
        private static <M extends MybatisSuperMapper<E, I>, E extends IdEntity<I>, I> MybatisMapperProvider<M, E, I> instance() {
            if (INSTANCE == null) {
                throw new BeanLackError("MybatisMapperProvider default instance not found");
            }
            return INSTANCE;
        }

        private static <M extends MybatisSuperMapper<E, I>, E extends IdEntity<I>, I> void instance(MybatisMapperProvider<M, E, I> instance) {
            MybatisMapperProviderInstance.INSTANCE = instance;
        }
    }
}
