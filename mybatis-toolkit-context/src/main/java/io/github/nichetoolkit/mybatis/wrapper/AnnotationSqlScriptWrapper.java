package io.github.nichetoolkit.mybatis.wrapper;

import io.github.nichetoolkit.mybatis.MybatisOrder;
import io.github.nichetoolkit.mybatis.MybatisSqlScript;
import io.github.nichetoolkit.mybatis.MybatisSqlScriptWrapper;
import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.error.MybatisSqlScriptLackError;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <code>AnnotationSqlScriptWrapper</code>
 * <p>The type annotation sql script wrapper class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisSqlScriptWrapper
 * @since Jdk1.8
 */
public class AnnotationSqlScriptWrapper implements MybatisSqlScriptWrapper {

    @Override
    public MybatisSqlScript wrap(ProviderContext context, MybatisTable table, MybatisSqlScript sqlScript) {
        Class<?> mapperType = context.getMapperType();
        Method mapperMethod = context.getMapperMethod();
        /** 接口注解 */
        List<AnnotationSqlWrapper> wrappers = parseAnnotations(mapperType, ElementType.TYPE, mapperType.getAnnotations());
        /** 方法注解 */
        wrappers.addAll(parseAnnotations(mapperMethod, ElementType.METHOD, mapperMethod.getAnnotations()));
        /** 参数注解 */
        Parameter[] parameters = mapperMethod.getParameters();
        Annotation[][] parameterAnnotations = mapperMethod.getParameterAnnotations();
        for (int i = 0; i < parameters.length; i++) {
            wrappers.addAll(parseAnnotations(parameters[i], ElementType.PARAMETER, parameterAnnotations[i]));
        }
        /** 去重，排序 */
        wrappers = wrappers.stream().distinct()
                .sorted(Comparator.comparing(wrapper -> ((MybatisOrder) wrapper).getOrder()).reversed())
                .collect(Collectors.toList());
        for (MybatisSqlScriptWrapper wrapper : wrappers) {
            sqlScript = wrapper.wrap(context, table, sqlScript);
        }
        return sqlScript;
    }

    /**
     * <code>parseAnnotations</code>
     * <p>the annotations method.</p>
     * @param target      {@link java.lang.Object} <p>the target parameter is <code>Object</code> type.</p>
     * @param type        {@link java.lang.annotation.ElementType} <p>the type parameter is <code>ElementType</code> type.</p>
     * @param annotations {@link java.lang.annotation.Annotation} <p>the annotations parameter is <code>Annotation</code> type.</p>
     * @return {@link java.util.List} <p>the annotations return object is <code>List</code> type.</p>
     * @see java.lang.Object
     * @see java.lang.annotation.ElementType
     * @see java.lang.annotation.Annotation
     * @see java.util.List
     */
    private List<AnnotationSqlWrapper> parseAnnotations(Object target, ElementType type, Annotation[] annotations) {
        List<Class<? extends AnnotationSqlWrapper>> classes = new ArrayList<>();
        for (int i = 0; i < annotations.length; i++) {
            Annotation annotation = annotations[i];
            Class<? extends Annotation> annotationType = annotation.annotationType();
            if (annotationType == RestSqlWrapper.class) {
                classes.addAll(Arrays.asList(((RestSqlWrapper) annotation).value()));
            } else if (annotationType.isAnnotationPresent(RestSqlWrapper.class)) {
                RestSqlWrapper annotationTypeAnnotation = AnnotationUtils.getAnnotation(annotationType, RestSqlWrapper.class);
                if (GeneralUtils.isNotEmpty(annotationTypeAnnotation) && GeneralUtils.isNotEmpty(annotationTypeAnnotation.value())) {
                    classes.addAll(Arrays.asList(annotationTypeAnnotation.value()));
                }
            }
        }
        return classes.stream().map(c -> (AnnotationSqlWrapper) newInstance(c, target, type, annotations))
                .collect(Collectors.toList());
    }

    /**
     * <code>newInstance</code>
     * <p>the instance method.</p>
     * @param <T>           {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param instanceClass {@link java.lang.Class} <p>the instance class parameter is <code>Class</code> type.</p>
     * @param target        {@link java.lang.Object} <p>the target parameter is <code>Object</code> type.</p>
     * @param type          {@link java.lang.annotation.ElementType} <p>the type parameter is <code>ElementType</code> type.</p>
     * @param annotations   {@link java.lang.annotation.Annotation} <p>the annotations parameter is <code>Annotation</code> type.</p>
     * @return T <p>the instance return object is <code>T</code> type.</p>
     * @see java.lang.Class
     * @see java.lang.Object
     * @see java.lang.annotation.ElementType
     * @see java.lang.annotation.Annotation
     */
    public <T> T newInstance(Class<T> instanceClass, Object target, ElementType type, Annotation[] annotations) {
        try {
            return instanceClass.getConstructor(Object.class, ElementType.class, Annotation[].class).newInstance(target, type, annotations);
        } catch (Exception e) {
            throw new MybatisSqlScriptLackError("instance [ " + instanceClass + " ] error", e);
        }
    }
}
