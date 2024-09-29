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

    public <T> T newInstance(Class<T> instanceClass, Object target, ElementType type, Annotation[] annotations) {
        try {
            return instanceClass.getConstructor(Object.class, ElementType.class, Annotation[].class).newInstance(target, type, annotations);
        } catch (Exception e) {
            throw new MybatisSqlScriptLackError("instance [ " + instanceClass + " ] error", e);
        }
    }
}
