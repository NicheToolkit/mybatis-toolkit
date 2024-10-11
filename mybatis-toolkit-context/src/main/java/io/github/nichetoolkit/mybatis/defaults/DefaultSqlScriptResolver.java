package io.github.nichetoolkit.mybatis.defaults;

import io.github.nichetoolkit.mybatis.*;
import io.github.nichetoolkit.mybatis.error.MybatisSqlScriptLackError;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class DefaultSqlScriptResolver implements MybatisSqlScriptResolver {

    @Override
    public MybatisSqlScript resolve(ProviderContext context, MybatisTable table, MybatisSqlScript sqlScript) {
        Class<?> mapperType = context.getMapperType();
        Method mapperMethod = context.getMapperMethod();
        /* 接口注解 */
        List<DefaultSqlResolver> resolvers = parseAnnotations(mapperType, ElementType.TYPE, mapperType.getAnnotations());
        /* 方法注解 */
        resolvers.addAll(parseAnnotations(mapperMethod, ElementType.METHOD, mapperMethod.getAnnotations()));
        /* 参数注解 */
        Parameter[] parameters = mapperMethod.getParameters();
        Annotation[][] parameterAnnotations = mapperMethod.getParameterAnnotations();
        for (int i = 0; i < parameters.length; i++) {
            resolvers.addAll(parseAnnotations(parameters[i], ElementType.PARAMETER, parameterAnnotations[i]));
        }
        /* 去重，排序 */
        resolvers = resolvers.stream().distinct()
                .sorted(Comparator.comparing(wrapper -> ((MybatisOrder) wrapper).getOrder()).reversed())
                .collect(Collectors.toList());
        for (MybatisSqlScriptResolver resolver : resolvers) {
            sqlScript = resolver.resolve(context, table, sqlScript);
        }
        return sqlScript;
    }

    private List<DefaultSqlResolver> parseAnnotations(Object target, ElementType type, Annotation[] annotations) {
        List<Class<? extends DefaultSqlResolver>> classes = new ArrayList<>();
        for (Annotation annotation : annotations) {
            Class<? extends Annotation> annotationType = annotation.annotationType();
            if (annotationType == MybatisSqlResolver.class) {
                classes.addAll(Arrays.asList(((MybatisSqlResolver) annotation).value()));
            } else if (annotationType.isAnnotationPresent(MybatisSqlResolver.class)) {
                MybatisSqlResolver annotationTypeAnnotation = AnnotationUtils.getAnnotation(annotationType, MybatisSqlResolver.class);
                if (GeneralUtils.isNotEmpty(annotationTypeAnnotation) && GeneralUtils.isNotEmpty(annotationTypeAnnotation.value())) {
                    classes.addAll(Arrays.asList(annotationTypeAnnotation.value()));
                }
            }
        }
        return classes.stream().map(c -> (DefaultSqlResolver) newInstance(c, target, type, annotations))
                .collect(Collectors.toList());
    }

    public <T> T newInstance(Class<T> instanceClass, Object target, ElementType type, Annotation[] annotations) {
        try {
            return instanceClass.getConstructor(Object.class, ElementType.class, Annotation[].class).newInstance(target, type, annotations);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException exception) {
            GeneralUtils.printStackTrace(log,exception,true);
            String message = "the sql script of '" + instanceClass + "' type to instance has error, " + exception.getMessage();
            throw new MybatisSqlScriptLackError(message, exception);
        }
    }
}
