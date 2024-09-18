package io.github.nichetoolkit.mybatis.record;

import io.github.nichetoolkit.mybatis.MybatisClassFinder;
import io.github.nichetoolkit.mybatis.error.MybatisProviderLackError;
import io.github.nichetoolkit.mybatis.MybatisSuperMapper;
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
 * <code>MybatisRecordProvider</code>
 * <p>The type mybatis record provider class.</p>
 * @param <M> {@link MybatisSuperMapper} <p>the generic parameter is <code>MybatisSuperMapper</code> type.</p>
 * @param <E> {@link io.github.nichetoolkit.rice.IdEntity} <p>the generic parameter is <code>IdEntity</code> type.</p>
 * @param <I> {@link java.lang.Object} <p>the parameter can be of any type.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see MybatisSuperMapper
 * @see io.github.nichetoolkit.rice.IdEntity
 * @see org.springframework.context.ApplicationContextAware
 * @see org.springframework.context.ApplicationListener
 * @since Jdk1.8
 */
public class MybatisRecordProvider<M extends MybatisSuperMapper<E, I>, E extends IdEntity<I>, I> implements ApplicationContextAware, ApplicationListener<ContextRefreshedEvent> {

    /**
     * <code>SUPER_MAPPER_CACHE</code>
     * {@link java.util.Map} <p>the <code>SUPER_MAPPER_CACHE</code> field.</p>
     * @see java.util.Map
     */
    protected final Map<Class<?>, MybatisSuperMapper<E, I>> SUPER_MAPPER_CACHE = new ConcurrentHashMap<>();

    /**
     * <code>applicationContext</code>
     * {@link org.springframework.context.ApplicationContext} <p>the constant <code>applicationContext</code> field.</p>
     * @see org.springframework.context.ApplicationContext
     */
    protected static ApplicationContext applicationContext;

    /**
     * <code>sqlSessionTemplate</code>
     * {@link org.mybatis.spring.SqlSessionTemplate} <p>the <code>sqlSessionTemplate</code> field.</p>
     * @see org.mybatis.spring.SqlSessionTemplate
     */
    protected SqlSessionTemplate sqlSessionTemplate;

    /**
     * <code>MybatisRecordProvider</code>
     * Instantiates a new mybatis record provider.
     * @param sqlSessionTemplate {@link org.mybatis.spring.SqlSessionTemplate} <p>the sql session template parameter is <code>SqlSessionTemplate</code> type.</p>
     * @see org.mybatis.spring.SqlSessionTemplate
     * @see org.springframework.beans.factory.annotation.Autowired
     */
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

    /**
     * <code>defaultInstance</code>
     * <p>the instance method.</p>
     * @param <M> {@link MybatisSuperMapper} <p>the generic parameter is <code>MybatisSuperMapper</code> type.</p>
     * @param <E> {@link io.github.nichetoolkit.rice.IdEntity} <p>the generic parameter is <code>IdEntity</code> type.</p>
     * @param <I> {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.record.MybatisRecordProvider} <p>the instance return object is <code>MybatisRecordProvider</code> type.</p>
     * @see MybatisSuperMapper
     * @see io.github.nichetoolkit.rice.IdEntity
     */
    public static <M extends MybatisSuperMapper<E, I>, E extends IdEntity<I>, I> MybatisRecordProvider<M, E, I> defaultInstance() {
        return MybatisMapperProviderInstance.instance();
    }

    /**
     * <code>getInstance</code>
     * <p>the instance getter method.</p>
     * @param <M>          {@link MybatisSuperMapper} <p>the generic parameter is <code>MybatisSuperMapper</code> type.</p>
     * @param <E>          {@link io.github.nichetoolkit.rice.IdEntity} <p>the generic parameter is <code>IdEntity</code> type.</p>
     * @param <I>          {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param instanceName {@link java.lang.String} <p>the instance name parameter is <code>String</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.record.MybatisRecordProvider} <p>the instance return object is <code>MybatisRecordProvider</code> type.</p>
     * @see MybatisSuperMapper
     * @see io.github.nichetoolkit.rice.IdEntity
     * @see java.lang.String
     * @see java.lang.SuppressWarnings
     */
    @SuppressWarnings(value = "unchecked")
    public static <M extends MybatisSuperMapper<E, I>, E extends IdEntity<I>, I> MybatisRecordProvider<M, E, I> getInstance(String instanceName) {
        return (MybatisRecordProvider<M, E, I>) applicationContext.getBean(instanceName);
    }

