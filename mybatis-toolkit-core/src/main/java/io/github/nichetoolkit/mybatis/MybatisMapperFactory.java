package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.error.MybatisProviderLackError;
import io.github.nichetoolkit.rest.holder.ApplicationContextHolder;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rice.IdEntity;
import io.github.nichetoolkit.rice.mapper.SuperMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <code>MybatisMapperFactory</code>
 * <p>The mybatis mapper factory class.</p>
 * @param <M> {@link io.github.nichetoolkit.rice.mapper.SuperMapper} <p>The generic parameter is <code>SuperMapper</code> type.</p>
 * @param <E> {@link io.github.nichetoolkit.rice.IdEntity} <p>The generic parameter is <code>IdEntity</code> type.</p>
 * @param <I> {@link java.lang.Object} <p>The parameter can be of any type.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.mapper.SuperMapper
 * @see io.github.nichetoolkit.rice.IdEntity
 * @see org.springframework.context.ApplicationContextAware
 * @see org.springframework.context.ApplicationListener
 * @see lombok.extern.slf4j.Slf4j
 * @since Jdk1.8
 */
@Slf4j
public abstract class MybatisMapperFactory<M extends SuperMapper<E, I>, E extends IdEntity<I>, I> implements ApplicationContextAware, ApplicationListener<ContextRefreshedEvent> {

    /**
     * <code>SUPER_MAPPER_CACHE</code>
     * {@link java.util.Map} <p>The <code>SUPER_MAPPER_CACHE</code> field.</p>
     * @see java.util.Map
     */
    protected final Map<Class<?>, SuperMapper<E, I>> SUPER_MAPPER_CACHE = new ConcurrentHashMap<>();

