package io.github.nichetoolkit.mybatis.wrapper;

import io.github.nichetoolkit.mybatis.MybatisOrder;
import io.github.nichetoolkit.mybatis.MybatisSqlScript;
import io.github.nichetoolkit.mybatis.MybatisSqlScriptWrapper;
import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.rest.error.lack.BeanLackError;
import io.github.nichetoolkit.rest.error.lack.InterfaceLackError;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rice.stereotype.mybatis.table.RestExcludes;
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
 * <p>AnnotationSqlScriptWrapper</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
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
     * 获取对象上的 MybatisAnnotationSqlWrapper 实例
     * @param target      Object
     * @param type        ElementType
     * @param annotations Annotation[]
     * @return List<MybatisAnnotationSqlWrapper>
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
     * 实例化 AbstractSqlScriptWrapper 对象
     * @param instanceClass 实例类型
     * @param target        对象目标
     * @param type          类型
     * @param annotations   注解
     * @param <T>           实例泛型
     * @return T 实例对象
     */
    public <T> T newInstance(Class<T> instanceClass, Object target, ElementType type, Annotation[] annotations) {
        try {
            return instanceClass.getConstructor(Object.class, ElementType.class, Annotation[].class).newInstance(target, type, annotations);
        } catch (Exception e) {
            throw new BeanLackError("instance [ " + instanceClass + " ] error", e);
        }
    }
}