    /**
     * <code>registryMappers</code>
     * <p>the mappers method.</p>
     */
    protected void registryMappers() {
        MapperRegistry mapperRegistry = this.sqlSessionTemplate.getConfiguration().getMapperRegistry();
        mapperRegistry.getMappers().forEach(mapper -> cacheMapper(mapper, this.sqlSessionTemplate.getMapper(mapper)));
    }

    /**
     * <code>cacheMapper</code>
     * <p>the mapper method.</p>
     * @param entityClazz {@link java.lang.Class} <p>the entity clazz parameter is <code>Class</code> type.</p>
     * @param mapper      {@link java.lang.Object} <p>the mapper parameter is <code>Object</code> type.</p>
     * @see java.lang.Class
     * @see java.lang.Object
     * @see java.lang.SuppressWarnings
     */
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

    /**
     * <code>superMapper</code>
     * <p>the mapper method.</p>
     * @param entityClazz {@link java.lang.Class} <p>the entity clazz parameter is <code>Class</code> type.</p>
     * @return M <p>the mapper return object is <code>M</code> type.</p>
     * @see java.lang.Class
     * @see java.lang.SuppressWarnings
     */
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

    /**
     * <code>registerAsDefault</code>
     * <p>the as default method.</p>
     */
    public void registerAsDefault() {
        MybatisMapperProviderInstance.instance(this);
    }

    /**
     * <code>MybatisMapperProviderInstance</code>
     * <p>The type mybatis mapper provider instance class.</p>
     * @author Cyan (snow22314@outlook.com)
     * @since Jdk1.8
     */
    private static class MybatisMapperProviderInstance {
        /**
         * <code>INSTANCE</code>
         * {@link io.github.nichetoolkit.mybatis.record.MybatisRecordProvider} <p>the constant <code>INSTANCE</code> field.</p>
         */
        public static MybatisRecordProvider<? extends MybatisSuperMapper<?, ?> , ? extends IdEntity<?>,?> INSTANCE;

        /**
         * <code>instance</code>
         * <p>the method.</p>
         * @param <M> {@link MybatisSuperMapper} <p>the generic parameter is <code>MybatisSuperMapper</code> type.</p>
         * @param <E> {@link io.github.nichetoolkit.rice.IdEntity} <p>the generic parameter is <code>IdEntity</code> type.</p>
         * @param <I> {@link java.lang.Object} <p>the parameter can be of any type.</p>
         * @return {@link io.github.nichetoolkit.mybatis.record.MybatisRecordProvider} <p>the return object is <code>MybatisRecordProvider</code> type.</p>
         * @see MybatisSuperMapper
         * @see io.github.nichetoolkit.rice.IdEntity
         * @see java.lang.SuppressWarnings
         */
        @SuppressWarnings(value = "unchecked")
        private static <M extends MybatisSuperMapper<E, I>, E extends IdEntity<I>, I> MybatisRecordProvider<M, E, I> instance() {
            if (INSTANCE == null) {
                throw new MybatisProviderLackError("MybatisMapperProvider default instance not found");
            }
            return (MybatisRecordProvider<M, E, I>) INSTANCE;
        }

        /**
         * <code>instance</code>
         * <p>the method.</p>
         * @param <M>      {@link MybatisSuperMapper} <p>the generic parameter is <code>MybatisSuperMapper</code> type.</p>
         * @param <E>      {@link io.github.nichetoolkit.rice.IdEntity} <p>the generic parameter is <code>IdEntity</code> type.</p>
         * @param <I>      {@link java.lang.Object} <p>the parameter can be of any type.</p>
         * @param instance {@link io.github.nichetoolkit.mybatis.record.MybatisRecordProvider} <p>the instance parameter is <code>MybatisRecordProvider</code> type.</p>
         * @see MybatisSuperMapper
         * @see io.github.nichetoolkit.rice.IdEntity
         */
        private static <M extends MybatisSuperMapper<E, I>, E extends IdEntity<I>, I> void instance(MybatisRecordProvider<M, E, I> instance) {
            MybatisMapperProviderInstance.INSTANCE = instance;
        }
    }
}