    /**
     * <code>applicationContext</code>
     * {@link org.springframework.context.ApplicationContext} <p>The constant <code>applicationContext</code> field.</p>
     * @see org.springframework.context.ApplicationContext
     */
    protected static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        MybatisMapperFactory.applicationContext = applicationContext;
        log.debug("The application context of mybatis mapper factory applicationContext initialized!");
    }

    @Override
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
        registryMappers();
    }

    /**
     * <code>applicationContext</code>
     * <p>The application context method.</p>
     * @return {@link org.springframework.context.ApplicationContext} <p>The application context return object is <code>ApplicationContext</code> type.</p>
     * @see org.springframework.context.ApplicationContext
     */
    private static ApplicationContext applicationContext() {
        if (applicationContext == null) {
            applicationContext = ApplicationContextHolder.getApplicationContext();
        }
        return applicationContext;
    }

    /**
     * <code>defaultInstance</code>
     * <p>The default instance method.</p>
     * @param <M> {@link io.github.nichetoolkit.rice.mapper.SuperMapper} <p>The generic parameter is <code>SuperMapper</code> type.</p>
     * @param <E> {@link io.github.nichetoolkit.rice.IdEntity} <p>The generic parameter is <code>IdEntity</code> type.</p>
     * @param <I> {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.MybatisMapperFactory} <p>The default instance return object is <code>MybatisMapperFactory</code> type.</p>
     * @see io.github.nichetoolkit.rice.mapper.SuperMapper
     * @see io.github.nichetoolkit.rice.IdEntity
     */
    public static <M extends SuperMapper<E, I>, E extends IdEntity<I>, I> MybatisMapperFactory<M, E, I> defaultInstance() {
        return MybatisMapperFactoryInstance.instance();
    }

    /**
     * <code>instanceOfBean</code>
     * <p>The instance of bean method.</p>
     * @param <M> {@link io.github.nichetoolkit.rice.mapper.SuperMapper} <p>The generic parameter is <code>SuperMapper</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.MybatisMapperFactory} <p>The instance of bean return object is <code>MybatisMapperFactory</code> type.</p>
     * @see io.github.nichetoolkit.rice.mapper.SuperMapper
     * @see java.lang.SuppressWarnings
     */
    @SuppressWarnings(value = "unchecked")
    public static <M extends SuperMapper<?, ?>> MybatisMapperFactory<M, ?, ?> instanceOfBean() {
        ApplicationContext applicationContext1 = applicationContext();
        return (MybatisMapperFactory<M, ?, ?>) applicationContext1.getBean(MybatisMapperFactory.class);
    }

    /**
     * <code>getInstance</code>
     * <p>The get instance getter method.</p>
     * @param <M>          {@link io.github.nichetoolkit.rice.mapper.SuperMapper} <p>The generic parameter is <code>SuperMapper</code> type.</p>
     * @param <E>          {@link io.github.nichetoolkit.rice.IdEntity} <p>The generic parameter is <code>IdEntity</code> type.</p>
     * @param <I>          {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param instanceName {@link java.lang.String} <p>The instance name parameter is <code>String</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.MybatisMapperFactory} <p>The get instance return object is <code>MybatisMapperFactory</code> type.</p>
     * @see io.github.nichetoolkit.rice.mapper.SuperMapper
     * @see io.github.nichetoolkit.rice.IdEntity
     * @see java.lang.String
     * @see java.lang.SuppressWarnings
     */
    @SuppressWarnings(value = "unchecked")
    public static <M extends SuperMapper<E, I>, E extends IdEntity<I>, I> MybatisMapperFactory<M, E, I> getInstance(String instanceName) {
        return (MybatisMapperFactory<M, E, I>) applicationContext().getBean(instanceName);
    }

    /**
     * <code>registryMappers</code>
     * <p>The registry mappers method.</p>
     */
    abstract public void registryMappers();

    /**
     * <code>cacheMapper</code>
     * <p>The cache mapper method.</p>
     * @param mapperClass {@link java.lang.Class} <p>The mapper class parameter is <code>Class</code> type.</p>
     * @param mapper      {@link java.lang.Object} <p>The mapper parameter is <code>Object</code> type.</p>
     * @see java.lang.Class
     * @see java.lang.Object
     * @see java.lang.SuppressWarnings
     */
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


    /**
     * <code>superMapper</code>
     * <p>The super mapper method.</p>
     * @param entityClazz {@link java.lang.Class} <p>The entity clazz parameter is <code>Class</code> type.</p>
     * @return M <p>The super mapper return object is <code>M</code> type.</p>
     * @see java.lang.Class
     * @see java.lang.SuppressWarnings
     */
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

    /**
     * <code>registerAsDefault</code>
     * <p>The register as default method.</p>
     */
    public void registerAsDefault() {
        MybatisMapperFactoryInstance.instance(this);
    }

    /**
     * <code>MybatisMapperFactoryInstance</code>
     * <p>The mybatis mapper factory instance class.</p>
     * @author Cyan (snow22314@outlook.com)
     * @since Jdk1.8
     */
    private static class MybatisMapperFactoryInstance {
        /**
         * <code>INSTANCE</code>
         * {@link io.github.nichetoolkit.mybatis.MybatisMapperFactory} <p>The constant <code>INSTANCE</code> field.</p>
         */
        public static MybatisMapperFactory<? extends SuperMapper<?, ?> , ? extends IdEntity<?>,?> INSTANCE;

        /**
         * <code>instance</code>
         * <p>The instance method.</p>
         * @param <M> {@link io.github.nichetoolkit.rice.mapper.SuperMapper} <p>The generic parameter is <code>SuperMapper</code> type.</p>
         * @param <E> {@link io.github.nichetoolkit.rice.IdEntity} <p>The generic parameter is <code>IdEntity</code> type.</p>
         * @param <I> {@link java.lang.Object} <p>The parameter can be of any type.</p>
         * @return {@link io.github.nichetoolkit.mybatis.MybatisMapperFactory} <p>The instance return object is <code>MybatisMapperFactory</code> type.</p>
         * @see io.github.nichetoolkit.rice.mapper.SuperMapper
         * @see io.github.nichetoolkit.rice.IdEntity
         * @see java.lang.SuppressWarnings
         */
        @SuppressWarnings(value = "unchecked")
        private static <M extends SuperMapper<E, I>, E extends IdEntity<I>, I> MybatisMapperFactory<M, E, I> instance() {
            if (INSTANCE == null) {
                throw new MybatisProviderLackError("MybatisMapperProvider default instance not found");
            }
            return (MybatisMapperFactory<M, E, I>) INSTANCE;
        }

        /**
         * <code>instance</code>
         * <p>The instance method.</p>
         * @param <M>      {@link io.github.nichetoolkit.rice.mapper.SuperMapper} <p>The generic parameter is <code>SuperMapper</code> type.</p>
         * @param <E>      {@link io.github.nichetoolkit.rice.IdEntity} <p>The generic parameter is <code>IdEntity</code> type.</p>
         * @param <I>      {@link java.lang.Object} <p>The parameter can be of any type.</p>
         * @param instance {@link io.github.nichetoolkit.mybatis.MybatisMapperFactory} <p>The instance parameter is <code>MybatisMapperFactory</code> type.</p>
         * @see io.github.nichetoolkit.rice.mapper.SuperMapper
         * @see io.github.nichetoolkit.rice.IdEntity
         */
        private static <M extends SuperMapper<E, I>, E extends IdEntity<I>, I> void instance(MybatisMapperFactory<M, E, I> instance) {
            MybatisMapperFactoryInstance.INSTANCE = instance;
        }
    }
}
